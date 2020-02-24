package com.example.fumagalli2020.UI;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.Class.Employee;
import com.example.fumagalli2020.Helper.EmployeeModifyHelper;
import com.example.fumagalli2020.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class EmployeeModify extends AppCompatActivity {

    TextView tvName;
    TextView tvSurname;
    EditText edtPhone;
    Spinner spnType;
    Button btnAcceptModify;
    protected String name, surname, phone, type, marketId, employeeId;
    EmployeeModifyHelper employeeModifyHelper;

    private ArrayAdapter adptTypeEmployee;
    private List<String> typeList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_employee_modify);

        tvName = findViewById(R.id.tvNameEmployee);
        tvSurname = findViewById(R.id.tvSurnameEmployee);
        edtPhone = findViewById(R.id.edtPhoneEmployee);
        spnType = findViewById(R.id.spnTypeEmployee);
        btnAcceptModify = findViewById(R.id.btnSaveModify);

        employeeModifyHelper = new EmployeeModifyHelper();

        Bundle bundle = getIntent().getExtras();

        name = bundle.getString("name");
        surname = bundle.getString("surname");
        phone = bundle.getString("phone");
        type = bundle.getString("type");
        marketId = bundle.getString("marketId");
        employeeId = bundle.getString("employeeId");

        typeList.add("Amministratore Negozio");
        typeList.add("Addetto alla Logistica");

        adptTypeEmployee = new ArrayAdapter<String>(getApplicationContext(), R.layout.first_custom_spinner,typeList);
        adptTypeEmployee.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnType.setAdapter(adptTypeEmployee);

        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setText(spnType.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        employeeModifyHelper.LoadEmployee(tvName,tvSurname,edtPhone,spnType,employeeId);


    }

    @Override
    protected void onStart(){
        super.onStart();

        btnAcceptModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipo;
                if(spnType.getSelectedItem().toString().equals("Amministratore Negozio")){
                    tipo = "1";
                }else{
                    tipo = "2";
                }
                OnCompleteListener onCompleteListener = new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Intent intent = new Intent(EmployeeModify.this,EmployeeList.class);
                        startActivity(intent);
                        Toast.makeText(EmployeeModify.this,"Modifica Effettuata",Toast.LENGTH_LONG).show();
                    }
                };
                employeeModifyHelper.UpdateEmployee(employeeId,edtPhone,tipo,onCompleteListener);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itmAdminLogout:
                FirebaseAuth.getInstance().signOut();
                Intent intentAdmLogout = new Intent(this,Login.class);
                startActivity(intentAdmLogout);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
