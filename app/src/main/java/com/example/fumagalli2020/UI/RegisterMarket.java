package com.example.fumagalli2020.UI;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.ExpandableHeightListView;
import com.example.fumagalli2020.Helper.RegisterMarketHelper;
import com.example.fumagalli2020.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterMarket extends AppCompatActivity {
    protected RegisterMarketHelper registerMarketHelper;
    protected Spinner spnChain;
    private Button btnRegMarketToRegAdmin;
    private List<String> chainList = new ArrayList<String>();
    Map<String,String> chainMap = new HashMap<String,String>();
    EditText edtRegMarketName;
    EditText edtRegMarketCity;
    EditText edtRegMarketAddress;
    EditText edtRegMarketCAP;
    EditText edtRegMarketPhone;
    EditText edtRegMarketEmail;
    protected ExpandableHeightListView lstDaysOfWeek;
    protected ArrayAdapter adptDaysOfWeek;
    protected ArrayAdapter adptChain;
    protected List<String> stringList = new ArrayList<String>();
    protected List<String> businessHour = new ArrayList<String>();
    protected List<Boolean> continuedSchedule = new ArrayList<Boolean>();
    protected List<Boolean> closedDays = new ArrayList<Boolean>();
    protected String marketId;
    protected OnCompleteListener<Void> onCompleteListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regmarket);
        marketId = "";

        for(int x = 0; x <7 ; x++){
            closedDays.add(false);
            continuedSchedule.add(false);
            for(int y = 0; y < 4; y++){
                businessHour.add("0");
            }
        }

        spnChain = findViewById(R.id.spChain);
        edtRegMarketName = findViewById(R.id.edtMarketName);
        edtRegMarketCity = findViewById(R.id.edtMarketCity);
        edtRegMarketAddress = findViewById(R.id.edtMarketAddress);
        edtRegMarketCAP = findViewById(R.id.edtMarketCAP);
        edtRegMarketPhone = findViewById(R.id.edtMarketPhone);
        edtRegMarketEmail = findViewById(R.id.edtMarketEmail);

        registerMarketHelper = new RegisterMarketHelper();
        btnRegMarketToRegAdmin = findViewById(R.id.btnRegMarketToRegAdmin);

        registerMarketHelper.LoadChain(chainMap,chainList,spnChain,getApplicationContext());

        lstDaysOfWeek = findViewById(R.id.lstRegMrktDayWeek);
        lstDaysOfWeek.setExpanded(true);
        stringList.add(getResources().getString(R.string.strLun));
        stringList.add(getResources().getString(R.string.strMar));
        stringList.add(getResources().getString(R.string.strMer));
        stringList.add(getResources().getString(R.string.strGio));
        stringList.add(getResources().getString(R.string.strVen));
        stringList.add(getResources().getString(R.string.strSab));
        stringList.add(getResources().getString(R.string.strDom));
        adptDaysOfWeek = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_list_dayweekmrkt, R.id.tvItemNameDay,stringList){


            @Override
            public long getItemId(int position) {
                return position;
            }

            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_list_dayweekmrkt,parent,false);
                }

                final TextView tvDayName = convertView.findViewById(R.id.tvItemNameDay);
                final CheckBox chbDayContinued = convertView.findViewById(R.id.chItemDayContinued);
                final CheckBox chbDayClosed = convertView.findViewById(R.id.chItemDayClosed);
                final EditText edtDayOpenMorn = convertView.findViewById(R.id.edtItemDayOpenMorn);
                final EditText edtDayCloseMorn = convertView.findViewById(R.id.edtItemDayCloseMorn);
                final EditText edtDayOpenAfter = convertView.findViewById(R.id.edtItemDayOpenAfter);
                final EditText edtDayCloseAfter = convertView.findViewById(R.id.edtItemDayCloseAfter);
                final LinearLayout layDayMorn = convertView.findViewById(R.id.layItemDayMorn);
                final LinearLayout layDayAfter = convertView.findViewById(R.id.layItemDayAfter);


                tvDayName.setText(stringList.get(position));

                chbDayContinued.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!chbDayContinued.isChecked()){
                            layDayAfter.setVisibility(View.VISIBLE);
                            continuedSchedule.set(position,false);
                        }else{
                            layDayMorn.setVisibility(View.VISIBLE);
                            layDayAfter.setVisibility(View.GONE);
                            edtDayOpenAfter.setText(null);
                            edtDayCloseAfter.setText(null);
                            businessHour.set((position*4)+2,"0");
                            businessHour.set((position*4)+3,"0");
                            chbDayClosed.setChecked(false);
                            closedDays.set(position,false);
                            continuedSchedule.set(position,true);
                        }
                    }
                });

                chbDayClosed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!chbDayClosed.isChecked()){
                            layDayMorn.setVisibility(View.VISIBLE);
                            layDayAfter.setVisibility(View.VISIBLE);
                            closedDays.set(position,false);
                            continuedSchedule.set(position,false);
                        }else{
                            layDayMorn.setVisibility(View.GONE);
                            edtDayOpenMorn.setText(null);
                            edtDayCloseMorn.setText(null);
                            businessHour.set((position*4),"0");
                            businessHour.set((position*4)+1,"0");
                            layDayAfter.setVisibility(View.GONE);
                            edtDayOpenAfter.setText(null);
                            edtDayCloseAfter.setText(null);
                            businessHour.set((position*4)+2,"0");
                            businessHour.set((position*4)+3,"0");
                            chbDayContinued.setChecked(false);
                            closedDays.set(position,true);
                        }
                    }
                });

                edtDayOpenMorn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edtDayOpenMorn.setError(null);
                        TimePickerDialog tpdDaysOpenMorn = new TimePickerDialog(RegisterMarket.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                                minutes = roundUp(minutes);
                                String minute = "";
                                String hour = "";
                                if(minutes < 10)
                                    minute = "0"+minutes;
                                else
                                    minute = ""+minutes;
                                if(hourOfDay < 10)
                                    hour = "0"+hourOfDay;
                                else
                                    hour = ""+hourOfDay;
                                edtDayOpenMorn.setText((hour+":"+minute));

                            }

                        }, 0, 0, true);
                        tpdDaysOpenMorn.show();
                    }
                });

                edtDayCloseMorn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edtDayCloseMorn.setError(null);
                        TimePickerDialog tpdDaysCloseMorn = new TimePickerDialog(RegisterMarket.this, android.R.style.Theme_Holo_Light_Dialog,new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                                minutes = roundUp(minutes);
                                String minute = "";
                                String hour = "";
                                if(minutes < 10)
                                    minute = "0"+minutes;
                                else
                                    minute = ""+minutes;
                                if(hourOfDay < 10)
                                    hour = "0"+hourOfDay;
                                else
                                    hour = ""+hourOfDay;
                                edtDayCloseMorn.setText((hour + ":" + minute));
                            }
                        }, 0, 0, true);
                        tpdDaysCloseMorn.show();
                    }
                });

                edtDayOpenAfter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edtDayOpenAfter.setError(null);
                        TimePickerDialog tpdDaysOpenAfter = new TimePickerDialog(RegisterMarket.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                                minutes = roundUp(minutes);
                                String minute = "";
                                String hour = "";
                                if(minutes < 10)
                                    minute = "0"+minutes;
                                else
                                    minute = ""+minutes;
                                if(hourOfDay < 10)
                                    hour = "0"+hourOfDay;
                                else
                                    hour = ""+hourOfDay;
                                edtDayOpenAfter.setText((hour + ":" + minute));
                            }
                        }, 0, 0, true);
                        tpdDaysOpenAfter.show();
                    }
                });

                edtDayCloseAfter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edtDayCloseAfter.setError(null);
                        TimePickerDialog tpdDaysCloseAfter = new TimePickerDialog(RegisterMarket.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                                minutes = roundUp(minutes);
                                String minute = "";
                                String hour = "";
                                if(minutes < 10)
                                    minute = "0"+minutes;
                                else
                                    minute = ""+minutes;
                                if(hourOfDay < 10)
                                    hour = "0"+hourOfDay;
                                else
                                    hour = ""+hourOfDay;
                                edtDayCloseAfter.setText((hour + ":" + minute));
                            }
                        }, 0, 0, true);
                        tpdDaysCloseAfter.show();
                    }
                });

                if(!chbDayClosed.isChecked()){
                    edtDayOpenMorn.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            businessHour.set((position*4),edtDayOpenMorn.getText().toString());
                            if(!edtDayCloseMorn.getText().toString().trim().equals("")){
                                if(edtDayCloseMorn.getText().toString().trim().compareTo(edtDayOpenMorn.getText().toString().trim()) < 0){
                                    edtDayOpenMorn.setError("");
                                }
                            }
                        }
                    });
                    edtDayCloseMorn.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {}

                        @Override
                        public void afterTextChanged(Editable s) {
                            businessHour.set((position*4)+1,edtDayCloseMorn.getText().toString());
                            if(!edtDayOpenMorn.getText().toString().trim().equals("")){
                                if(edtDayCloseMorn.getText().toString().trim().compareTo(edtDayOpenMorn.getText().toString().trim()) < 0){
                                    edtDayCloseMorn.setError("");
                                }
                            }
                        }
                    });
                    if(!chbDayContinued.isChecked()){
                        edtDayOpenAfter.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {}

                            @Override
                            public void afterTextChanged(Editable s) {
                                businessHour.set((position*4)+2,edtDayOpenAfter.getText().toString());
                                if(!edtDayCloseAfter.getText().toString().trim().equals("")){
                                    if(edtDayCloseAfter.getText().toString().trim().compareTo(edtDayOpenAfter.getText().toString().trim()) < 0){
                                        edtDayOpenAfter.setError("");
                                    }
                                }
                                if(!edtDayCloseMorn.getText().toString().trim().equals("")){
                                    if(edtDayOpenAfter.getText().toString().trim().compareTo(edtDayCloseMorn.getText().toString().trim()) < 0){
                                        edtDayOpenAfter.setError("");
                                    }
                                }
                            }
                        });
                        edtDayCloseAfter.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {}

                            @Override
                            public void afterTextChanged(Editable s) {
                                businessHour.set((position*4)+3,edtDayCloseAfter.getText().toString());
                                if(!edtDayOpenAfter.getText().toString().trim().equals("")){
                                    if(edtDayCloseAfter.getText().toString().trim().compareTo(edtDayOpenAfter.getText().toString().trim()) < 0){
                                        edtDayCloseAfter.setError("");
                                    }
                                }
                            }
                        });
                    }
                }


                return convertView;
            }
        };
        lstDaysOfWeek.setAdapter(adptDaysOfWeek);

        spnChain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setText(spnChain.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        onCompleteListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterMarket.this,"Registrato",Toast.LENGTH_LONG).show();
                    gotoregemployee();
                }else{
                    Toast.makeText(RegisterMarket.this,"Registrazione Fallita",Toast.LENGTH_LONG).show();
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        btnRegMarketToRegAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 marketId = registerMarketHelper.RegisterMarket(spnChain,edtRegMarketName,edtRegMarketCity,edtRegMarketAddress,edtRegMarketCAP,
                        edtRegMarketPhone,edtRegMarketEmail,businessHour,continuedSchedule,closedDays,getApplicationContext(),chainMap,onCompleteListener);
            }
        });
    }

    protected int roundUp(int n) {
        return (n + 4) / 5 * 5;
    }

    public void gotoregemployee(){
        Bundle bundle = new Bundle();
        bundle.putString("marketId",marketId);
        bundle.putInt("source",0);
        Intent intent = new Intent(this,RegisterEmployee.class);
        intent.putExtras(bundle);
        startActivity(intent);
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
