## Architecture

```
ui/        ← what the user sees and clicks (Swing windows)
   │
   ▼
service/   ← business logic (login rules, order workflow, reports)
   │
   ▼
data/      ← in-memory storage (the DataStore singleton + Seeder)
   │
   ▼
model/     ← the concepts (Employee, Order, Table, MenuItem...)
```

```
src/com/jcorner/
├── Main.java                   # entry point
├── model/                      # concepts the business uses 
├── data/                       # in-memory data population
├── service/                    # business logic (static methods)
└── ui/                         # Swing panels
    ├── AppFrame.java           # single JFrame, panel-swap navigation
    ├── HeaderBar.java          # shared top bar (back/home/logout)
    ├── UIStyle.java            # colors/fonts/component factories
    ├── LoginPanel.java
    ├── ClockPanel.java
    ├── waiter/                 # 7 panels
    ├── cook/                   # 3 panels
    ├── busboy/                 # 2 panels
    └── manager/                # 8 panels
```