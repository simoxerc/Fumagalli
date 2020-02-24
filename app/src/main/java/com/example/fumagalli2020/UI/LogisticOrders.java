package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.AdapterLogisticOrders;
import com.example.fumagalli2020.Class.Order;
import com.example.fumagalli2020.Class.Product;
import com.example.fumagalli2020.Helper.LogisticOrderHelper;
import com.example.fumagalli2020.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogisticOrders extends AppCompatActivity {
    protected AdapterLogisticOrders adapterLogisticOrders;
    protected ExpandableListView lstOrders;
    protected List<Order> orderList;
    protected List<String> custfiscal;
    protected HashMap<Order,List<Product>> orderListHashMap;
    protected LogisticOrderHelper orderHelper;
    protected DatabaseReference databaseReference;
    protected String currentMarketId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        lstOrders = findViewById(R.id.lstOrders);
        orderHelper = new LogisticOrderHelper();
        orderList = new ArrayList<Order>();
        custfiscal = new ArrayList<String>();
        orderListHashMap = new HashMap<Order,List<Product>>();

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("marketId");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentMarketId = dataSnapshot.getValue(String.class);
                orderHelper.LoadLogistcOrders(orderList,orderListHashMap,adapterLogisticOrders,currentMarketId,custfiscal);
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
                        menuItem.setChecked(true);
                        Intent intent = new Intent(LogisticOrders.this,CategoryList.class);
                        startActivity(intent);
                        break;
                    case R.id.logistic_orders:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapterLogisticOrders = new AdapterLogisticOrders(this,orderList,orderListHashMap,custfiscal);
        lstOrders.setAdapter(adapterLogisticOrders);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this,CategoryList.class);
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
                Intent intentAdmLogout = new Intent(this, Login.class);
                startActivity(intentAdmLogout);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
