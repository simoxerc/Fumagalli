package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.AdapterCustCartSingle;
import com.example.fumagalli2020.Class.Product;
import com.example.fumagalli2020.Helper.CustCartSingleHelper;
import com.example.fumagalli2020.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;
import java.util.List;

public class CustCartSingle extends AppCompatActivity {

    protected List<Product> lstSingleCart;
    protected AdapterCustCartSingle adapterCustCartSingle;
    protected ListView listView;
    protected String marketId;
    protected String cartId;
    protected String currentUser;
    protected CustCartSingleHelper custCartSingleHelper;
    protected TextView tvGetTotal;
    protected Button btnNextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_cart_single);

        tvGetTotal = findViewById(R.id.tvSingleCartTotal);
        btnNextStep = findViewById(R.id.btnSingleCartBuy);

        custCartSingleHelper = new CustCartSingleHelper();

        Bundle bundle = getIntent().getExtras();

        marketId = bundle.getString("marketId");
        cartId = bundle.getString("cartId");

        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        lstSingleCart = new LinkedList<Product>();


        listView = findViewById(R.id.lstSingleCart);

        adapterCustCartSingle = new AdapterCustCartSingle(this, R.layout.item_cust_single_cart, lstSingleCart, tvGetTotal, cartId);
        listView.setAdapter(adapterCustCartSingle);

        custCartSingleHelper.LoadProductCart(cartId,currentUser,lstSingleCart,adapterCustCartSingle,marketId);

        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotocheckout();
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.cust_navigation_shop:
                        item.setChecked(true);
                        Intent a = new Intent(CustCartSingle.this, CustChainList.class);
                        startActivity(a);
                        break;
                    case R.id.cust_navigation_cart:
                        break;
                    case R.id.cust_navigation_profile:
                        Intent b = new Intent(CustCartSingle.this,CustomerInfo.class);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });

    }

    private void gotocheckout(){
        Intent intent = new Intent(CustCartSingle.this, Checkout.class);
        Bundle bundle = new Bundle();
        bundle.putString("marketId",marketId);
        bundle.putString("cartId",cartId);
        bundle.putString("total",tvGetTotal.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.itmCustOrders:
                Intent intentOrders = new Intent(this, CustOrders.class);
                Bundle bundle = new Bundle();
                bundle.putInt("source",1);
                intentOrders.putExtras(bundle);
                startActivity(intentOrders);
                return true;
            case R.id.itmCustLogout:
                FirebaseAuth.getInstance().signOut();
                Intent intentLogoutCust = new Intent(this,Login.class);
                startActivity(intentLogoutCust);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this,CustCartList.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.cust_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


}
