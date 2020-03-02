package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.Helper.LoginHelper;
import com.example.fumagalli2020.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button btnLogin;
    Button btnToRegistration;
    private TextView tvReset,tvAdminInfo;
    private EditText edtEmail;
    public Intent intent;
    private TextInputLayout tilPassword;
    public LoginHelper helper;
    protected boolean checkLogin;
    protected OnCompleteListener onCompleteListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        btnLogin = findViewById(R.id.btnLogEnter);
        btnToRegistration = findViewById(R.id.btnLogToRegistration);
        edtEmail = findViewById(R.id.edtLogEmail);
        tilPassword = findViewById(R.id.edtLogPassword);
        tvReset = findViewById(R.id.tvResetPassword);
        tvAdminInfo = findViewById(R.id.tvAdminInfo);
        helper = new LoginHelper();

        onCompleteListener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())
                    checkLogin = true;
                else
                    checkLogin = false;
                authentication();
            }
        };

    }

    @Override
    protected void onStart(){
        super.onStart();

        btnToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoregistration();
            }
        });

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoresetpassword();
            }
        });

        tvAdminInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoadmininfo();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.login(edtEmail,tilPassword,onCompleteListener);
            }
        });

    }

    private void authentication() {
        if(checkLogin) {
            Toast.makeText(Login.this,"Accesso effettuato",Toast.LENGTH_LONG).show();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            DatabaseReference mDBRef;
            mDBRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        if(ds.getKey().equals("type")){
                            String tipo = ds.getValue(String.class);
                            if(tipo.equals("0"))
                                intent = new Intent(Login.this,RegisterMarket.class);
                            else if(tipo.equals("1"))
                                intent = new Intent(Login.this,EmployeeList.class);
                            else if(tipo.equals("2"))
                                intent = new Intent(Login.this,CategoryList.class);
                            else if(tipo.equals("3"))
                                intent = new Intent(Login.this,CustChainList.class);
                            else
                                intent = new Intent(Login.this,Login.class);
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
            Toast.makeText(Login.this,"Nome Utente e/o Password errati",Toast.LENGTH_LONG).show();
        }
    }

    private void gotonewui(Intent intent) {
        startActivity(intent);
    }

    private void gotoregistration(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void gotoresetpassword(){
        Intent intent = new Intent(this,ResetPassword.class);
        startActivity(intent);
    }

    private void gotoadmininfo(){
        Intent intent = new Intent(this,AdminInfo.class);
        startActivity(intent);
    }

}
