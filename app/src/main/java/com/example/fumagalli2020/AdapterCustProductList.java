package com.example.fumagalli2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.fumagalli2020.Class.Cart;
import com.example.fumagalli2020.Class.Product;
import com.example.fumagalli2020.Helper.CatalogHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

public class AdapterCustProductList extends ArrayAdapter<Product> {

    public AdapterCustProductList(Context context, int textViewResourceId, List<Product> object){
        super(context,textViewResourceId,object);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_cust_list_product, null);

        TextView tvProductName = convertView.findViewById(R.id.tvItemProductName);
        TextView tvProductOrigin = convertView.findViewById(R.id.tvItemProductOrigin);
        TextView tvProductPackDate = convertView.findViewById(R.id.tvItemProductPackDate);
        final TextView tvProductExpireDate = convertView.findViewById(R.id.tvItemProductExpireDate);
        TextView tvProductType = convertView.findViewById(R.id.tvItemProductType);
        TextView tvProductPrice = convertView.findViewById(R.id.tvItemProductPrice);
        TextView tvProductQuantity = convertView.findViewById(R.id.tvItemProductQuantity);
        final EditText edtQuantitySelected = convertView.findViewById(R.id.edtItemProductQuantity);

        AppCompatImageButton btnAddProductToCart = convertView.findViewById(R.id.imgBtnItemPrdAddToCart);

        final Product product = getItem(position);

        tvProductName.setText(product.getName());
        tvProductOrigin.setText(("Prov. " + product.getOrigin()));
        tvProductPackDate.setText(("Conf. " + product.getPackagingDate()));
        tvProductExpireDate.setText(("Scad. " + product.getExpireDate()));
        tvProductType.setText(product.getType());
        if(tvProductType.getText().toString().equals("Confezionato")) {
            tvProductPrice.setText(("€" + product.getPrice() + " /pz"));
        }else{
            tvProductPrice.setText(("€" + product.getPrice() + " /h"));
        }
        tvProductQuantity.setText(("Disp. " + product.getQuantity()));

        btnAddProductToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CatalogHelper catalogHelper = new CatalogHelper();
                final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if(edtQuantitySelected.getText().toString().trim().equals("")){
                    edtQuantitySelected.setError("");
                }else{
                    if(product.getType().equals("Confezionato")){
                        if(edtQuantitySelected.getText().toString().contains(",") || edtQuantitySelected.getText().toString().contains(".")){
                            edtQuantitySelected.setError("");
                        }else{
                            int qtn = Integer.valueOf(edtQuantitySelected.getText().toString());
                            if(qtn > Integer.valueOf(product.getQuantity())){
                                edtQuantitySelected.setError("Quantità non disponibile");
                            }else{
                                catalogHelper.intcheckdbquantity(currentUser+product.getMarketId(),
                                        new Product(product.getName(),product.getProductId(),product.getPrice(),edtQuantitySelected.getText().toString()),product.getType(),
                                        product.getMarketId(),currentUser,parent.getContext(),edtQuantitySelected,qtn);
                            }
                        }
                    }else{
                        float qtn = Float.valueOf(edtQuantitySelected.getText().toString());
                        if(qtn > Float.valueOf(product.getQuantity())){
                            edtQuantitySelected.setError("Quantità non disponibile");
                        }else{
                            catalogHelper.floatcheckdbquantity(currentUser+product.getMarketId(),
                                    new Product(product.getName(),product.getProductId(),product.getPrice(),edtQuantitySelected.getText().toString()),product.getType(),
                                    product.getMarketId(),currentUser,parent.getContext(),edtQuantitySelected,qtn);
                        }
                    }
                }
            }
        });

        return convertView;
    }



}
