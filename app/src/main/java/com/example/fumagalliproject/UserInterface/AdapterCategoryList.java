package com.example.fumagalliproject.UserInterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fumagalliproject.Class.Category;
import com.example.fumagalliproject.R;

import java.util.List;

public class AdapterCategoryList extends ArrayAdapter<Category> {

    public AdapterCategoryList(Context context, int textViewResourceId, List<Category> objects){
        super(context,textViewResourceId,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_categorylist, null);

        TextView tvName = convertView.findViewById(R.id.tvItemCategoryName);
        TextView tvProductOne = convertView.findViewById(R.id.tvItemCategoryPrdTypeOne);
        TextView tvProductTwo = convertView.findViewById(R.id.tvItemCategoryPrdTypeTwo);
        TextView tvProductThree = convertView.findViewById(R.id.tvItemCategoryPrdTypeThree);

        Category category = getItem(position);

        tvName.setText(category.getName());
        tvProductOne.setText(category.getProductTypeOne());
        tvProductTwo.setText(category.getProductTypeTwo());
        tvProductThree.setText(category.getProductTypeThree());

        return convertView;
    }

}
