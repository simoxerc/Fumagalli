package com.example.fumagalli2020.Helper;

import android.content.Context;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fumagalli2020.Class.Employee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterEmployeeHelper {

    private final Pattern patternNumberPhone = Pattern.compile("^3\\d{2}[. ]??\\d{6,7}([,;]/^((00|" + ")39[. ]??)??3\\d{2}[. ]??\\d{6,7})*$");
    private final Pattern patternEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public void registerEmployee(final EditText edtName, EditText edtSurname, EditText edtMobile, Spinner spnType, final EditText edtEmail,
                                 TextInputLayout tilPassword, String marketId, final Context context, final OnCompleteListener<Void> onCompleteListener) {
        String name,surname,mobile,email,pwd, type;
        final FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        name = edtName.getText().toString();
        surname = edtSurname.getText().toString();
        mobile = edtMobile.getText().toString();
        email = edtEmail.getText().toString();
        pwd = tilPassword.getEditText().getText().toString();
        if(spnType.getSelectedItem().toString().equals("Addetto alla Logistica"))
            type = "2";
        else
            type = "1";

        FirebaseAuth firebaseAuth;

        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("https://fumagalliids.firebaseio.com/")
                .setApiKey("AIzaSyBUkFYt7GTUpBsVSMQKv6FXOsuzcwgN6_g")
                .setApplicationId("fumagalliids").build();

        try { FirebaseApp myApp = FirebaseApp.initializeApp(context, firebaseOptions, "AnyAppName");
            firebaseAuth = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            firebaseAuth = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
        }


        if(checkfield(edtName,edtSurname,edtMobile,edtEmail,tilPassword)){
            final Employee employee= new Employee(name,surname,mobile,marketId,type,marketId+mobile);
            final FirebaseAuth finalFirebaseAuth = firebaseAuth;
            finalFirebaseAuth.createUserWithEmailAndPassword(email,pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                final DatabaseReference mDBRef = FirebaseDatabase.getInstance().getReference();
                                mDBRef.child("Users").child(finalFirebaseAuth.getCurrentUser().getUid())
                                        .setValue(employee).addOnCompleteListener(onCompleteListener);
                            }else{
                                edtEmail.setError("Email in uso");
                            }
                        }
                    });
        }
    }

    private boolean checkfield(EditText edtName, EditText edtSurname, EditText edtMobile, EditText edtEmail, TextInputLayout tilPassword){
        boolean registrable = true;

        if (edtName.getText().toString().trim().equals("")){
            edtName.setError("");
            registrable = false;
        }

        if(edtSurname.getText().toString().trim().equals("")){
            edtSurname.setError("");
            registrable = false;
        }


        if(!patternNumberPhone.matcher(edtMobile.getText().toString()).matches()){
            edtMobile.setError("");
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

        return registrable;
    }


}
