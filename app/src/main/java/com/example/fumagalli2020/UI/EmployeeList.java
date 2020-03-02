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

import com.example.fumagalli2020.AdapterEmployeeList;
import com.example.fumagalli2020.Class.Employee;
import com.example.fumagalli2020.Helper.EmployeeListHelper;
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

public class EmployeeList extends AppCompatActivity {

    protected ListView listView;
    protected DatabaseReference databaseReference;
    protected List<Employee> lstEmployee;
    protected EmployeeListHelper employeeListHelper;
    protected String currentMarketId;
    protected Button btnAddEmployee;
    protected AdapterEmployeeList adapterEmployeeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees_list);

        btnAddEmployee = findViewById(R.id.btnLstEmpAddEmployee);

        lstEmployee = new LinkedList<Employee>();

        employeeListHelper = new EmployeeListHelper();
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("marketId");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentMarketId = dataSnapshot.getValue(String.class);
                employeeListHelper.loadEmployee(lstEmployee, currentMarketId, adapterEmployeeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.adminMrkt_menu);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.admMrkt_navigation_employee:
                        break;
                    case R.id.admMrkt_navigation_infomrkt:
                        Bundle bundle = new Bundle();
                        bundle.putString("marketId",currentMarketId);
                        Intent a = new Intent(EmployeeList.this, MarketInfo.class);
                        a.putExtras(bundle);
                        startActivity(a);
                        break;
                }
                return false;
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        listView = (ListView) findViewById(R.id.employeeList);

        adapterEmployeeList = new AdapterEmployeeList(this, R.layout.item_list_employee, lstEmployee);
        listView.setAdapter(adapterEmployeeList);

        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoregemployee();
            }
        });

    }

    private void gotoregemployee(){
        Bundle bundle = new Bundle();
        bundle.putString("marketId",currentMarketId);
        bundle.putInt("source",1);
        Intent intent = new Intent(this,RegisterEmployee.class);
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
