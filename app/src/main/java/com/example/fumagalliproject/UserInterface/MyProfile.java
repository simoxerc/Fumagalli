package com.example.fumagalliproject.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalliproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_shop:
                        Intent a = new Intent(MyProfile.this, ChainList.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_cart:
                        Intent b = new Intent(MyProfile.this, CartList.class);
                        startActivity(b);
                        break;
                    case R.id.navigation_profile:
                        break;
                }
                return false;
            }
        });

    }

}
