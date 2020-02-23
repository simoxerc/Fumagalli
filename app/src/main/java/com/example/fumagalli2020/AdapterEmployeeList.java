package com.example.fumagalli2020;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.fumagalli2020.Class.Employee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterEmployeeList extends ArrayAdapter<Employee> {

    public AdapterEmployeeList(Context context, int textViewResourceId, List<Employee> objects){
        super(context,textViewResourceId,objects);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        convertView = inflater.inflate(R.layout.item_list_employee, null);

        TextView tvEmployeeName = convertView.findViewById(R.id.tvItemNameEmployee);
        TextView tvEmployeeWork = convertView.findViewById(R.id.tvItemWorkEmployee);
        TextView tvEmployeeCode = convertView.findViewById(R.id.tvItemCodeEmployee);
        ImageButton btnItemDeleteEmployee = convertView.findViewById(R.id.btnItemDeleteEmployee);

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
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(employee.getEmployeeId());
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(parent.getContext(), "Carrello eliminato", Toast.LENGTH_LONG).show();
                            remove(employee);
                            notifyDataSetChanged();
                        }
                        else
                            Toast.makeText(parent.getContext(),"Eliminazione carrello non riuscita",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final AlertDialog alertDialog = builder.create();

        btnItemDeleteEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        return convertView;
    }

}
