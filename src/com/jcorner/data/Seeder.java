package com.jcorner.data;

import com.jcorner.model.*;
import com.jcorner.model.RestaurantTable;

// populate DataStore w/ initial data scraped from project documents on D2L via LLM
public class Seeder {
    // populate the corresponding items
    public static void seed() {
        DataStore ds = DataStore.get();
        seedTables(ds);
        seedMenu(ds);
        seedEmployees(ds);
        seedInventory(ds);
    }

    private static void seedTables(DataStore ds) {
        String[] cols = {"A", "B", "C", "D", "E", "F"};
        for (String c : cols) {
            for (int r = 1; r <= 6; r++) {
                RestaurantTable t = new RestaurantTable(c, r, 4);
                ds.tables().put(t.getTableID(), t);
            }
        }
    }

    private static void seedMenu(DataStore ds) {
        MenuCategory appetizers = cat(ds, "CAT01", "Appetizers", 1);
        MenuCategory salads     = cat(ds, "CAT02", "Salads", 2);
        MenuCategory entrees    = cat(ds, "CAT03", "Entrees", 3);
        MenuCategory sides      = cat(ds, "CAT04", "Sides", 4);
        MenuCategory sandwiches = cat(ds, "CAT05", "Sandwiches", 5);
        MenuCategory burgers    = cat(ds, "CAT06", "Burgers", 6);
        MenuCategory beverages  = cat(ds, "CAT07", "Beverages", 7);

        // Appetizers
        item(ds, "APP01", "Chicken Nachos", 8.50,
                "Pulled chicken, spicy white cheese sauce & cheddar, topped with red onions & cilantro", appetizers);
        item(ds, "APP02", "Pork Nachos", 8.50,
                "Pulled pork, spicy white cheese sauce & pepper jack, topped with tomato, scallions & cilantro", appetizers);
        item(ds, "APP03", "Pork or Chicken Sliders", 5.00,
                "3 sliders with choice of chipotle, Jim Beam, or Carolina Gold BBQ sauce", appetizers);
        item(ds, "APP04", "Catfish Bites", 6.50,
                "Cornmeal-battered catfish pieces, fried and served with lemon & spicy cocktail sauce", appetizers);
        item(ds, "APP05", "Fried Veggies", 6.50,
                "Okra, zucchini, squash, or mix & match. Served with spicy ranch", appetizers);

        // Salads
        item(ds, "SAL01", "House Salad", 7.50,
                "Mixed greens, bacon, tomato & blue cheese crumbles", salads);
        item(ds, "SAL02", "Wedge Salad", 7.50,
                "Iceberg wedge, bacon, tomato & blue cheese crumbles", salads);
        item(ds, "SAL03", "Caesar Salad", 7.50,
                "Romaine, shredded Parmesan & croutons in Caesar dressing", salads);
        item(ds, "SAL04", "Sweet Potato Chicken Salad", 11.50,
                "Mixed greens, red onion, dried cranberries & goat cheese with sweet potato crusted chicken", salads);

        // Entrees
        item(ds, "ENT01", "Shrimp & Grits", 13.50,
                "Sauteed shrimp with garlic on cheese grits, topped with peppers & onions", entrees);
        item(ds, "ENT02", "Sweet Tea Fried Chicken", 11.50,
                "Fried chicken breast marinated in sweet tea & spices, topped with sweet tea reduction", entrees);
        item(ds, "ENT03", "Caribbean Chicken", 11.50,
                "Grilled chicken with spicy Caribbean seasoning, mango salsa & avocado", entrees);
        item(ds, "ENT04", "Grilled Pork Chops", 11.00,
                "Two bone-in grilled pork chops", entrees);
        item(ds, "ENT05", "New York Strip Steak", 17.00,
                "New York Strip cut in-house, cooked to temperature", entrees);
        item(ds, "ENT06", "Seared Tuna", 15.00,
                "Seared ahi tuna, topped with mango salsa & honey lime vinaigrette", entrees);
        item(ds, "ENT07", "Captain Crunch Chicken Tenders", 11.50,
                "Fried chicken tenders coated in Captain Crunch with dipping sauce", entrees);
        item(ds, "ENT08", "Shock Top Grouper Fingers", 11.50,
                "Shock Top beer-battered grouper with tartar sauce & lemon", entrees);
        item(ds, "ENT09", "Mac & Cheese Bar", 8.50,
                "Cast iron skillet of mac & cheese with choice of regular or spicy cheese & two toppings", entrees);

        // Sides
        for (String s : new String[]{"Curly Fries", "Wing Chips", "Sweet Potato Fries",
                "Creamy Cabbage Slaw", "Adluh Cheese Grits", "Mashed Potatoes",
                "Mac & Cheese", "Seasonal Vegetables", "Baked Beans"}) {
            String id = "SID" + String.format("%02d", sides.getItems().size() + 1);
            item(ds, id, s, 2.50, "Side dish", sides);
        }

        // Sandwiches
        item(ds, "SAN01", "Grilled Cheese", 5.50,
                "American cheese on multigrain or white bread", sandwiches);
        item(ds, "SAN02", "Chicken BLT&A", 10.00,
                "Grilled chicken, bacon, lettuce, tomato & avocado on a pretzel bun", sandwiches);
        item(ds, "SAN03", "Philly", 13.50,
                "Shaved NY strip or grilled chicken with mushrooms, peppers, onions & provolone on a hoagie", sandwiches);
        item(ds, "SAN04", "Club", 10.00,
                "Ham, turkey, swiss, cheddar, lettuce, tomato, mayo & bacon on multigrain", sandwiches);
        item(ds, "SAN05", "Meatball Sub", 10.00,
                "House meatballs, marinara & mozzarella. Peppers & onions on request", sandwiches);

        // Burgers
        item(ds, "BUR01", "Bacon Cheeseburger", 11.00,
                "8oz burger with bacon & choice of cheese on a brioche bun", burgers);
        item(ds, "BUR02", "Carolina Burger", 11.00,
                "8oz burger with chili, diced onions & slaw on a brioche bun", burgers);
        item(ds, "BUR03", "Portobello Burger (V)", 8.50,
                "Marinated portobello cap with mango salsa, lettuce, tomato & onion on telera bun", burgers);
        item(ds, "BUR04", "Vegan Boca Burger (V)", 10.50,
                "Boca burger with lettuce, tomato & onion on telera bun", burgers);

        // Beverages
        for (String b : new String[]{"Sweet Tea", "Unsweetened Tea", "Coke", "Diet Coke",
                "Sprite", "Bottled Water", "Lemonade", "Orange Juice"}) {
            String id = "BEV" + String.format("%02d", beverages.getItems().size() + 1);
            item(ds, id, b, 2.00, "Beverage", beverages);
        }
    }

