package com.hugoroman.pharmacys.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.model.Product;
import com.hugoroman.pharmacys.util.LoadImage;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    private List<Product> products;
    private static ClickListener clickListener;

    public ProductsAdapter(List<Product> products) {

        this.products = products;
    }

    public static class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView productPhoto;
        public TextView productName;
        public TextView productLaboratory;

        public ProductsViewHolder(View itemView) {
            super(itemView);

            productPhoto = (ImageView) itemView.findViewById(R.id.product_photo);
            productName = (TextView) itemView.findViewById(R.id.product_name);
            productLaboratory = (TextView) itemView.findViewById(R.id.product_laboratory);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {

        ProductsAdapter.clickListener = clickListener;
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_layout, parent, false);

        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, final int position) {

        new LoadImage(holder.productPhoto).execute(products.get(position).getUrlImage());
        holder.productName.setText(products.get(position).getName());
        holder.productLaboratory.setText(products.get(position).getLaboratory());

    }

    @Override
    public int getItemCount() {

        return products.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);
    }
}

