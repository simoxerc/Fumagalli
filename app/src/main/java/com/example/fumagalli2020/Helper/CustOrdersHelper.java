package com.example.fumagalli2020.Helper;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fumagalli2020.AdapterCustOrders;
import com.example.fumagalli2020.Class.Order;
import com.example.fumagalli2020.Class.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CustOrdersHelper {

    public void LoadCustOrders(String currentUser, final AdapterCustOrders adapterCustOrders, final List<Order> listDataHeader, final HashMap<Order, List<Product>> listDataChild){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Orders");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listDataHeader.clear();
                listDataChild.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    Order order = new Order(ds.getKey(),ds.child("creditcard").getValue(String.class),ds.child("pickDate").getValue(String.class),ds.child("pickTime").getValue(String.class),
                            ds.child("customerId").getValue(String.class), ds.child("totalPayed").getValue(String.class), ds.child("orderState").getValue(String.class));
                    listDataHeader.add(order);
                    List<Product> productList = new ArrayList<Product>();
                    for(DataSnapshot dss: ds.child("Products").getChildren()){
                        Product product = new Product(dss.child("name").getValue(String.class), dss.getKey(),dss.child("price").getValue(String.class), dss.child("qntSelected").getValue(String.class));
                        productList.add(product);
                    }
                    listDataChild.put(order,productList);
                }
                adapterCustOrders.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }

    public void RequestDelete(final OnCompleteListener<Void> onCompleteListener, final String orderId, final String currentUser, final String customerId, final Context context){

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String marketId = "";
                for(DataSnapshot ds : dataSnapshot.child("Market").getChildren() ){
                    if(ds.child("Orders").hasChild(orderId)){
                        marketId = ds.getKey();
                    }
                }
                try {
                    databaseReference.child("Market").child(marketId).child("Orders").child(orderId).child("orderState").setValue("Richiesta Annullamento");
                    databaseReference.child("Users").child(customerId).child("Orders").child(orderId).child("orderState").setValue("Richiesta Annullamento").addOnCompleteListener(onCompleteListener);
                }catch (Exception e){
                    Toast.makeText(context,"Ordine non aggiornato",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
