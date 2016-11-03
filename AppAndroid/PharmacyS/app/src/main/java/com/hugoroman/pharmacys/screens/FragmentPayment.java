package com.hugoroman.pharmacys.screens;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
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
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.adapters.BasketAdapter;
import com.hugoroman.pharmacys.adapters.LongClickListener;
import com.hugoroman.pharmacys.data.DBConnector;
import com.hugoroman.pharmacys.model.Basket;
import com.hugoroman.pharmacys.model.Inventory;
import com.hugoroman.pharmacys.model.Pharmacy;
import com.hugoroman.pharmacys.model.PriceVisitor;
import com.hugoroman.pharmacys.model.Product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FragmentPayment extends Fragment {

    private boolean anim = false;

    private View view;
    private DBConnector dbConnector;
    private BasketAdapter basketAdapter;
    private RecyclerView recyclerView;
    private Basket basket;
    private FloatingActionButton fab;
    private OvershootInterpolator interpolator;
    private boolean modeSelection;
    private ArrayList<Integer> selections;
    private PriceVisitor visitor;
    private TextView basketPrice;
    private ArrayList<String> pharmaciesCif;
    private String userEmail;

    public FragmentPayment() {
        // Required empty public constructor
        if(!anim && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setEnterTransition(new Slide(Gravity.TOP));
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

        view = inflater.inflate(R.layout.fragment_payment, container, false);

        pharmaciesCif = getArguments().getStringArrayList("PH_CIFS");

        userEmail = getArguments().getString("USER_EMAIL");

        dbConnector = new DBConnector(this.getContext());

        basket = dbConnector.getPharmacyBasket(pharmaciesCif.get(0));
        modeSelection = false;
        selections = new ArrayList<Integer>();

        visitor = new PriceVisitor();

        basketPrice = (TextView) view.findViewById(R.id.payment_price);

        fab = (FloatingActionButton) view.findViewById(R.id.fab_payment_action);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.simple_grow);
        fab.setAnimation(animation);

        interpolator = new OvershootInterpolator();

        if(basket.getProductsPharmaciesQuantities().size() != 0) {
            visitorVisit(basket);
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.payment_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        basketAdapter = new BasketAdapter(basket);

        basketAdapter.setOnItemClickListener(new LongClickListener() {

            @Override
            public boolean onLongItemClick(int position, View v) {

                if(!modeSelection) {
                    ViewCompat.animate(fab).rotation(360f).withLayer().setDuration(300).setInterpolator(interpolator).start();
                    fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_delete));
                    modeSelection = true;
                }

                toggle(position, (CardView) v);

                return true;
            }

            @Override
            public void onItemClick(int position, View v) {

                if(!modeSelection) {
                    // Añadir los eventos que tienen que ocurrir cuando se pulse algún CardView del RecyclerView
                    FragmentProduct fragmentProduct = new FragmentProduct();

                    Bundle bundle = new Bundle();

                    Pharmacy pharmacy = (Pharmacy) basket.getProductsPharmaciesQuantities().get(position).get(0);

                    bundle.putString("PH_CIF", pharmacy.getCif());

                    fragmentProduct.setArguments(bundle);

                    Product product = (Product) basket.getProductsPharmaciesQuantities().get(position).get(1);

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

                if(modeSelection) {
                    // Procesar el borrado de elementos de la cesta
                    Iterator<Integer> listIterator = selections.iterator();

                    while(listIterator.hasNext()) {
                        Integer position = listIterator.next();

                        Pharmacy pharmacy = (Pharmacy) basket.getProductsPharmaciesQuantities().get(position).get(0);
                        Product product = (Product) basket.getProductsPharmaciesQuantities().get(position).get(1);

                        dbConnector.removeFromBasket(pharmacy.getCif(), product.getId());
                    }

                    basket = dbConnector.getPharmacyBasket(pharmaciesCif.get(0));

                    if(basket.getProductsPharmaciesQuantities().size() != 0)
                        visitorVisit(basket);
                    else
                        visitor.resetPrice();

                    basketAdapter = new BasketAdapter(basket);
                    recyclerView.setAdapter(basketAdapter);

                    ViewCompat.animate(fab).rotation(0f).withLayer().setDuration(300).setInterpolator(interpolator).start();
                    fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_payment));
                    modeSelection = false;

                    if(basket.getProductsPharmaciesQuantities().size() == 0)
                        getActivity().getSupportFragmentManager().popBackStackImmediate();

                    selections.clear();
                }
                else {
                    Toast.makeText(getContext(), getResources().getString(R.string.pay), Toast.LENGTH_SHORT).show();
                    // Borrar la cesta
                    Iterator<List<Object>> iterator = basket.getProductsPharmaciesQuantities().iterator();

                    List<Product> products = new ArrayList<Product>();
                    List<Integer> quantities = new ArrayList<Integer>();

                    while(iterator.hasNext()) {
                        List<Object> current = iterator.next();
                        Pharmacy pharmacy = (Pharmacy) current.get(0);
                        Product product = (Product) current.get(1);
                        Integer quantity = (Integer) current.get(2);

                        products.add(product);
                        quantities.add(quantity);

                        dbConnector.removeFromBasket(pharmacy.getCif(), product.getId());
                        dbConnector.updateStock(pharmacy.getCif(), product.getId(), quantity);
                    }
                    // Añadir a pedidos
                    if(userEmail == null)
                        userEmail = getActivity().getSharedPreferences(MainActivity.SYSPRE, Context.MODE_PRIVATE).getString(MainActivity.USER_EMAIL, MainActivity.NOT_USER_EMAIL);

                    dbConnector.addToOrder(userEmail, pharmaciesCif.get(0), System.currentTimeMillis(), visitor.getBasketPrice(), products, quantities, false, null);

                    basket = dbConnector.getPharmacyBasket(pharmaciesCif.get(0));

                    if(pharmaciesCif.size() > 1) {
                        pharmaciesCif.remove(0);
                        // Continuar con el proceso del pago
                        // Por cada una de las farmacias restantes , pasar por su pago.
                        Toast.makeText(getContext(), getResources().getString(R.string.continue_payment), Toast.LENGTH_SHORT).show();
                        FragmentPayment fragmentPayment = new FragmentPayment();

                        Bundle bundle = new Bundle();

                        bundle.putStringArrayList("PH_CIFS", pharmaciesCif);

                        fragmentPayment.setArguments(bundle);

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentPayment).commit();
                        else
                            getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.content_frame, fragmentPayment).commit();

                        ((MainActivity) getActivity()).setMenuItemCheck(fragmentPayment);
                    }
                    else {
                        Toast.makeText(getContext(), getResources().getString(R.string.payment_finish), Toast.LENGTH_SHORT).show();

                        ((MainActivity) getActivity()).cleanFragmentStack();

                        // Volver al inicio
                        FragmentMain fragmentMain = new FragmentMain();

                        fragmentMain.setUser(dbConnector.getUser(userEmail));

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentMain).commit();
                        else
                            getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.content_frame, fragmentMain).commit();

                        ((MainActivity) getActivity()).setMenuItemCheck(fragmentMain);
                    }
                }
            }
        });

        recyclerView.setAdapter(basketAdapter);

        return view;
    }

    public void toggle(int position, CardView cv) {

        if(!selections.contains(position)) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                cv.setCardBackgroundColor(getResources().getColor(R.color.colorAccent, getActivity().getTheme()));
            else
                cv.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));

            selections.add(position);
        }
        else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                cv.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryLogin, getActivity().getTheme()));
            else
                cv.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryLogin));

            selections.remove((Integer) position);

            if(selections.size() == 0) {
                modeSelection = false;

                ViewCompat.animate(fab).rotation(0f).withLayer().setDuration(300).setInterpolator(interpolator).start();
                fab.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_payment));
            }
        }
    }

    private void visitorVisit(Basket basket) {

        Iterator<List<Object>> iterator = basket.getProductsPharmaciesQuantities().iterator();

        visitor.resetPrice();

        while(iterator.hasNext()) {
            List<Object> current = iterator.next();
            Pharmacy pharmacy = (Pharmacy) current.get(0);
            Product product = (Product) current.get(1);
            Integer quantity = (Integer) current.get(2);

            Inventory inventory = dbConnector.getInventory(pharmacy.getCif(), product.getId());

            inventory.acceptVisitor(visitor, quantity);
        }

        basketPrice.setText(getResources().getString(R.string.total_price_basket) + " " + visitor.getBasketPrice() + "€");
    }

    public String getPharmacyName() {

        return dbConnector.getPharmacyName(pharmaciesCif.get(0));
    }
}