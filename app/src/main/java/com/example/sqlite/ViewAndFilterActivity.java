package com.example.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite.models.Employee;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Activity for displaying and filtering employee data
 * with options to sort by last name and filter by company or phone prefix.
 */
public class ViewAndFilterActivity extends AppCompatActivity {

    Spinner spFilterType, spCompanyFilter, spPhonePrefixFilter;
    ListView lvEmployees;
    HelperDB hlp;
    SQLiteDatabase db;

    ArrayList<String> employeesList = new ArrayList<>();
    ArrayList<String> allEmployeesRaw = new ArrayList<>();
    ArrayList<String> companies = new ArrayList<>();
    ArrayAdapter<String> adapter;

    String[] filterOptions = {"All", "Sort: Last Name A-Z", "Sort: Last Name Z-A", "Filter: Company", "Filter: Phone Prefix"};
    String[] phonePrefixes = {"050", "052", "053", "054", "055"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_filter);

        getSupportActionBar().setTitle("View & Filter Employees");

        spFilterType = findViewById(R.id.spFilterType);
        spCompanyFilter = findViewById(R.id.spCompanyFilter);
        spPhonePrefixFilter = findViewById(R.id.spPhonePrefixFilter);
        lvEmployees = findViewById(R.id.lvEmployees);

        hlp = new HelperDB(this);

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filterOptions);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFilterType.setAdapter(filterAdapter);

        ArrayAdapter<String> phoneAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, phonePrefixes);
        phoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPhonePrefixFilter.setAdapter(phoneAdapter);

        spFilterType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilter(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spCompanyFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilter(spFilterType.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spPhonePrefixFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilter(spFilterType.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        loadAllEmployees();
        loadCompanies();
    }

    /**
     * Loads all employees from the database into the list.
     */
    private void loadAllEmployees() {
        employeesList.clear();
        allEmployeesRaw.clear();
        db = hlp.getReadableDatabase();
        Cursor c = db.query(Employee.TABLE_EMPLOYEES, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                String card = c.getString(c.getColumnIndexOrThrow(Employee.KEY_CARD));
                String first = c.getString(c.getColumnIndexOrThrow(Employee.FIRST_NAME));
                String last = c.getString(c.getColumnIndexOrThrow(Employee.LAST_NAME));
                String company = c.getString(c.getColumnIndexOrThrow(Employee.COMPANY));
                String id = c.getString(c.getColumnIndexOrThrow(Employee.ID));
                String phone = c.getString(c.getColumnIndexOrThrow(Employee.PHONE));

                String row = first + " " + last + " | ID: " + id + " | Phone: " + phone + " | Company: " + company;
                employeesList.add(row);
                allEmployeesRaw.add(row);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employeesList);
        lvEmployees.setAdapter(adapter);
    }

    /**
     * Loads unique company names from the database into the company filter Spinner.
     */
    private void loadCompanies() {
        companies.clear();
        db = hlp.getReadableDatabase();
        Cursor c = db.query(Employee.TABLE_EMPLOYEES, new String[]{Employee.COMPANY}, null, null, Employee.COMPANY, null, null);
        if (c.moveToFirst()) {
            do {
                companies.add(c.getString(c.getColumnIndexOrThrow(Employee.COMPANY)));
            } while (c.moveToNext());
        }
        c.close();
        db.close();

        ArrayAdapter<String> companyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, companies);
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCompanyFilter.setAdapter(companyAdapter);
    }

    /**
     * Applies the selected filter or sort operation to the employee list.
     * @param option index of the selected filter option
     */
    private void applyFilter(int option) {
        employeesList.clear();
        employeesList.addAll(allEmployeesRaw);

        switch (option) {
            case 1:
                Collections.sort(employeesList);
                break;
            case 2:
                Collections.sort(employeesList, Collections.reverseOrder());
                break;
            case 3:
                String selectedCompany = spCompanyFilter.getSelectedItem().toString();
                filterByCompany(selectedCompany);
                break;
            case 4:
                String prefix = spPhonePrefixFilter.getSelectedItem().toString();
                filterByPhonePrefix(prefix);
                break;
        }

        adapter.notifyDataSetChanged();
    }

    /**
     * Filters the employee list by company name.
     * @param company the selected company to filter by
     */
    private void filterByCompany(String company) {
        ArrayList<String> filtered = new ArrayList<>();
        for (String row : allEmployeesRaw) {
            if (row.contains("Company: " + company)) {
                filtered.add(row);
            }
        }
        employeesList.clear();
        employeesList.addAll(filtered);
    }

    /**
     * Filters the employee list by phone number prefix.
     * @param prefix the selected phone prefix to filter by
     */
    private void filterByPhonePrefix(String prefix) {
        ArrayList<String> filtered = new ArrayList<>();
        for (String row : allEmployeesRaw) {
            if (row.contains("Phone: " + prefix)) {
                filtered.add(row);
            }
        }
        employeesList.clear();
        employeesList.addAll(filtered);
    }
}
