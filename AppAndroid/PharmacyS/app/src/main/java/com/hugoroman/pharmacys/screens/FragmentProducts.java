package com.hugoroman.pharmacys.screens;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.adapters.ClickListener;
import com.hugoroman.pharmacys.adapters.ProductsAdapter;
import com.hugoroman.pharmacys.data.DBConnector;
import com.hugoroman.pharmacys.model.Product;

import java.util.List;

// https://danielme.com/2015/09/13/diseno-android-endless-recyclerview/  HACER PAGINACIÓN DE PRODUCTOS DE 20 EN 20

public class FragmentProducts extends Fragment {

    private boolean anim = false;

    private View view;
    private int categoryID;
    private String categoryName;
    private String pharmacyCif;

    public FragmentProducts() {
        // Required empty public constructor
        if(!anim && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setEnterTransition(new Slide(Gravity.RIGHT));
            this.setExitTransition(new Slide(Gravity.RIGHT));

            anim = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout para este Fragment
        super.onCreateView(inflater, container, savedInstanceState);

        // Mantener el Fragment y los datos a cambios de orientación de pantalla
        setRetainInstance(true);

        view = inflater.inflate(R.layout.fragment_products, container, false);

        categoryID = getArguments().getInt("CATEGORY_ID");
        pharmacyCif = getArguments().getString("PH_CIF");

        DBConnector dbConnector = new DBConnector(this.getContext());

        categoryName = dbConnector.getCategoryName(categoryID);

        final List<Product> products = dbConnector.getAllProductsByCategoryId(categoryID, pharmacyCif);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.products_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        ProductsAdapter productsAdapter = new ProductsAdapter(products);

        productsAdapter.setOnItemClickListener(new ClickListener() {

            @Override
            public void onItemClick(int position, View v) {

                // Añadir los eventos que tienen que ocurrir cuando se pulse algún CardView del RecyclerView
                FragmentProduct fragmentProduct = new FragmentProduct();

                Bundle bundle = new Bundle();

                bundle.putString("PH_CIF", pharmacyCif);

                fragmentProduct.setArguments(bundle);

                fragmentProduct.setProduct(products.get(position));

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentProduct).addToBackStack(null).commit();
                else
                    getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.content_frame, fragmentProduct).addToBackStack(null).commit();


                ((MainActivity) getActivity()).setMenuItemCheck(fragmentProduct);
            }
        });

        recyclerView.setAdapter(productsAdapter);

        FloatingActionButton FAB = (FloatingActionButton) view.findViewById(R.id.fab_basket);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.simple_grow);
        FAB.setAnimation(animation);

        FAB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentBasket fragmentBasket = new FragmentBasket();

                Bundle bundle = new Bundle();

                bundle.putString("PH_CIF", pharmacyCif);

                fragmentBasket.setArguments(bundle);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentBasket).addToBackStack(null).commit();
                else
                    getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.content_frame, fragmentBasket).addToBackStack(null).commit();


                ((MainActivity) getActivity()).setMenuItemCheck(fragmentBasket);
            }
        });

        ((MainActivity) getActivity()).setMenuItemCheck(this);

        return view;
    }

    public String getCategoryName() {

        return categoryName;
    }
}

