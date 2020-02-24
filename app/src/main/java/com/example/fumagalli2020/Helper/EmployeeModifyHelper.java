package com.example.fumagalli2020.Helper;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class EmployeeModifyHelper {

    private final Pattern patternNumberPhone = Pattern.compile("^3\\d{2}[. ]??\\d{6,7}([,;]/^((00|" + ")39[. ]??)??3\\d{2}[. ]??\\d{6,7})*$");

    public void UpdateEmployee(final String employeeId, final EditText edtPhone, final String type, final OnCompleteListener onCompleteListener) {
        if (checkfield(edtPhone)) {
            FirebaseDatabase.getInstance().getReference().child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.hasChild("employeeId")) {
                            if (ds.child("employeeId").getValue(String.class).equals(employeeId)) {
                                FirebaseDatabase.getInstance().getReference().child("Users").child(ds.getKey()).child("mobile").setValue(edtPhone.getText().toString().trim());
                                FirebaseDatabase.getInstance().getReference().child("Users").child(ds.getKey()).child("type").setValue(type).addOnCompleteListener(onCompleteListener);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    private boolean checkfield(EditText edtPhone){
        boolean check = true;

        if(!patternNumberPhone.matcher(edtPhone.getText().toString()).matches()){
            edtPhone.setError("Numero telefono non valido");
            check = false;
        }

        return check;
    }

}
