package com.example.sqlite;

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

import com.example.sqlite.models.Employee;

/**
 * Activity for deleting an employee from the database by card number.
 */
public class DeleteEmployeeActivity extends AppCompatActivity {

    EditText etCardToDelete;
    Button btnDeleteEmployee;
    SQLiteDatabase db;
    HelperDB hlp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);

        getSupportActionBar().setTitle("Delete Employee Page");

        etCardToDelete = findViewById(R.id.etCardToDelete);
        btnDeleteEmployee = findViewById(R.id.btnDeleteEmployee);

        hlp = new HelperDB(this);
    }

    /**
     * Deletes an employee by card number after validation.
     * @param view the button clicked
     */
    public void deleteEmployee(View view) {
        String card = etCardToDelete.getText().toString().trim();

        if (card.isEmpty()) {
            Toast.makeText(this, "Please enter card number", Toast.LENGTH_SHORT).show();
            return;
        }

        db = hlp.getWritableDatabase();
        int deleted = db.delete(Employee.TABLE_EMPLOYEES, Employee.KEY_CARD + "=?", new String[]{card});
        db.close();

        if (deleted > 0) {
            Toast.makeText(this, "Employee deleted successfully", Toast.LENGTH_SHORT).show();
            etCardToDelete.setText("");
        } else {
            Toast.makeText(this, "No employee found with that card number", Toast.LENGTH_SHORT).show();
        }
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
        }else if (id == R.id.p_order) {
            Intent si = new Intent(this, AddOrderActivity.class);
            startActivity(si);
        }else if (id == R.id.p_viewTables) {
            Intent si = new Intent(this, ViewTablesActivity.class);
            startActivity(si);
        }else if (id == R.id.p_filterFileds) {
            Intent si = new Intent(this, ViewAndFilterActivity.class);
            startActivity(si);
        }else if (id == R.id.p_Sign_Up) {
            Intent si = new Intent(this, SignUpEmployeeActivity.class);
            startActivity(si);
        }else if (id == R.id.p_credits) {
            Intent si = new Intent(this, CreditsActivity.class);
            startActivity(si);
        }
        return true;
    }
}
