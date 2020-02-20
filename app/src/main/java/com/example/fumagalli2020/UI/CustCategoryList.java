package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.AdapterCustCategoryList;
import com.example.fumagalli2020.Class.Category;
import com.example.fumagalli2020.Helper.CatalogHelper;
import com.example.fumagalli2020.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;
import java.util.List;

public class CustCategoryList extends AppCompatActivity {

    protected List<Category> lstCategory;
    protected AdapterCustCategoryList adapterCustCategoryList;
    protected ListView listView;
    protected CatalogHelper catalogHelper;
    protected String currentMarketId,currentChainId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_category_list);

        lstCategory = new LinkedList<Category>();

        Bundle bundle = getIntent().getExtras();

        currentMarketId = bundle.getString("marketId");
        currentChainId = bundle.getString("chainId");

        listView = findViewById(R.id.lstCustCategory);
        adapterCustCategoryList = new AdapterCustCategoryList(this, R.layout.item_cust_list_category, lstCategory);
        listView.setAdapter(adapterCustCategoryList);

        catalogHelper = new CatalogHelper();

        catalogHelper.LoadCategory(lstCategory,currentMarketId,adapterCustCategoryList);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_shop:
                        break;
                    case R.id.navigation_cart:
                        item.setChecked(true);
                        Intent a = new Intent(CustCategoryList.this, CustCartList.class);
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


    @Override
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
        Intent intent = new Intent(this,CustMarketList.class);
        Bundle bundle = new Bundle();
        bundle.putString("chainId",currentChainId);
        intent.putExtras(bundle);
        startActivity(intent);
    }



}
