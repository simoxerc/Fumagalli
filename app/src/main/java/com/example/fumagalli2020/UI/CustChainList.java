package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.AdapterCustChainList;
import com.example.fumagalli2020.Class.Chain;
import com.example.fumagalli2020.Helper.CustMarketListHelper;
import com.example.fumagalli2020.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.LinkedList;
import java.util.List;

public class CustChainList extends AppCompatActivity {
    private List<Chain> lstChain;
    private ListView listView;
    private CustMarketListHelper custMarketListHelper;
    private AdapterCustChainList adapterCustChainList;
    protected DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_chain_list);
        lstChain = new LinkedList<Chain>();
        custMarketListHelper = new CustMarketListHelper();



        listView = findViewById(R.id.lstCustChain);
        adapterCustChainList = new AdapterCustChainList(this, R.layout.item_cust_list_chain, lstChain);
        listView.setAdapter(adapterCustChainList);

        custMarketListHelper.LoadChain(lstChain,adapterCustChainList);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cust_navigation_shop:
                        break;
                    case R.id.cust_navigation_cart:
                        Intent a = new Intent(CustChainList.this, CustCartList.class);
                        startActivity(a);
                        item.setChecked(true);
                        break;
                    case R.id.cust_navigation_profile:
                        Intent b = new Intent(CustChainList.this,CustomerInfo.class);
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
            case R.id.itmCustLogout:
                FirebaseAuth.getInstance().signOut();
                Intent intentLogin = new Intent(this,Login.class);
                startActivity(intentLogin);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
