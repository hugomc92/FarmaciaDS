package com.hugoroman.pharmacys.screens;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.data.DBConnector;
import com.hugoroman.pharmacys.model.Product;
import com.hugoroman.pharmacys.util.LoadImage;

import java.sql.Date;
import java.text.DateFormat;

public class FragmentProduct extends Fragment implements View.OnClickListener {

    private boolean anim = false;

    private View view;
    private Product product;
    private FABToolbarLayout morph;

    private ImageView productPhoto;
    private TextView productName;
    private TextView productPrice;
    private TextView productCategory;
    private TextView productDescription;
    private TextView productLaboratory;
    private TextView productSizeUnit;
    private TextView productExpDate;
    private TextView productAvailables;

    private ImageView substractQuantity;
    private TextView quantitySelector;
    private ImageView addQuantity;
    private ImageView addReserveAction;

    private int quantity;
    private int maxQuantity;

    private String pharmacyCif;

    public FragmentProduct() {
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

        view = inflater.inflate(R.layout.fragment_product, container, false);

        pharmacyCif = getArguments().getString("PH_CIF");

        if(product != null && pharmacyCif != null)
            maxQuantity = new DBConnector(this.getContext()).getInventoryQuantity(pharmacyCif, product.getId());

        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.rel_product);

        relativeLayout.setOnClickListener(this);

        productPhoto = (ImageView) view.findViewById(R.id.pro_photo);
        productName = (TextView) view.findViewById(R.id.pro_name);
        productPrice = (TextView) view.findViewById(R.id.pro_price);
        productDescription = (TextView) view.findViewById(R.id.pro_description);
        productCategory = (TextView) view.findViewById(R.id.pro_category);
        productLaboratory = (TextView) view.findViewById(R.id.pro_laboratory);
        productSizeUnit = (TextView) view.findViewById(R.id.pro_size_unit);
        productExpDate = (TextView) view.findViewById(R.id.pro_expiration_date);
        productAvailables = (TextView) view.findViewById(R.id.pro_availables);

        if(product != null) {
            new LoadImage(productPhoto).execute(product.getUrlImage());
            productName.setText(product.getName());

            DBConnector dbConnector = new DBConnector(getContext());
            productPrice.setText(String.valueOf(dbConnector.getProductPrice(pharmacyCif, product.getId())) + "€");
            productCategory.setText(dbConnector.getProductCategoryName(product.getId()));

            productDescription.setText(product.getDescription());
            productLaboratory.setText(product.getLaboratory());
            productSizeUnit.setText(product.getSizeUnits() + " " + product.getUnits());

            Date expDate = product.getExpiration_date();
            if(expDate.getTime() != 0) {
                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
                productExpDate.setText("Expiration date: " + dateFormat.format(expDate));
            }
            else {
                relativeLayout.removeView(productExpDate);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.BELOW, R.id.pro_size_unit);

                productAvailables.setLayoutParams(layoutParams);
            }

            productAvailables.setText("Availables: " + maxQuantity);

        }

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_add_basket);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.simple_grow);
        fab.setAnimation(animation);

        morph = (FABToolbarLayout) view.findViewById(R.id.fabtoolbar);

        fab.setOnClickListener(this);

        quantity = 1;

        substractQuantity = (ImageView) view.findViewById(R.id.substract_count);
        quantitySelector = (TextView) view.findViewById(R.id.count);
        addQuantity = (ImageView) view.findViewById(R.id.add_count);
        addReserveAction = (ImageView) view.findViewById(R.id.add_reserve);

        substractQuantity.setOnClickListener(this);
        addQuantity.setOnClickListener(this);
        addReserveAction.setOnClickListener(this);

        if(maxQuantity == 0) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_reservations, getActivity().getTheme()));
                addReserveAction.setImageDrawable(getResources().getDrawable(R.drawable.ic_reservations, getActivity().getTheme()));
            }
            else {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_reservations));
                addReserveAction.setImageDrawable(getResources().getDrawable(R.drawable.ic_reservations));
            }
        }
        else if(maxQuantity == 1) {
            addQuantity.setImageAlpha(0);
        }

        substractQuantity.setImageAlpha(0);
        quantitySelector.setText("1");

        return view;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.fab_add_basket:
                // Abrir selector de cantidad de elementos
                morph.show();
                break;
            case R.id.rel_product:
                // Ocultar el selector de cantidad de elementos si se pulsa en cualquier otro sitio de la pantall
                morph.hide();
                break;
            case R.id.substract_count:
                // Cambiar el número de unidades que se puede tramitar restando 1
                if(quantity > 1) {
                    quantity--;

                    if(quantity == 1)
                        substractQuantity.setImageAlpha(0);
                    else
                        substractQuantity.setImageAlpha(1000);

                    quantitySelector.setText(String.valueOf(quantity));

                    addQuantity.setImageAlpha(1000);
                }
                break;
            case R.id.add_count:
                Log.e("MAX QUANTITY", "" + maxQuantity);
                // Cambiar el número de unidades que se puede tramitar sumando 1
                if(maxQuantity != 0) {
                    if(quantity < maxQuantity) {
                        quantity++;

                        if(quantity == maxQuantity)
                            addQuantity.setImageAlpha(0);
                        else
                            addQuantity.setImageAlpha(1000);

                        quantitySelector.setText(String.valueOf(quantity));

                        substractQuantity.setImageAlpha(1000);
                    }
                    else {
                        addQuantity.setImageAlpha(0);
                        substractQuantity.setImageAlpha(0);
                    }
                }
                else {
                    quantity++;

                    quantitySelector.setText(String.valueOf(quantity));

                    substractQuantity.setImageAlpha(1000);
                }

                //substractQuantity.setImageAlpha(1000);
                break;
            case R.id.add_reserve:
                if(maxQuantity > 0) {
                    // Añadirlo con toda la cantidad a la base de datos de la cesta
                    DBConnector dbConnector = new DBConnector(getContext());

                    dbConnector.addToBasket(pharmacyCif, product.getId(), quantity);

                    // Notificar al usuario que ha sido, o no, correctamente añadido a la cesta
                    Toast.makeText(getContext(), getResources().getString(R.string.insert_basket), Toast.LENGTH_LONG).show();
                }
                else {
                    // Procesar la reserva
                    DBConnector dbConnector = new DBConnector(getContext());

                    String userEmail = getActivity().getSharedPreferences(MainActivity.SYSPRE, Context.MODE_PRIVATE).getString(MainActivity.USER_EMAIL, MainActivity.NOT_USER_EMAIL);

                    dbConnector.addToReservation(pharmacyCif, product.getId(), quantity, userEmail);

                    // Notificar al usuario que ha sido, o no, correctamente añadido como reserva
                    Toast.makeText(getContext(), getResources().getString(R.string.insert_reservations), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void setProduct(Product product) {

        this.product = product;
    }

    public String getProductName() {

        return product.getName();
    }
}
