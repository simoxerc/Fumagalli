package com.example.fumagalli2020.Helper;

import android.content.Context;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.regex.Pattern;

public class MarketInfoHelper {
<<<<<<< HEAD
<<<<<<< HEAD
//    private final Pattern patternNumberPhone = Pattern.compile("^3\\d{2}[. ]??\\d{6,7}([,;]/^((00|" + ")39[. ]??)??3\\d{2}[. ]??\\d{6,7})*$");

    private final Pattern patternNumberPhone = Pattern.compile("^[0-9]{9,10}$");
=======
    private final Pattern patternNumberPhone = Pattern.compile("^3\\d{2}[. ]??\\d{6,7}([,;]/^((00|" + ")39[. ]??)??3\\d{2}[. ]??\\d{6,7})*$");
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
=======
    private final Pattern patternNumberPhone = Pattern.compile("^3\\d{2}[. ]??\\d{6,7}([,;]/^((00|" + ")39[. ]??)??3\\d{2}[. ]??\\d{6,7})*$");
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
    private final Pattern patternEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public void LoadMarketInfo(String marketId, final EditText edtMarketName, final EditText edtMarketCity, final EditText edtMarketAddress,
                               final EditText edtMarketCAP, final EditText edtMarketPhone, final EditText edtMarketEmail, final List<String> businessHour,
                               final List<Boolean> continuedSchedule, final List<Boolean> closedDays, final ArrayAdapter<String> adapterHours,
                               final List<Boolean> oldContinuedSchedule, final List<Boolean> oldClosedDays, final TextView[][] tabletime){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Market").child(marketId);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String marketName = dataSnapshot.child("name").getValue(String.class);
                edtMarketName.setText(marketName);
                String marketAddressTotal = dataSnapshot.child("address").getValue(String.class);
                String marketAddress = "";
                String marketCity = "";
                String marketCAP = "";
                int x = 0;
                for(x = 0;x<marketAddressTotal.length();x++){
                    char curr = marketAddressTotal.charAt(x);
                    if(curr == ','){
                        marketAddress = marketAddressTotal.substring(0,x);
                    }
                }
                int y;
                boolean check = true;
                for(y = marketAddress.length()+2;y<marketAddressTotal.length();y++){
                    char curr = marketAddressTotal.charAt(y);
                    if(Character.isDigit(curr) && check) {
                        marketCity = marketAddressTotal.substring(marketAddress.length()+2, y);
                        marketCAP = marketAddressTotal.substring(y);
                        check = false;
                    }
                }
                edtMarketAddress.setText(marketAddress);
                edtMarketCity.setText(marketCity);
                edtMarketCAP.setText(marketCAP);
                String marketPhone = dataSnapshot.child("number").getValue(String.class);
                edtMarketPhone.setText(marketPhone);
                String marketEmail = dataSnapshot.child("email").getValue(String.class);
                edtMarketEmail.setText(marketEmail);
                for(DataSnapshot dsbusiness:dataSnapshot.child("businessHour").getChildren()){
                    businessHour.add(dsbusiness.getValue(String.class));
                }
                for(DataSnapshot dscontinued:dataSnapshot.child("continuedSchedule").getChildren()){
                    continuedSchedule.add(dscontinued.getValue(Boolean.class));
                    oldContinuedSchedule.add(dscontinued.getValue(Boolean.class));
                }
                for(DataSnapshot dsclosed:dataSnapshot.child("closedDays").getChildren()){
                    closedDays.add(dsclosed.getValue(Boolean.class));
                    oldClosedDays.add(dsclosed.getValue(Boolean.class));
                }
                for(int  c= 0;c<7;c++){
                    if(closedDays.get(c)){
                        tabletime[c][0].setText("CHIUSO");
                        tabletime[c][1].setText("CHIUSO");
                    }else{
                        if(continuedSchedule.get(c)){
                            tabletime[c][0].setText(businessHour.get(c*4));
                            tabletime[c][1].setText(businessHour.get((c*4)+1));
                        }else{
                            tabletime[c][0].setText((businessHour.get(c*4)+"-"+ businessHour.get((c*4)+1)));
                            tabletime[c][1].setText((businessHour.get((c*4)+2)+"-"+ businessHour.get((c*4)+3)));
                        }
                    }
                }
                adapterHours.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }

