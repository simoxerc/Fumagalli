package com.example.fumagalli2020.Helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class CustomerInfoHelper {

    private final Pattern patternNumberPhone = Pattern.compile("^3\\d{2}[. ]??\\d{6,7}([,;]/^((00|" + ")39[. ]??)??3\\d{2}[. ]??\\d{6,7})*$");
    private final Pattern patternEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

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

    public void ModifyCustomerInfo(final EditText edtCustEmail, EditText edtCustMobile, final Context context, Button btnSaveChanges, Button btnToCustModifyData, final String oldEmail){
        if(CheckModifiedCustomerInfo(edtCustEmail,edtCustMobile)){
            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String newMobile = edtCustMobile.getText().toString();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
            try {
                databaseReference.child("mobile").setValue(newMobile);
                if (!oldEmail.equals(edtCustEmail.getText().toString())) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("PASSWORD");
                    alertDialog.setMessage("Per modificare l'email, inserire la password");
                    final EditText input = new EditText(context);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    alertDialog.setView(input);
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            if (input.getText().toString().trim().isEmpty())
                                input.setError("Inserisci la password per proseguire");
                            else {
                                AuthCredential authCredential = EmailAuthProvider
                                        .getCredential(oldEmail, input.getText().toString());
                                user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                            currentUser.updateEmail(edtCustEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> taskb) {
                                                    if (taskb.isSuccessful()) {
                                                        Toast.makeText(context, "Dati aggiornati con successo!", Toast.LENGTH_LONG).show();
                                                        dialog.dismiss();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });

                            }
                        }
                    });
                    alertDialog.show();
                }
                edtCustMobile.setEnabled(false);
                edtCustEmail.setEnabled(false);
                btnSaveChanges.setVisibility(View.INVISIBLE);
                btnToCustModifyData.setVisibility(View.VISIBLE);
            }catch (Exception e){
                Toast.makeText(context,"Errore nell'aggiornamento dei dati.",Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean CheckModifiedCustomerInfo(EditText edtCustEmail,EditText edtCustMobile){
        boolean check = true;
        if(edtCustEmail.getText().toString().trim().isEmpty()){
            edtCustEmail.setError("Email obbligatoria per essere modificata.");
            check = false;
        }
        if(!patternEmail.matcher(edtCustEmail.getText().toString()).matches()){
            edtCustEmail.setError("Email non valida");
            check = false;
        }
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
