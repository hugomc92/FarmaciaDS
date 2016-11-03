package com.hugoroman.pharmacys.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.model.Order;

import java.text.DateFormat;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private List<Order> orders;
    private static Context context;
    private static ClickListener clickListener;

    public OrdersAdapter(List<Order> orders, Context context) {

        this.orders = orders;
        this.context = context;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView orderReference;
        public TextView orderDate;
        public TextView orderPrice;
        public DateFormat dateFormat;

        public OrderViewHolder(View itemView) {
            super(itemView);

            orderReference = (TextView) itemView.findViewById(R.id.order_id);
            orderDate = (TextView) itemView.findViewById(R.id.order_date);
            orderPrice = (TextView) itemView.findViewById(R.id.order_price);

            dateFormat = android.text.format.DateFormat.getDateFormat(context);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {

        OrdersAdapter.clickListener = clickListener;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);

        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {

        holder.orderReference.setText("Reference: " + orders.get(position).getId());
        holder.orderPrice.setText(String.valueOf(orders.get(position).getPrice()) + "€");
        holder.orderDate.setText(holder.dateFormat.format(orders.get(position).getDate()));
    }

    @Override
    public int getItemCount() {

        return orders.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);
    }
}
