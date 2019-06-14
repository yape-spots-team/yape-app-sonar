package com.software.yapespots.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
/*
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
*/
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.software.yapespots.R;

public class MapPresenter {
    public MapActivity mapview;

    public MapPresenter(MapActivity view) {
        setView(view);
    }

    public void getLocationPermission() {
        Log.d(mapview.TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(mapview.getApplicationContext(),
                mapview.FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(mapview.getApplicationContext(),
                    mapview.COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mapview.mLocationPermissionsGranted = true;
                mapview.initMap();
            } else {
                ActivityCompat.requestPermissions(mapview,
                        permissions,
                        mapview.LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(mapview,
                    permissions,
                    mapview.LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void getDeviceLocation() {
        Log.d(mapview.TAG, "getDeviceLocation: getting the devices current location");
        mapview.mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mapview);
        try {
            if (mapview.mLocationPermissionsGranted) {

                final Task location = mapview.mFusedLocationProviderClient.getLastLocation();
                OnCompleteListener listener = new CompleteListener(mapview);
                location.addOnCompleteListener(listener);
            }
        } catch (SecurityException e) {
            Log.e(mapview.TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }

    }

    protected boolean checkIfLocationOpened() {
        String provider = Settings.Secure.getString(mapview.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (provider.contains("gps") || provider.contains("network")) {
            return true;
        }
        return false;
    }

    protected boolean movetorequest(LatLng latLng, float zoom) {
        double lat = Math.abs(mapview.lastPositionOfCamera.latitude - latLng.latitude);
        double lon = Math.abs(mapview.lastPositionOfCamera.longitude - latLng.longitude);
        float zoo = Math.abs(mapview.lastZoomOfCamara - zoom);
        double prueba = Math.pow(1 / zoom, 2) * 5.906322333335355 - 0.018964583333337787203819180725;
        if (lat > prueba || lon > prueba || zoo > 0.59) {

            return true;
        }
        return false;
    }

    public void setView(MapActivity map) {
        this.mapview = map;
    }
}
