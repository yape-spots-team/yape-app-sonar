package com.software.yapespots.utils;

import com.software.yapespots.model.Place;

import java.util.ArrayList;
import java.util.HashMap;

public class ParserJson {
    private ArrayList<Place> newplaces;

    public ParserJson(ArrayList<HashMap<String, Object>> places) {
        newplaces = new ArrayList<Place>();
        if (places.isEmpty()) {
            return;
        }
        for (HashMap<String, Object> place : places) {
            if (!place.equals(null)) {
                Place newplace = new Place();
                newplace.setId(String.valueOf(place.get("place_id")));
                //Log.d("IANNNNNN",jsonobject.getString("name"));
                newplace.setName(String.valueOf(place.get("name")));
                //Log.d("IANNNNNN",jsonobject.getString("types"));
                newplace.setType((ArrayList<String>) place.get("types"));
                //Log.d("IANNNNNN",place.get("geometry"));
                HashMap<String, Object> geometry = (HashMap<String, Object>) place.get("geometry");
                HashMap<String, Object> location = (HashMap<String, Object>) geometry.get("location");
                //Log.d("lat",sub_obj.getString("lat"));
                newplace.setLat(String.valueOf(location.get("lat")));
                //Log.d("lng",sub_obj.getString("lng"));
                newplace.setLng(String.valueOf(location.get("lng")));
                if (place.get("opening_hours") != null) {
                    HashMap<String, Boolean> opening_hours = (HashMap<String, Boolean>) place.get("opening_hours");
                    newplace.setOpennow(opening_hours.get("open_now"));
                }
                newplaces.add(newplace);
            }
        }
    }

    public ArrayList<Place> getPlaces() {
        return newplaces;
    }
}