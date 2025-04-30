package com.example.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

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
        }else if (id == R.id.p_deleteEmployee) {
            Intent si = new Intent(this, DeleteEmployeeActivity.class);
            startActivity(si);
        }else if (id == R.id.p_Sign_Up) {
            Intent si = new Intent(this, SignUpEmployeeActivity.class);
            startActivity(si);
        }
        return true;
    }
}