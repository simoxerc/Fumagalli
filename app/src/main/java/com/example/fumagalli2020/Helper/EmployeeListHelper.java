package com.example.fumagalli2020.Helper;

import androidx.annotation.NonNull;

import com.example.fumagalli2020.AdapterEmployeeList;
import com.example.fumagalli2020.Class.Employee;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class EmployeeListHelper {

    public void loadEmployee(final List<Employee> lstEmployee, final String currentMarketId, final AdapterEmployeeList adapterEmployeeList){

        DatabaseReference databasereference = FirebaseDatabase.getInstance().getReference().child("Users");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.child("marketId").exists()) {
                        if ((Objects.requireNonNull(ds.child("type").getValue(String.class)).equals("1") || Objects.requireNonNull(ds.child("type").getValue(String.class)).equals("2"))
                                && (Objects.requireNonNull(ds.child("marketId").getValue(String.class)).equals(currentMarketId))) {
                            lstEmployee.add(new Employee(ds.child("name").getValue(String.class), ds.child("surname").getValue(String.class), ds.child("mobile").getValue(String.class),
                                    ds.child("marketId").getValue(String.class), ds.child("type").getValue(String.class), ds.child("employeeId").getValue(String.class)));
                        }
                    }
                }
                adapterEmployeeList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databasereference.addListenerForSingleValueEvent(valueEventListener);
    }

}
