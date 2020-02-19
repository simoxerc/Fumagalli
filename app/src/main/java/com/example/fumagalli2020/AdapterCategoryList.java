package com.example.fumagalli2020;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;

import com.example.fumagalli2020.Class.Category;
import com.example.fumagalli2020.UI.ProductList;

import java.util.List;

public class AdapterCategoryList extends ArrayAdapter<Category> {
    public AdapterCategoryList(Context context, int textViewResourceId,List<Category> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        convertView = inflater.inflate(R.layout.item_list_category, null);

        TextView tvCategoryName = convertView.findViewById(R.id.tvItemNameCategory);
        TextView tvCategoryDesc = convertView.findViewById(R.id.tvItemDescCategory);
        TextView tvCategoryCode = convertView.findViewById(R.id.tvItemCodeCategory);

        AppCompatImageButton btnCategoryView = convertView.findViewById(R.id.btnItemViewCategory);


        final Category category = getItem(position);

        tvCategoryName.setText(category.getName());
        tvCategoryDesc.setText(category.getDesc());
        tvCategoryCode.setText(category.getCategoryId());

        btnCategoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(parent.getContext(), ProductList.class);
                Bundle bundle = new Bundle();
                bundle.putString("categoryId",category.getCategoryId());
                bundle.putString("marketId",category.getMarketId());
                intent.putExtras(bundle);
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
