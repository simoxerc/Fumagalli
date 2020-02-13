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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button btnLogin;
    Button btnToRegistration;
    private TextView tvReset,tvAdminInfo;
    private EditText edtEmail;
    private TextInputLayout tilPassword;
    public LoginHelper helper;
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

    }

    @Override
    protected void onStart(){
        super.onStart();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(helper.login(edtEmail,tilPassword)){
                    Toast.makeText(Login.this,"",Toast.LENGTH_LONG).show();
                    gotoregistration();
                }else{
                    Toast.makeText(Login.this,"",Toast.LENGTH_LONG).show();
                }
            }
        });

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
