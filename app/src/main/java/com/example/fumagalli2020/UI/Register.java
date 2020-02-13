package com.example.fumagalli2020.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fumagalli2020.Helper.RegisterHelper;
import com.example.fumagalli2020.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class Register extends AppCompatActivity {

    private Button btnBack;
    private Button btnRegistration;
    private TextView tvReadTermsLink;
    private EditText edtName, edtSurname, edtBirthdate, edtMobile, edtFiscalCode, edtEmail;
    private TextInputLayout tilPassword;
    private CheckBox chbReadTerms;
    private FirebaseAuth mAuth;
    private final Calendar calendar = Calendar.getInstance();
    private DatePickerDialog dateBirth;
    public RegisterHelper reghelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnBack = findViewById(R.id.btnRegBackToLogin);
        btnRegistration = findViewById(R.id.btnRegRegistration);
        tvReadTermsLink = findViewById(R.id.tvResetPassword);
        edtName = findViewById(R.id.edtRegName);
        edtSurname = findViewById(R.id.edtRegSurname);
        edtBirthdate = findViewById(R.id.edtRegBirthDate);
        edtMobile = findViewById(R.id.edtRegMobile);
        edtFiscalCode = findViewById(R.id.edtRegFiscalCode);
        edtEmail = findViewById(R.id.edtRegEmail);
        tilPassword = findViewById(R.id.edtRegPassword);
        chbReadTerms = findViewById(R.id.chbRegReadTerms);
        reghelper = new RegisterHelper();
        mAuth = FirebaseAuth.getInstance();
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
                if(reghelper.registration(edtName,edtSurname,edtBirthdate,edtMobile,edtFiscalCode,edtEmail,tilPassword,chbReadTerms)){
                    Toast.makeText(Register.this,"",Toast.LENGTH_LONG).show();
                    backtologin();
                }else{
                    Toast.makeText(Register.this,"",Toast.LENGTH_LONG).show();
                }
            }
        });

        tvReadTermsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gototerms();
            }
        });

        edtBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtBirthdate.setError(null);
                setDate();
                dateBirth.show();
            }
        });

        chbReadTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chbReadTerms.setError(null);
            }
        });

    }

    private void backtologin(){
        Intent loginIntent = new Intent(this, Login.class);
        startActivity(loginIntent);
    }


    private void setDate(){
        calendar.setTimeInMillis(System.currentTimeMillis());
        dateBirth = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edtBirthdate.setText((dayOfMonth + "/" + (month + 1) +  "/" + year).toString());
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(calendar.YEAR,-18);
        dateBirth.getDatePicker().setMaxDate(calendar.getTimeInMillis());
    }
    

    private void gototerms(){
        Intent intent = new Intent(this,ReadTerms.class);
        startActivity(intent);
    }
}
