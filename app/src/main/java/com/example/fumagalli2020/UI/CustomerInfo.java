package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.Helper.CustomerInfoHelper;
import com.example.fumagalli2020.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerInfo extends AppCompatActivity {

    private EditText edtCustName;
    private EditText edtCustSurname;
    private EditText edtCustFiscalCode;
    private EditText edtCustBirthDate;
    private EditText edtCustMobile;
    private EditText edtCustEmail;
    private Button btnToCustModifyData;
    private Button btnSaveChanges;
    private CustomerInfoHelper customerInfoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);

        edtCustName = findViewById(R.id.custName);
        edtCustSurname = findViewById(R.id.custSurname);
        edtCustFiscalCode = findViewById(R.id.custFiscalCode);
        edtCustBirthDate = findViewById(R.id.custBirthDate);
        edtCustMobile = findViewById(R.id.custMobile);
        edtCustEmail = findViewById(R.id.custEmail);
        btnToCustModifyData = findViewById(R.id.btnToCustModifyData);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        customerInfoHelper = new CustomerInfoHelper();

        customerInfoHelper.LoadCustomerInfo(edtCustEmail,edtCustName,edtCustSurname,edtCustFiscalCode,edtCustBirthDate,edtCustMobile);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cust_navigation_shop:
                        Intent b = new Intent(CustomerInfo.this,CustChainList.class);
                        startActivity(b);
                        break;
                    case R.id.cust_navigation_cart:
                        Intent a = new Intent(CustomerInfo.this, CustCartList.class);
                        startActivity(a);
                        item.setChecked(true);
                        break;
                    case R.id.cust_navigation_profile:
                        break;
                }
                return false;
            }
        });


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.itmCustOrders:
                Intent intentOrders = new Intent(this, CustOrders.class);
                Bundle bundle = new Bundle();
                bundle.putInt("source",2);
                intentOrders.putExtras(bundle);
                startActivity(intentOrders);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnToCustModifyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnToCustModifyData.setVisibility(View.INVISIBLE);
                btnSaveChanges.setVisibility(View.VISIBLE);
                edtCustEmail.setEnabled(true);
                edtCustMobile.setEnabled(true);
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                customerInfoHelper.ModifyCustomerInfo(edtCustEmail,edtCustMobile,CustomerInfo.this,btnSaveChanges,btnToCustModifyData);
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent c = new Intent(CustomerInfo.this,CustChainList.class);
        startActivity(c);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.cust_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

}