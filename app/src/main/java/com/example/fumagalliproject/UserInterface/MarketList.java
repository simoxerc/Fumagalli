package com.example.fumagalliproject.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalliproject.Class.Market;
import com.example.fumagalliproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;
import java.util.List;

public class MarketList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketlist);

        ListView listView = (ListView)findViewById(R.id.lstMarket);

        List<Market> list = new LinkedList<Market>();
        list.add(new Market("Via Schiaffino","Sant'Elpidio a mare 63811", "3384725693"));
        list.add(new Market("Via Rimembranza","Sant'Elpidio a mare 63811", "3384725693"));
        list.add(new Market("Via Fratelli Rosselli","Sant'Elpidio a mare 63811", "3384725693"));

        AdapterMarketList adapterMarketList = new AdapterMarketList(this, R.layout.item_marketlist, list);
        listView.setAdapter(adapterMarketList);

        Button button1 = findViewById(R.id.avan);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent(MarketList.this,CategoryList.class);
                startActivity(d);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_shop:
                        break;
                    case R.id.navigation_cart:
                        Intent a = new Intent(MarketList.this, CartList.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_profile:
                        Intent b = new Intent(MarketList.this,MyProfile.class);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });

    }

}
