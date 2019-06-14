package com.software.yapespots.ui.search.presenter;

import android.content.Context;
import android.view.View;
import com.software.yapespots.model.Place;
import com.software.yapespots.core.BasePresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface SearchPresenter extends BasePresenter {
    void applyFilter(List<Place> places, String type);
    Collection<Place> applySearch(String search);
    String getActualType();
    void cleanButtons(View view, Context ctx);
    void onButtonPressed(View view, List<Place> places);
}