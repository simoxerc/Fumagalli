package com.example.fumagalli2020;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.fumagalli2020.Class.Cart;
import com.example.fumagalli2020.UI.CustCartSingle;
import com.example.fumagalli2020.UI.CustCategoryList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AdapterCustCartList extends ArrayAdapter<Cart> {

    public AdapterCustCartList(Context context, int textViewResourceId, List<Cart> object){
        super(context,textViewResourceId,object);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_cust_list_cart, null);



        final TextView tvItemCartMkName = convertView.findViewById(R.id.tvItemCartMkName);
        final TextView tvItemCartMkAddress = convertView.findViewById(R.id.tvItemCartMkAddress);
        final TextView tvItemCartMkEmail = convertView.findViewById(R.id.tvItemCartMkEmail);
        final TextView tvItemCartMkPhone = convertView.findViewById(R.id.tvItemCartMkPhone);
        final TextView tvItemCartPrice = convertView.findViewById(R.id.tvItemCartPrice);
        final AppCompatImageButton btnItemDeleteCart = convertView.findViewById(R.id.btnItemDeleteCart);
        final ImageButton btnViewCart = convertView.findViewById(R.id.btnItemViewCart);

        final Cart cart = getItem(position);

        final String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUID).child("Carts").child(cart.getCartId());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String marketId = dataSnapshot.child("marketId").getValue(String.class);
                tvItemCartPrice.setText(dataSnapshot.child("totalPrice").getValue(String.class));
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Market").child(marketId);
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot ds) {
                        tvItemCartMkName.setText(ds.child("name").getValue(String.class));
                        tvItemCartMkAddress.setText(ds.child("address").getValue(String.class));
                        tvItemCartMkEmail.setText(ds.child("email").getValue(String.class));
                        tvItemCartMkPhone.setText(ds.child("number").getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), CustCartSingle.class);
                Bundle bundle = new Bundle();
                bundle.putString("marketId",cart.getMarketId());
                bundle.putString("cartId",cart.getCartId());
                intent.putExtras(bundle);
                parent.getContext().startActivity(intent);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
        builder.setMessage("Vuoi davvero cancellare il carrello?");
        builder.setCancelable(false);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUID).child("Carts").child(cart.getCartId());
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(parent.getContext(), "Carrello eliminato", Toast.LENGTH_LONG).show();
                            remove(cart);
                            notifyDataSetChanged();
                        }
                        else
                            Toast.makeText(parent.getContext(),"Eliminazione carrello non riuscita",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final AlertDialog alertDialog = builder.create();

        btnItemDeleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        return convertView;
    }

}
