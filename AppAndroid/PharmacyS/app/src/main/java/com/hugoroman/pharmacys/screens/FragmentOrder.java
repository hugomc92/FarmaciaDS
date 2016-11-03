package com.hugoroman.pharmacys.screens;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.adapters.ClickListener;
import com.hugoroman.pharmacys.adapters.OrderAdapter;
import com.hugoroman.pharmacys.data.DBConnector;
import com.hugoroman.pharmacys.model.Order;
import com.hugoroman.pharmacys.model.Product;

import java.util.List;


public class FragmentOrder extends Fragment {

    private boolean anim = false;

    private View view;
    private Order order;

    public FragmentOrder() {
        // Required empty public constructor
        if(!anim && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setEnterTransition(new Slide(Gravity.LEFT));
            this.setExitTransition(new Slide(Gravity.TOP));

            anim = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout para este Fragment
        super.onCreateView(inflater, container, savedInstanceState);

        // Mantener el Fragment y los datos a cambios de orientación de pantalla
        setRetainInstance(true);

        view = inflater.inflate(R.layout.fragment_order, container, false);

        if(order != null) {
            // Mostrar los productos, en qué farmacia se compraron y la cantidad
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.order_rv);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());

            recyclerView.setLayoutManager(linearLayoutManager);

            DBConnector dbConnector = new DBConnector(getContext());

            List<List<String>> productQuantity = dbConnector.getOrderInfo(order.getId());
            String pharmacyId = dbConnector.getOrderPharmacyId(order.getId());
            String pharmacyName = dbConnector.getPharmacyName(pharmacyId);

            OrderAdapter orderAdapter = new OrderAdapter(productQuantity, pharmacyName);

            final List<Product>products = dbConnector.getAllOrderProducts(order.getId());

            orderAdapter.setOnItemClickListener(new ClickListener() {

                @Override
                public void onItemClick(int position, View v) {

                    FragmentProduct fragmentProduct = new FragmentProduct();

                    Bundle bundle = new Bundle();

                    bundle.putString("PH_CIF", order.getPharmacyId());

                    fragmentProduct.setArguments(bundle);

                    fragmentProduct.setProduct(products.get(position));

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentProduct).addToBackStack(null).commit();
                    else
                        getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.content_frame, fragmentProduct).addToBackStack(null).commit();


                    ((MainActivity) getActivity()).setMenuItemCheck(fragmentProduct);
                }
            });

            recyclerView.setAdapter(orderAdapter);
        }

        return view;
    }

    public void setOrder(Order order) {

        this.order = order;
    }

    public int getOrderId() {

        return this.order.getId();
    }
}
