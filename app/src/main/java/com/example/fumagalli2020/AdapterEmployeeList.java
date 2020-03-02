package com.example.fumagalli2020;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.fumagalli2020.Class.Employee;
import com.example.fumagalli2020.UI.EmployeeModify;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
<<<<<<< HEAD
<<<<<<< HEAD
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
=======
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
=======
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575

import java.util.List;

public class AdapterEmployeeList extends ArrayAdapter<Employee> {

    protected String employeeUserId;
    protected String employeeId;

    public AdapterEmployeeList(Context context, int textViewResourceId, List<Employee> objects){
        super(context,textViewResourceId,objects);
    }

    @Override
<<<<<<< HEAD
<<<<<<< HEAD
    public View getView(final int position, View convertView, final ViewGroup parent) {
=======
    public View getView(int position, View convertView, final ViewGroup parent) {
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
=======
    public View getView(int position, View convertView, final ViewGroup parent) {
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        convertView = inflater.inflate(R.layout.item_list_employee, null);

        TextView tvEmployeeName = convertView.findViewById(R.id.tvItemNameEmployee);
        TextView tvEmployeeWork = convertView.findViewById(R.id.tvItemWorkEmployee);
        TextView tvEmployeeCode = convertView.findViewById(R.id.tvItemCodeEmployee);
        ImageButton btnItemDeleteEmployee = convertView.findViewById(R.id.btnItemDeleteEmployee);
        ImageButton btnModifyEmployee = convertView.findViewById(R.id.btnItemModifyEmployee);

        final Employee employee = getItem(position);

        tvEmployeeName.setText((employee.getName() + " " + employee.getSurname()));
        if (employee.getType().equals("1"))
            tvEmployeeWork.setText("Amministratore Negozio");
        else
            tvEmployeeWork.setText("Addetto alla Logistica");
        tvEmployeeCode.setText(employee.getEmployeeId());

        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
        builder.setMessage("Vuoi davvero rimuovere il dipendente?");
        builder.setCancelable(false);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
<<<<<<< HEAD
<<<<<<< HEAD

            @Override
            public void onClick(DialogInterface dialog, int which) {
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            if(employeeUserId != null) {
                                final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                if(!employeeUserId.equals(currentUser)) {
                                    databaseReference.child(employeeUserId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(parent.getContext(), "Dipendente eliminato", Toast.LENGTH_LONG).show();
                                                remove(employee);
                                                parent.updateViewLayout(parent.findViewById(R.id.employeeList), null);
                                            } else
                                                Toast.makeText(parent.getContext(), "Eliminazione Dipendente non riuscita", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }else{
                                    Toast.makeText(parent.getContext(),"Non puoi eliminare te stesso", Toast.LENGTH_LONG).show();
                                }
                        }else{
                            Toast.makeText(parent.getContext(),"Eliminazione Dipendente non riuscita",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

=======
=======
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(employee.getEmployeeId());
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(parent.getContext(), "Dipendente Eliminato", Toast.LENGTH_LONG).show();
                            remove(employee);
                            notifyDataSetChanged();
                        }
                        else
                            Toast.makeText(parent.getContext(),"Eliminazione dipendente non riuscita",Toast.LENGTH_LONG).show();
<<<<<<< HEAD
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
=======
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
                    }
                });

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
=======

>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
            }
        });

        final AlertDialog alertDialog = builder.create();

        btnItemDeleteEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
<<<<<<< HEAD
                employeeId = employee.getEmployeeId();
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            if (ds.hasChild("employeeId")) {
                                if (ds.child("employeeId").getValue(String.class).equals(employeeId)) {
                                    employeeUserId = ds.getKey();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
=======
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
=======
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
                alertDialog.show();
            }
        });

        btnModifyEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(parent.getContext(), EmployeeModify.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",employee.getName());
                bundle.putString("surname",employee.getSurname());
                bundle.putString("phone",employee.getMobile());
                bundle.putString("type",employee.getType());
                bundle.putString("marketId",employee.getMarketId());
                bundle.putString("employeeId",employee.getEmployeeId());
                intent.putExtras(bundle);
                parent.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

}
