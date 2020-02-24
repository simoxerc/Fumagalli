package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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
                    case R.id.cust_navigation_shop:
                        item.setChecked(true);
                        Intent a = new Intent(CustCartList.this, CustChainList.class);
                        startActivity(a);
                        break;
                    case R.id.cust_navigation_cart:
                        break;
                    case R.id.cust_navigation_profile:
                        Intent b = new Intent(CustCartList.this,CustomerInfo.class);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });

        navigation.getMenu().getItem(0).setChecked(true);


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
                bundle.putInt("source",1);
                intentOrders.putExtras(bundle);
                startActivity(intentOrders);
                return true;
            case R.id.itmCustLogout:
                FirebaseAuth.getInstance().signOut();
                Intent intentCustLogout = new Intent(this,Login.class);
                startActivity(intentCustLogout);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
