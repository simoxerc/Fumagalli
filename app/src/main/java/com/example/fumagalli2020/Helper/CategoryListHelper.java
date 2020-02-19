package com.example.fumagalli2020.Helper;

import androidx.annotation.NonNull;

import com.example.fumagalli2020.AdapterCategoryList;
import com.example.fumagalli2020.Class.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CategoryListHelper {

    public void LoadCategory(final List<Category> lstCategory, final String currentMarketId, final AdapterCategoryList adapterCategoryList){
        DatabaseReference databasereference = FirebaseDatabase.getInstance().getReference().child("Market").child(currentMarketId).child("Category");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                        lstCategory.add(new Category(ds.child("categoryId").getValue(String.class),ds.child("name").getValue(String.class),
                                ds.child("desc").getValue(String.class),currentMarketId));
                }
                adapterCategoryList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databasereference.addListenerForSingleValueEvent(valueEventListener);
    }
}
