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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.ExpandableHeightListView;
import com.example.fumagalli2020.Helper.MarketInfoHelper;
import com.example.fumagalli2020.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MarketInfo extends AppCompatActivity {

    protected EditText edtName;
    protected EditText edtCity;
    protected EditText edtAddress;
    protected EditText edtCap;
    protected EditText edtPhone;
    protected EditText edtEmail;
    protected Button btnModifyInfo;
    protected Button btnModifyHour;
    protected Button btnAcceptModify;
    protected Integer modifyType;
    protected String oldAddress;
    protected String oldEmail;
    protected ExpandableHeightListView lstDaysOfWeek;
    protected ArrayAdapter adptDaysOfWeek;
    protected List<String> stringList = new ArrayList<String>();
    protected List<String> businessHour = new ArrayList<String>();
    protected List<Boolean> oldClosedDays = new ArrayList<Boolean>();
    protected List<Boolean> oldContinedSchedule = new ArrayList<Boolean>();
    protected List<Boolean> continuedSchedule = new ArrayList<Boolean>();
    protected List<Boolean> closedDays = new ArrayList<Boolean>();
    protected MarketInfoHelper marketInfoHelper;
    private TextView[][] tabletime = new TextView[7][2];
    protected String marketId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_info);

        marketInfoHelper = new MarketInfoHelper();

        Bundle bundle = getIntent().getExtras();
        marketId = bundle.getString("marketId");

        for(int x = 0; x<7;x++){
            for(int y = 0;y<2;y++){
                String tbID = "tb" + x + y;
                int resID = getResources().getIdentifier(tbID, "id", getPackageName());
                tabletime[x][y] = findViewById(resID);
            }
        }

        edtName = findViewById(R.id.edtInfoMarketName);
        edtCity = findViewById(R.id.edtInfoMarketCity);
        edtAddress = findViewById(R.id.edtInfoMarketAddress);
        edtCap = findViewById(R.id.edtInfoMarketCAP);
        edtPhone = findViewById(R.id.edtInfoMarketPhone);
        edtEmail = findViewById(R.id.edtInfoMarketEmail);

        btnModifyInfo = findViewById(R.id.btnInfoMarketModify);
        btnModifyHour = findViewById(R.id.btnHoursMarketModify);
        btnAcceptModify = findViewById(R.id.btnAcceptModify);

        edtName.setEnabled(false);
        edtCity.setEnabled(false);
        edtAddress.setEnabled(false);
        edtCap.setEnabled(false);
        edtPhone.setEnabled(false);
        edtEmail.setEnabled(false);

        lstDaysOfWeek = findViewById(R.id.lstInfoMrktDayWeek);
        lstDaysOfWeek.setExpanded(true);
        stringList.add(getResources().getString(R.string.strLun));
        stringList.add(getResources().getString(R.string.strMar));
        stringList.add(getResources().getString(R.string.strMer));
        stringList.add(getResources().getString(R.string.strGio));
        stringList.add(getResources().getString(R.string.strVen));
        stringList.add(getResources().getString(R.string.strSab));
        stringList.add(getResources().getString(R.string.strDom));
        adptDaysOfWeek = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_list_dayweekmrkt,stringList){


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
                        chbContinued(chbDayContinued,layDayAfter,layDayMorn,edtDayOpenAfter,edtDayCloseAfter,chbDayClosed,position);
                    }
                });

                chbDayClosed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chbClose(chbDayContinued,layDayAfter,layDayMorn,edtDayOpenAfter,edtDayCloseAfter,chbDayClosed,position,
                                edtDayOpenMorn,edtDayCloseMorn);
                    }
                });

                edtDayOpenMorn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edtDayOpenMorn.setError(null);
                        TimePickerDialog tpdDaysOpenMorn = new TimePickerDialog(MarketInfo.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
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
                        TimePickerDialog tpdDaysCloseMorn = new TimePickerDialog(MarketInfo.this, android.R.style.Theme_Holo_Light_Dialog,new TimePickerDialog.OnTimeSetListener() {
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
                        TimePickerDialog tpdDaysOpenAfter = new TimePickerDialog(MarketInfo.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
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
                        TimePickerDialog tpdDaysCloseAfter = new TimePickerDialog(MarketInfo.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
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

            private void chbClose(CheckBox chbDayContinued, LinearLayout layDayAfter, LinearLayout layDayMorn, EditText edtDayOpenAfter,
                                  EditText edtDayCloseAfter, CheckBox chbDayClosed, Integer position, EditText edtDayOpenMorn, EditText edtDayCloseMorn){
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

            private void chbContinued(CheckBox chbDayContinued, LinearLayout layDayAfter, LinearLayout layDayMorn, EditText edtDayOpenAfter, EditText edtDayCloseAfter, CheckBox chbDayClosed, Integer position){
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

        };
        lstDaysOfWeek.setAdapter(adptDaysOfWeek);

        marketInfoHelper.LoadMarketInfo(marketId,edtName,edtCity,edtAddress,edtCap,edtPhone,edtEmail,businessHour,continuedSchedule,closedDays,adptDaysOfWeek,oldContinedSchedule,oldClosedDays,tabletime);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.adminMrkt_menu);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.admMrkt_navigation_employee:
                        Intent a = new Intent(MarketInfo.this, EmployeeList.class);
                        startActivity(a);
                        break;
                    case R.id.admMrkt_navigation_infomrkt:
                        break;
                }
                return false;
            }
        });


    }

    @Override
    protected void onStart(){
        super.onStart();

        btnModifyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtCity.setEnabled(true);
                edtAddress.setEnabled(true);
                edtCap.setEnabled(true);
                edtPhone.setEnabled(true);
                edtEmail.setEnabled(true);
                oldAddress = edtAddress.getText().toString().trim()+", "+edtCity.getText().toString().trim()+" "+edtCap.getText().toString().trim();
                oldEmail = edtEmail.getText().toString();
                modifyType = 0;
                btnAcceptModify.setVisibility(View.VISIBLE);
                btnModifyHour.setVisibility(View.GONE);
                btnModifyInfo.setVisibility(View.GONE);
            }
        });

        btnModifyHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAcceptModify.setVisibility(View.VISIBLE);
                btnModifyHour.setVisibility(View.GONE);
                btnModifyInfo.setVisibility(View.GONE);
                closedDays.clear();
                continuedSchedule.clear();
                businessHour.clear();
                for(int x = 0; x <7 ; x++){
                    closedDays.add(false);
                    continuedSchedule.add(false);
                    for(int y = 0; y < 4; y++){
                        businessHour.add("0");
                    }
                }
                lstDaysOfWeek.setVisibility(View.VISIBLE);
                modifyType = 1;
            }
        });

        btnAcceptModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(modifyType){
                    case 0:
                        OnCompleteListener onCompleteListener0 = new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(MarketInfo.this,"Modifica Effettuata",Toast.LENGTH_LONG).show();
                                    edtCity.setEnabled(false);
                                    edtAddress.setEnabled(false);
                                    edtCap.setEnabled(false);
                                    edtPhone.setEnabled(false);
                                    edtEmail.setEnabled(false);
                                    btnAcceptModify.setVisibility(View.GONE);
                                    btnModifyHour.setVisibility(View.VISIBLE);
                                    btnModifyInfo.setVisibility(View.VISIBLE);
                                }
                            }
                        };
                        marketInfoHelper.ModifyMarketContact(marketId,edtCity,edtAddress,oldAddress,oldEmail,edtCap,edtPhone,edtEmail,MarketInfo.this,onCompleteListener0);
                        break;
                    case 1:
                        OnCompleteListener onCompleteListener1 = new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(MarketInfo.this,"Modifica Effettuata",Toast.LENGTH_LONG).show();
                                    btnAcceptModify.setVisibility(View.GONE);
                                    btnModifyHour.setVisibility(View.VISIBLE);
                                    btnModifyInfo.setVisibility(View.VISIBLE);
                                    marketInfoHelper.LoadMarketInfo(marketId,edtName,edtCity,edtAddress,edtCap,edtPhone,edtEmail,businessHour,
                                            continuedSchedule,closedDays,adptDaysOfWeek,oldContinedSchedule,oldClosedDays,tabletime);
                                    lstDaysOfWeek.setVisibility(View.GONE);
                                }
                            }
                        };
                        marketInfoHelper.ModifyMarketHours(marketId,businessHour,continuedSchedule,closedDays,MarketInfo.this,onCompleteListener1,oldContinedSchedule,oldClosedDays);
                        break;
                }
            }
        });


    }

    protected int roundUp(int n) {
        return (n + 4) / 5 * 5;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MarketInfo.this,EmployeeList.class);
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
