package com.example.fumagalli2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.fumagalli2020.Class.Employee;

import java.util.List;

public class AdapterEmployeeList extends ArrayAdapter<Employee> {

    public AdapterEmployeeList(Context context, int textViewResourceId, List<Employee> objects){
        super(context,textViewResourceId,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        convertView = inflater.inflate(R.layout.item_list_employee, null);

        TextView tvEmployeeName = convertView.findViewById(R.id.tvItemNameEmployee);
        TextView tvEmployeeWork = convertView.findViewById(R.id.tvItemWorkEmployee);
        TextView tvEmployeeCode = convertView.findViewById(R.id.tvItemCodeEmployee);

        Employee employee = getItem(position);

        tvEmployeeName.setText((employee.getName() + " " + employee.getSurname()));
        if (employee.getType().equals("1"))
            tvEmployeeWork.setText("Amministratore Negozio");
        else
            tvEmployeeWork.setText("Addetto alla Logistica");
        tvEmployeeCode.setText(employee.getEmployeeId());


        return convertView;
    }

}
