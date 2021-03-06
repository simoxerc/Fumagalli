package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.AdapterCategoryList;
import com.example.fumagalli2020.Class.Category;
import com.example.fumagalli2020.Helper.CategoryListHelper;
import com.example.fumagalli2020.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class CategoryList extends AppCompatActivity {
    protected Button btnAddCategory;
    protected ListView listView;
    protected List<Category> lstCategory;
    protected String currentMarketId;
    protected AdapterCategoryList adapterCategoryList;
    protected CategoryListHelper categoryListHelper;
    protected DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        btnAddCategory = findViewById(R.id.btnLstAddCategory);
        lstCategory = new LinkedList<Category>();
        categoryListHelper = new CategoryListHelper();
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("marketId");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentMarketId = dataSnapshot.getValue(String.class);
                categoryListHelper.LoadCategory(lstCategory,currentMarketId,adapterCategoryList);
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
                        break;
                    case R.id.logistic_orders:
                        menuItem.setChecked(true);
                        Intent intent = new Intent(CategoryList.this,LogisticOrders.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        listView = findViewById(R.id.categoryList);
        adapterCategoryList = new AdapterCategoryList(this, R.layout.item_list_category, lstCategory);
        listView.setAdapter(adapterCategoryList);

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoregcategory();
            }
        });
    }

    private void gotoregcategory(){
        Bundle bundle = new Bundle();
        bundle.putString("marketId",currentMarketId);
        Intent intent = new Intent(this,RegisterCategory.class);
        intent.putExtras(bundle);
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
                Intent intentAdmLogout = new Intent(this,Login.class);
                startActivity(intentAdmLogout);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
