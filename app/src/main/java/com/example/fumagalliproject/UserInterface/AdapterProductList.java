package com.example.fumagalliproject.UserInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fumagalliproject.Class.Product;
import com.example.fumagalliproject.R;

import java.util.List;

public class AdapterProductList extends ArrayAdapter<Product> {

    public AdapterProductList(Context context, int textViewResourceId, List<Product> objects){
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_productlist, null);

        TextView tvName = convertView.findViewById(R.id.tvItemProductName);
        TextView tvSource = convertView.findViewById(R.id.tvItemProductSource);
        TextView tvTypeQnt = convertView.findViewById(R.id.tvItemProductTypeQnt);

        Product product = getItem(position);

        tvName.setText(product.getName());
        tvSource.setText(product.getSource());
        tvTypeQnt.setText(product.getTypeQuantity());

        return convertView;
    }

}
