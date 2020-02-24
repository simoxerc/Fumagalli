package com.example.fumagalli2020;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.fumagalli2020.Class.Product;
import com.example.fumagalli2020.UI.ProductModify;

import java.util.List;

public class AdapterProductList extends ArrayAdapter<Product> {

    public AdapterProductList(Context context, int textViewResourceId, List<Product> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        convertView = inflater.inflate(R.layout.item_list_product, null);

        TextView tvProductName = convertView.findViewById(R.id.tvItemNameProduct);
        TextView tvProductDesc = convertView.findViewById(R.id.tvItemDescProduct);
        TextView tvProductCode = convertView.findViewById(R.id.tvItemCodeProduct);
        ImageButton btnItemModifyProduct = convertView.findViewById(R.id.btnItemModifyProduct);

        final Product product = getItem(position);

        tvProductName.setText(product.getName());
        tvProductDesc.setText("Scad. "+product.getExpireDate()+" Qta. "+product.getQuantity());
        tvProductCode.setText(product.getProductId());

        btnItemModifyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), ProductModify.class);
                Bundle bundle = new Bundle();
                bundle.putString("marketId",product.getMarketId());
                bundle.putString("categoryId",product.getCategoryId());
                bundle.putString("productId",product.getProductId());
                bundle.putString("productName",product.getName());
                bundle.putString("productOrigin",product.getOrigin());
                bundle.putString("productQnt",product.getQuantity());
                bundle.putString("productPrice",product.getPrice());
                bundle.putString("productExp",product.getExpireDate());
                bundle.putString("productPckg",product.getPackagingDate());
                bundle.putString("productType",product.getType());
                intent.putExtras(bundle);
                parent.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
