package com.example.fumagalli2020.Helper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fumagalli2020.Class.Market;
import com.example.fumagalli2020.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterMarketHelper {
    private DatabaseReference mDBRef;


//    private final Pattern patternNumberPhone = Pattern.compile("^3\\d{2}[. ]??\\d{6,7}([,;]/^((00|" + ")39[. ]??)??3\\d{2}[. ]??\\d{6,7})*$");
    private final Pattern patternNumberPhone = Pattern.compile("^[0-9]{9,10}$");
    private final Pattern patternEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public void LoadChain(final Map chainMap, final List chainList, final Spinner spnChain, final Context context){
        mDBRef = FirebaseDatabase.getInstance().getReference().child("Chain");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    chainMap.put(ds.getKey(),ds.child("name").getValue().toString());
                    chainList.add(ds.child("name").getValue().toString());
                }
                ArrayAdapter<String> adptChain = new ArrayAdapter<String>(context, R.layout.first_custom_spinner,chainList);
                adptChain.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnChain.setAdapter(adptChain);
                spnChain.setSelection(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDBRef.addListenerForSingleValueEvent(valueEventListener);
    }

    public String RegisterMarket(Spinner chain, final EditText edtMarketName, EditText edtMarketCity, EditText edtMarketAddress,
                                 EditText edtMarketCAP, EditText edtMarketPhone, EditText edtMarketEmail, List<String> businessHour, List<Boolean> continuedSchedule, List<Boolean> closedDays,
                                 final Context context, Map chainMap, OnCompleteListener<Void> onCompleteListener){
        String marketId = "";
        if(checkfield(chain,edtMarketName,edtMarketCity,edtMarketAddress,edtMarketCAP,edtMarketPhone,edtMarketEmail,businessHour, continuedSchedule, closedDays,context)){
            String name = edtMarketName.getText().toString();
            String address = edtMarketAddress.getText().toString()+", "+edtMarketCity.getText().toString()+" "+edtMarketCAP.getText().toString();
            String email = edtMarketEmail.getText().toString();
            String phone = edtMarketPhone.getText().toString();
            String indexchain = getkey(chainMap,chain.getSelectedItem().toString());
            marketId = indexchain + edtMarketCAP.getText().toString()+edtMarketPhone.getText().toString();
            final Market market = new Market(name,address,phone,email, marketId, indexchain,businessHour,continuedSchedule,closedDays);
            mDBRef = FirebaseDatabase.getInstance().getReference().child("Market").child(marketId);
            mDBRef.setValue(market).addOnCompleteListener(onCompleteListener);
        }
        return marketId;
    }

    private boolean checkfield(Spinner chain, EditText edtMarketName, EditText edtMarketCity, EditText edtMarketAddress,
                              EditText edtMarketCAP, EditText edtMarketPhone, EditText edtMarketEmail,
                               List<String> businessHour, List<Boolean> continuedSchedule, List<Boolean> closedDays, Context context){
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
        if(!patternNumberPhone.matcher(edtMarketPhone.getText().toString()).matches()){
            edtMarketPhone.setError("Numero non valido");
            check = false;
        }
        if(edtMarketEmail.getText().toString().trim().isEmpty()){
            edtMarketEmail.setError("Email obbligatoria");
            check = false;
        }
        if(!patternEmail.matcher(edtMarketEmail.getText().toString()).matches()){
            edtMarketEmail.setError("Email non valida");
            check = false;
        }
        if(chain.getSelectedItem().toString().trim().isEmpty()){
            TextView errorText = (TextView)chain.getSelectedView();
            errorText.setError("Seleziona una catena");
        }
        for(int x = 0; x < 7; x++){
            if(!closedDays.get(x)){
                if(businessHour.get((x*4)).equals("0")){
                    check = false;
                }
                if(businessHour.get((x*4)+1).equals("0")){
                    check = false;
                }
                if(!continuedSchedule.get(x)){
                    if(businessHour.get((x*4)+2).equals("0")){
                        check = false;
                    }
                    if(businessHour.get((x*4)+3).equals("0")){
                        check = false;
                    }
                }
            }
            if(!check){
                Toast.makeText(context,"Dati non corretti, controllare l'inserimento degli orari",Toast.LENGTH_LONG).show();
            }
        }
        return check;
    }

    private String getkey(Map<String,String> map,String value){
        for(String key:map.keySet()){
            if(map.get(key).equals(value)){
                return key;
            }
        }
        return null;
    }

}
