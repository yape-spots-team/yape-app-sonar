package com.software.yapespots.ui.map;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.software.yapespots.R;
import com.software.yapespots.model.local.FavoritePlace;
import com.software.yapespots.repository.firebase.functions.FirebaseFunction;
import com.software.yapespots.repository.local.LocalDatabase;
import com.software.yapespots.ui.map.listener.NearbyPlacesListener;
import com.software.yapespots.ui.map.presenter.MapPresenterImpl;

import java.util.HashMap;
import java.util.List;

public class MapsActivity_test extends AppCompatActivity implements OnMapReadyCallback {
    private MapPresenterImpl presenter = new MapPresenterImpl();
    private GoogleMap mMap;
    private FirebaseFunction firebaseInteractor = new FirebaseFunction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //etContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getApplicationContext(), R.raw.map_style));
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // User location
        final LatLng userLocation = new LatLng(-12.1358002, -77.0219256);
        mMap.addMarker(new MarkerOptions()
                .position(userLocation)
                .icon(this.presenter.bitmapDescriptorFromVector(this, R.drawable.pin_user)));

        // Nearby places
        HashMap<String, Object> location = new HashMap<>();
        location.put("latitude", userLocation.latitude);
        location.put("longitude", userLocation.longitude);

        /*NearbyPlacesListener listener = new NearbyPlacesListener(this,getApplicationContext(), mMap, userLocation);
        firebaseInteractor.withData(location).getPlaces(listener);*/
    }
}
