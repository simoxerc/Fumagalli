package com.example.fumagalli2020;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.fumagalli2020.Class.Cart;
import com.example.fumagalli2020.Class.Chain;
import com.example.fumagalli2020.UI.CustMarketList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCustChainList extends ArrayAdapter<Chain> {

    public AdapterCustChainList(Context context, int textViewResourceId, List<Chain> object){
        super(context,textViewResourceId,object);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_cust_list_chain, null);

        ImageView imgItemChainChain = convertView.findViewById(R.id.imgItemChainChain);

        final Chain chain = getItem(position);

        Picasso.get().load(chain.getImgLink()).into(imgItemChainChain);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), CustMarketList.class);
                Bundle bundle = new Bundle();
                bundle.putString("chainId",chain.getChain_Id());
                intent.putExtras(bundle);
                parent.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

}
