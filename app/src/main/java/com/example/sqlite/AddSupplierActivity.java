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

import com.example.sqlite.models.FoodSupplier;

/**
 * Activity for adding a new food supplier to the database.
 * Includes input validation, insertion, and menu navigation.
 */
public class AddSupplierActivity extends AppCompatActivity {

    EditText etCompanyID, etCompanyName, etPrimaryPhone, etSecondaryPhone;
    Button btnSaveSupplier;
    SQLiteDatabase db;
    HelperDB hlp;

    /**
     * Initializes the activity layout and logic.
     * @param savedInstanceState saved instance state bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_supplier);

        // Set page title in the action bar
        getSupportActionBar().setTitle("Add Food Supplier Page");

        weddings();
        hlp = new HelperDB(this);
        
    }

    /**
     * Connects layout views to variables.
     */
    public void weddings() {
        etCompanyID = findViewById(R.id.etCompanyID);
        etCompanyName = findViewById(R.id.etCompanyName);
        etPrimaryPhone = findViewById(R.id.etPrimaryPhone);
        etSecondaryPhone = findViewById(R.id.etSecondaryPhone);
        btnSaveSupplier = findViewById(R.id.btnSaveSupplier);
    }

    /**
     * Adds a new supplier to the database after validation.
     */
    private void addSupplier() {
        String companyId = etCompanyID.getText().toString();
        String companyName = etCompanyName.getText().toString();
        String primaryPhone = etPrimaryPhone.getText().toString();
        String secondaryPhone = etSecondaryPhone.getText().toString();

        if (companyId.isEmpty() || companyName.isEmpty() || primaryPhone.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        db = hlp.getWritableDatabase();
        if (db != null && db.isOpen()) {
            ContentValues cv = new ContentValues();
            cv.put(FoodSupplier.COMPANY_ID, companyId);
            cv.put(FoodSupplier.COMPANY_NAME, companyName);
            cv.put(FoodSupplier.PRIMARY_PHONE, primaryPhone);
            cv.put(FoodSupplier.SECONDARY_PHONE, secondaryPhone);
            db.insert(FoodSupplier.TABLE_SUPPLIERS, null, cv);
            db.close();

            Toast.makeText(this, "Supplier added successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Database not available", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Clears the input fields after insertion.
     */
    private void clearFields() {
        etCompanyID.setText("");
        etCompanyName.setText("");
        etPrimaryPhone.setText("");
        etSecondaryPhone.setText("");
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
     * Handles top menu item selection and page navigation.
     * @param item the selected menu item
     * @return true if handled
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.p_Sign_Up) {
            Intent si = new Intent(this, SignUpEmployeeActivity.class);
            startActivity(si);
        } else if (id == R.id.p_meal) {
            Intent si = new Intent(this, AddMealActivity.class);
            startActivity(si);
        }else if (id == R.id.p_order) {

        }

        return true;
    }
}
