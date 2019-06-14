package com.software.yapespots.ui.detailplace;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.software.yapespots.R;
import com.software.yapespots.repository.firebase.functions.FirebaseFunction;
import com.software.yapespots.ui.detailplace.listener.LikeListener;
import com.software.yapespots.ui.detailplace.listener.ReportListener;
import com.software.yapespots.ui.search.FirstListenerHolder;
import com.software.yapespots.ui.search.SecondListenerHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DetailPlaceBottomSheetDialog extends BottomSheetDialogFragment {
    ArrayList<PhotosClass> photos;
    PhotosAdapter adapter;
    FirebaseFunction firebaseFunction = new FirebaseFunction();
    String placeId;
    Double latitudeUser;
    Double longitudeUser;
    Double latitudePlace;
    Double longitudePlace;
    Boolean opennow;
    TextView opennowStatus;
    TextView distance;
    TextView typeText;
    String type;
    ImageView opennowImage;
    Place actualPlace;
    public DetailPlaceBottomSheetDialog withPlaceId(String placeId, Boolean opennow,Double latUser, Double lonUser, Double latPlace, Double lonPlace,String type) {
        this.opennow = opennow;
        this.placeId = placeId;
        this.latitudeUser = latUser;
        this.longitudeUser = lonUser;
        this.latitudePlace = latPlace;
        this.longitudePlace = lonPlace;
        this.type = type;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detailplace_bottom_sheet_layout, container, false);
        String placeId = this.placeId;
        photos = new ArrayList<>();
        getImage(view, placeId);
        adapter = new PhotosAdapter(this.getContext(), photos);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        getName(view, placeId);
        opennowStatus = view.findViewById(R.id.textOpenStatus);
        distance = view.findViewById(R.id.detailplace_distance);
        opennowImage = view.findViewById(R.id.openImage);
        typeText = view.findViewById(R.id.detailplace_typetext);
        setType();
        ImageButton callPlaceButton = view.findViewById(R.id.detailplace_call_icon);
        setOpenNow();
        setDistance(getDistance(latitudePlace,longitudePlace,latitudeUser,longitudeUser));
        callPlaceButton.setOnClickListener(v -> callPlace(view, placeId));

        ImageButton sharePlaceButton = view.findViewById(R.id.detailplace_share_icon);

        sharePlaceButton.setOnClickListener(v -> shareIt(view, placeId));

        ImageButton getDirectionToPlace = view.findViewById(R.id.directionsButton);

        getDirectionToPlace.setOnClickListener(v -> howToGo(view, placeId));

        ImageButton getViewOnGoogle = view.findViewById(R.id.detailplace_googlemaps_icon);

        getViewOnGoogle.setOnClickListener(v -> showOnGoogle(placeId));

        ImageButton favoritePlace = view.findViewById(R.id.detailplace_favorite);
        favoritePlace.setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
        });

        ImageButton likePlace = view.findViewById(R.id.detailplace_like);
        likePlace.setOnClickListener(v -> {
            LikeListener likeListener = new LikeListener(v.getContext());
            HashMap<String, Object> data = new HashMap<>();
            data.put("id", placeId);
            data.put("factor", 1);
            firebaseFunction.withData(data).likePlace(likeListener);
        });

        ImageButton reportPlace = view.findViewById(R.id.detailplace_report);
        reportPlace.setOnClickListener(v -> {
            ReportListener reportListener = new ReportListener(v.getContext());
            HashMap<String, Object> data = new HashMap<>();
            data.put("id", placeId);
            firebaseFunction.withData(data).reportPlace(reportListener);
        });


        return view;
    }

    public void getName(View view, String placeId) {
        Places.initialize(view.getContext(), view.getContext().getString(R.string.google_maps_key));
        PlacesClient placesClient = Places.createClient(view.getContext());
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME);
        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();
        placesClient.fetchPlace(request).addOnSuccessListener(response -> {
            Place place = response.getPlace();
            String placeName = place.getName();
            if (placeName.length() > 25) {
                placeName = placeName.substring(0, 25) + "...";
            }
            TextView placeNameField = view.findViewById(R.id.editText);
            placeNameField.setText(placeName);
        });
    }

    public void shareIt(View view, String placeId) {
        Places.initialize(view.getContext(), view.getContext().getString(R.string.google_maps_key));
        PlacesClient placesClient = Places.createClient(view.getContext());
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME);
        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();
        placesClient.fetchPlace(request).addOnSuccessListener(response -> {
            Place place = response.getPlace();
            ///////////////////////////////////////////////////////////////////////
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Vamos a este lugar, aceptan Yape:\n" + place.getName() + "\n" + place.getAddress() + "\n" + "https://www.google.com/maps/search/?api=1&query=Google&query_place_id=" + placeId + "\n" + "#Yape";
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        });
    }


    public void callPlace(View view, String placeId) {
        Places.initialize(view.getContext(), view.getContext().getString(R.string.google_maps_key));
        PlacesClient placesClient = Places.createClient(view.getContext());
        List<Place.Field> placeFields = Arrays.asList(Place.Field.PHONE_NUMBER);
        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();
        placesClient.fetchPlace(request).addOnSuccessListener(response -> {
            Place place = response.getPlace();
            SecondListenerHolder secondListenerHolder = new SecondListenerHolder(view.getContext(), place.getPhoneNumber());
            secondListenerHolder.onClick(view);
        });
    }

    public void getImage(View view, String placeId) {
        Places.initialize(view.getContext(), view.getContext().getString(R.string.google_maps_key));
        PlacesClient placesClient = Places.createClient(view.getContext());
        List<Place.Field> placeFields = Arrays.asList(Place.Field.PHOTO_METADATAS);
        FetchPlaceRequest placeRequest = FetchPlaceRequest.builder(placeId, placeFields).build();
        placesClient.fetchPlace(placeRequest).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            addImage(place, placesClient);

        });
    }

    public void addImage(Place place, PlacesClient placesClient) {
        if (place.getPhotoMetadatas() == null) {
            return;
        }

        for (int i = 0; i < place.getPhotoMetadatas().size(); i++) {
            PhotoMetadata photoMetadata = place.getPhotoMetadatas().get(i);
            FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                    .setMaxWidth(500)
                    .setMaxHeight(300)
                    .build();
            placesClient.fetchPhoto(photoRequest).addOnSuccessListener(fetchPhotoResponse -> {
                Bitmap bitmap = fetchPhotoResponse.getBitmap();
                PhotosClass photo = new PhotosClass(bitmap);
                photo.getResizedBitmap(120,120);
                photos.add(photo);
                adapter.notifyDataSetChanged();
            });
        }

    }

    public void howToGo(View view, String placeId) {
        Places.initialize(view.getContext(), view.getContext().getString(R.string.google_maps_key));
        PlacesClient placesClient = Places.createClient(view.getContext());
        List<Place.Field> placeFields = Arrays.asList(Place.Field.LAT_LNG);
        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();
        placesClient.fetchPlace(request).addOnSuccessListener(response -> {
            Place place = response.getPlace();
            actualPlace=place;
            FirstListenerHolder firstListenerHolder = new FirstListenerHolder(view.getContext(), String.valueOf(place.getLatLng().latitude), String.valueOf(place.getLatLng().longitude));
            firstListenerHolder.onClick(view);
        });
    }

    public void showOnGoogle(String placeId) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=Google&query_place_id=" + placeId));
        startActivity(intent);
    }

    public Boolean isAvailableOpennow(){
        Boolean result = true;
        if(this.opennow==null){
            result = false;
        }
        return result;
    }

    public void setOpenNow(){
        if(!isAvailableOpennow()){
            return;
        }
        if(this.opennow) {
            opennowImage.setImageResource(R.drawable.icon_detailplace_open);
            opennowStatus.setText("Abierto");
            opennowStatus.setTextColor(Color.parseColor("#4CAF50"));
        }
        else{
            opennowImage.setImageResource(R.drawable.icon_detailplace_close);
            opennowStatus.setText("Cerrado");
            opennowStatus.setTextColor(Color.parseColor("#DB3725"));
        }
    }

    public void setType(){
        typeText.setText(type);
    }

    public void setDistance(double newDistance){
        if(this.latitudeUser == 0 && this.longitudeUser == 0){
            distance.setText("");
            return;
        }
        String placeDistance = String.format("%.2f", newDistance)+"km";
        distance.setText(placeDistance);

    }

    double getDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6372.8; // In kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
}
