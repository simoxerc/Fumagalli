package com.example.fumagalli2020;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fumagalli2020.Class.Order;
import com.example.fumagalli2020.Class.Product;
import com.example.fumagalli2020.Helper.CustCartSingleHelper;
import com.example.fumagalli2020.Helper.CustOrdersHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

public class AdapterCustOrders extends BaseExpandableListAdapter {

    private Context context;
    private List<Order> listDataHeader;
    private HashMap<Order, List<Product>> listDataChild;

    public AdapterCustOrders(Context context, List<Order> listDataHeader,
                             HashMap<Order, List<Product>> listDataChild){
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_cust_orders_element, null);
        }

        TextView tvNameProduct = convertView.findViewById(R.id.tvNameCustOrderElement);
        TextView tvQuantityProduct = convertView.findViewById(R.id.tvQuantityCustOrderElement);

        Product product = (Product) getChild(groupPosition,childPosition);

        tvNameProduct.setText(product.getName());
        tvQuantityProduct.setText(product.getQntSelected());

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, final ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_cust_orders_header, null);
        }

        TextView tvNameOrder = convertView.findViewById(R.id.tvNameCustOrderHeader);
        TextView tvDateOrder = convertView.findViewById(R.id.tvDateCustOrderHeader);
        TextView tvTimeOrder = convertView.findViewById(R.id.tvTimeCustOrderHeader);
        TextView tvTotalOrder = convertView.findViewById(R.id.tvTotalCustOrderHeader);
        final TextView tvStateOrder = convertView.findViewById(R.id.tvStateCustOrder);

        ImageButton imgBtnRequestDelete = convertView.findViewById(R.id.btnRemoveCustOrderHeader);
        imgBtnRequestDelete.setFocusable(false);

        final Order order = (Order) getGroup(groupPosition);

        tvNameOrder.setText(order.getOrderId().substring(0,10));
        tvDateOrder.setText(order.getPickDate());
        tvTimeOrder.setText(order.getPickTime());
        tvTotalOrder.setText(order.getTotalPayed());
        tvStateOrder.setText(order.getOrderState());

        if(order.getOrderState().equals("In Attesa di conferma")){
            imgBtnRequestDelete.setVisibility(View.VISIBLE);
        }else{
            imgBtnRequestDelete.setVisibility(View.INVISIBLE);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
        builder.setMessage("Vuoi inviare la richiesta di cancellazione ordine?");
        builder.setCancelable(false);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CustOrdersHelper custOrdersHelper = new CustOrdersHelper();
                OnCompleteListener onCompleteListener = new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(parent.getContext(),"Richiesta Inviata",Toast.LENGTH_LONG).show();
                        }
                    }
                };
                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                custOrdersHelper.RequestDelete(onCompleteListener,order.getOrderId(),currentUser,order.getCustomerId(),parent.getContext());
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        final AlertDialog alertDialog = builder.create();

        imgBtnRequestDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvStateOrder.getText().equals("In Attesa di Conferma")){
                    alertDialog.show();
                }
            }
        });

        return convertView;
    }



}
