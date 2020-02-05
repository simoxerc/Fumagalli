package com.example.fumagalliproject.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalliproject.Class.Cart;
import com.example.fumagalliproject.Class.Product;
import com.example.fumagalliproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;
import java.util.List;

public class CartList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartlist);

        ListView listView = (ListView)findViewById(R.id.lstCart);

        List<Cart> list = new LinkedList<Cart>();
        list.add(new Cart("Eurospin","Via Schiaffino", "Casette d'Ete", "3355664465","15,00 €",new Product("","",""),"0"));
        list.add(new Cart("Si con Te","Via Rosselli", "Casette d'Ete", "3355664465","12,00 €",new Product("","",""),"0"));
        list.add(new Cart("Auchan","Via Turati", "Casette d'Ete", "3355664465","11,00 €",new Product("","",""),"0"));

        AdapterCartList adapterCartList = new AdapterCartList(this, R.layout.item_cartlist, list);
        listView.setAdapter(adapterCartList);

        Button button = findViewById(R.id.next0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(CartList.this, MyCart.class);
                startActivity(c);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_shop:
                        Intent a = new Intent(CartList.this, ChainList.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_cart:
                        break;
                    case R.id.navigation_profile:
                        Intent b = new Intent(CartList.this,MyProfile.class);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });


    }

}
