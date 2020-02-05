package com.example.fumagalliproject.UserInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.fumagalliproject.Class.Chain;
import com.example.fumagalliproject.R;

import java.util.List;

public class AdapterChainList extends ArrayAdapter<Chain> {

    public AdapterChainList(Context context, int textViewResourceId, List<Chain> objects){
        super(context,textViewResourceId,objects);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_chainlist, null);

        Chain chain = getItem(position);

        return convertView;
    }

}
