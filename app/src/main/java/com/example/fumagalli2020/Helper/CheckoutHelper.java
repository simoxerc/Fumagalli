package com.example.fumagalli2020.Helper;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fumagalli2020.Class.Order;
import com.example.fumagalli2020.Class.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CheckoutHelper {
    public void LoadMarketTime(final TextView[][] tabletime, String currentMarketId){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Market").child(currentMarketId);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(int x = 0;x<7;x++){
                    if(dataSnapshot.child("closedDays").child(Integer.toString(x)).getValue(Boolean.class)){
                        tabletime[x][0].setText("CHIUSO");
                        tabletime[x][1].setText("CHIUSO");
                    }else{
                        if(dataSnapshot.child("continuedSchedule").child(Integer.toString(x)).getValue(Boolean.class)){
                            tabletime[x][0].setText(dataSnapshot.child("businessHour").child(Integer.toString(x*4)).getValue(String.class));
                            tabletime[x][1].setText(dataSnapshot.child("businessHour").child(Integer.toString((x*4)+1)).getValue(String.class));
                        }else{
                            tabletime[x][0].setText(dataSnapshot.child("businessHour").child(Integer.toString(x*4)).getValue(String.class)+"-"+
                                    dataSnapshot.child("businessHour").child(Integer.toString((x*4)+1)).getValue(String.class));
                            tabletime[x][1].setText((dataSnapshot.child("businessHour").child(Integer.toString((x*4)+2)).getValue(String.class))+"-"+
                                    dataSnapshot.child("businessHour").child(Integer.toString((x*4)+3)).getValue(String.class));
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }

    public void createorder(EditText edtPayCard, EditText edtCardExpMonth, EditText edtCardExpYear, EditText edtCardCVV, EditText edtOrderDate, EditText edtOrderTime,
                            TextView[][] tabletime, OnCompleteListener listener, final String currentCartId, final String currentMarketId, String total, Context context){
        if(checkdatetime(edtOrderDate,edtOrderTime,tabletime) && checkorderfield(edtPayCard,edtCardExpMonth,edtCardExpYear,edtCardCVV)){
            final String creditcard = "CARD"+edtPayCard.getText().toString()+"EXPM"+edtCardExpMonth.getText().toString()+"EXPY"+edtCardExpYear.getText().toString()+"CVV"+edtCardCVV.getText().toString();
            String pickdate = edtOrderDate.getText().toString();
            String picktime = edtOrderTime.getText().toString();
            Calendar calendar = Calendar.getInstance();
            final String orderId = "order"+currentCartId+calendar.get(Calendar.HOUR_OF_DAY)+calendar.get(Calendar.MINUTE)+calendar.get(Calendar.SECOND);
            boolean first = false;
            boolean second = false;
            final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Order order = new Order(orderId,creditcard,pickdate,picktime,currentUser,total,"Pending");
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            try {
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Product> prod = new ArrayList<Product>();
                        List<String> categoryIds = new ArrayList<String>();
                        int x = 0;

                        for (DataSnapshot dss: dataSnapshot.child("Users").child(currentUser).child("Carts").child(currentCartId).child("Products").getChildren()){
                            prod.add(new Product(dss.child("name").getValue(String.class),dss.child("productId").getValue(String.class),
                                    dss.child("price").getValue(String.class),dss.child("qntSelected").getValue(String.class)));
                            Product product = prod.get(x);
                            String productId = product.getProductId();
                            categoryIds.add( product.getProductId().substring(0,product.getProductId().length() - product.getName().length()));
                            databaseReference.child("Market").child(currentMarketId).child("Orders").child(orderId).child("Products").child(productId).setValue(product);
                            databaseReference.child("Users").child(currentUser).child("Orders").child(orderId).child("Products").child(productId).setValue(product);
                            x++;
                        }

                        x = 0;

                        for(String categoryId : categoryIds) {
                            Product product = prod.get(x);
                            for (DataSnapshot ds : dataSnapshot.child("Market").child(currentMarketId).child("Category").child(categoryId).child("Product").getChildren()) {
                                if(ds.getKey().equals(product.getProductId())) {
                                    String productqnt = ds.child("quantity").getValue(String.class);
                                    String type = ds.child("type").getValue(String.class);
                                    if (type.equals("Confezionato")) {
                                        int actualqnt = Integer.parseInt(productqnt);
                                        actualqnt = actualqnt - Integer.parseInt(product.getQntSelected());
                                        databaseReference.child("Market").child(currentMarketId).child("Category").
                                                child(categoryId).child("Product").child(product.getProductId()).child("quantity").setValue(Integer.toString(actualqnt));
                                    } else {
                                        double actualqnt = Double.parseDouble(productqnt);
                                        actualqnt = actualqnt - Double.parseDouble(product.getQntSelected());
                                        databaseReference.child("Market").child(currentMarketId).child("Category").
                                                child(categoryId).child("Product").child(product.getProductId()).child("quantity").setValue(Double.toString(actualqnt));
                                    }
                                }
                            }
                            x++;
                        }
                        databaseReference.child("Users").child(currentUser).child("Carts").child(currentCartId).removeValue();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                reference.addListenerForSingleValueEvent(valueEventListener);
                databaseReference.child("Market").child(currentMarketId).child("Orders").child(orderId).setValue(order);
                first = true;
                databaseReference.child("Users").child(currentUser).child("Orders").child(orderId).setValue(order).addOnCompleteListener(listener);
//                second = true;
//                databaseReference.child("Users").child(currentUser).child("Carts").child(currentCartId).removeValue().addOnCompleteListener(listener);
            }catch (Exception e){
                if(first)
                    databaseReference.child("Market").child(currentMarketId).child("Orders").child(orderId).removeValue();
//                if(second)
//                    databaseReference.child("Users").child(currentUser).child("Orders").child(orderId).removeValue();
                Toast.makeText(context,"Ordine non eseguito",Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean checkorderfield(EditText edtPayCard,EditText edtCardExpMonth,EditText edtCardExpYear,EditText edtCardCVV){
        boolean ordinable = true;
        boolean checkcard = false;
        ArrayList<String> listOfPattern=new ArrayList<String>();
        String ptVisa = "^4[0-9]{6,}$";
        listOfPattern.add(ptVisa);
        String ptMasterCard = "^5[1-5][0-9]{5,}$";
        listOfPattern.add(ptMasterCard);
        String ptAmeExp = "^3[47][0-9]{5,}$";
        listOfPattern.add(ptAmeExp);
        String ptDinClb = "^3(?:0[0-5]|[68][0-9])[0-9]{4,}$";
        listOfPattern.add(ptDinClb);
        String ptDiscover = "^6(?:011|5[0-9]{2})[0-9]{3,}$";
        listOfPattern.add(ptDiscover);
        String ptJcb = "^(?:2131|1800|35[0-9]{3})[0-9]{3,}$";
        listOfPattern.add(ptJcb);
        if(edtPayCard.getText().toString().trim().isEmpty()){
            edtPayCard.setError("Carta Obbligatoria");
            ordinable = false;
        }else{
            for(String regex:listOfPattern){
                if(edtPayCard.getText().toString().matches(regex)){
                    checkcard = true;
                }
            }
            if(!checkcard){
                edtPayCard.setError("Carta non valida");
                ordinable = false;
            }
        }
        if(edtCardExpMonth.getText().toString().trim().isEmpty()){
            edtCardExpMonth.setError("Mese necessario");
            ordinable = false;
        }
        if(edtCardExpYear.getText().toString().trim().isEmpty()){
            edtCardExpYear.setError("Anno necessario");
            ordinable = false;
        }
        if(edtCardCVV.getText().toString().trim().isEmpty()){
            edtCardCVV.setError("CVV mancante");
            ordinable = false;
        }
        return ordinable;
    }


    public boolean checkdatetime(EditText edtOrderDate,EditText edtOrderTime,TextView[][] tabletime){
        boolean ordinable = true;
        boolean check = true;
        int dayOfWeek = 0;
        if(edtOrderDate.getText().toString().trim().isEmpty()){
            ordinable = false;
            edtOrderDate.setError("Data obbligatoria");
        }else{
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date date = format.parse(edtOrderDate.getText().toString());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                switch (dayOfWeek){
                    case 1:
                        dayOfWeek = 6;
                    break;
                    default:
                        dayOfWeek = dayOfWeek - 2;
                    break;
                }
                if(tabletime[dayOfWeek][0].getText().toString().equals("CHIUSO")){
                    edtOrderDate.setError("Hai selezionato un giorno di chiusura. Cambia data ritiro");
                    ordinable = false;
                    check = false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(edtOrderTime.getText().toString().trim().isEmpty()){
            ordinable = false;
            edtOrderTime.setError("Orario obbligatorio");
        }else{
            if(check){
                if(tabletime[dayOfWeek][0].getText().toString().contains("-")){
                    if(edtOrderTime.getText().toString().compareTo(tabletime[dayOfWeek][0].getText().toString().substring(0,5) ) < 0 ||
                            edtOrderTime.getText().toString().compareTo(tabletime[dayOfWeek][0].getText().toString().substring(6)) > 0){
                        edtOrderTime.setError("Hai scelto un orario di chiusura del negozio.");
                        ordinable = false;
                    }
                    if(edtOrderTime.getText().toString().compareTo(tabletime[dayOfWeek][1].getText().toString().substring(0,5) ) < 0 ||
                            edtOrderTime.getText().toString().compareTo(tabletime[dayOfWeek][1].getText().toString().substring(6)) > 0){
                        edtOrderTime.setError("Hai scelto un orario di chiusura del negozio.");
                        ordinable = false;
                    }
                }else{
                    if(edtOrderTime.getText().toString().compareTo(tabletime[dayOfWeek][0].getText().toString()) < 0 ||
                            edtOrderTime.getText().toString().compareTo(tabletime[dayOfWeek][1].getText().toString()) > 0){
                        edtOrderTime.setError("Hai scelto un orario di chiusura del negozio.");
                        ordinable = false;
                    }
                }
            }

        }
        return ordinable;
    }
}
