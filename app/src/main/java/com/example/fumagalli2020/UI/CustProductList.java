package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.AdapterCustProductList;
import com.example.fumagalli2020.Class.Product;
import com.example.fumagalli2020.Helper.CatalogHelper;
import com.example.fumagalli2020.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;
import java.util.List;

public class CustProductList extends AppCompatActivity {

    protected List<Product> lstProduct;
    protected AdapterCustProductList adapterCustProductList;
    protected ListView listView;
    protected CatalogHelper catalogHelper;
    protected String currentCategoryId;
    protected String currentMarketId;
    protected String currentChainId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_product_list);

        lstProduct = new LinkedList<Product>();

        Bundle bundle = getIntent().getExtras();

        currentMarketId = bundle.getString("marketId");
        currentCategoryId = bundle.getString("categoryId");
        currentChainId = bundle.getString("chainId");

        listView = findViewById(R.id.lstProduct);
        adapterCustProductList = new AdapterCustProductList(this, R.layout.item_cust_list_product, lstProduct);
        listView.setAdapter(adapterCustProductList);

        catalogHelper = new CatalogHelper();

        catalogHelper.LoadProduct(lstProduct, currentMarketId, currentCategoryId, adapterCustProductList);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_shop:
                        break;
                    case R.id.navigation_cart:
                        item.setChecked(true);
                        Intent a = new Intent(CustProductList.this, CustCartList.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_profile:
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this,CustCategoryList.class);
        Bundle bundle = new Bundle();
        bundle.putString("chainId",currentChainId);
        bundle.putString("marketId",currentMarketId);
        intent.putExtras(bundle);
        startActivity(intent);
    }



}
