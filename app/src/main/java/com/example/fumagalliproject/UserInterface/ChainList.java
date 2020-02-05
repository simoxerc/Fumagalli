package com.example.fumagalliproject.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalliproject.Class.Chain;
import com.example.fumagalliproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;
import java.util.List;


public class ChainList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chainlist);

        ListView listView = (ListView)findViewById(R.id.lstChain);

        List<Chain> list = new LinkedList<Chain>();
        list.add(new Chain("Eurospin"));
        list.add(new Chain("Sì con te"));
        list.add(new Chain("Auchan"));

        AdapterChainList adapterChainList = new AdapterChainList(this, R.layout.item_chainlist, list);
        listView.setAdapter(adapterChainList);

        Button button = findViewById(R.id.next1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(ChainList.this, MarketList.class);
                startActivity(c);
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
                        Intent a = new Intent(ChainList.this, CartList.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_profile:
                        Intent b = new Intent(ChainList.this,MyProfile.class);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.itmMyOrders:
                Intent intentOrders = new Intent(this,MyOrders.class);
                startActivity(intentOrders);
                return true;
            case R.id.itmTerms:
                Intent intentTerms = new Intent(this,Terms.class);
                startActivity(intentTerms);
                return true;
            case R.id.itmLogout:
                Intent intentLogout = new Intent(this,Login.class);
                startActivity(intentLogout);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
