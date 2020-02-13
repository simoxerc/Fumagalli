package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.Helper.ResetPasswordHelper;
import com.example.fumagalli2020.R;

public class ResetPassword extends AppCompatActivity {

    private EditText edtResEmail;
    private Button btnResPwd;
    private ResetPasswordHelper resetPasswordHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpwd);

        edtResEmail = findViewById(R.id.edtResetEmail);
        btnResPwd = findViewById(R.id.btnResetPassword);
    }

    @Override
    protected void onStart() {
        super.onStart();

        btnResPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resetPasswordHelper.sendreset(edtResEmail)){
                    Toast.makeText(ResetPassword.this,"",Toast.LENGTH_LONG).show();
                    backtologin();
                }else{
                    Toast.makeText(ResetPassword.this,"",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void backtologin(){
        Intent loginIntent = new Intent(this, Login.class);
        startActivity(loginIntent);
    }
}
