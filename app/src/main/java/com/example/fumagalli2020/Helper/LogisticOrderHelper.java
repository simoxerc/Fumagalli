package com.example.fumagalli2020.Helper;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fumagalli2020.AdapterLogisticOrders;
import com.example.fumagalli2020.Class.Order;
import com.example.fumagalli2020.Class.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogisticOrderHelper {
    public void LoadLogistcOrders(final List<Order> orderList, final HashMap<Order,List<Product>> orderListHashMap, final AdapterLogisticOrders adapterLogisticOrders,
                                  final String currentMarketId, final List<String> custfiscal){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
                orderListHashMap.clear();
                custfiscal.clear();
                for(DataSnapshot ds : dataSnapshot.child("Market").child(currentMarketId).child("Orders").getChildren()){
                    List<Product> tempList = new ArrayList<Product>();
                    Order order = new Order(ds.getKey(),ds.child("creditcard").getValue(String.class),ds.child("pickDate").getValue(String.class),ds.child("pickTime").getValue(String.class),
                            ds.child("customerId").getValue(String.class),ds.child("totalPayed").getValue(String.class),ds.child("orderState").getValue(String.class));
                    orderList.add(order);
                    custfiscal.add(dataSnapshot.child("Users").child(order.getCustomerId()).child("fiscalcode").getValue(String.class));
                    for(DataSnapshot dss : ds.child("Products").getChildren()){
                        Product product = new Product(dss.child("name").getValue(String.class),dss.getKey(),dss.child("type").getValue(String.class),dss.child("qntSelected").getValue(String.class));
                        tempList.add(product);
                    }
                    orderListHashMap.put(order,tempList);
                }
                adapterLogisticOrders.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }

    public void btnConfirmOrder(final String orderId, final String newstate, final String customerId, final Context context){
        final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String marketId = dataSnapshot.child("Users").child(currentUser).child("marketId").getValue(String.class);
                try {
                    databaseReference.child("Market").child(marketId).child("Orders").child(orderId).child("orderState").setValue(newstate);
                    databaseReference.child("Users").child(customerId).child("Orders").child(orderId).child("orderState").setValue(newstate).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "Stato ordine aggiornato", Toast.LENGTH_LONG).show();
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(context,"Ordine non aggiornato",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void btnDeleteOrder(final String orderId, final String newState, final String customerId, final Context context){
        final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String marketId = dataSnapshot.child("Users").child(currentUser).child("marketId").getValue(String.class);
                try {
                    databaseReference.child("Market").child(marketId).child("Orders").child(orderId).child("orderState").setValue(newState);
                    databaseReference.child("Users").child(customerId).child("Orders").child(orderId).child("orderState").setValue(newState).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                Toast.makeText(context,"Stato ordine aggiornato",Toast.LENGTH_LONG).show();

                        }
                    });
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
