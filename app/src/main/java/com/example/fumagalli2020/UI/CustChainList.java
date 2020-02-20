package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.AdapterCategoryList;
import com.example.fumagalli2020.AdapterCustChainList;
import com.example.fumagalli2020.Class.Chain;
import com.example.fumagalli2020.Helper.CustMarketListHelper;
import com.example.fumagalli2020.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                    case R.id.navigation_shop:
                        break;
                    case R.id.navigation_cart:
                        item.setChecked(true);
                        Intent a = new Intent(CustChainList.this, CustCartList.class);
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



}
