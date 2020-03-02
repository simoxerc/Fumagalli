package com.example.fumagalli2020.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.Helper.ModifyProductHelper;
import com.example.fumagalli2020.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class ProductModify extends AppCompatActivity {

    private final Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    private EditText edtProductQnt,edtProductPrice,edtProductExpDate,edtProductOrigin,edtProductPckDate;
    private TextView tvProductName;
    private Button btnModifyProduct;
    private OnCompleteListener listener;
    private ModifyProductHelper modifyProductHelper;
    private String marketId,productId,categoryId,productType,productName,productQuantity,productPrice,productOrigin,productExpDate,productPckDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_modify);

        tvProductName = findViewById(R.id.tvProductModifyName);
        edtProductOrigin = findViewById(R.id.edtModProductOrigin);
        edtProductQnt = findViewById(R.id.edtModProductQuantity);
        edtProductPrice = findViewById(R.id.edtModProductPrice);
        edtProductPckDate = findViewById(R.id.edtModProductPackageDate);
        edtProductExpDate = findViewById(R.id.edtModProductExpireDate);
        btnModifyProduct = findViewById(R.id.btnModifyProduct);

        Bundle bundle = getIntent().getExtras();
        productId = bundle.getString("productId");
        marketId = bundle.getString("marketId");
        categoryId = bundle.getString("categoryId");
        productType = bundle.getString("productType");
        productName = bundle.getString("productName");
        productQuantity = bundle.getString("productQnt");
        productPrice = bundle.getString("productPrice");
        productOrigin = bundle.getString("productOrigin");
        productExpDate = bundle.getString("productExp");
        productPckDate = bundle.getString("productPckg");

        tvProductName.setText(productName);
        edtProductQnt.setText(productQuantity);
        edtProductPrice.setText(productPrice);
        edtProductOrigin.setText(productOrigin);
        edtProductPckDate.setText(productPckDate);
        edtProductExpDate.setText(productExpDate);

        modifyProductHelper = new ModifyProductHelper();

        listener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(ProductModify.this,"Prodotto modificato!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ProductModify.this,ProductList.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("marketId",marketId);
                    bundle1.putString("categoryId",categoryId);
                    intent.putExtras(bundle1);
                    startActivity(intent);
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();

        edtProductPckDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtProductPckDate.setError(null);
                setDate(edtProductPckDate);
                datePickerDialog.show();
            }
        });

        edtProductExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtProductExpDate.setError(null);
                setDate(edtProductExpDate);
                datePickerDialog.show();
            }
        });

        btnModifyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyProductHelper.ModifyProduct(edtProductQnt,edtProductPrice,edtProductOrigin,edtProductExpDate,edtProductPckDate,marketId,productId,
                        categoryId,productType,ProductModify.this,productQuantity,productPrice,productExpDate,productPckDate,listener);
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

    @Override
    public void onBackPressed() {
<<<<<<< HEAD
<<<<<<< HEAD
        Bundle backthings = new Bundle();
        backthings.putString("categoryId",categoryId);
        backthings.putString("marketId",marketId);
        Intent backintent = new Intent(this,ProductList.class);
        backintent.putExtras(backthings);
        startActivity(backintent);
=======
        super.onBackPressed();
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
=======
        super.onBackPressed();
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
<<<<<<< HEAD
<<<<<<< HEAD
            case android.R.id.home:
                onBackPressed();
                return true;
=======
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
=======
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
            case R.id.itmAdminLogout:
                FirebaseAuth.getInstance().signOut();
                Intent intentAdmLogout = new Intent(this,Login.class);
                startActivity(intentAdmLogout);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
