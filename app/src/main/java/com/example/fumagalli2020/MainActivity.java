package com.example.fumagalli2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.fumagalli2020.Class.Category;
import com.example.fumagalli2020.Helper.LoginHelper;
import com.example.fumagalli2020.UI.CategoryList;
import com.example.fumagalli2020.UI.CustChainList;
import com.example.fumagalli2020.UI.EmployeeList;
import com.example.fumagalli2020.UI.Login;
import com.example.fumagalli2020.UI.RegisterMarket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private FirebaseAuth mAuth;
    private DatabaseReference mDBRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getUid() != null){
            mDBRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid());
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        if(ds.getKey().equals("type")){
                            String tipo = ds.getValue(String.class);
                            if(tipo.equals("0"))
                                intent = new Intent(MainActivity.this,RegisterMarket.class);
                            else if(tipo.equals("1"))
                                intent = new Intent(MainActivity.this,EmployeeList.class);
                            else if(tipo.equals("2"))
                                intent = new Intent(MainActivity.this, CategoryList.class);
                            else if(tipo.equals("3"))
                                intent = new Intent(MainActivity.this, CustChainList.class);
                            else
                                intent = new Intent(MainActivity.this,Login.class);
                            gotonewui(intent);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };
            mDBRef.addListenerForSingleValueEvent(eventListener);
        }else{
            intent = new Intent(MainActivity.this,Login.class);
            startActivity(intent);
        }
    }

    private void gotonewui(Intent intent){startActivity(intent);}

}
