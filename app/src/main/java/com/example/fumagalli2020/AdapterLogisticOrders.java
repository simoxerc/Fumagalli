package com.example.fumagalli2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fumagalli2020.Class.Order;
import com.example.fumagalli2020.Class.Product;
import com.example.fumagalli2020.Helper.LogisticOrderHelper;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class AdapterLogisticOrders extends BaseExpandableListAdapter {
    private Context context;
    private List<Order> listOrders; // header titles
    // child data in format of header title, child title
    private HashMap<Order, List<Product>> listProducts;
    private List<String> custfiscal;

<<<<<<< HEAD
<<<<<<< HEAD
    public AdapterLogisticOrders(Context context, List<Order> listOrders, HashMap<Order,
            List<Product>> listProducts, List<String> custfiscal) {
=======
    public AdapterLogisticOrders(Context context, List<Order> listOrders,
                                 HashMap<Order, List<Product>> listProducts, List<String> custfiscal) {
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
=======
    public AdapterLogisticOrders(Context context, List<Order> listOrders,
                                 HashMap<Order, List<Product>> listProducts, List<String> custfiscal) {
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
        this.context = context;
        this.listOrders= listOrders;
        this.listProducts = listProducts;
        this.custfiscal = custfiscal;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listProducts.get(this.listOrders.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        convertView = inflater.inflate(R.layout.item_logistic_orders_element, null);

        TextView tvOrderProductName = convertView.findViewById(R.id.tvOrderProductName);
        TextView tvOrderProductQuantity = convertView.findViewById(R.id.tvOrderProductQuantity);

        Product product = (Product) getChild(groupPosition,childPosition);

        tvOrderProductName.setText(product.getName());
        tvOrderProductQuantity.setText(": "+product.getQntSelected());

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listProducts.get(this.listOrders.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listOrders.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listOrders.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        convertView = inflater.inflate(R.layout.item_logistic_orders_header, null);

        final LogisticOrderHelper helper = new LogisticOrderHelper();

        TextView tvOrderFiscal = convertView.findViewById(R.id.tvOrderCustFiscal);
        TextView tvOrderState = convertView.findViewById(R.id.tvOrderState);
        TextView tvOrderTotal = convertView.findViewById(R.id.tvOrderTotal);
        TextView tvOrderPickDate = convertView.findViewById(R.id.tvOrderPickDate);
        TextView tvOrderPickTime = convertView.findViewById(R.id.tvOrderPickTime);
        ImageButton btnOrderConfirm = convertView.findViewById(R.id.btnOrderConfirm);
        ImageButton btnOrderDelete = convertView.findViewById(R.id.btnOrderDelete);
        final ImageView icOrderDeleteRequest = convertView.findViewById(R.id.icOrderDeleteRequest);
        btnOrderConfirm.setFocusable(false);
        btnOrderDelete.setFocusable(false);

        final Order order = (Order) getGroup(groupPosition);

        tvOrderFiscal.setText(custfiscal.get(groupPosition));
        tvOrderState.setText("Stato: "+order.getOrderState());
        tvOrderTotal.setText("Pagato: "+order.getTotalPayed()+"€");
        tvOrderPickDate.setText(order.getPickDate());
        tvOrderPickTime.setText(order.getPickTime());

        if(order.getOrderState().equals("Richiesta Annullamento")){
            icOrderDeleteRequest.setVisibility(View.VISIBLE);
        }else{
            icOrderDeleteRequest.setVisibility(View.INVISIBLE);
        }

        btnOrderConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(icOrderDeleteRequest.getVisibility() == View.VISIBLE)
                    helper.btnConfirmOrder(order.getOrderId(),"Annullamento Accettato",order.getCustomerId(),parent.getContext());
                else
                    helper.btnConfirmOrder(order.getOrderId(),"Confermato",order.getCustomerId(),parent.getContext());
            }
        });

        btnOrderDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
<<<<<<< HEAD
                if(!order.getOrderState().equals("Confermato")) {
=======
                if(!order.getOrderState().equals("Confirmed")) {
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
=======
                if(!order.getOrderState().equals("Confirmed")) {
>>>>>>> 650d922d07e1a4390bc1c75c9085c4d76a663575
                    if (icOrderDeleteRequest.getVisibility() == View.VISIBLE)
                        helper.btnDeleteOrder(order.getOrderId(), "Annullamento Rifiutato", order.getCustomerId(), parent.getContext());
                    else
                        helper.btnDeleteOrder(order.getOrderId(), "Annullato", order.getCustomerId(), parent.getContext());
                }else
                    Toast.makeText(parent.getContext(),"Non puoi cambiare lo stato",Toast.LENGTH_LONG).show();
            }
        });


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
