package com.example.fumagalli2020.Helper;

import androidx.annotation.NonNull;

import com.example.fumagalli2020.AdapterCustChainList;
import com.example.fumagalli2020.AdapterCustMarketList;
import com.example.fumagalli2020.Class.Chain;
import com.example.fumagalli2020.Class.Market;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CustMarketListHelper {
    public void LoadChain(final List<Chain> lstChain, final AdapterCustChainList adapterCustChainList){
        final DatabaseReference databasereference = FirebaseDatabase.getInstance().getReference().child("Chain");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    lstChain.add(new Chain(ds.child("name").getValue(String.class),ds.getKey(),ds.child("link").getValue(String.class)));
                }
                adapterCustChainList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databasereference.addListenerForSingleValueEvent(valueEventListener);
    }

    public void LoadMarket(final List<Market> lstMarket, final AdapterCustMarketList adapterCustMarketList, final String currentChainId){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Market");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.child("chain_Id").getValue(String.class).equals(currentChainId)){
                        lstMarket.add(new Market(ds.child("name").getValue(String.class),ds.child("address").getValue(String.class),ds.child("number").getValue(String.class),
                                ds.child("email").getValue(String.class),ds.child("marketId").getValue(String.class),ds.child("chain_Id").getValue(String.class)));
                    }
                }
                adapterCustMarketList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }
}