    public void ModifyMarketContact(String marketId, EditText edtMarketCity, EditText edtMarketAddress,String oldAddress, String oldEmail,
                                    EditText edtMarketCAP, EditText edtMarketPhone, EditText edtMarketEmail, final Context context,OnCompleteListener listener){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Market").child(marketId);
        if(CheckModifiedField(edtMarketCity,edtMarketAddress,edtMarketCAP,edtMarketPhone,edtMarketEmail)){
            boolean check1 = false;
            boolean check2 = false;
            String address = edtMarketAddress.getText().toString()+", "+edtMarketCity.getText().toString()+" "+edtMarketCAP.getText().toString();
            String phone = edtMarketPhone.getText().toString();
            String email = edtMarketEmail.getText().toString();
            try {
                databaseReference.child("address").setValue(address);
                check1 = true;
                databaseReference.child("email").setValue(email);
                check2 = true;
                databaseReference.child("number").setValue(phone).addOnCompleteListener(listener);
            }catch (Exception e){
                Toast.makeText(context,"Modifica dati negozio non riuscita",Toast.LENGTH_LONG).show();
                if(check1)
                    databaseReference.child("address").setValue(oldAddress);
                if(check2)
                    databaseReference.child("email").setValue(oldEmail);
            }
        }

    }


    public void ModifyMarketHours(String marketId, List<String> businessHours, List<Boolean> continuedSchedule, List<Boolean> closedDays,
                                  final Context context,OnCompleteListener listener, List<Boolean> oldContinuedSchedule, List<Boolean> oldClosedDays){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Market").child(marketId);
        if(CheckHours(businessHours,continuedSchedule,closedDays,context)){
            boolean check1 = false;
            boolean check2 = false;
            try{
                databaseReference.child("closedDays").setValue(closedDays);
                check1 = true;
                databaseReference.child("continuedSchedule").setValue(continuedSchedule);
                check2 = true;
                databaseReference.child("businessHour").setValue(businessHours).addOnCompleteListener(listener);
            }catch (Exception e){
                Toast.makeText(context,"Orari negozio non aggiornati",Toast.LENGTH_LONG).show();
                if(check1)
                    databaseReference.child("closedDays").setValue(oldClosedDays);
                if(check2)
                    databaseReference.child("continuedSchedule").setValue(oldContinuedSchedule);
            }
        }
    }

    public boolean CheckModifiedField(EditText edtMarketCity, EditText edtMarketAddress,EditText edtMarketCAP, EditText edtMarketPhone,
                                      EditText edtMarketEmail){
        boolean checked = true;
        if(edtMarketCity.getText().toString().trim().isEmpty()){
            edtMarketCity.setError("Città obbligatoria");
            checked = false;
        }
        if(edtMarketAddress.getText().toString().trim().isEmpty()){
            edtMarketAddress.setError("Indirizzo obbligatiorio");
            checked = false;
        }
        if(edtMarketCAP.getText().toString().trim().isEmpty()){
            edtMarketCAP.setError("CAP obbligatorio");
            checked = false;
        }
        if(edtMarketPhone.getText().toString().trim().isEmpty()){
            edtMarketPhone.setError("Numero telefono obbligatorio");
            checked = false;
        }
        if(!patternNumberPhone.matcher(edtMarketPhone.getText().toString()).matches()){
            edtMarketPhone.setError("Numero telefono non valido");
            checked = false;
        }
        if(edtMarketEmail.getText().toString().trim().isEmpty()){
            edtMarketEmail.setError("Email obbligatoria");
            checked = false;
        }
        if(!patternEmail.matcher(edtMarketEmail.getText().toString()).matches()){
            edtMarketEmail.setError("Email non valida");
            checked = false;
        }
        return checked;
    }

    public boolean CheckHours(List<String> businessHour,List<Boolean> continuedSchedule, List<Boolean> closedDays, Context context){
        boolean check = true;
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
}
