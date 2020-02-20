package com.example.fumagalli2020;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fumagalli2020.Class.Category;
import com.example.fumagalli2020.UI.CustMarketList;
import com.example.fumagalli2020.UI.CustProductList;

import java.util.List;

public class AdapterCustCategoryList extends ArrayAdapter<Category> {

    public AdapterCustCategoryList(Context context, int textViewResourceId, List<Category> object){
        super(context,textViewResourceId,object);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_cust_list_category, null);

        TextView tvCategoryName = convertView.findViewById(R.id.tvItemCategoryName);
        TextView tvCategoryDesc = convertView.findViewById(R.id.tvItemCategoryDescription);

        final Category category = getItem(position);

        tvCategoryName.setText(category.getName());
        tvCategoryDesc.setText(category.getDesc());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), CustProductList.class);
                Bundle bundle = new Bundle();
                bundle.putString("marketId",category.getMarketId());
                bundle.putString("categoryId",category.getCategoryId());
                bundle.putString("chainId", category.getMarketId().substring(0,3));
                intent.putExtras(bundle);
                parent.getContext().startActivity(intent);
            }
        });


        return convertView;
    }

}
