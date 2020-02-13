package com.example.fumagalli2020.Helper;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fumagalli2020.Class.Customer;
import com.example.fumagalli2020.UI.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterHelper {

    private final Pattern patternFiscalCode = Pattern.compile("^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$");
    private final Pattern patternNumberPhone = Pattern.compile("^3\\d{2}[. ]??\\d{6,7}([,;]/^((00|" + ")39[. ]??)??3\\d{2}[. ]??\\d{6,7})*$");
    private final Pattern patternEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public boolean registration(EditText edtName, EditText edtSurname, EditText edtBirthdate, EditText edtMobile, EditText edtFiscalCode, final EditText edtEmail, TextInputLayout tilPassword, CheckBox chbReadTerms){
        String name,surname,birthdate,mobile,fiscalcode,email,pwd;
        final FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        name = edtName.getText().toString();
        surname = edtSurname.getText().toString();
        birthdate = edtBirthdate.getText().toString();
        mobile = edtMobile.getText().toString();
        fiscalcode = edtFiscalCode.getText().toString();
        email = edtEmail.getText().toString();
        pwd = tilPassword.getEditText().getText().toString();
        if(checkfield(edtName,edtSurname,edtBirthdate,edtMobile,edtFiscalCode,edtEmail,tilPassword,chbReadTerms)){
            final Customer customer = new Customer(name,surname,birthdate,mobile,fiscalcode,"3");
            mAuth.createUserWithEmailAndPassword(email,pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                final DatabaseReference mDBRef = FirebaseDatabase.getInstance().getReference();
                                mDBRef.child("Users").child(mAuth.getCurrentUser().getUid())
                                        .setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> taskb) {
                                        if(!taskb.isSuccessful()){
                                          mAuth.getCurrentUser().delete();
                                        }
                                    }
                                });
                            }else{
                                edtEmail.setError("Email in uso");
                            }
                        }
                    });
        }
        if(mAuth.getCurrentUser() == null)
            return false;
        else
            return true;
    }


    private boolean checkfield(EditText edtName,EditText edtSurname,EditText edtBirthdate,EditText edtMobile,EditText edtFiscalCode,EditText edtEmail,TextInputLayout tilPassword, CheckBox chbReadTerms){
        boolean registrable = true;

        if (edtName.getText().toString().trim().equals("")){
            edtName.setError("");
            registrable = false;
        }

        if(edtSurname.getText().toString().trim().equals("")){
            edtSurname.setError("");
            registrable = false;
        }

        if(edtBirthdate.getText().toString().trim().equals("")){
            edtBirthdate.setError("");
            registrable = false;
        }

        if(!patternNumberPhone.matcher(edtMobile.getText().toString()).matches()){
            edtMobile.setError("");
            registrable = false;
        }

        if(!patternFiscalCode.matcher(edtFiscalCode.getText().toString()).matches()){
            edtFiscalCode.setError("");
            registrable = false;
        }

        if(!patternEmail.matcher(edtEmail.getText().toString()).matches()){
            edtEmail.setError("");
            registrable = false;
        }

        if(tilPassword.getEditText().getText().toString().length() < 8){
            tilPassword.getEditText().setError("");
            registrable = false;
        }

        if(!chbReadTerms.isChecked()){
            chbReadTerms.setError("");
            registrable = false;
        }

        return registrable;
    }


}
