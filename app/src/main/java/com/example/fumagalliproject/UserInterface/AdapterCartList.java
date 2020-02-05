package com.example.fumagalliproject.UserInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fumagalliproject.Class.Cart;
import com.example.fumagalliproject.R;

import java.util.List;

public class AdapterCartList extends ArrayAdapter<Cart> {


    public AdapterCartList(Context context, int textViewResourceId, List<Cart> objects){
        super(context,textViewResourceId,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_cartlist, null);

        TextView tvMarketName = convertView.findViewById(R.id.tvItemCartMkName);
        TextView tvMarketAddress = convertView.findViewById(R.id.tvItemCartMkAddress);
        TextView tvMarketCity = convertView.findViewById(R.id.tvItemCartMkCity);
        TextView tvMarketPhone = convertView.findViewById(R.id.tvItemCartMkPhone);
        TextView tvPrice = convertView.findViewById(R.id.tvItemCartPrice);

        Cart cart = getItem(position);

        tvMarketName.setText(cart.getMarketName());
        tvMarketAddress.setText(cart.getMarketAddress());
        tvMarketCity.setText(cart.getMarketCity());
        tvMarketPhone.setText(cart.getMarketPhone());
        tvPrice.setText(cart.getTotalprice());

        return convertView;
    }

}
