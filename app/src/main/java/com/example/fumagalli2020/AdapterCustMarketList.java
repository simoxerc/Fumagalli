package com.example.fumagalli2020;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fumagalli2020.Class.Cart;
import com.example.fumagalli2020.Class.Market;
import com.example.fumagalli2020.UI.CustCategoryList;
import com.example.fumagalli2020.UI.CustMarketList;

import java.util.List;

public class AdapterCustMarketList extends ArrayAdapter<Market> {

    public AdapterCustMarketList(Context context, int textViewResourceId, List<Market> object){
        super(context,textViewResourceId,object);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_cust_list_market, null);

        TextView tvItemMarketName = convertView.findViewById(R.id.tvItemMarketName);
        TextView tvItemMarketAddress = convertView.findViewById(R.id.tvItemMarketAddress);
        TextView tvItemMarketPhone = convertView.findViewById(R.id.tvItemMarketPhone);
        TextView tvItemMarketEmail = convertView.findViewById(R.id.tvItemMarketEmail);

        final Market market = getItem(position);

        tvItemMarketName.setText(market.getName());
        tvItemMarketAddress.setText(market.getAddress());
        tvItemMarketPhone.setText(market.getNumber());
        tvItemMarketEmail.setText(market.getEmail());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), CustCategoryList.class);
                Bundle bundle = new Bundle();
                bundle.putString("marketId",market.getMarketId());
                bundle.putString("chainId",market.getChain_Id());
                intent.putExtras(bundle);
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
