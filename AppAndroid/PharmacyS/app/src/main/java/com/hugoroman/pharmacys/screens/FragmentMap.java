package com.hugoroman.pharmacys.screens;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hugoroman.pharmacys.R;
import com.hugoroman.pharmacys.data.DBConnector;
import com.hugoroman.pharmacys.model.Pharmacy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class FragmentMap extends Fragment implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener, LocationListener  {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 0;
    private boolean anim = false;

    private View view;
    private View infoView;
    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    private Location currentLocation;
    private DBConnector dbConnector;
    private List<Pharmacy> pharmacies;

    public FragmentMap() {
        // Required empty public constructor
        if(!anim && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setEnterTransition(new Slide(Gravity.RIGHT));
            this.setExitTransition(new Slide(Gravity.LEFT));

            anim = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if(view == null)
            view = inflater.inflate(R.layout.fragment_map, container, false);           // Inflar el layout para este Fragment

        infoView = inflater.inflate(R.layout.info_market_layout, container, false);

        // Volver a solicitar permisos Localización si se hubieran denegado
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);

        LocationManager locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);

        try {
            if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            else {
                Log.e("Test", "network provider unavailable");
                if(locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER))
                    locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
                else
                    Log.e("Test", "passive provider unavailable");
            }

            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            currentLocation = lastKnownLocation;

            if(lastKnownLocation != null) {
                Log.e("Test", lastKnownLocation.getLatitude() + ", " + lastKnownLocation.getLongitude());
                locationManager.removeUpdates(this);
            }
        } catch(SecurityException se) {
            Log.e("SECURITY EXCEPTION", "FAILING ON CREATE - LOCATION MANAGER");
            se.printStackTrace();
        }

        if(mapFragment == null)
            mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));

        mapFragment.getMapAsync(this);

        dbConnector = new DBConnector(getContext());

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        googleMap.setInfoWindowAdapter(this);
        googleMap.setOnInfoWindowClickListener(this);

        try {
            googleMap.setMyLocationEnabled(true);
        }catch (SecurityException se) {
            Log.e("SECURITY EXCEPTION", "SETTING MY LOCATION ENABLED");
            se.printStackTrace();
        }

        setUpMyLocation();
        setUpPharmacies();
    }

    public void setUpMyLocation() {

        if(currentLocation != null) {
            // Crear marca en el mapa
            MarkerOptions marker = new MarkerOptions().position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).title("You are here");

            // Cambiar estilo del icono
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            // Añadir la marca al mapa
            googleMap.addMarker(marker);

            // Mover y centrar la cámara en la marca
            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).zoom(14).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    public void setUpPharmacies() {

        if(pharmacies != null && !pharmacies.isEmpty()) {
            pharmacies = dbConnector.getAllPharmacies();

            for(Pharmacy pharmacy : pharmacies) {
                MarkerOptions marker = new MarkerOptions().position(new LatLng(pharmacy.getLatitude(), pharmacy.getLongitude())).title(pharmacy.getCif());

                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                googleMap.addMarker(marker);
            }
        }
        else {
            pharmacies = dbConnector.getAllPharmacies();
            setUpPharmacies();
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Iterator<Pharmacy> iterator = pharmacies.iterator();

        while(iterator.hasNext()) {
            Pharmacy pharmacy = iterator.next();

            if(pharmacy.getCif().equals(marker.getTitle())) {
                FragmentPharmacy fragmentPharmacy = new FragmentPharmacy();

                fragmentPharmacy.setPharmacy(pharmacy);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentPharmacy).addToBackStack(null).commit();
                else
                    getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.content_frame, fragmentPharmacy).addToBackStack(null).commit();

                ((MainActivity) getActivity()).setMenuItemCheck(fragmentPharmacy);

                Log.e("ON INFO WINDOW CLICK", "Clicked " + pharmacy.getCif());
            }
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        Log.e("INFO CONTENTS MARKER", "ENTER");

        TextView pharmacyName = (TextView) infoView.findViewById(R.id.pharmacy_info_name);

        /*AsyncTask<String, Void, Drawable> asyncTask = new AsyncTask<String, Void, Drawable>() {

            /*@Override
            protected void onPreExecute() {

                pharmacyLogo.setImageDrawable(null);
            }*/

            /*Drawable drawable;

            @Override
            protected Drawable doInBackground(String... params) {
                try {
                    drawable = drawableFromUrl(params[0]);
                } catch (IOException e) {
                    Log.e("IOException", "In FragmentMap asynctask, retrieving pharmacy logo at market");
                    e.printStackTrace();
                }

                return drawable;
            }

            @Override
            protected void onPostExecute(Drawable drawable) {
                super.onPostExecute(drawable);
                pharmacyLogo.setImageDrawable(drawable);

                Log.e("ON POST EXECUTE", "LLEGA " + drawable.toString());
            }

        };*/

        boolean found = false;
        Iterator<Pharmacy> iterator = pharmacies.iterator();

        while(iterator.hasNext() && !found) {
            Pharmacy pharmacy = iterator.next();

            if(pharmacy.getCif().equals(marker.getTitle())) {
                //asyncTask.execute(pharmacy.getLogo());
                Log.e("Entra IF", pharmacy.getName() + " - " + marker.getTitle());

                pharmacyName.setText(pharmacy.getName());
                //asyncTask.execute(pharmacy.getLogo());
                found = true;
            }
        }

        if(!found) {
            pharmacyName.setText("You are approximately here");
        }

        return infoView;
    }

    @Override
    public void onLocationChanged(Location location) {

        if(location != null) {
            Log.e("FRAGMENT MAP - ", "ON LOCATION CHANGED " + location.getLatitude() + ", " + location.getLongitude());

            currentLocation = location;

            setUpMyLocation();
        }
        else {
            Log.e("FRAGMENT MAP - ", "ON LOCATION CHANGED - LOCATION = NULL");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

        Log.d("FRAGMENT MAP - " + provider, "status changed to" + status);
    }

    @Override
    public void onProviderEnabled(String provider) {

        Log.d("FRAGMENT MAP - " + provider,"enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {

        Log.d("FRAGMENT MAP - " + provider,"disabled");
    }

    public Drawable drawableFromUrl(String url) throws IOException {

        Bitmap bitmap;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        bitmap = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(this.getResources(), bitmap);
    }
}