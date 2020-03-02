package com.example.fumagalli2020;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.fumagalli2020.Class.Product;
import com.example.fumagalli2020.Helper.CustCartSingleHelper;
import com.example.fumagalli2020.UI.CustCartList;
import com.example.fumagalli2020.UI.CustCartSingle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterCustCartSingle extends ArrayAdapter<Product> {

    TextView tvGetTotal;
    List<Product> object;
    String cartId;
    public AdapterCustCartSingle(Context context, int textViewResourceId, List<Product> object, TextView tvGetTotal, String cartId){
        super(context,textViewResourceId,object);
        this.tvGetTotal = tvGetTotal;
        this.object = object;
        this.cartId = cartId;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_cust_single_cart, null);

        TextView tvNameProduct = convertView.findViewById(R.id.tvItemSingleCartName);
        TextView tvPriceProduct = convertView.findViewById(R.id.tvItemSingleCartPrice);
        TextView tvAvaibleProduct = convertView.findViewById(R.id.tvItemSingleCartAvailable);
        final TextView tvQntSelected = convertView.findViewById(R.id.tvItemSingleCartQntSelected);
        final EditText edtChangeQuantity = convertView.findViewById(R.id.edtItemSingleCartQntChange);
        ImageButton imgBtnModifyQnt = convertView.findViewById(R.id.imgBtnItemSingleCartChangeQtn);
        final ImageButton imgBtnRemovePrd = convertView.findViewById(R.id.imgBtnItemSingleCartRemovePrd);
        final String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final Product product = getItem(position);


        tvNameProduct.setText(product.getName());
        if(product.getType().equals("Confezionato")) {
            tvPriceProduct.setText(("€" + product.getPrice() + "/pz"));
            tvAvaibleProduct.setText(("Disp. " + product.getQuantity() + " pz"));
        }else {
            tvPriceProduct.setText(("€" + product.getPrice() + "/kg"));
            tvAvaibleProduct.setText(("Disp. " + product.getQuantity() +" kg"));
        }
        tvQntSelected.setText(product.getQntSelected());
      double total=0;
        for (Product item : object) {
            total+= Double.parseDouble(item.getPrice()) * Double.parseDouble(item.getQntSelected()) ;
        }
        tvGetTotal.setText(Double.toString(total));

        final AlertDialog.Builder builderModify = new AlertDialog.Builder(parent.getContext());
        builderModify.setMessage("Vuoi davvero modificare la quantità?");
        builderModify.setCancelable(false);
        builderModify.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CustCartSingleHelper custCartSingleHelper = new CustCartSingleHelper();
                if(!edtChangeQuantity.getText().toString().trim().equals("") || !(Double.parseDouble(edtChangeQuantity.getText().toString()) == 0.0)) {
                    if(product.getType().equals("Confezionato")) {
                        if (edtChangeQuantity.getText().toString().contains(",") || edtChangeQuantity.getText().toString().contains(".")) {
                            edtChangeQuantity.setError("Quantità non valida");
                        }else{
                            if (Double.parseDouble(product.getQuantity()) > Double.parseDouble(edtChangeQuantity.getText().toString())) {
                                custCartSingleHelper.ChangeQuantity(cartId,currentUID,product, AdapterCustCartSingle.this,
                                        edtChangeQuantity.getText().toString(),parent.getContext(),object,tvGetTotal);
                            }else{
                                edtChangeQuantity.setError("Quantità non valida");
                            }
                        }
                    }else{
                        if (Double.parseDouble(product.getQuantity()) > Double.parseDouble(edtChangeQuantity.getText().toString())) {
                            custCartSingleHelper.ChangeQuantity(cartId,currentUID,product, AdapterCustCartSingle.this,
                                    edtChangeQuantity.getText().toString(),parent.getContext(),object,tvGetTotal);
                        }else{
                            edtChangeQuantity.setError("Quantità non valida");
                        }
                    }
                }else{
                    edtChangeQuantity.setError("Quantità obbligatoria");
                }
            }
        });

        builderModify.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final AlertDialog alertDialogModify = builderModify.create();


        imgBtnModifyQnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogModify.show();
            }
        });

        AlertDialog.Builder builderDelete = new AlertDialog.Builder(parent.getContext());
        builderDelete.setMessage("Vuoi davvero cancellare il prodotto?");
        builderDelete.setCancelable(false);
        builderDelete.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OnCompleteListener onCompleteListener = new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(parent.getContext(),"Carrello Rimosso",Toast.LENGTH_LONG).show();
                            gotocustcartlist(parent.getContext());
                        }
                    }
                };
                CustCartSingleHelper custCartSingleHelper = new CustCartSingleHelper();
                custCartSingleHelper.DeleteProductCart(cartId,currentUID,product,
                        AdapterCustCartSingle.this,parent.getContext(),object,tvGetTotal,onCompleteListener);
            }
        });
        builderDelete.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final AlertDialog alertDialogDelete = builderDelete.create();

        imgBtnRemovePrd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialogDelete.show();
            }
        });

        return convertView;

    }

    private void gotocustcartlist(Context context){
        Intent intent = new Intent(context,CustCartList.class);
        context.startActivity(intent);
    }

}
