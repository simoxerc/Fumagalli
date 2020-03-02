package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.Helper.RegisterEmployeeHelper;
import com.example.fumagalli2020.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class RegisterEmployee extends AppCompatActivity {

   private RegisterEmployeeHelper helper;
   private Button regEmplButton;
   private EditText regEmplName;
   private EditText regEmplSurname;
   private EditText regEmplMobile;
   private EditText regEmplEmail;
   private TextInputLayout tilEmplPassword;
   private Spinner regEmplSpinner;
   private ArrayAdapter adptTypeEmployee;
   private List<String> typeList = new ArrayList();
   private String marketId;
   private Integer source;
   protected FirebaseAuth firebaseAuth;
   protected OnCompleteListener<Void> onCompleteListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeremployee);
        regEmplButton = findViewById(R.id.btnRegEmployeeRegistration);
        regEmplName = findViewById(R.id.edtRegEmployeeName);
        regEmplSurname = findViewById(R.id.edtRegEmployeeSurname);
        regEmplSpinner = findViewById(R.id.spnRegEmployeeType);
        regEmplMobile = findViewById(R.id.edtRegEmployeeMobile);
        regEmplEmail = findViewById(R.id.edtRegEmployeeEmail);
        tilEmplPassword = findViewById(R.id.edtRegEmployeePassword);

        Bundle bundle = getIntent().getExtras();
        source = bundle.getInt("source");
        marketId = bundle.getString("marketId");

        helper = new RegisterEmployeeHelper();

        typeList.add("Amministratore Negozio");
        if(source == 1)
            typeList.add("Addetto alla Logistica");


        adptTypeEmployee = new ArrayAdapter<String>(getApplicationContext(), R.layout.first_custom_spinner,typeList);
        adptTypeEmployee.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regEmplSpinner.setAdapter(adptTypeEmployee);
        regEmplSpinner.setSelection(0);

        regEmplSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setText(regEmplSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        createnewfirebaseauth();

        final FirebaseAuth finalFirebaseAuth = firebaseAuth;

        onCompleteListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    finalFirebaseAuth.getCurrentUser().delete();
                    Toast.makeText(getApplicationContext(),"Registrazione fallita", Toast.LENGTH_LONG).show();
                }else{
                    finalFirebaseAuth.signOut();
                    Toast.makeText(getApplicationContext(),"Registrazione Riuscita", Toast.LENGTH_LONG).show();
                    if(source == 1)
                        gotoemployeelist();
                    else
                        gotoregmarket();
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        regEmplButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.registerEmployee(regEmplName, regEmplSurname, regEmplMobile, regEmplSpinner, regEmplEmail, tilEmplPassword,marketId,getApplicationContext(),onCompleteListener);
            }
        });
    }

    private void gotoemployeelist() {
        Intent intent = new Intent(this, EmployeeList.class);
        startActivity(intent);
    }

    private void gotoregmarket(){
        Intent intent = new Intent(this,RegisterMarket.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case android.R.id.home:
                if (source == 1)
                    onBackPressed();
<<<<<<< HEAD
<<<<<<< HEAD
                return true;
=======
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
=======
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
            case R.id.itmAdminLogout:
                if(source == 1){
                    FirebaseAuth.getInstance().signOut();
                    Intent intentAdmLogout = new Intent(this, Login.class);
                    startActivity(intentAdmLogout);
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        if(source == 1) {
            super.onBackPressed();
        }
    }

    private void createnewfirebaseauth(){
        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl("https://fumagalliids.firebaseio.com/")
                .setApiKey("AIzaSyBUkFYt7GTUpBsVSMQKv6FXOsuzcwgN6_g")
                .setApplicationId("fumagalliids").build();

        try { FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "AnyAppName");
            firebaseAuth = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e){
            firebaseAuth = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(source == 1)
            getMenuInflater().inflate(R.menu.admin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
