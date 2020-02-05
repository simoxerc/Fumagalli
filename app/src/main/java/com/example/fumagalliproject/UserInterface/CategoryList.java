package com.example.fumagalliproject.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalliproject.Class.Category;
import com.example.fumagalliproject.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;
import java.util.List;

public class CategoryList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorylist);

        ListView listView = (ListView)findViewById(R.id.lstCategory);

        List<Category> list = new LinkedList<Category>();

        list.add(new Category("Cura Persona","Crema Viso", "Shampoo", "BagnoSchiuma"));
        list.add(new Category("Animali Domestici","Alimenti per cani", "Alimenti per gatti", "Sabbia per lettiera"));
        list.add(new Category("Carne","Manzo", "Pollo", "Coniglio"));

        AdapterCategoryList adapterCategoryList = new AdapterCategoryList(this, R.layout.item_categorylist, list);
        listView.setAdapter(adapterCategoryList);

        Button button1 = findViewById(R.id.avanti);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent (CategoryList.this, ProductList.class);
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
                        Intent a = new Intent(CategoryList.this, CartList.class);
                        startActivity(a);
                        break;
                    case R.id.navigation_profile:
                        Intent b = new Intent(CategoryList.this,MyProfile.class);
                        startActivity(b);
                        break;
                }
                return false;
            }
        });

    }

}
