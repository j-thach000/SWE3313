// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package com.jcorner.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FoodOrder {
   private final String orderID;
   private final String serverID;
   private final String tableID;
   private final LocalDateTime orderTime;
   private LocalDateTime readyTime;
   private LocalDateTime servedTime;
   private LocalDateTime closedTime;
   private OrderStatus status;
   private String paymentMethod;
   private int guestCount;
   private final List<OrderItem> items = new ArrayList();

   public FoodOrder(String var1, String var2, String var3, int var4) {
      this.orderID = var1;
      this.serverID = var2;
      this.tableID = var3;
      this.guestCount = var4;
      this.orderTime = LocalDateTime.now();
      this.status = OrderStatus.PENDING;
   }

   public String getOrderID() {
      return this.orderID;
   }

   public String getServerID() {
      return this.serverID;
   }

   public String getTableID() {
      return this.tableID;
   }

   public LocalDateTime getOrderTime() {
      return this.orderTime;
   }

   public LocalDateTime getReadyTime() {
      return this.readyTime;
   }

   public void setReadyTime(LocalDateTime var1) {
      this.readyTime = var1;
   }

   public LocalDateTime getServedTime() {
      return this.servedTime;
   }

   public void setServedTime(LocalDateTime var1) {
      this.servedTime = var1;
   }

   public LocalDateTime getClosedTime() {
      return this.closedTime;
   }

   public void setClosedTime(LocalDateTime var1) {
      this.closedTime = var1;
   }

   public OrderStatus getStatus() {
      return this.status;
   }

   public void setStatus(OrderStatus var1) {
      this.status = var1;
   }

   public String getPaymentMethod() {
      return this.paymentMethod;
   }

   public void setPaymentMethod(String var1) {
      this.paymentMethod = var1;
   }

   public int getGuestCount() {
      return this.guestCount;
   }

   public void setGuestCount(int var1) {
      this.guestCount = var1;
   }

   public List<OrderItem> getItems() {
      return this.items;
   }

   public double getSubtotal() {
      return this.items.stream().mapToDouble(OrderItem::getSubtotal).sum();
   }

   public double getTax() {
      return (double)Math.round(this.getSubtotal() * 0.08 * (double)100.0F) / (double)100.0F;
   }

   public double getGrandTotal() {
      return (double)Math.round((this.getSubtotal() + this.getTax()) * (double)100.0F) / (double)100.0F;
   }

   public List<OrderItem> itemsForSeat(int var1) {
      return this.items.stream().filter((var1x) -> var1x.getSeatNumber() == var1).toList();
   }
}
