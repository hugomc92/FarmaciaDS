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
import com.hugoroman.pharmacys.adapters.PharmaciesAdapter;
import com.hugoroman.pharmacys.data.DBConnector;
import com.hugoroman.pharmacys.model.Pharmacy;

import java.util.List;

public class FragmentPharmacies extends Fragment {

    private boolean anim = false;

    private View view;

    public FragmentPharmacies() {
        // Required empty public constructor
        if(!anim && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setEnterTransition(new Slide(Gravity.RIGHT));
            this.setExitTransition(new Slide(Gravity.LEFT));

            anim = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout para este Fragment
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_pharmacies, container, false);

        DBConnector dbConnector = new DBConnector(this.getContext());

        final List<Pharmacy> pharmacies = dbConnector.getAllPharmacies();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.pharmacies_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        PharmaciesAdapter pharmaciesAdapter = new PharmaciesAdapter(pharmacies);

        pharmaciesAdapter.setOnItemClickListener(new ClickListener() {

            @Override
            public void onItemClick(int position, View v) {

                // Añadir los eventos que tienen que ocurrir cuando se pulse algún CardView del RecyclerView
                FragmentPharmacy fragmentPharmacy = new FragmentPharmacy();

                fragmentPharmacy.setPharmacy(pharmacies.get(position));

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentPharmacy).addToBackStack(null).commit();
                else
                    getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.content_frame, fragmentPharmacy).addToBackStack(null).commit();


                ((MainActivity) getActivity()).setMenuItemCheck(fragmentPharmacy);
            }
        });

        recyclerView.setAdapter(pharmaciesAdapter);

        return view;
    }
}