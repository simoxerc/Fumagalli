package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.Helper.RegisterCategoryHelper;
import com.example.fumagalli2020.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class RegisterCategory extends AppCompatActivity {
    private Button btnRegCategory;
    private EditText edtCategoryName;
    private EditText edtCategoryDesc;
    private RegisterCategoryHelper registerCategoryHelper;
    private String marketId;
    protected OnCompleteListener<Void> listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_registercategory);
        btnRegCategory = findViewById(R.id.btnRegCategory);
        edtCategoryName = findViewById(R.id.edtCategoryName);
        edtCategoryDesc = findViewById(R.id.edtCategoryDesc);

        Bundle bundle = getIntent().getExtras();
        marketId = bundle.getString("marketId");
        listener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterCategory.this,"Registrato",Toast.LENGTH_LONG).show();
                    gotocategorylist();
                }else{
                    Toast.makeText(RegisterCategory.this,"Registrazione Fallita",Toast.LENGTH_LONG).show();
                }
            }
        };

        registerCategoryHelper = new RegisterCategoryHelper();
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnRegCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              registerCategoryHelper.registerCategory(edtCategoryName,edtCategoryDesc,marketId, listener);
            }
        });
    }

    public void gotocategorylist(){
        Intent intent = new Intent(this,CategoryList.class);
        startActivity(intent);
    }
}
