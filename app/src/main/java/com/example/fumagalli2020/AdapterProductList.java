package com.example.fumagalli2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.fumagalli2020.Class.Product;

import java.util.List;

public class AdapterProductList extends ArrayAdapter<Product> {

    public AdapterProductList(Context context, int textViewResourceId, List<Product> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        convertView = inflater.inflate(R.layout.item_list_product, null);

        TextView tvProductName = convertView.findViewById(R.id.tvItemNameProduct);
        TextView tvProductDesc = convertView.findViewById(R.id.tvItemDescProduct);
        TextView tvProductCode = convertView.findViewById(R.id.tvItemCodeProduct);

        final Product product = getItem(position);

        tvProductName.setText(product.getName());
        tvProductDesc.setText("Scad. "+product.getExpireDate()+" Qta. "+product.getQuantity());
        tvProductCode.setText(product.getProductId());

        return convertView;
    }
}
