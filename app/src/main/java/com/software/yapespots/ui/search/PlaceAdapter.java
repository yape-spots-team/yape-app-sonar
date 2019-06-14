package com.software.yapespots.ui.search;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.software.yapespots.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaceAdapter extends ArrayAdapter<String> {
    private int layout;
    private Context thisContext;
    List<com.software.yapespots.model.Place> places;

    public PlaceAdapter(Context context, int resource, List<String> objects, List<com.software.yapespots.model.Place> newplaces) {
        super(context, resource, objects);
        places = newplaces;
        layout = resource;
        thisContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mainViewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.textView4);
            viewHolder.directions = convertView.findViewById(R.id.directions);
            FirstListenerHolder firstListenerHolder = new FirstListenerHolder(thisContext, places.get(position).getLat(), places.get(position).getLng());
            viewHolder.directions.setOnClickListener(firstListenerHolder);
            setPhone(places.get(position).getId(), viewHolder, convertView);
            convertView.setTag(viewHolder);
        } else {
            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.name.setText(getItem(position));
        }
        return convertView;


    }

    void setPhone(String placeId, ViewHolder viewHolder, View convertView) {
        Places.initialize(thisContext, thisContext.getString(R.string.google_maps_key));
        PlacesClient placesClient = Places.createClient(thisContext);
        List<Place.Field> placeFields = Arrays.asList(Place.Field.PHONE_NUMBER);
        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();
        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            viewHolder.call = convertView.findViewById(R.id.call);
            SecondListenerHolder secondListenerHolder = new SecondListenerHolder(thisContext, place.getPhoneNumber());
            viewHolder.call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(place.getPhoneNumber()==null){
                        Toast.makeText(thisContext, "Este Spot no cuenta con número telefónico", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        secondListenerHolder.onClick(convertView);
                    }
                }
            });
        }).addOnFailureListener(exception -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                Log.d("Place not found", exception.getMessage());
            }
        });
    }
}
