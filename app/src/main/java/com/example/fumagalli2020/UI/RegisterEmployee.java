package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.Helper.RegisterEmployeeHelper;
import com.example.fumagalli2020.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class RegisterEmployee extends AppCompatActivity {

   private RegisterEmployeeHelper helper;
   private Button regEmplButton;
   private EditText regEmplName;
   private EditText regEmplSurname;
   private EditText regEmplMobile;
   private EditText regEmplEmail;
   private TextInputLayout tilEmplPassword;
   private Spinner regEmplSpinner;
   private ArrayAdapter adptTypeEmployee;
   private List<String> typeList = new ArrayList();
   private String marketId;
   private Integer source;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeremployee);
        regEmplButton = findViewById(R.id.btnRegEmployeeRegistration);
        regEmplName = findViewById(R.id.edtRegEmployeeName);
        regEmplSurname = findViewById(R.id.edtRegEmployeeSurname);
        regEmplSpinner = findViewById(R.id.spnRegEmployeeType);
        regEmplMobile = findViewById(R.id.edtRegEmployeeMobile);
        regEmplEmail = findViewById(R.id.edtRegEmployeeEmail);
        tilEmplPassword = findViewById(R.id.edtRegEmployeePassword);

        Bundle bundle = getIntent().getExtras();
        source = bundle.getInt("source");
        marketId = bundle.getString("marketId");

        helper = new RegisterEmployeeHelper();

        typeList.add("Amministratore Negozio");
        if(source == 1) {
            typeList.add("Addetto alla Logistica");
        }

        adptTypeEmployee = new ArrayAdapter<String>(getApplicationContext(), R.layout.first_custom_spinner,typeList);
        adptTypeEmployee.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regEmplSpinner.setAdapter(adptTypeEmployee);
        regEmplSpinner.setSelection(0);

        regEmplSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setText(regEmplSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        regEmplButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean ret = helper.registerEmployee(regEmplName, regEmplSurname, regEmplMobile, regEmplSpinner, regEmplEmail, tilEmplPassword,marketId,getApplicationContext());
                if(ret){
                    Toast.makeText(RegisterEmployee.this, "OK", Toast.LENGTH_LONG).show();
                    gotoemployeelist();
                }
            }
            });
    }
    private void gotoemployeelist() {
        Intent intent = new Intent(this, EmployeeList.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                if(source == 1)
                    onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }


}
