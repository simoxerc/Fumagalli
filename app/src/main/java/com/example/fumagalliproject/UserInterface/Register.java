package com.example.fumagalliproject.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalliproject.R;

public class Register extends AppCompatActivity {

    Button btnBack;
    Button btnRegistration;
    TextView tvReadTermsLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnBack = findViewById(R.id.btnRegBackToLogin);
        btnRegistration = findViewById(R.id.btnRegRegistration);
        tvReadTermsLink = findViewById(R.id.tvRegReadTerms);

    }

    @Override
    protected void onStart(){
        super.onStart();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backtologin();
            }
        });

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registration();
            }
        });

        tvReadTermsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gototerms();
            }
        });

    }

    private void backtologin(){
        Intent loginIntent = new Intent(this, Login.class);
        startActivity(loginIntent);
    }

    private void registration(){}

    private void gototerms(){
        Intent termsIntent = new Intent(this,Terms.class);
        startActivity(termsIntent);
    }
}
