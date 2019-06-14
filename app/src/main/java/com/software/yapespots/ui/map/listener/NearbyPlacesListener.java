package com.software.yapespots.ui.map.listener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.software.yapespots.R;
import com.software.yapespots.model.Place;
import com.software.yapespots.ui.map.MapActivity;
import com.software.yapespots.utils.Image;
import com.software.yapespots.utils.ParserJson;

import java.util.ArrayList;
import java.util.HashMap;

public class NearbyPlacesListener implements OnCompleteListener<HashMap<String, Object>> {
    private GoogleMap map;
    private LatLng location;
    private Context context;
    private MapActivity activity;
    public ArrayList<Place> newplaces;

    public NearbyPlacesListener(MapActivity view, Context context, GoogleMap map, LatLng location) {
        this.map = map;
        this.location = location;
        this.context = context;
        this.activity = view;
        newplaces = new ArrayList<>();
    }

    @Override
    public void onComplete(@NonNull Task<HashMap<String, Object>> task) {
        if (!task.isSuccessful()) {
            Exception exception = task.getException();

            Log.e("Firebase::getPlaces::", "getPlaces:onFailure", exception);
            Toast.makeText(this.context, R.string.map_nearby_places_function_failed, Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> result = task.getResult();
        ArrayList<HashMap<String, Object>> places = (ArrayList<HashMap<String, Object>>) result.get("data");
        ParserJson parser = new ParserJson(places);
        newplaces = parser.getPlaces();
        activity.places = newplaces;
        for (Place place : newplaces) {
            /*HashMap<String, Object> geometry = (HashMap<String, Object>) place.get("geometry");
            HashMap<String, Object> location = (HashMap<String, Object>) geometry.get("location");
            LatLng position = new LatLng((double) location.get("lat"), (double) location.get("lng"));
            Marker Marker = this.map.addMarker(new MarkerOptions().position(position).title((String) place.get("name")));
            //Marker.setTag();*/
            LatLng position = new LatLng(Double.parseDouble(place.getLat()), Double.parseDouble(place.getLng()));
            Marker marker = this.map.addMarker(new MarkerOptions().position(position).title((String) place.getName()));
            marker.setTag(place);
            //Marker.setIcon(Image.getIcon(context, (ArrayList<String>) place.get("types")));
            marker.setIcon(Image.getIcon(context, place.getType()));
        }
    }
}
