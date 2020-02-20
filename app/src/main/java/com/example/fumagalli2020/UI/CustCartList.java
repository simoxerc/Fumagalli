package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.AdapterCustCartList;
import com.example.fumagalli2020.Class.Cart;
import com.example.fumagalli2020.Helper.CustCartListHelper;
import com.example.fumagalli2020.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;
import java.util.List;

public class CustCartList extends AppCompatActivity {

    protected List<Cart> lstCart;
    protected AdapterCustCartList adapterCustCartList ;
    protected ListView listView;
    protected CustCartListHelper custCartListHelper ;
    protected String currentCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_cart_list);

        lstCart = new LinkedList<Cart>();

        listView = findViewById(R.id.lstCustCart);

        adapterCustCartList = new AdapterCustCartList(this, R.layout.item_cust_list_cart, lstCart);
        listView.setAdapter(adapterCustCartList);
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        custCartListHelper = new CustCartListHelper();

        custCartListHelper.LoadCart(lstCart, currentUser,adapterCustCartList);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_shop:
                        item.setChecked(true);
                        Intent a = new Intent(CustCartList.this, CustChainList.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_cart:
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
