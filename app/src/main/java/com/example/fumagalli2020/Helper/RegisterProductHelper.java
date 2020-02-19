package com.example.fumagalli2020.Helper;

import android.widget.EditText;
import android.widget.Spinner;
import com.example.fumagalli2020.Class.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterProductHelper {
    public void RegisterProduct(EditText edtProductName, EditText edtProductOrigin, EditText edtProductQuantity, EditText edtProductPrice, EditText edtProductPackageDate, EditText edtProductExpireDate,
                                 Spinner spnProductType, String marketId, String categoryId, OnCompleteListener<Void> onCompleteListener){
        String name,origin,quantity,price,pckgDate,expDate,type,productid;
        name = edtProductName.getText().toString();
        origin = edtProductOrigin.getText().toString();
        quantity = edtProductQuantity.getText().toString();
        price = edtProductPrice.getText().toString();
        pckgDate = edtProductPackageDate.getText().toString();
        expDate = edtProductExpireDate.getText().toString();
        type = spnProductType.getSelectedItem().toString();
        productid = categoryId+name;
        if(checkfield(edtProductName,edtProductOrigin,edtProductQuantity,edtProductPrice,edtProductPackageDate,edtProductExpireDate)){
            final Product product = new Product(name,productid,categoryId,pckgDate,expDate,origin,type,quantity,price);
            final DatabaseReference mDBRef = FirebaseDatabase.getInstance().getReference();
            mDBRef.child("Market").child(marketId).child("Category").child(categoryId).child("Product").child(productid).setValue(product).addOnCompleteListener(onCompleteListener);
        }

    }

    private boolean checkfield(EditText edtProductName,EditText edtProductOrigin,EditText edtProductQuantity,EditText edtProductPrice,EditText edtProductPackageDate,EditText edtProductExpireDate){
        boolean registrable = true;
        if(edtProductName.getText().toString().trim().isEmpty()){
            edtProductName.setError("");
            registrable = false;
        }
        if(edtProductOrigin.getText().toString().trim().isEmpty()){
            edtProductOrigin.setError("");
            registrable = false;
        }
        if(edtProductPrice.getText().toString().trim().isEmpty()){
            edtProductPrice.setError("");
            registrable = false;
        }
        if(edtProductQuantity.getText().toString().trim().isEmpty()){
            edtProductQuantity.setError("");
            registrable = false;
        }
        if(edtProductPackageDate.getText().toString().trim().isEmpty()){
            edtProductPackageDate.setError("");
            registrable = false;
        }
        if(edtProductExpireDate.getText().toString().trim().isEmpty()){
            edtProductExpireDate.setError("");
            registrable = false;
        }
        if(edtProductPackageDate.getText().toString().compareTo(edtProductExpireDate.getText().toString())>0){
            edtProductPackageDate.setError("");
            registrable = false;
        }
        return registrable;
    }
}
