package com.software.yapespots.model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.software.yapespots.R;

import java.util.ArrayList;
import java.util.List;

public class Search {
    boolean SearchRestaurantButtonActivated = false;
    boolean SearchBarButtonActivated = false;
    boolean SearchFavoritesButtonActivated = false;
    String type="";
    public Search(){

    }

    public boolean onRestaurantButtonPressed(View view, Context ctx) {

        Drawable drawableFile = ctx.getResources().getDrawable(R.drawable.icon_border_restaurant);
        if (SearchRestaurantButtonActivated) {
            SearchRestaurantButtonActivated=false;
            type="";
        } else {
            type="restaurant";
            drawableFile.setColorFilter(Color.parseColor("#ADD8E6"), PorterDuff.Mode.SRC_ATOP);
            SearchRestaurantButtonActivated=true;
        }
        view.setBackground(drawableFile);
        return SearchRestaurantButtonActivated;
    }
    public boolean onBarButtonPressed(View view, Context ctx) {
        Drawable drawableFile = ctx.getResources().getDrawable(R.drawable.icon_border_bar);
        if (SearchBarButtonActivated) {
            SearchBarButtonActivated=false;
            type="";
        } else {
            type="bar";
            drawableFile.setColorFilter(Color.parseColor("#ADD8E6"), PorterDuff.Mode.SRC_ATOP);
            SearchBarButtonActivated= true;
        }
        view.setBackground(drawableFile);
        return SearchBarButtonActivated;
    }
    public boolean onFavoritesButtonPressed(View view, Context ctx) {

        Drawable drawableFile = ctx.getResources().getDrawable(R.drawable.icon_border_favorites);
        if (SearchFavoritesButtonActivated) {
            SearchFavoritesButtonActivated= false;
            type="";
        } else {
            type="favorite";
            drawableFile.setColorFilter(Color.parseColor("#ADD8E6"), PorterDuff.Mode.SRC_ATOP);
            SearchFavoritesButtonActivated= true;
        }
        view.setBackground(drawableFile);
        return SearchFavoritesButtonActivated;
    }
    public ArrayList<Place> FilterPlaces(String filter, List<Place> places){
        ArrayList<Place> filterPlaces = new ArrayList<>();
        for (int i =0;i<places.size();i++){
            for (int j =0;j<places.get(i).getType().size();j++){
                if(places.get(i).getType().get(j).equals(filter)){
                    filterPlaces.add(places.get(i));
                }
            }
        }
        return filterPlaces;

    }
    public void setSearchRestaurantButtonActivated(boolean searchRestaurantButtonActivated) {
        SearchRestaurantButtonActivated = searchRestaurantButtonActivated;
    }

    public void setSearchBarButtonActivated(boolean searchBarButtonActivated) {
        SearchBarButtonActivated = searchBarButtonActivated;
    }

    public void setSearchFavoritesButtonActivated(boolean searchFavoritesButtonActivated) {
        SearchFavoritesButtonActivated = searchFavoritesButtonActivated;
    }

    public boolean getBar() {
        return SearchBarButtonActivated;
    }

    public boolean getrest() {
        return SearchRestaurantButtonActivated;
    }

    public boolean getFavorites() {
        return SearchFavoritesButtonActivated;
    }
    public String getType(){
        return type;
    };
}
