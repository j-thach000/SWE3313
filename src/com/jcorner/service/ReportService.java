package com.jcorner.service;

import com.jcorner.data.DataStore;
import com.jcorner.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

// generates the reports required by the project overview:
// revenue per item, item popularity, average turnaround time,
// average preparation time, and personnel efficiency

public class ReportService {

    public record DailySummary(int totalOrders, double totalRevenue, double taxCollected,
                               Map<String, Double> paymentBreakdown,
                               List<Map.Entry<String, Integer>> topItems,
                               double avgOrderValue) {}

    // returns a high-level daily summary used by the sample Daily Sales Report.
    public static DailySummary dailySummary() {
        LocalDateTime start = LocalDateTime.now().toLocalDate().atStartOfDay();
        List<FoodOrder> served = DataStore.get().orders().values().stream()
                .filter(o -> o.getStatus() == OrderStatus.SERVED
                        && o.getClosedTime() != null
                        && o.getClosedTime().isAfter(start))
                .toList();

        int totalOrders = served.size();
        double totalRevenue = served.stream().mapToDouble(FoodOrder::getGrandTotal).sum();
        double taxCollected = served.stream().mapToDouble(FoodOrder::getTax).sum();

        Map<String, Double> paymentBreakdown = new LinkedHashMap<>();
        paymentBreakdown.put("CASH", 0.0);
        paymentBreakdown.put("CARD", 0.0);
        for (FoodOrder o : served) {
            String pm = o.getPaymentMethod() == null ? "CASH" : o.getPaymentMethod();
            paymentBreakdown.merge(pm, o.getGrandTotal(), Double::sum);
        }

        // top items by count
        Map<String, Integer> counts = new HashMap<>();
        for (FoodOrder o : served) {
            for (OrderItem i : o.getItems()) {
                counts.merge(i.getMenuItem().getName(), i.getQuantity(), Integer::sum);
            }
        }
        List<Map.Entry<String, Integer>> top = counts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .toList();

        double avg = totalOrders == 0 ? 0 : totalRevenue / totalOrders;
        return new DailySummary(totalOrders, totalRevenue, taxCollected, paymentBreakdown, top, avg);
    }

    // revenue per menu item across all completed orders. 
    public static Map<String, Double> revenueByItem() {
        Map<String, Double> result = new LinkedHashMap<>();
        for (FoodOrder o : completedOrders()) {
            for (OrderItem i : o.getItems()) {
                result.merge(i.getMenuItem().getName(), i.getSubtotal(), Double::sum);
            }
        }
        return sortByValueDesc(result);
    }

    // number of times each menu item was ordered. 
    public static Map<String, Integer> itemPopularity() {
        Map<String, Integer> result = new LinkedHashMap<>();
        for (FoodOrder o : completedOrders()) {
            for (OrderItem i : o.getItems()) {
                result.merge(i.getMenuItem().getName(), i.getQuantity(), Integer::sum);
            }
        }
        return sortByValueDescInt(result);
    }

    // average prep time in minutes (orderTime -> readyTime). 
    public static double avgPrepTimeMinutes() {
        List<Long> secs = new ArrayList<>();
        for (FoodOrder o : DataStore.get().orders().values()) {
            if (o.getOrderTime() != null && o.getReadyTime() != null) {
                secs.add(Duration.between(o.getOrderTime(), o.getReadyTime()).toSeconds());
            }
        }
        return secs.isEmpty() ? 0 : secs.stream().mapToLong(Long::longValue).average().orElse(0) / 60.0;
    }

    // average turnaround in minutes (orderTime -> closedTime). 
    public static double avgTurnaroundMinutes() {
        List<Long> secs = new ArrayList<>();
        for (FoodOrder o : DataStore.get().orders().values()) {
            if (o.getOrderTime() != null && o.getClosedTime() != null) {
                secs.add(Duration.between(o.getOrderTime(), o.getClosedTime()).toSeconds());
            }
        }
        return secs.isEmpty() ? 0 : secs.stream().mapToLong(Long::longValue).average().orElse(0) / 60.0;
    }

    // per-employee metrics: number of orders handled & total revenue generated. 
    public static Map<String, double[]> personnelEfficiency() {
        Map<String, double[]> result = new LinkedHashMap<>();
        for (Employee e : DataStore.get().employees().values()) {
            result.put(e.getEmployeeID() + " " + e.getFullName(), new double[]{0, 0});
        }
        for (FoodOrder o : completedOrders()) {
            Employee e = DataStore.get().employees().get(o.getServerID());
            if (e == null) continue;
            String k = e.getEmployeeID() + " " + e.getFullName();
            double[] vals = result.get(k);
            vals[0] += 1;
            vals[1] += o.getGrandTotal();
        }
        return result;
    }

    private static List<FoodOrder> completedOrders() {
        return DataStore.get().orders().values().stream()
                .filter(o -> o.getStatus() == OrderStatus.SERVED)
                .toList();
    }

    private static Map<String, Double> sortByValueDesc(Map<String, Double> m) {
        return m.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), e.getValue()), Map::putAll);
    }

    private static Map<String, Integer> sortByValueDescInt(Map<String, Integer> m) {
        return m.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(LinkedHashMap::new, (map, e) -> map.put(e.getKey(), e.getValue()), Map::putAll);
    }
}
