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

import com.example.sqlite.models.Meal;

/**
 * Activity for adding a new meal to the database.
 * Includes input validation, insertion, and menu navigation.
 */
public class AddMealActivity extends AppCompatActivity {

    EditText etStarter, etMainCourse, etSide, etDessert, etDrink;
    Button btnSaveMeal;
    SQLiteDatabase db;
    HelperDB hlp;

    /**
     * Initializes the activity layout and logic.
     * @param savedInstanceState saved instance state bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        getSupportActionBar().setTitle("Add Meal Page");

        initViews();
        hlp = new HelperDB(this);

    }

    /**
     * Connects layout views to variables.
     */
    public void initViews() {
        etStarter = findViewById(R.id.etStarter);
        etMainCourse = findViewById(R.id.etMainCourse);
        etSide = findViewById(R.id.etSide);
        etDessert = findViewById(R.id.etDessert);
        etDrink = findViewById(R.id.etDrink);
        btnSaveMeal = findViewById(R.id.btnSaveMeal);
    }

    /**
     * Adds a new meal to the database after validation.
     */
    public void addMeal(View view) {
        String starter = etStarter.getText().toString();
        String main = etMainCourse.getText().toString();
        String side = etSide.getText().toString();
        String dessert = etDessert.getText().toString();
        String drink = etDrink.getText().toString();

        if (starter.isEmpty() || main.isEmpty() || side.isEmpty() || dessert.isEmpty() || drink.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        db = hlp.getWritableDatabase();
        if (db != null && db.isOpen()) {
            ContentValues cv = new ContentValues();
            cv.put(Meal.STARTER, starter);
            cv.put(Meal.MAIN_COURSE, main);
            cv.put(Meal.SIDE, side);
            cv.put(Meal.DESSERT, dessert);
            cv.put(Meal.DRINK, drink);
            db.insert(Meal.TABLE_MEALS, null, cv);
            db.close();

            Toast.makeText(this, "Meal added successfully", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Database not available", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Clears the input fields after insertion.
     */
    private void clearFields() {
        etStarter.setText("");
        etMainCourse.setText("");
        etSide.setText("");
        etDessert.setText("");
        etDrink.setText("");
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
        } else if (id == R.id.p_food_company) {
            Intent si = new Intent(this, AddSupplierActivity.class);
            startActivity(si);
        } else if (id == R.id.p_order) {
            Intent si = new Intent(this, AddOrderActivity.class);
            startActivity(si);        }
        else if (id == R.id.p_viewTables) {
            Intent si = new Intent(this, ViewTablesActivity.class);
            startActivity(si);
        }
        return true;
    }
}
