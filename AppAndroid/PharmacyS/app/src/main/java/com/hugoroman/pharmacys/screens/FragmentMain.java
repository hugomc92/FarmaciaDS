package com.hugoroman.pharmacys.screens;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.data.DBConnector;
import com.hugoroman.pharmacys.model.User;

public class FragmentMain extends Fragment implements View.OnClickListener {

    private boolean anim = false;

    private View view;
    private User user;
    private CardView pharmaciesCardView;
    private CardView mapCardView;
    private CardView basketCardView;
    private CardView ordersCardView;
    private CardView reservationsCardView;

    public FragmentMain() {
        // Required empty public constructor
        if(!anim && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setEnterTransition(new Slide(Gravity.LEFT));
            this.setExitTransition(new Slide(Gravity.BOTTOM));

            anim = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Mantener el Fragment y los datos a cambios de orientación de pantalla
        setRetainInstance(true);

        view = inflater.inflate(R.layout.fragment_main, container, false);

        pharmaciesCardView = (CardView) view.findViewById(R.id.pharmacies_cv);
        mapCardView = (CardView) view.findViewById(R.id.map_cv);
        basketCardView = (CardView) view.findViewById(R.id.basket_cv);
        ordersCardView = (CardView) view.findViewById(R.id.orders_cv);
        reservationsCardView = (CardView) view.findViewById(R.id.reservations_cv);

        pharmaciesCardView.setOnClickListener(this);
        mapCardView.setOnClickListener(this);
        basketCardView.setOnClickListener(this);
        ordersCardView.setOnClickListener(this);
        reservationsCardView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        Fragment fragment = null;

        switch(v.getId()) {
            case R.id.pharmacies_cv:
                fragment = new FragmentPharmacies();
                break;
            case R.id.map_cv:
                fragment = new FragmentMap();
                break;
            case R.id.basket_cv:
                fragment = new FragmentBasket();

                Bundle bundle = new Bundle();

                if(user == null) {
                    String userEmail = getActivity().getSharedPreferences(MainActivity.SYSPRE, Context.MODE_PRIVATE).getString(MainActivity.USER_EMAIL, MainActivity.NOT_USER_EMAIL);

                    user = new DBConnector(getContext()).getUser(userEmail);
                }

                bundle.putString("USER_EMAIL", user.getEmail());

                fragment.setArguments(bundle);
                break;
            case R.id.orders_cv:
                fragment = new FragmentOrders();

                if(user == null) {
                    String userEmail = getActivity().getSharedPreferences(MainActivity.SYSPRE, Context.MODE_PRIVATE).getString(MainActivity.USER_EMAIL, MainActivity.NOT_USER_EMAIL);

                    user = new DBConnector(getContext()).getUser(userEmail);
                }

                ((FragmentOrders) fragment).setUser(user);

                break;
            case R.id.reservations_cv:
                fragment = new FragmentReservation();
                break;
        }

        if(fragment != null) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();
            else
                getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.content_frame, fragment).addToBackStack(null).commit();

            ((MainActivity) getActivity()).setMenuItemCheck(fragment);
        }
    }

    public void setUser(User user) {

        this.user = user;
    }
}