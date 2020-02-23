package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.AdapterCustOrders;
import com.example.fumagalli2020.Class.Order;
import com.example.fumagalli2020.Class.Product;
import com.example.fumagalli2020.Helper.CustOrdersHelper;
import com.example.fumagalli2020.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustOrders extends AppCompatActivity {

    protected Integer source;
    protected AdapterCustOrders adapterCustOrders;
    protected ExpandableListView expandableListView;
    protected List<Order> listDataHeader;
    protected HashMap<Order, List<Product>> listDataChild;
    protected CustOrdersHelper custOrdersHelper;
    

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_orders);

        Bundle bundle = getIntent().getExtras();

        source = bundle.getInt("source");

        expandableListView = findViewById(R.id.lstCustOrders);

        listDataHeader = new ArrayList<Order>();
        listDataChild = new HashMap<Order, List<Product>>();

        adapterCustOrders = new AdapterCustOrders(this,listDataHeader,listDataChild);
        expandableListView.setAdapter(adapterCustOrders);

        custOrdersHelper = new CustOrdersHelper();

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        custOrdersHelper.LoadCustOrders(currentUser,adapterCustOrders,listDataHeader,listDataChild);

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
            case R.id.home:
                onBackPressed();
                return true;
            case R.id.itmCustOrders:
                Intent intentOrders = new Intent(this, CustOrders.class);
                startActivity(intentOrders);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        if(source == 0){
            Intent intentToShop = new Intent(this,CustChainList.class);
            startActivity(intentToShop);
        }else if(source == 1){
            Intent intentToCart = new Intent(this,CustCartList.class);
            startActivity(intentToCart);
        }else{
            Intent intentToProfile = new Intent(this,CustomerInfo.class);
            startActivity(intentToProfile);
        }
    }

}
