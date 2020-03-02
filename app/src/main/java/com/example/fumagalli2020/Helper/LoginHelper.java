package com.example.fumagalli2020.Helper;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class LoginHelper {
    public void login(EditText edtEmail, TextInputLayout tilPassword, OnCompleteListener listener){
        String email,pwd;
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        email = edtEmail.getText().toString();
        pwd = tilPassword.getEditText().getText().toString();
        if(email.trim().isEmpty())
            edtEmail.setError("");
        else if(pwd.trim().isEmpty())
            tilPassword.getEditText().setError("");
        else{
            mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(listener);
        }
    }
}
