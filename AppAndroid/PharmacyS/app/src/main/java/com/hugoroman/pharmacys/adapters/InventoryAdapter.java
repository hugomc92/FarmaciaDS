package com.hugoroman.pharmacys.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.data.DBConnector;
import com.hugoroman.pharmacys.model.Inventory;

import java.util.List;


public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private List<Inventory> inventories;
    private Context context;
    private static ClickListener clickListener;

    public InventoryAdapter(List<Inventory> inventories, Context context) {

        this.inventories = inventories;
        this.context = context;
    }

    public static class InventoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView categoryPhoto;

        public InventoryViewHolder(View itemView) {
            super(itemView);

            categoryPhoto = (ImageView) itemView.findViewById(R.id.category_photo);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {

        InventoryAdapter.clickListener = clickListener;
    }

    @Override
    public InventoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_layout, parent, false);

        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InventoryViewHolder holder, int position) {

        DBConnector dbConnector = new DBConnector(holder.itemView.getContext());

        int categoryPicture = dbConnector.getProductCategoryPhoto(inventories.get(position).getProductID());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            holder.categoryPhoto.setImageDrawable(context.getDrawable(categoryPicture));
        else
            holder.categoryPhoto.setImageDrawable(context.getResources().getDrawable(categoryPicture));
    }

    @Override
    public int getItemCount() {

        return inventories.size();
    }
}