package com.example.fumagalli2020.Helper;

import androidx.annotation.NonNull;

import com.example.fumagalli2020.AdapterCustCartList;
import com.example.fumagalli2020.Class.Cart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CustCartListHelper {

    public void LoadCart(final List<Cart> lstCart, final String currentUser, final AdapterCustCartList adapterCustCartList){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Carts");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    lstCart.add(new Cart(ds.child("cartId").getValue(String.class),ds.child("totalPrice").getValue(String.class),ds.child("marketId").getValue(String.class)));
                }
                adapterCustCartList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }

}
