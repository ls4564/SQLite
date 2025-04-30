package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlite.models.Employee;
import com.example.sqlite.models.FoodSupplier;
import com.example.sqlite.models.Meal;
import com.example.sqlite.models.Order;

/**
 * @author  Lior Shem Tov
 * @version 1.1
 * @since   30/04/2025
 * Manage the app database: creation and upgrade of tables for employees, food suppliers, meals and orders.
 */
public class HelperDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GYNbus.db";
    private static final int DATABASE_VERSION = 1;
    String strCreate, strDelete;

    /**
     * Constructor for the database helper
     * @param context the context from the activity
     */
    public HelperDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the application's database tables: Employees, FoodSuppliers, Meals, Orders.
     * @param db the database instance on first creation
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Employee table
        strCreate = "CREATE TABLE " + Employee.TABLE_EMPLOYEES + " ("
                + Employee.KEY_CARD + " TEXT PRIMARY KEY, "
                + Employee.FIRST_NAME + " TEXT, "
                + Employee.LAST_NAME + " TEXT, "
                + Employee.COMPANY + " TEXT, "
                + Employee.ID + " TEXT, "
                + Employee.PHONE + " TEXT);";
        db.execSQL(strCreate);

        // Food suppliers table
        strCreate = "CREATE TABLE " + FoodSupplier.TABLE_SUPPLIERS + " ("
                + FoodSupplier.COMPANY_ID + " TEXT PRIMARY KEY, "
                + FoodSupplier.COMPANY_NAME + " TEXT, "
                + FoodSupplier.PRIMARY_PHONE + " TEXT, "
                + FoodSupplier.SECONDARY_PHONE + " TEXT);";
        db.execSQL(strCreate);

        // Meals table
        strCreate = "CREATE TABLE " + Meal.TABLE_MEALS + " ("
                + Meal.MEAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Meal.STARTER + " TEXT, "
                + Meal.MAIN_COURSE + " TEXT, "
                + Meal.SIDE + " TEXT, "
                + Meal.DESSERT + " TEXT, "
                + Meal.DRINK + " TEXT);";
        db.execSQL(strCreate);

        // Orders table
        strCreate = "CREATE TABLE " + Order.TABLE_ORDERS + " ("
                + Order.ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Order.DATE + " TEXT, "
                + Order.TIME + " TEXT, "
                + Order.EMPLOYEE_ID + " TEXT, "
                + Order.MEAL_ID + " INTEGER, "
                + Order.SUPPLIER_ID + " TEXT, "
                + "FOREIGN KEY(" + Order.EMPLOYEE_ID + ") REFERENCES " + Employee.TABLE_EMPLOYEES + "(" + Employee.KEY_CARD + "), "
                + "FOREIGN KEY(" + Order.MEAL_ID + ") REFERENCES " + Meal.TABLE_MEALS + "(" + Meal.MEAL_ID + "), "
                + "FOREIGN KEY(" + Order.SUPPLIER_ID + ") REFERENCES " + FoodSupplier.TABLE_SUPPLIERS + "(" + FoodSupplier.COMPANY_ID + "));";
        db.execSQL(strCreate);
    }

    /**
     * Upgrades the database by dropping and recreating all tables if version changes.
     * @param db the database
     * @param oldVer old version
     * @param newVer new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + Order.TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + Meal.TABLE_MEALS);
        db.execSQL("DROP TABLE IF EXISTS " + FoodSupplier.TABLE_SUPPLIERS);
        db.execSQL("DROP TABLE IF EXISTS " + Employee.TABLE_EMPLOYEES);
        onCreate(db);
    }
}
