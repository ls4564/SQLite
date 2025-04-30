package com.example.sqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite.models.Order;

/**
 * Activity for placing a new meal order in the database.
 * Includes input validation, insertion, and menu navigation.
 */
public class AddOrderActivity extends AppCompatActivity {

    EditText etDate, etTime, etEmployeeId, etMealId, etSupplierId;
    Button btnSaveOrder;
    SQLiteDatabase db;
    HelperDB hlp;

    /**
     * Initializes the activity layout and logic.
     * @param savedInstanceState saved instance state bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        getSupportActionBar().setTitle("Add Order Page");

        initViews();
        hlp = new HelperDB(this);
    }

    /**
     * Connects layout views to variables.
     */
    public void initViews() {
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        etEmployeeId = findViewById(R.id.etEmployeeId);
        etMealId = findViewById(R.id.etMealId);
        etSupplierId = findViewById(R.id.etSupplierId);
        btnSaveOrder = findViewById(R.id.btnSaveOrder);
    }

    /**
     * Called when the "Save Order" button is clicked.
     * Validates input and inserts the order if valid.
     * @param view the view that triggered the method
     */
    public void addOrder(View view) {
        String date = etDate.getText().toString();
        String time = etTime.getText().toString();
        String employeeId = etEmployeeId.getText().toString();
        String mealId = etMealId.getText().toString();
        String supplierId = etSupplierId.getText().toString();

        if (date.isEmpty() || time.isEmpty() || employeeId.isEmpty() || mealId.isEmpty() || supplierId.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        db = hlp.getWritableDatabase();
        if (db != null && db.isOpen()) {
            ContentValues cv = new ContentValues();
            cv.put(Order.DATE, date);
            cv.put(Order.TIME, time);
            cv.put(Order.EMPLOYEE_ID, employeeId);
            cv.put(Order.MEAL_ID, mealId);
            cv.put(Order.SUPPLIER_ID, supplierId);
            db.insert(Order.TABLE_ORDERS, null, cv);
            db.close();

            Toast.makeText(this, "Order added successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Database not available", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Clears the input fields after insertion.
     */
    private void clearFields() {
        etDate.setText("");
        etTime.setText("");
        etEmployeeId.setText("");
        etMealId.setText("");
        etSupplierId.setText("");
    }

    /**
     * Inflates the top menu of the activity.
     * @param menu the options menu
     * @return true if the menu is shown
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handles top menu item selection.
     * @param item the selected menu item
     * @return true if handled
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.p_food_company) {
            Intent si = new Intent(this, AddSupplierActivity.class);
            startActivity(si);
        } else if (id == R.id.p_meal) {
            Intent si = new Intent(this, AddMealActivity.class);
            startActivity(si);
        }else if (id == R.id.p_Sign_Up) {
            Intent si = new Intent(this, SignUpEmployeeActivity.class);
            startActivity(si);
        }else if (id == R.id.p_viewTables) {
            Intent si = new Intent(this, ViewTablesActivity.class);
            startActivity(si);
        }else if (id == R.id.p_filterFileds) {
            Intent si = new Intent(this, ViewAndFilterActivity.class);
            startActivity(si);
        }else if (id == R.id.p_deleteEmployee) {
            Intent si = new Intent(this, DeleteEmployeeActivity.class);
            startActivity(si);
        }else if (id == R.id.p_credits) {
            Intent si = new Intent(this, CreditsActivity.class);
            startActivity(si);
        }
        return true;
    }
}

