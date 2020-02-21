package com.example.fumagalli2020.UI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fumagalli2020.Helper.CheckoutHelper;
import com.example.fumagalli2020.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Calendar;

public class Checkout extends AppCompatActivity {
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private EditText edtOrderDate,edtOrderTime,edtPayCard,edtCardExpMonth,edtCardExpYear,edtCardCVV;
    private Button btnPay;
    private TextView[][] tabletime = new TextView[7][2];
    private TextView tvTotalPay;
    private String currentMarketId,totalPay,currentCartId;
    private CheckoutHelper checkoutHelper;
    private OnCompleteListener onCompleteListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        edtOrderDate = findViewById(R.id.edtOrderDate);
        edtOrderTime = findViewById(R.id.edtOrderTime);
        edtPayCard = findViewById(R.id.edtPayCard);
        edtCardExpMonth = findViewById(R.id.edtCardExpMonth);
        edtCardExpYear = findViewById(R.id.edtCardExpYear);
        edtCardCVV = findViewById(R.id.edtCardVV);
        btnPay = findViewById(R.id.btnPay);
        tvTotalPay = findViewById(R.id.tvTotalPay);
        checkoutHelper = new CheckoutHelper();

        for(int x = 0; x<7;x++){
            for(int y = 0;y<2;y++){
                String tbID = "tb" + x + y;
                int resID = getResources().getIdentifier(tbID, "id", getPackageName());
                tabletime[x][y] = findViewById(resID);
            }
        }

        Bundle bundle = getIntent().getExtras();
        currentMarketId = bundle.getString("marketId");
        totalPay = bundle.getString("total");
        currentCartId = bundle.getString("cartId");


        checkoutHelper.LoadMarketTime(tabletime,currentMarketId);
        tvTotalPay.setText(("Totale :"+totalPay+"€"));

        onCompleteListener = new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(Checkout.this,"Ordine effettuato!",Toast.LENGTH_LONG).show();
                    gotocustcartlist();
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();

        edtOrderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOrderDate.setError(null);
                setDate();
                datePickerDialog.show();
            }
        });

        edtOrderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOrderTime.setError(null);
                setTime();
                timePickerDialog.show();
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkoutHelper.createorder(edtPayCard,edtCardExpMonth,edtCardExpYear,edtCardCVV,edtOrderDate,edtOrderTime,tabletime,
                        onCompleteListener,currentCartId,currentMarketId,totalPay,Checkout.this);
            }
        });


    }

    private void setDate(){
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edtOrderDate.setText((dayOfMonth + "/" + (month + 1) +  "/" + year).toString());
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        calendar.add(calendar.DAY_OF_MONTH,7);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
    }

    private void setTime(){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                selectedMinute = roundUp(selectedMinute);
                String minute = "";
                String hour = "";
                if(selectedMinute < 10)
                    minute = "0"+selectedMinute;
                else
                    minute = ""+selectedMinute;
                if(selectedHour < 10)
                    hour = "0"+selectedHour;
                else
                    hour = ""+selectedHour;
                edtOrderTime.setText( (hour + ":" + minute));
            }
        }, hour, minute, true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this,CustCartSingle.class);
        Bundle bundle = new Bundle();
        bundle.putString("cartId",currentCartId);
        bundle.putString("marketId",currentMarketId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected int roundUp(int n) {
        return (n + 4) / 5 * 5;
    }

    private void gotocustcartlist(){
        Intent intent = new Intent(Checkout.this,CustCartList.class);
        startActivity(intent);
    }

}
