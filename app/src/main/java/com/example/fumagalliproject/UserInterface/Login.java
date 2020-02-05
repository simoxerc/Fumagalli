package com.example.fumagalliproject.UserInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fumagalliproject.R;

public class Login extends AppCompatActivity {

    Button btnLogin;
    Button btnToRegistration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        btnLogin = findViewById(R.id.btnLogEnter);
        btnToRegistration = findViewById(R.id.btnLogToRegistration);

    }

    @Override
    protected void onStart(){
        super.onStart();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeactivity1();
            }
        });


    }

    private void login(){
        changeactivity0();
    }

    private void changeactivity0(){
        Intent intent = new Intent(this , ChainList.class);

        startActivity(intent);
    }

    private void changeactivity1(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
