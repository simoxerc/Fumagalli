package com.example.fumagalli2020.Helper;

import android.content.Context;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fumagalli2020.Class.Market;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegisterMarketHelper {
    private DatabaseReference mDBRef;

    public void LoadChain(final Map chainMap, final List chainList){
        mDBRef = FirebaseDatabase.getInstance().getReference().child("Chain");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    chainMap.put(ds.getKey(),ds.child("name").getValue().toString());
                    chainList.add(ds.child("name").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDBRef.addListenerForSingleValueEvent(valueEventListener);
    }

    public String RegisterMarket(Spinner chain, final EditText edtMarketName, EditText edtMarketCity, EditText edtMarketAddress,
                                 EditText edtMarketCAP, EditText edtMarketPhone, EditText edtMarketEmail, String[][] businessHour, boolean[] continuedSchedule, boolean[] closedDays, final Context context, Map chainMap){
        String marketid = "";
        if(checkfield(chain,edtMarketName,edtMarketCity,edtMarketAddress,edtMarketCAP,edtMarketPhone,edtMarketEmail,businessHour)){
            String name = edtMarketName.getText().toString();
            String address = edtMarketAddress.getText().toString()+", "+edtMarketCity.getText().toString()+" "+edtMarketCAP.getText().toString();
            String email = edtMarketEmail.getText().toString();
            String phone = edtMarketPhone.getText().toString();
            List<String> indexes = new ArrayList<String>(chainMap.keySet());
            marketid = indexes.get((int) chain.getSelectedItemId())+edtMarketCAP.getText().toString()+edtMarketPhone.getText().toString();
            final Market market = new Market(name,address,phone,email, marketid, indexes.get((int) chain.getSelectedItem()),businessHour,continuedSchedule,closedDays);
            mDBRef = FirebaseDatabase.getInstance().getReference().child("Market").child(marketid);
            mDBRef.setValue(market).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(context,"Negozio inserito",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(context,"Problema in inserimento al database",Toast.LENGTH_LONG).show();
                        changem(edtMarketName);
                    }
                }
            });
        }
        if(edtMarketName.getText().equals("Registrato"))
            return marketid;
        else
            return "";
    }

    private boolean checkfield(Spinner chain, EditText edtMarketName, EditText edtMarketCity, EditText edtMarketAddress,
                              EditText edtMarketCAP, EditText edtMarketPhone, EditText edtMarketEmail, String[][] businessHour){
        boolean check = true;
        if(edtMarketName.getText().toString().trim().isEmpty()){
            edtMarketName.setError("Nome Obbligatorio");
            check = false;
        }
        if(edtMarketCity.getText().toString().trim().isEmpty()){
            edtMarketCity.setError("Città obbligatoria");
            check = false;
        }
        if(edtMarketAddress.getText().toString().trim().isEmpty()){
            edtMarketAddress.setError("Indirizzo Obbligatorio");
            check = false;
        }
        if(edtMarketCAP.getText().toString().trim().isEmpty()){
            edtMarketCAP.setError("CAP Obbligatorio");
            check = false;
        }
        if(edtMarketPhone.getText().toString().trim().isEmpty()){
            edtMarketPhone.setError("Telefono Obbligatorio");
            check = false;
        }
        if(edtMarketEmail.getText().toString().trim().isEmpty()){
            edtMarketEmail.setError("Email obbligatoria");
            check = false;
        }
        if(chain.getSelectedItem().toString().trim().isEmpty()){
            TextView errorText = (TextView)chain.getSelectedView();
            errorText.setError("Seleziona una catena");
        }
        return check;
    }

    private void changem(EditText edtMarketName){
        edtMarketName.setText("Registrato");
    }
}
