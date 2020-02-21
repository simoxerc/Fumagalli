package com.example.fumagalli2020.Helper;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fumagalli2020.AdapterCustCartSingle;
import com.example.fumagalli2020.Class.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CustCartSingleHelper {

    public void LoadProductCart(final String cartId, final String currentUser, final List<Product> lstSingleCart, final AdapterCustCartSingle adapterCustCartSingle, final String marketId){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.child("Users").child(currentUser).child("Carts").child(cartId).child("Products").getChildren()){
                    String categoryId = ds.getKey().substring(0,ds.getKey().length() - ds.child("name").getValue(String.class).length());
                    String quantity = dataSnapshot.child("Market").child(marketId).child("Category").child(categoryId).
                            child("Product").child(ds.getKey()).child("quantity").getValue(String.class);
                    String type = dataSnapshot.child("Market").child(marketId).child("Category").child(categoryId).
                            child("Product").child(ds.getKey()).child("type").getValue(String.class);
                    lstSingleCart.add(new Product(ds.getKey(),ds.child("name").getValue(String.class),type,ds.child("price").getValue(String.class),quantity,ds.child("qntSelected").getValue(String.class)));
                }
                adapterCustCartSingle.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);

    }

    public void ChangeQuantity(final String cartId, final String currentUser, final Product product, final AdapterCustCartSingle adapterCustCartSingle,
                               final String newQuantity, final Context context, final List<Product> object, final TextView tvGetTotal){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).
                child("Carts").child(cartId);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                final String oldQuantity = dataSnapshot.child("Products").child(product.getProductId()).child("qntSelected").getValue(String.class);
                databaseReference.child("Products").child(product.getProductId()).child("qntSelected").setValue(newQuantity).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                        String oldTotalPrice = Double.toString(Double.parseDouble(dataSnapshot.child("totalPrice").getValue(String.class))
                                - (Double.parseDouble(product.getQntSelected())* Double.parseDouble(product.getPrice())));
                        product.setQntSelected(newQuantity);
                        String newTotalPrice = Double.toString(Double.parseDouble(oldTotalPrice) + (Double.parseDouble(product.getQntSelected())*Double.parseDouble(product.getPrice())));
                        databaseReference.child("totalPrice").setValue(newTotalPrice).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(context,"Quantità modificata con successo",Toast.LENGTH_LONG).show();
                                    double total = 0;
                                    for (Product item : object) {
                                        total+= Double.parseDouble(item.getPrice()) * Double.parseDouble(item.getQntSelected()) ;
                                    }
                                    tvGetTotal.setText(Double.toString(total));
                                    adapterCustCartSingle.notifyDataSetChanged();
                                }else{
                                    product.setQntSelected(oldQuantity);
                                    databaseReference.child("Products").child(product.getProductId()).child("qntSelected").setValue(oldQuantity);
                                    Toast.makeText(context,"Errore nella modifica della quantità",Toast.LENGTH_LONG).show();
                                    adapterCustCartSingle.notifyDataSetChanged();
                                }
                            }
                        });
                        } else{
                            Toast.makeText(context,"Errore nella modifica della quantità",Toast.LENGTH_LONG).show();
                            adapterCustCartSingle.notifyDataSetChanged();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);

    }

    public void DeleteProductCart(final String cartId, final String currentUser, final Product product, final AdapterCustCartSingle adapterCustCartSingle,
                                  final Context context, final List<Product> object, final TextView tvGetTotal, final OnCompleteListener<Void> onCompleteListener){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Carts").child(cartId).child("Products").child(product.getProductId());
        reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(context, "Prodotto eliminato", Toast.LENGTH_LONG).show();
                    adapterCustCartSingle.remove(product);
                    double total=0;
                    for (Product item : object) {
                        total+= Double.parseDouble(item.getPrice()) * Double.parseDouble(item.getQntSelected()) ;
                    }
                    if(object.size() == 0) {
                        FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Carts").child(cartId).removeValue().addOnCompleteListener(onCompleteListener);
                    }else{
                        tvGetTotal.setText(Double.toString(total));
                        adapterCustCartSingle.notifyDataSetChanged();
                    }
                }
                else
                    Toast.makeText(context,"Eliminazione Prodotto non riuscita",Toast.LENGTH_LONG).show();
            }
        });
    }

}
