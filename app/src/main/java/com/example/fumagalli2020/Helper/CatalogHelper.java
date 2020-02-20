package com.example.fumagalli2020.Helper;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fumagalli2020.AdapterCustCategoryList;
import com.example.fumagalli2020.AdapterCustProductList;
import com.example.fumagalli2020.Class.Category;
import com.example.fumagalli2020.Class.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CatalogHelper {

    public void LoadCategory(final List<Category> lstCategory, final String currentMarketId, final AdapterCustCategoryList adapterCustCategoryList){
        DatabaseReference databasereference = FirebaseDatabase.getInstance().getReference().child("Market").child(currentMarketId).child("Category");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    lstCategory.add(new Category(ds.child("categoryId").getValue(String.class),ds.child("name").getValue(String.class),
                            ds.child("desc").getValue(String.class),currentMarketId));
                }
                adapterCustCategoryList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databasereference.addListenerForSingleValueEvent(valueEventListener);
    }

    public void LoadProduct(final List<Product> lstProduct, final String currentMarketId, final String currentCategoryId, final AdapterCustProductList adapterCustProductList){
        DatabaseReference databasereference = FirebaseDatabase.getInstance().getReference().child("Market").child(currentMarketId).child("Category").child(currentCategoryId).child("Product");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    lstProduct.add(new Product(ds.child("name").getValue(String.class),ds.child("productId").getValue(String.class),currentCategoryId, currentMarketId,
                            ds.child("packagingDate").getValue(String.class),ds.child("expireDate").getValue(String.class),ds.child("origin").getValue(String.class),ds.child("type").getValue(String.class),
                            ds.child("quantity").getValue(String.class),ds.child("price").getValue(String.class)));
                }
                adapterCustProductList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databasereference.addListenerForSingleValueEvent(valueEventListener);
    }

    public void AddProductCart(final String cartId, final Product product, final String prdType, final String marketId, final String currentUser, final Context context, final EditText edtQtySelected){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("Carts");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(cartId)) {
                    if(dataSnapshot.child(cartId).child("Products").hasChild(product.getProductId())){
                        if(prdType.equals("Confezionato")){
                            try {
                                Integer appo = Integer.parseInt(dataSnapshot.child(cartId).child("Products").child(product.getProductId()).child("qntSelected").getValue(String.class))+
                                        Integer.parseInt(product.getQntSelected());
                                databaseReference.child(cartId).child("Products").child(product.getProductId()).child("qntSelected").
                                        setValue(Integer.toString(appo));
                                Double apptotal = Double.parseDouble(dataSnapshot.child(cartId).child("totalPrice").getValue(String.class)) +
                                        (Integer.parseInt(product.getQntSelected()) * Double.parseDouble(product.getPrice()));
                                databaseReference.child(cartId).child("totalPrice").
                                        setValue(Double.toString(apptotal));
                                Toast.makeText(context,"Prodotto aggiunto al carrello",Toast.LENGTH_LONG).show();
                                edtQtySelected.setText(null);
                            }catch (Exception e){
                                Toast.makeText(context,"Prodotto non aggiunto",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            try {
                                Double appo = Double.parseDouble(dataSnapshot.child(cartId).child("Products").child(product.getProductId()).child("qtnSelected").getValue(String.class))+
                                        Double.parseDouble(product.getQntSelected());
                                databaseReference.child(cartId).child("Products").child(product.getProductId()).child("qntSelected").
                                        setValue(Double.toString(appo));
                                Double apptotal = Double.parseDouble(dataSnapshot.child(cartId).child("totalPrice").getValue(String.class))+
                                        (Double.parseDouble(product.getQntSelected()) * Double.parseDouble(product.getPrice()));
                                databaseReference.child(cartId).child("totalPrice").
                                        setValue(Double.toString(apptotal));
                                Toast.makeText(context,"Prodotto aggiunto al carrello",Toast.LENGTH_LONG).show();
                                edtQtySelected.setText(null);
                            }catch (Exception e){
                                Toast.makeText(context,"Prodotto non aggiunto",Toast.LENGTH_LONG).show();
                            }
                        }
                    }else{
                        try {
                            databaseReference.child(cartId).child("Products").child(product.getProductId()).setValue(product);
                            if (prdType.equals("Confezionato")) {
                                Double apptotal = Double.parseDouble(dataSnapshot.child(cartId).child("totalPrice").getValue(String.class))+
                                        Double.parseDouble(product.getPrice()) * Integer.parseInt(product.getQntSelected());
                                databaseReference.child(cartId).child("totalPrice").
                                        setValue(Double.toString(apptotal));
                            } else {
                                Double apptotal = Double.parseDouble(dataSnapshot.child(cartId).child("totalPrice").getValue(String.class))+
                                        Double.parseDouble(product.getPrice()) * Double.parseDouble(product.getQntSelected());
                                databaseReference.child(cartId).child("totalPrice").
                                        setValue(Double.toString(apptotal));
                            }
                            Toast.makeText(context,"Prodotto aggiunto al carrello",Toast.LENGTH_LONG).show();
                            edtQtySelected.setText(null);
                        }catch (Exception e){
                            Toast.makeText(context,"Prodotto non aggiunto",Toast.LENGTH_LONG).show();
                        }
                    }
                }else{
                    try {
                        databaseReference.child(cartId).child("cartId").setValue(cartId);
                        databaseReference.child(cartId).child("marketId").setValue(marketId);
                        if (prdType.equals("Confezionato")) {
                            databaseReference.child(cartId).child("totalPrice").setValue(Double.toString(Integer.parseInt(product.getQntSelected()) * Double.parseDouble(product.getPrice())));
                        } else {
                            databaseReference.child(cartId).child("totalPrice").setValue(Double.toString(Double.parseDouble(product.getQntSelected()) * Double.parseDouble(product.getPrice())));
                        }
                        databaseReference.child(cartId).child("Products").child(product.getProductId()).setValue(product);
                        Toast.makeText(context,"Prodotto aggiunto al carrello",Toast.LENGTH_LONG).show();
                        edtQtySelected.setText(null);
                    }catch (Exception e){
                        Toast.makeText(context,"Prodotto non aggiunto",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);

    }

}
