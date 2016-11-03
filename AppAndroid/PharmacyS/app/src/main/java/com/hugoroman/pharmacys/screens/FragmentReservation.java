package com.hugoroman.pharmacys.screens;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.adapters.LongClickListener;
import com.hugoroman.pharmacys.adapters.ReservationAdapter;
import com.hugoroman.pharmacys.data.DBConnector;
import com.hugoroman.pharmacys.model.Pharmacy;
import com.hugoroman.pharmacys.model.Product;
import com.hugoroman.pharmacys.model.Reservation;

import java.util.ArrayList;
import java.util.Iterator;

public class FragmentReservation extends Fragment {

    private boolean anim = false;

    private View view;
    private TextView emptyReservation;
    private RecyclerView recyclerView;
    private ReservationAdapter reservationAdapter;
    private Reservation reservation;
    private FloatingActionButton fab;
    private boolean modeSelection;
    private ArrayList<Integer> selections;

    public FragmentReservation() {
        // Required empty public constructor
        if(!anim && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setEnterTransition(new Slide(Gravity.TOP));
            this.setExitTransition(new Slide(Gravity.LEFT));

            anim = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout para este Fragment
        super.onCreateView(inflater, container, savedInstanceState);

        // Mantener el Fragment y los datos a cambios de orientación de pantalla
        setRetainInstance(true);

        view = inflater.inflate(R.layout.fragment_reservation, container, false);

        final DBConnector dbConnector = new DBConnector(this.getContext());

        reservation = dbConnector.getReservation();
        emptyReservation = (TextView) view.findViewById(R.id.reservation_empty);
        modeSelection = false;
        selections = new ArrayList<Integer>();

        if(reservation.getProductsPharmaciesQuantities().size() != 0) {
            FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.frame_reservation);

            frameLayout.removeView(emptyReservation);
        }

        fab = (FloatingActionButton) view.findViewById(R.id.fab_delete);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.simple_grow);
        fab.setAnimation(animation);
        fab.hide();

        recyclerView = (RecyclerView) view.findViewById(R.id.reservation_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        reservationAdapter = new ReservationAdapter(reservation);

        reservationAdapter.setOnItemClickListener(new LongClickListener() {

            @Override
            public boolean onLongItemClick(int position, View v) {

                toggle(position, (CardView) v);

                fab.show();

                modeSelection = true;

                return true;
            }

            @Override
            public void onItemClick(int position, View v) {

                if(!modeSelection) {
                    // Añadir los eventos que tienen que ocurrir cuando se pulse algún CardView del RecyclerView
                    FragmentProduct fragmentProduct = new FragmentProduct();

                    Bundle bundle = new Bundle();

                    Pharmacy pharmacy = (Pharmacy) reservation.getProductsPharmaciesQuantities().get(position).get(0);

                    bundle.putString("PH_CIF", pharmacy.getCif());

                    fragmentProduct.setArguments(bundle);

                    Product product = (Product) reservation.getProductsPharmaciesQuantities().get(position).get(1);

                    fragmentProduct.setProduct(product);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentProduct).addToBackStack(null).commit();
                    else
                        getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.content_frame, fragmentProduct).addToBackStack(null).commit();


                    ((MainActivity) getActivity()).setMenuItemCheck(fragmentProduct);
                }
                else {
                    toggle(position, (CardView) v);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Iterator<Integer> listIterator = selections.iterator();

                while(listIterator.hasNext()) {
                    Integer position = listIterator.next();

                    Pharmacy pharmacy = (Pharmacy) reservation.getProductsPharmaciesQuantities().get(position).get(0);
                    Product product = (Product) reservation.getProductsPharmaciesQuantities().get(position).get(1);

                    String userEmail = getActivity().getSharedPreferences(MainActivity.SYSPRE, Context.MODE_PRIVATE).getString(MainActivity.USER_EMAIL, MainActivity.NOT_USER_EMAIL);

                    dbConnector.removeFromReservation(pharmacy.getCif(), product.getId(), userEmail, false);
                }

                reservation = dbConnector.getReservation();

                reservationAdapter = new ReservationAdapter(reservation);
                recyclerView.setAdapter(reservationAdapter);

                fab.hide();
                modeSelection = false;

                if(reservation.getProductsPharmaciesQuantities().size() == 0) {
                    FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.frame_reservation);

                    frameLayout.addView(emptyReservation);
                }

                selections.clear();
            }
        });

        recyclerView.setAdapter(reservationAdapter);

        return view;
    }

    public void toggle(int position, CardView cv) {

        if(!selections.contains(position)) {
            cv.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));
            selections.add(position);
        }
        else {
            cv.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryLogin));
            selections.remove((Integer) position);

            if(selections.size() == 0) {
                modeSelection = false;

                fab.hide();
            }
        }
    }
}