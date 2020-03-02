package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.AdapterCategoryList;
import com.example.fumagalli2020.AdapterProductList;
import com.example.fumagalli2020.Class.Product;
import com.example.fumagalli2020.Helper.ProductListHelper;
import com.example.fumagalli2020.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class ProductList extends AppCompatActivity {
    protected Button btnAddProduct;
    protected ListView listView;
    protected List<Product> lstProduct;
    protected String currentCategoryId;
    protected String currentMarketId;
    protected AdapterProductList adapterProductList;
    protected ProductListHelper productListHelper;
    protected DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        btnAddProduct = findViewById(R.id.btnLstAddProduct);
        lstProduct = new LinkedList<Product>();
        productListHelper = new ProductListHelper();
        Bundle bundle = getIntent().getExtras();
        currentCategoryId = bundle.getString("categoryId");
        currentMarketId = bundle.getString("marketId");
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("marketId");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentMarketId = dataSnapshot.getValue(String.class);
                productListHelper.LoadProduct(lstProduct,currentMarketId,currentCategoryId,adapterProductList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        BottomNavigationView navigationView = findViewById(R.id.logistic_navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.logistic_market:
                        break;
                    case R.id.logistic_orders:
                        menuItem.setChecked(true);
                        Intent intent = new Intent(ProductList.this,LogisticOrders.class);
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

        listView = findViewById(R.id.productList);
        adapterProductList = new AdapterProductList(this, R.layout.item_list_category, lstProduct);
        listView.setAdapter(adapterProductList);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoregproduct();
            }
        });
    }

    private void gotoregproduct(){
        Intent intent = new Intent(this,RegisterProduct.class);
        Bundle bundle = new Bundle();
        bundle.putString("marketId",currentMarketId);
        bundle.putString("categoryId",currentCategoryId);
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
