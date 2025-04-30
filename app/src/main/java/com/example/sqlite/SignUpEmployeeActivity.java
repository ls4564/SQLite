package com.example.sqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite.models.Employee;

/**
 * Activity for signing up a new employee into the database.
 * Handles input validation, duplicate checks, and insertion into SQLite.
 */
public class SignUpEmployeeActivity extends AppCompatActivity {

    EditText eDCardNumber, eDLastName, eDFirstName, eDIdNumber, eDPhoneNumber;
    Spinner sPCompany;
    Button btnSaveEmployee;

    SQLiteDatabase db;
    HelperDB hlp;

    /**
     * Initializes the activity, sets up view components and database helper.
     * @param savedInstanceState the saved instance state bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_employee);
        weddings();
        hlp = new HelperDB(this);
        String[] companies = {"Intel", "IBM", "Google"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, companies);
        sPCompany.setAdapter(adapter);
    }

    /**
     * Called when the "Save Employee" button is clicked.
     * Validates input, checks if employee already exists, and inserts if valid.
     * @param view the view that triggered this method
     */
    public void add_employee(View view) {
        String card = eDCardNumber.getText().toString();
        String first = eDFirstName.getText().toString();
        String last = eDLastName.getText().toString();
        String company = sPCompany.getSelectedItem().toString();
        String id = eDIdNumber.getText().toString();
        String phone = eDPhoneNumber.getText().toString();

        if (card.isEmpty() || first.isEmpty() || last.isEmpty() || id.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        db = hlp.getReadableDatabase();
        Cursor c = runQuery(db, Employee.TABLE_EMPLOYEES, null, Employee.KEY_CARD + "=?", new String[]{card}, null, null, null, null);

        if (c.moveToFirst()) {
            Toast.makeText(this, "Employee already exists", Toast.LENGTH_SHORT).show();
            c.close();
            db.close();
            return;
        }
        c.close();

        db = hlp.getWritableDatabase();
        ContentValues cv = createEmployeeContentValues(card, first, last, company, id, phone);
        db.insert(Employee.TABLE_EMPLOYEES, null, cv);
        db.close();

        Toast.makeText(this, "Employee added successfully", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    /**
     * Builds a ContentValues object from employee input.
     * @param cardNumber the employee card number
     * @param firstName the first name
     * @param lastName the last name
     * @param company the company name
     * @param idNumber the ID number
     * @param phoneNumber the phone number
     * @return ContentValues with all employee data
     */
    public ContentValues createEmployeeContentValues(String cardNumber, String firstName, String lastName, String company, String idNumber, String phoneNumber) {
        ContentValues cv = new ContentValues();
        cv.put(Employee.KEY_CARD, cardNumber);
        cv.put(Employee.FIRST_NAME, firstName);
        cv.put(Employee.LAST_NAME, lastName);
        cv.put(Employee.COMPANY, company);
        cv.put(Employee.ID, idNumber);
        cv.put(Employee.PHONE, phoneNumber);
        return cv;
    }

    /**
     * Executes a SELECT query on the database with all optional parameters.
     * @param db readable or writable database
     * @param table table name to query
     * @param columns columns to return (null for all)
     * @param selection WHERE clause (without WHERE)
     * @param selectionArgs values for WHERE clause
     * @param groupBy how to group rows
     * @param having filter on groups
     * @param orderBy sorting order
     * @param limit limit results
     * @return Cursor with query result
     */
    public Cursor runQuery(SQLiteDatabase db, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    /**
     * Connects UI XML views to Java variables.
     */
    public void weddings() {
        eDCardNumber = findViewById(R.id.eDCardNumber);
        eDLastName = findViewById(R.id.eDLastName);
        eDFirstName = findViewById(R.id.eDFirstName);
        eDIdNumber = findViewById(R.id.eDIdNumber);
        eDPhoneNumber = findViewById(R.id.eDPhoneNumber);
        sPCompany = findViewById(R.id.sPCompany);
        btnSaveEmployee = findViewById(R.id.btnSaveEmployee);
    }

    /**
     * Clears all fields in the employee input form.
     */
    private void clearFields() {
        eDCardNumber.setText("");
        eDFirstName.setText("");
        eDLastName.setText("");
        eDIdNumber.setText("");
        eDPhoneNumber.setText("");
        sPCompany.setSelection(0);
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
        return true;
    }
}
