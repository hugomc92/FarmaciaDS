package com.hugoroman.pharmacys.screens;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.model.Pharmacy;
import com.hugoroman.pharmacys.util.LoadImage;


public class FragmentPharmacy extends Fragment {

    private boolean anim = false;

    private View view;
    private Pharmacy pharmacy;
    private ImageView pharmacyPhoto;
    private TextView pharmacyName;
    private TextView pharmacyPhone;
    private TextView pharmacyAddress;
    private TextView pharmacyDescription;
    private TextView pharmacyOpeningtime;
    private Button catalogButton;

    public FragmentPharmacy() {
        // Required empty public constructor
        if(!anim && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setEnterTransition(new Slide(Gravity.RIGHT));
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

        view = inflater.inflate(R.layout.fragment_pharmacy, container, false);

        pharmacyPhoto = (ImageView) view.findViewById(R.id.ph_photo);
        pharmacyName = (TextView) view.findViewById(R.id.ph_name);
        pharmacyPhone = (TextView) view.findViewById(R.id.ph_phone);
        pharmacyAddress = (TextView) view.findViewById(R.id.ph_address);
        pharmacyDescription = (TextView) view.findViewById(R.id.ph_description);
        pharmacyOpeningtime = (TextView) view.findViewById(R.id.ph_opening_time);
        catalogButton = (Button) view.findViewById(R.id.ph_catalog_button);

        if(pharmacy != null) {
            new LoadImage(pharmacyPhoto).execute(pharmacy.getLogo());
            pharmacyName.setText(pharmacy.getName());
            pharmacyPhone.setText(String.valueOf(pharmacy.getPhoneNumber()));
            pharmacyAddress.setText(pharmacy.getAddress());
            pharmacyDescription.setText(pharmacy.getDescription());
            pharmacyOpeningtime.setText("Opening times: " + pharmacy.getStartSchedule() + ":00 - " + pharmacy.getEndSchedule() + ":00");
        }

        catalogButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentInventory fragmentInventory = new FragmentInventory();

                Bundle bundle = new Bundle();

                bundle.putString("PH_CIF", pharmacy.getCif());

                fragmentInventory.setArguments(bundle);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentInventory).addToBackStack(null).commit();
                else
                    getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.content_frame, fragmentInventory).addToBackStack(null).commit();


                ((MainActivity) getActivity()).setMenuItemCheck(fragmentInventory);
            }
        });

        return view;
    }

    public void setPharmacy(Pharmacy pharmacy) {

        this.pharmacy = pharmacy;
    }

    public String getPharmacyName() {

        return pharmacy.getName();
    }
}
