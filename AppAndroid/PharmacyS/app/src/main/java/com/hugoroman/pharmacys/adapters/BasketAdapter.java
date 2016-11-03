package com.hugoroman.pharmacys.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.model.Basket;
import com.hugoroman.pharmacys.model.Pharmacy;
import com.hugoroman.pharmacys.model.Product;
import com.hugoroman.pharmacys.util.LoadImage;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketViewHolder> {

    private Basket basket;
    private static LongClickListener clickListener;

    public BasketAdapter(Basket basket) {

        this.basket = basket;
    }

    public static class BasketViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public CardView basketCardView;
        public ImageView basketProductPhoto;
        public TextView basketProductName;
        public TextView basketPharmacyName;
        public TextView basketQuantity;

        public BasketViewHolder(View itemView) {
            super(itemView);

            basketCardView = (CardView) itemView.findViewById(R.id.basket_cv);
            basketProductPhoto = (ImageView) itemView.findViewById(R.id.basket_product_photo);
            basketProductName = (TextView) itemView.findViewById(R.id.basket_product_name);
            basketPharmacyName = (TextView) itemView.findViewById(R.id.basket_pharmacy_name);
            basketQuantity = (TextView) itemView.findViewById(R.id.basket_quantity);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {

            BasketAdapter.clickListener.onItemClick(getAdapterPosition(), view);
        }

        @Override
        public boolean onLongClick(View view) {

            return BasketAdapter.clickListener.onLongItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(LongClickListener clickListener) {

        BasketAdapter.clickListener = clickListener;
    }

    @Override
    public BasketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basket_layout, parent, false);

        return new BasketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BasketViewHolder holder, int position) {

        Pharmacy pharmacy = (Pharmacy) basket.getProductsPharmaciesQuantities().get(position).get(0);
        Product product = (Product) basket.getProductsPharmaciesQuantities().get(position).get(1);
        Integer quantity = (Integer) basket.getProductsPharmaciesQuantities().get(position).get(2);

        holder.basketCardView.setLongClickable(true);
        new LoadImage(holder.basketProductPhoto).execute(product.getUrlImage());
        holder.basketProductName.setText(product.getName());
        holder.basketPharmacyName.setText(pharmacy.getName());
        holder.basketQuantity.setText(quantity.toString());

    }

    @Override
    public int getItemCount() {

        return basket.getProductsPharmaciesQuantities().size();
    }
}