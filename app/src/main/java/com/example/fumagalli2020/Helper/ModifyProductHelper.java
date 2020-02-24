package com.example.fumagalli2020.Helper;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModifyProductHelper {

    public void ModifyProduct(EditText edtProductQnt, EditText edtProductPrice, EditText edtProductOrigin,
                              EditText edtProductExp, EditText edtProductPck, String marketId, String productId, String categoryId, String productType,
                              final Context context, String oldQnt, String oldPrice, String oldExp, String oldPck, OnCompleteListener listener){
        if(checkmodifiedfield(edtProductQnt,edtProductPrice,edtProductOrigin,edtProductExp,edtProductPck,productType)){
            Boolean check1 = false;
            Boolean check2 = false;
            Boolean check3 = false;
            Boolean check4 = false;
            String newQnt = edtProductQnt.getText().toString();
            String newPrice = edtProductPrice.getText().toString();
            String newOrigin = edtProductOrigin.getText().toString();
            String newExp = edtProductExp.getText().toString();
            String newPck = edtProductPck.getText().toString();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Market").child(marketId).child("Category").child(categoryId).child("Product").child(productId);
            try{
                databaseReference.child("quantity").setValue(newQnt);
                check1 = true;
                databaseReference.child("price").setValue(newPrice);
                check2 = true;
                databaseReference.child("expireDate").setValue(newExp);
                check3 = true;
                databaseReference.child("packagingDate").setValue(newPck);
                check4 = true;
                databaseReference.child("origin").setValue(newOrigin).addOnCompleteListener(listener);
            }catch (Exception e){
                Toast.makeText(context,"Prodotto non modificato",Toast.LENGTH_LONG).show();
                if(check1)
                    databaseReference.child("quantity").setValue(oldQnt);
                if(check2)
                    databaseReference.child("price").setValue(oldPrice);
                if(check3)
                    databaseReference.child("expireDate").setValue(oldExp);
                if(check4)
                    databaseReference.child("packagingDate").setValue(oldPck);
            }
        }
    }

    public boolean checkmodifiedfield(EditText edtProductQnt,EditText edtProductPrice,EditText edtProductOrigin,
                                      EditText edtProductExp,EditText edtProductPck,String productType){
        boolean check = true;
        if(edtProductOrigin.getText().toString().trim().isEmpty()){
            edtProductOrigin.setError("Origine prodotto obbligatoria");
            check = false;
        }
        if(edtProductQnt.getText().toString().trim().isEmpty()){
            edtProductQnt.setError("Quantità prodotto obbligatoria");
            check = false;
        }else if(productType.equals("Confezionato") && ((edtProductQnt.getText().toString().contains(",")) || (edtProductQnt.getText().toString().contains(".")))){
            edtProductQnt.setError("Numero decimale non disponibile per prodotto confezionato");
            check = false;
        }
        if(edtProductPrice.getText().toString().trim().isEmpty()){
            edtProductPrice.setError("Prezzo prodotto obbligatorio");
            check = false;
        }
        if(edtProductExp.getText().toString().trim().isEmpty()){
            edtProductExp.setError("Data Scadenza obbligatoria");
            check = false;
        }
        if(edtProductPck.getText().toString().trim().isEmpty()){
            edtProductQnt.setError("Data Confezionamento obbligatoria");
            check = false;
        }
        if(edtProductPck.getText().toString().compareTo(edtProductExp.getText().toString())>0){
            edtProductPck.setError("Il prodotto è già scaduto.");
            check = false;
        }
        return check;
    }
}
