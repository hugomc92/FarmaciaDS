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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.adapters.ClickListener;
import com.hugoroman.pharmacys.adapters.OrdersAdapter;
import com.hugoroman.pharmacys.data.DBConnector;
import com.hugoroman.pharmacys.model.Order;
import com.hugoroman.pharmacys.model.User;

import java.util.List;

public class FragmentOrders extends Fragment {

    private boolean anim = false;

    private View view;
    private User user;

    public FragmentOrders() {
        // Required empty public constructor
        if(!anim && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setEnterTransition(new Slide(Gravity.BOTTOM));
            this.setExitTransition(new Slide(Gravity.RIGHT));

            anim = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout para este Fragment
        super.onCreateView(inflater, container, savedInstanceState);

        setRetainInstance(true);

        view = inflater.inflate(R.layout.fragment_orders, container, false);

        DBConnector dbConnector = new DBConnector(this.getContext());

        final List<Order> orders = dbConnector.getAllOrders(user.getEmail());

        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.frame_orders);
        TextView emptyOrder = (TextView) view.findViewById(R.id.orders_empty);

        if(orders.size() > 0)
            frameLayout.removeView(emptyOrder);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.orders_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        OrdersAdapter ordersAdapter = new OrdersAdapter(orders, getContext());

        ordersAdapter.setOnItemClickListener(new ClickListener() {

            @Override
            public void onItemClick(int position, View v) {

                // Añadir los eventos que tienen que ocurrir cuando se pulse algún CardView del RecyclerView
                FragmentOrder fragmentOrder = new FragmentOrder();

                fragmentOrder.setOrder(orders.get(position));

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentOrder).addToBackStack(null).commit();
                else
                    getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.content_frame, fragmentOrder).addToBackStack(null).commit();


                ((MainActivity) getActivity()).setMenuItemCheck(fragmentOrder);
            }
        });

        recyclerView.setAdapter(ordersAdapter);

        return view;
    }

    public void setUser(User user) {

        this.user = user;
    }
}
