package com.example.fumagalliproject.UserInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fumagalliproject.Class.Market;
import com.example.fumagalliproject.R;

import java.util.List;

public class AdapterMarketList extends ArrayAdapter<Market> {



    public AdapterMarketList(Context context, int textViewResourceId, List<Market> objects){
        super(context,textViewResourceId,objects);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_marketlist, null);

        TextView tvAddress = convertView.findViewById(R.id.tvItemMarketAddress);
        TextView tvCity = convertView.findViewById(R.id.tvItemMarketCity);
        TextView tvPhone = convertView.findViewById(R.id.tvItemMarketPhone);

        Market market = getItem(position);

        tvAddress.setText(market.getAddress());
        tvCity.setText(market.getCity());
        tvPhone.setText(market.getPhone());

        return convertView;
    }

}
