package com.example.fumagalli2020.Helper;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordHelper {
    private FirebaseAuth mAuth;

    public boolean sendreset(EditText edtResEmail){
        String email;
        final boolean[] ret = {false};
        email = edtResEmail.getText().toString();
        if(!email.trim().isEmpty()) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                ret[0] = true;
                            }
                        }
                    });
        }else{
            edtResEmail.setError("Email obbligatoria.");
        }
        return ret[0];
    }
}
