package com.example.fumagalliproject.UserInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fumagalliproject.Class.Cart;
import com.example.fumagalliproject.Class.Product;
import com.example.fumagalliproject.R;

import java.util.List;

public class AdapterCartProduct extends ArrayAdapter<Cart> {

    public AdapterCartProduct(Context context, int textViewResourceId, List<Cart> objects){
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_cartproduct, null);

        TextView tvPrdName = convertView.findViewById(R.id.tvItemCartProductName);
        TextView tvPrdTypeQnt = convertView.findViewById(R.id.tvItemCartProductTypeQnt);
        TextView tvPrdSelectedQnt = convertView.findViewById(R.id.tvItemCartProductQntSelected);

        Cart cart = getItem(position);

        tvPrdName.setText(cart.getCartProduct().getName());
        tvPrdTypeQnt.setText(cart.getCartProduct().getTypeQuantity());
        tvPrdSelectedQnt.setText(cart.getProductQnt());

        return convertView;
    }

}
