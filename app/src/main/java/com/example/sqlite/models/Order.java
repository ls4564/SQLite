package com.example.sqlite.models;

public class Order
{
    public static final String TABLE_ORDERS = "Orders";
    public static final String ORDER_ID = "OrderID";
    public static final String DATE = "Date";
    public static final String TIME = "Time";
    public static final String EMPLOYEE_ID = "EmployeeID";      // foreign key to Employee
    public static final String MEAL_ID = "MealID";              // foreign key to Meal
    public static final String SUPPLIER_ID = "SupplierID";      // foreign key to FoodSupplier
}
