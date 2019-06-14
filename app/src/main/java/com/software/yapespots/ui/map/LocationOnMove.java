package com.software.yapespots.ui.map;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.software.yapespots.model.Marker;
import com.software.yapespots.R;

public  class LocationOnMove implements LocationListener {
    MapActivity mapview;
    Marker marker;

    public LocationOnMove(MapActivity map){
        mapview=map;
        marker = new Marker();
    }
    @Override
    public void onLocationChanged(Location location) {

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (mapview.currentUserLocationMarker != null)
        {
            mapview.currentUserLocationMarker.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Your Location");
        markerOptions.icon(marker.bitmapDescriptorFromVector(mapview, R.drawable.marcador));
        mapview.currentUserLocationMarker = mapview.mMap.addMarker(markerOptions);


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
