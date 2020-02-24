package com.example.fumagalli2020.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.Helper.RegisterProductHelper;
import com.example.fumagalli2020.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterProduct extends AppCompatActivity {
    private final Calendar calendar = Calendar.getInstance();
    private RegisterProductHelper registerProductHelper;
    private DatePickerDialog datePickerDialog;
    private EditText edtProductName,edtProductOrigin,edtProductQuantity,edtProductPrice,edtProductPackageDate,edtProductExpireDate;
    private Button btnAddProduct;
    private Spinner spnProductType;
    private ArrayAdapter adptTypeProduct;
    private List<String> typeList = new ArrayList();
    private OnCompleteListener<Void> onCompleteListener;
    private String marketId,categoryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerproduct);
        edtProductName = findViewById(R.id.edtRegProductName);
        edtProductOrigin = findViewById(R.id.edtRegProductOrigin);
        edtProductQuantity = findViewById(R.id.edtRegProductQuantity);
        edtProductPrice = findViewById(R.id.edtRegProductPrice);
        edtProductExpireDate = findViewById(R.id.edtRegProductExpireDate);
        edtProductPackageDate = findViewById(R.id.edtRegProductPackageDate);
        spnProductType = findViewById(R.id.spnRegProductType);
        btnAddProduct = findViewById(R.id.btnRegProductRegistration);
        registerProductHelper = new RegisterProductHelper();
        Bundle bundle = getIntent().getExtras();
        marketId = bundle.getString("marketId");
        categoryId = bundle.getString("categoryId");

        typeList.add("Confezionato");
        typeList.add("Sfuso");
        adptTypeProduct = new ArrayAdapter<String>(getApplicationContext(), R.layout.first_custom_spinner,typeList);
        adptTypeProduct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProductType.setAdapter(adptTypeProduct);
        spnProductType.setSelection(0);

        spnProductType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setText(spnProductType.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        onCompleteListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterProduct.this,"Prodotto Aggiunto",Toast.LENGTH_LONG).show();
                    gotoproductlist();
                }else{
                    Toast.makeText(RegisterProduct.this,"Prodotto Non Aggiunto",Toast.LENGTH_LONG).show();
                }
            }
        };

        BottomNavigationView navigationView = findViewById(R.id.logistic_navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.logistic_market:
                        break;
                    case R.id.logistic_orders:
                        menuItem.setChecked(true);
                        Intent intent = new Intent(RegisterProduct.this,LogisticOrders.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        edtProductPackageDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtProductPackageDate.setError(null);
                setDate(edtProductPackageDate);
                datePickerDialog.show();
            }
        });

        edtProductExpireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtProductExpireDate.setError(null);
                setDate(edtProductExpireDate);
                datePickerDialog.show();
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerProductHelper.RegisterProduct(edtProductName,edtProductOrigin,edtProductQuantity,edtProductPrice,edtProductPackageDate,edtProductExpireDate,
                        spnProductType,marketId,categoryId,onCompleteListener);
            }
        });

    }

    private void setDate(final EditText edtDate){
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edtDate.setText((dayOfMonth + "/" + (month + 1) +  "/" + year).toString());
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void gotoproductlist(){
        Intent intent = new Intent(RegisterProduct.this,ProductList.class);
        Bundle bundle = new Bundle();
        bundle.putString("marketId",marketId);
        bundle.putString("categoryId",categoryId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itmAdminLogout:
                FirebaseAuth.getInstance().signOut();
                Intent intentAdmLogout = new Intent(this,Login.class);
                startActivity(intentAdmLogout);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
