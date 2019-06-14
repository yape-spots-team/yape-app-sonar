package com.software.yapespots.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.software.yapespots.R;
import com.software.yapespots.model.Place;
import com.software.yapespots.repository.firebase.functions.FirebaseFunction;
import com.software.yapespots.ui.detailplace.DetailPlaceBottomSheetDialog;
import com.software.yapespots.ui.home.HomeActivity;
import com.software.yapespots.ui.login.LoginActivity;
import com.software.yapespots.ui.map.listener.FiltersListener;
import com.software.yapespots.ui.map.listener.NearbyPlacesListener;
import com.software.yapespots.ui.search.SearchActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    public static final String TAG = "MapActivity";
    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    //WIDGETS
    private ImageView mGps;
    private ImageView favo;
    private ImageView goback;
    private ImageView search;
    private ImageView filterOnMap;
    //VARIABLES
    private LocationManager lm;
    protected LatLng lastPositionOfCamera;
    protected float lastZoomOfCamara;
    Bitmap smallMarker;
    public Marker currentUserLocationMarker;
    private Marker prueba;
    public Boolean mLocationPermissionsGranted = false;
    public GoogleMap mMap;
    public FusedLocationProviderClient mFusedLocationProviderClient;
    private FirebaseFunction firebaseInteractor = new FirebaseFunction();
    public ArrayList<Place> places;
    MapPresenter presenter;
    boolean log = false;

    public void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapActivity.this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log = getIntent().getBooleanExtra("logged", false);
        places = new ArrayList<>();
        setContentView(R.layout.activity_map);
        presenter = new MapPresenter(this);
        goback = findViewById(R.id.back);
        search = findViewById(R.id.search);
        mGps = findViewById(R.id.ic_gps);
        favo = findViewById(R.id.favorite);
        filterOnMap = findViewById(R.id.filters);
        presenter.getLocationPermission();
    }

    private void init() {
        mGps.setOnClickListener(view -> {
            if (presenter.checkIfLocationOpened()) {
                presenter.getDeviceLocation();
            } else {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });


        favo.setOnClickListener(view -> {
            if (!log) {
                //Al precionar favoritos
                TextView aceptar;
                TextView cancelar;

                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup, null);
                aceptar = popupView.findViewById(R.id.aceptar_pop);
                cancelar = popupView.findViewById(R.id.cancelar_pop);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;

                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                aceptar.setOnClickListener(view1 -> startActivity(new Intent(MapActivity.this, LoginActivity.class)));
                cancelar.setOnClickListener(view12 -> popupWindow.dismiss());
            }
        });

        mMap.setOnCameraIdleListener(() -> {
            if (presenter.movetorequest(mMap.getCameraPosition().target, mMap.getCameraPosition().zoom)) {
                //aqui se debe hacer el request a la base de datos
                lastPositionOfCamera = mMap.getCameraPosition().target;

                // Create data object
                HashMap<String, Object> location = new HashMap<>();
                location.put("latitude", lastPositionOfCamera.latitude);
                location.put("longitude", lastPositionOfCamera.longitude);

                NearbyPlacesListener listener = new NearbyPlacesListener(MapActivity.this, getApplicationContext(), mMap, lastPositionOfCamera);
                firebaseInteractor.withData(location).getPlaces(listener);

                lastZoomOfCamara = mMap.getCameraPosition().zoom;
            }

        });

        search.setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        goback.setOnClickListener(v -> {
            Intent intent = new Intent(MapActivity.this, HomeActivity.class);
            intent.putExtra("logged", log);
            startActivity(intent);
        });

        FiltersListener filterListener = FiltersListener.getInstance();
        filterOnMap.setOnClickListener(filterListener);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        LocationOnMove locationmove = new LocationOnMove(this);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationmove);

        /* FLOATING ACTION BUTTON */
        ImageView icon = new ImageView(this);
        icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_more_horiz_black));
        FloatingActionButton.LayoutParams layoutParams = new FloatingActionButton.LayoutParams(100, 100);
        layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin + 40, layoutParams.bottomMargin);
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setPosition(FloatingActionButton.POSITION_RIGHT_CENTER)
                .setLayoutParams(layoutParams)
                .build();

        // buttons
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        FloatingActionButton.LayoutParams buttonsParams = new FloatingActionButton.LayoutParams(100, 100);

        ImageView barIconMenu = new ImageView(this);
        barIconMenu.setImageDrawable(this.getResources().getDrawable(R.drawable.icon_circle_bar_selector));
        SubActionButton barActionMenu = itemBuilder.setLayoutParams(buttonsParams).setContentView(barIconMenu).build();

        ImageView hotelIconMenu = new ImageView(this);
        hotelIconMenu.setImageDrawable(this.getResources().getDrawable(R.drawable.icon_circle_hotel_selector));
        SubActionButton hotelActionMenu = itemBuilder.setLayoutParams(buttonsParams).setContentView(hotelIconMenu).build();

        ImageView restauranteIconMenu = new ImageView(this);
        restauranteIconMenu.setImageDrawable(this.getResources().getDrawable(R.drawable.icon_circle_restaurante_selector));
        SubActionButton restauranteActionMenu = itemBuilder.setLayoutParams(buttonsParams).setContentView(restauranteIconMenu).build();

        ImageView storeIconMenu = new ImageView(this);
        storeIconMenu.setImageDrawable(this.getResources().getDrawable(R.drawable.icon_circle_tienda_selector));
        SubActionButton storeActionMenu = itemBuilder.setLayoutParams(buttonsParams).setContentView(storeIconMenu).build();

        ImageView floristIconMenu = new ImageView(this);
        floristIconMenu.setImageDrawable(this.getResources().getDrawable(R.drawable.icon_circle_floreria_selector));
        SubActionButton floristActionMenu = itemBuilder.setLayoutParams(buttonsParams).setContentView(floristIconMenu).build();

        // Final
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(barActionMenu)
                .addSubActionView(hotelActionMenu)
                .addSubActionView(restauranteActionMenu)
                .addSubActionView(storeActionMenu)
                .addSubActionView(floristActionMenu)
                .attachTo(actionButton)
                .setStartAngle(100)
                .build();
        /* /FLOATING ACTION BUTTON */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                mLocationPermissionsGranted = true;
                //initialize our map
                initMap();
            }
        }
    }

    public void searchOnClick(View view) {
        Intent intent = new Intent(MapActivity.this, SearchActivity.class);
        intent.putExtra("places", places);
        intent.putExtra("location", currentUserLocationMarker.getPosition());
        startActivityForResult(intent, 2);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Place place = (Place) marker.getTag();
        DetailPlaceBottomSheetDialog bottomSheet = new DetailPlaceBottomSheetDialog();
        if (currentUserLocationMarker == null) {
            bottomSheet.withPlaceId(place.getId(), place.getOpennow(), 0.0, 0.0, Double.parseDouble(place.getLat()), Double.parseDouble(place.getLng()), place.getType().get(0)).show(getSupportFragmentManager(), "detailPlaceBottomSheet");
        } else {
            bottomSheet.withPlaceId(place.getId(), place.getOpennow(), currentUserLocationMarker.getPosition().latitude, currentUserLocationMarker.getPosition().longitude, Double.parseDouble(place.getLat()), Double.parseDouble(place.getLng()), place.getType().get(0)).show(getSupportFragmentManager(), "detailPlaceBottomSheet");
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.map_style));
        mMap.setOnMarkerClickListener(this);

        if (mLocationPermissionsGranted) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            lastPositionOfCamera = mMap.getCameraPosition().target;
            lastZoomOfCamara = mMap.getCameraPosition().zoom;
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(-12.0749168, -76.9656708)));
            mMap.setMaxZoomPreference((float) 17.2);
            mMap.setMinZoomPreference((float) 14.8);
            init();
        }
    }
}
