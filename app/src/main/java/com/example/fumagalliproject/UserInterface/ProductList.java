package com.example.fumagalliproject.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalliproject.Class.Product;
import com.example.fumagalliproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;
import java.util.List;

public class ProductList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);

        ListView listView = (ListView)findViewById(R.id.lstProduct);

        List<Product> list = new LinkedList<Product>();
        list.add(new Product("Pennette Rigate","Italia, Chiaravalle", "0,5 kg"));
        list.add(new Product("Lampadina","Germania, Berlino", "1"));
        list.add(new Product("Carne","Italia, Camerino", "Quantità minima 0,1 Kg"));

        AdapterProductList adapterProductList = new AdapterProductList(this, R.layout.item_productlist, list);
        listView.setAdapter(adapterProductList);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_shop:
                        break;
                    case R.id.navigation_cart:
                        Intent a = new Intent(ProductList.this, CartList.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_profile:
                        Intent b = new Intent(ProductList.this,MyProfile.class);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });

    }
}
