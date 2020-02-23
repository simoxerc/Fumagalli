package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.AdapterCustMarketList;
import com.example.fumagalli2020.Class.Market;
import com.example.fumagalli2020.Helper.CustMarketListHelper;
import com.example.fumagalli2020.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;
import java.util.List;

public class CustMarketList extends AppCompatActivity {

    private List<Market> lstMarket;
    private ListView listView;
    private CustMarketListHelper custMarketListHelper;
    private AdapterCustMarketList adapterCustMarketList;
    private String currentChainId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_market_list);
        lstMarket = new LinkedList<Market>();
        custMarketListHelper = new CustMarketListHelper();

        listView = findViewById(R.id.lstCustMarket);
        adapterCustMarketList = new AdapterCustMarketList(this,R.layout.item_cust_list_market,lstMarket);
        listView.setAdapter(adapterCustMarketList);

        Bundle bundle = getIntent().getExtras();
        currentChainId = bundle.getString("chainId");

        custMarketListHelper.LoadMarket(lstMarket,adapterCustMarketList,currentChainId);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cust_navigation_shop:
                        break;
                    case R.id.cust_navigation_cart:
                        item.setChecked(true);
                        Intent a = new Intent(CustMarketList.this, CustCartList.class);
                        startActivity(a);
                        break;
                    case R.id.cust_navigation_profile:
                        Intent b = new Intent(CustMarketList.this, CustomerInfo.class);
                        startActivity(b);
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
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.cust_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.itmCustOrders:
                Intent intentOrders = new Intent(this, CustOrders.class);
                Bundle bundle = new Bundle();
                bundle.putInt("source",0);
                intentOrders.putExtras(bundle);
                startActivity(intentOrders);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent back = new Intent(this,CustChainList.class);
        startActivity(back);
    }

}
