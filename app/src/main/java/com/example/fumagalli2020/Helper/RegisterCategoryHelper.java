package com.example.fumagalli2020.Helper;

import android.content.Context;
import android.widget.EditText;
import com.example.fumagalli2020.Class.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterCategoryHelper {
    public void registerCategory(final EditText edtCategoryName, EditText edtCategoryDesc, String marketId, final Context context, OnCompleteListener<Void> listener){
        String name,desc,categoryId;
        if(checkfield(edtCategoryName,edtCategoryDesc)){
            name = edtCategoryName.getText().toString();
            desc = edtCategoryDesc.getText().toString();
            categoryId = marketId+name;
            Category category = new Category(categoryId,name,desc,marketId);
            final DatabaseReference mDBRef = FirebaseDatabase.getInstance().getReference();
            mDBRef.child("Market").child(marketId).child("Category").child(categoryId).setValue(category).addOnCompleteListener(listener);
        }
    }

    private boolean checkfield(EditText edtCategoryName,EditText edtCategoryDesc){
        boolean registrable = true;
        if(edtCategoryName.getText().toString().trim().isEmpty()){
            edtCategoryName.setError("");
            registrable = false;
        }
        if(edtCategoryDesc.getText().toString().trim().isEmpty()){
            edtCategoryDesc.setError("");
            registrable = false;
        }
        return registrable;
    }
}