    private static MenuCategory cat(DataStore ds, String id, String name, int order) {
        MenuCategory c = new MenuCategory(id, name, order);
        ds.categories().put(id, c);
        return c;
    }

    private static void item(DataStore ds, String id, String name, double price,
                             String desc, MenuCategory category) {
        MenuItem m = new MenuItem(id, name, price, desc, category);
        ds.menuItems().put(id, m);
    }

    private static void seedEmployees(DataStore ds) {
        // FAQ: 5 waiters, 2 busboys, 5 cooks, 1 manager
        // Simple passwords (easy to remember, pass the "not 1111/123456" check)
        addEmp(ds, "MG1001", "pass1001", "Pat", "Morgan",  Role.MANAGER);
        addEmp(ds, "WT2001", "pass2001", "Jessica", "Williams", Role.WAITER, new String[]{"A1","A2","B1","B2"});
        addEmp(ds, "WT2002", "pass2002", "Daniel",  "Brown",    Role.WAITER, new String[]{"A3","A4","B3","B4"});
        addEmp(ds, "WT2003", "pass2003", "Rachel",  "Garcia",   Role.WAITER, new String[]{"C1","C2","D1","D2"});
        addEmp(ds, "WT2004", "pass2004", "Marcus",  "Johnson",  Role.WAITER, new String[]{"C3","C4","D3","D4"});
        addEmp(ds, "WT2005", "pass2005", "Priya",   "Patel",    Role.WAITER, new String[]{"E1","E2","F1","F2"});
        addEmp(ds, "CK3001", "pass3001", "Tomas",  "Reyes",    Role.COOK);
        addEmp(ds, "CK3002", "pass3002", "Aiko",   "Tanaka",   Role.COOK);
        addEmp(ds, "CK3003", "pass3003", "Dmitri", "Volkov",   Role.COOK);
        addEmp(ds, "CK3004", "pass3004", "Naomi",  "Baker",    Role.COOK);
        addEmp(ds, "CK3005", "pass3005", "Hassan", "Ali",      Role.COOK);
        addEmp(ds, "BB4001", "pass4001", "Carlos", "Martinez", Role.BUSBOY);
        addEmp(ds, "BB4002", "pass4002", "Sophie", "Nguyen",   Role.BUSBOY);
    }

    private static void addEmp(DataStore ds, String id, String pw, String fn, String ln, Role r) {
        addEmp(ds, id, pw, fn, ln, r, new String[0]);
    }

    private static void addEmp(DataStore ds, String id, String pw, String fn, String ln, Role r, String[] tableIDs) {
        Employee e = new Employee(id, pw, fn, ln, r);
        e.setEmail((fn.toLowerCase() + "." + ln.toLowerCase() + "@jscorner.com"));
        e.setAddress("Marietta, GA");
        // All employees work Mon-Fri by default (bits 1-5)
        for (int d = 1; d <= 5; d++) e.setWorksDay(d, true);
        for (String tid : tableIDs) e.getAssignedTableIDs().add(tid);
        ds.employees().put(id, e);
    }

    private static void seedInventory(DataStore ds) {
        // From the sample Weekly Inventory Report in the design doc
        inv(ds, "INV01", "Chicken Breast",    42, "lbs",   10);
        inv(ds, "INV02", "Pulled Pork",       18, "lbs",    8);
        inv(ds, "INV03", "NY Strip Steak",    11, "lbs",   15);
        inv(ds, "INV04", "Ahi Tuna",           4, "lbs",    8);
        inv(ds, "INV05", "Shrimp",            25, "lbs",   10);
        inv(ds, "INV06", "Catfish Fillets",   14, "lbs",    8);
        inv(ds, "INV07", "Grouper",            2, "lbs",    6);
        inv(ds, "INV08", "Cheese Grits Mix",  30, "lbs",   10);
        inv(ds, "INV09", "Burger Patties",    55, "ct",    20);
        inv(ds, "INV10", "Brioche Buns",      48, "ct",    20);
        inv(ds, "INV11", "Hoagie Rolls",      12, "ct",    15);
        inv(ds, "INV12", "Romaine Lettuce",   10, "heads",  5);
        inv(ds, "INV13", "Avocados",           3, "ct",    10);
        inv(ds, "INV14", "Coke Syrup",         2, "boxes",  2);
        inv(ds, "INV15", "Sweet Tea Bags",    80, "ct",    30);
    }

    private static void inv(DataStore ds, String id, String name, int qty, String unit, int low) {
        ds.inventory().put(id, new InventoryItem(id, name, qty, unit, low));
    }
}