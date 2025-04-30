package com.example.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Activity that allows user to select a table from a spinner
 * and displays its data in a ListView.
 */
public class ViewTablesActivity extends AppCompatActivity {

    Spinner spTables;
    ListView lvTableData;
    HelperDB hlp;
    SQLiteDatabase db;

    String[] tableNames = {"Employees", "FoodSuppliers", "Meals", "Orders"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tables);

        getSupportActionBar().setTitle("View Tables Page");

        spTables = findViewById(R.id.spTables);
        lvTableData = findViewById(R.id.lvTableData);

        hlp = new HelperDB(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tableNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTables.setAdapter(adapter);

        spTables.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                displayTableData(tableNames[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    /**
     * Displays the contents of the selected table in the ListView.
     * @param tableName name of the table to query
     */
    private void displayTableData(String tableName) {
        db = hlp.getReadableDatabase();
        Cursor c = db.query(tableName, null, null, null, null, null, null);
        ArrayList<String> dataList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                StringBuilder row = new StringBuilder();
                for (int i = 0; i < c.getColumnCount(); i++) {
                    row.append(c.getColumnName(i)).append(": ").append(c.getString(i)).append("  ");
                }
                dataList.add(row.toString());
            } while (c.moveToNext());
        }
        c.close();
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        lvTableData.setAdapter(adapter);
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

