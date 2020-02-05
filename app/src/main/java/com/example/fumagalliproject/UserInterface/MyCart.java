package com.example.fumagalliproject.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalliproject.Class.Cart;
import com.example.fumagalliproject.Class.Product;
import com.example.fumagalliproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;
import java.util.List;

public class MyCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);

        Product product0 = new Product("Penne","Italia","0,5 Kg");
        Product product1 = new Product("Lampadina","Germania","1");

        ListView listView = (ListView)findViewById(R.id.lstCartProduct);

        List<Cart> list = new LinkedList<Cart>();
        list.add(new Cart("Eurospin","Via Schiaffino", "Casette d'Ete", "3355664465","15,00 €", product0, "4"));
        list.add(new Cart("Si con Te","Via Rosselli", "Casette d'Ete", "3355664465","12,00 €", product1, "3"));

        AdapterCartProduct adapterCartProduct = new AdapterCartProduct(this, R.layout.item_cartproduct, list);
        listView.setAdapter(adapterCartProduct);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_shop:
                        Intent a = new Intent(MyCart.this, ChainList.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_cart:
                        break;
                    case R.id.navigation_profile:
                        Intent b = new Intent(MyCart.this,MyProfile.class);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });



    }
}
