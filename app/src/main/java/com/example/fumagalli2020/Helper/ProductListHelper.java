package com.example.fumagalli2020.Helper;

import androidx.annotation.NonNull;

import com.example.fumagalli2020.AdapterProductList;
import com.example.fumagalli2020.Class.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProductListHelper {
    public void LoadProduct(final List<Product> lstProduct, final String currentMarketId, final String currentCategoryId, final AdapterProductList adapterProductList){
        DatabaseReference databasereference = FirebaseDatabase.getInstance().getReference().child("Market").child(currentMarketId).child("Category").child(currentCategoryId).child("Product");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    lstProduct.add(new Product(ds.child("name").getValue(String.class),ds.child("productId").getValue(String.class),currentCategoryId,
                            ds.child("packagingDate").getValue(String.class),ds.child("expireDate").getValue(String.class),ds.child("origin").getValue(String.class),ds.child("type").getValue(String.class),
                            ds.child("quantity").getValue(String.class),ds.child("price").getValue(String.class)));
                }
                adapterProductList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databasereference.addListenerForSingleValueEvent(valueEventListener);
    }

}
