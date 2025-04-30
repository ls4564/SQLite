package com.example.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpEmployeeActivity extends AppCompatActivity {

    EditText eDCardNumber, eDLastName, eDFirstName, eDIdNumber, eDPhoneNumber;
    Spinner sPCompany;
    Button btnSaveEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_employee);

    }




    public void weddings()
    {
        eDCardNumber = findViewById(R.id.eDCardNumber);
        eDLastName = findViewById(R.id.eDLastName);
        eDFirstName = findViewById(R.id.eDFirstName);
        eDIdNumber = findViewById(R.id.eDIdNumber);
        eDPhoneNumber = findViewById(R.id.eDPhoneNumber);
        sPCompany = findViewById(R.id.sPCompany);
        btnSaveEmployee = findViewById(R.id.btnSaveEmployee);
    }

    private void clearFields()
    {
        eDCardNumber.setText("");
        eDFirstName.setText("");
        eDLastName.setText("");
        eDIdNumber.setText("");
        eDPhoneNumber.setText("");
        sPCompany.setSelection(0);
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        /*
        if(id == R.id.it2)
        {
            //Intent si = new Intent(this, MainActivity2.class);
            //startActivity(si);
        }
        */
        return true;
    }
}