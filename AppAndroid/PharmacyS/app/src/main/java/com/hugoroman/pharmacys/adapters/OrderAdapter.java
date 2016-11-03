package com.hugoroman.pharmacys.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hugoroman.pharmacys.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<List<String>> productsQuantity;
    private String pharmacyName;
    private static ClickListener clickListener;

    public OrderAdapter(List<List<String>> productsQuantity, String pharmacyName) {

        this.productsQuantity = productsQuantity;
        this.pharmacyName = pharmacyName;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView productName;
        public TextView pharmacyName;
        public TextView orderQuantity;

        public OrderViewHolder(View itemView) {
            super(itemView);

            productName = (TextView) itemView.findViewById(R.id.order_product_name);
            pharmacyName = (TextView) itemView.findViewById(R.id.order_product_pharmacy);
            orderQuantity = (TextView) itemView.findViewById(R.id.order_product_quantity);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {

        OrderAdapter.clickListener = clickListener;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);

        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {

        holder.productName.setText(productsQuantity.get(position).get(0));
        holder.pharmacyName.setText(pharmacyName);
        holder.orderQuantity.setText(productsQuantity.get(position).get(1));
    }

    @Override
    public int getItemCount() {

        return productsQuantity.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);
    }
}
