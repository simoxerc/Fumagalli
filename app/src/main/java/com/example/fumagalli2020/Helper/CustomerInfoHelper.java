package com.example.fumagalli2020.Helper;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class CustomerInfoHelper {

    private final Pattern patternNumberPhone = Pattern.compile("^3\\d{2}[. ]??\\d{6,7}([,;]/^((00|" + ")39[. ]??)??3\\d{2}[. ]??\\d{6,7})*$");

    public void LoadCustomerInfo(EditText edtCustEmail, final EditText edtCustName, final EditText edtCustSurname, final EditText edtCustFiscalCode, final EditText edtCustBirthDate, final EditText edtCustMobile){

        String customerEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        edtCustEmail.setText(customerEmail);

        final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser);
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String customerName = dataSnapshot.child("name").getValue(String.class);
                String customerSurname = dataSnapshot.child("surname").getValue(String.class);
                String customerFiscalCode = dataSnapshot.child("fiscalcode").getValue(String.class);
                String customerBirthDate = dataSnapshot.child("birthdate").getValue(String.class);
                String customerMobile = dataSnapshot.child("mobile").getValue(String.class);

                edtCustName.setText(customerName);
                edtCustSurname.setText(customerSurname);
                edtCustFiscalCode.setText(customerFiscalCode);
                edtCustBirthDate.setText(customerBirthDate);
                edtCustMobile.setText(customerMobile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void ModifyCustomerInfo(final EditText edtCustMobile, final Context context, final Button btnSaveChanges, final Button btnToCustModifyData){
        if(CheckModifiedCustomerInfo(edtCustMobile)){
            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String newMobile = edtCustMobile.getText().toString();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
            try {
                databaseReference.child("mobile").setValue(newMobile).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context,"Dati aggiornati correttamente!",Toast.LENGTH_LONG).show();
                        edtCustMobile.setEnabled(false);
                        btnSaveChanges.setVisibility(View.INVISIBLE);
                        btnToCustModifyData.setVisibility(View.VISIBLE);
                    }
                });
            }catch (Exception e){
                Toast.makeText(context,"Errore nell'aggiornamento dei dati.",Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean CheckModifiedCustomerInfo(EditText edtCustMobile){
        boolean check = true;
        if(edtCustMobile.getText().toString().trim().isEmpty()){
            edtCustMobile.setError("Numero obbligatorio per essere modificato");
            check = false;
        }
        if(!patternNumberPhone.matcher(edtCustMobile.getText().toString()).matches()){
            edtCustMobile.setError("Numero non valido");
            check = false;
        }
        return check;
    }
}
