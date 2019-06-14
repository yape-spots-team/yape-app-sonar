package com.software.yapespots.ui.search.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.software.yapespots.R;
import com.software.yapespots.core.BaseView;
import com.software.yapespots.model.Place;
import com.software.yapespots.model.Search;
import com.software.yapespots.ui.search.SearchActivity;
import com.software.yapespots.ui.search.view.SearchView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchPresenterImpl implements SearchPresenter, TextView.OnEditorActionListener{

    private SearchView viewprincipal;
    private Search searchmodel;

    public SearchPresenterImpl(SearchActivity view){
        setView(view);
        searchmodel = new Search();
    }

    public void changeToAdvancedFilter() {
        /* not implemented yet*/

    }

    public String getActualType(){
        return searchmodel.getType();
    }

    public void cleanButtons(View view, Context ctx) {
        ImageButton temp= (ImageButton) viewprincipal.findView(R.id.imageButton);
        if(view.getId()!=temp.getId()){
            Drawable drawableFile = ctx.getResources().getDrawable(R.drawable.icon_border_restaurant);
            temp.setBackground(drawableFile);
            searchmodel.setSearchRestaurantButtonActivated(false);
        }
        temp= (ImageButton)viewprincipal.findView(R.id.imageButton2);
        if(view.getId()!=temp.getId()){
            Drawable drawableFile = ctx.getResources().getDrawable(R.drawable.icon_border_bar);
            temp.setBackground(drawableFile);
            searchmodel.setSearchBarButtonActivated(false);
        }
        temp= (ImageButton)viewprincipal.findView(R.id.imageButton5);
        if(view.getId()!=temp.getId()){
            Drawable drawableFile = ctx.getResources().getDrawable(R.drawable.icon_border_favorites);
            temp.setBackground(drawableFile);
            searchmodel.setSearchFavoritesButtonActivated(false);
        }
    }

    public void onButtonPressed(View view, List<Place> newplaces ){
        String tagString = (String) view.getTag();
        boolean showResults;
        cleanButtons(view,viewprincipal.getBaseContext());
        if(tagString.equals("restaurant") ){
            showResults=searchmodel.onRestaurantButtonPressed(view,viewprincipal.getBaseContext());
        }
        else if(tagString.equals("favorite")){
            showResults=searchmodel.onFavoritesButtonPressed(view, viewprincipal.getBaseContext());
        }else{
            showResults=searchmodel.onBarButtonPressed(view,viewprincipal.getBaseContext());
        }
        String type=searchmodel.getType();
        if(showResults){
            applyFilter(newplaces,type);
        }else{
            viewprincipal.ShowSearchResult(newplaces);
        }

    }

    public Collection<Place> applySearch(String search) {

        Collection<Place> places = null;

        return places;
    }

    public void applyFilter(List<Place> places, String type) {
        ArrayList<Place> newplaces = searchmodel.FilterPlaces(type,places);
        viewprincipal.ShowSearchResult(newplaces);
    }

    @Override
    public void setView(BaseView view) {
        viewprincipal = (SearchView) view;
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if(i == EditorInfo.IME_ACTION_SEND){
            viewprincipal.SearchSpots(textView);
            return true;
        }
        return false;
    }
}
