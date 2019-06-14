package com.software.yapespots.ui.search.listener;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.software.yapespots.model.Place;
import com.software.yapespots.ui.search.SearchActivity;
import com.software.yapespots.utils.ParserJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchPlacesListener implements OnCompleteListener<HashMap<String, Object>> {
    SearchActivity activity;
    public List<Place> newplaces;
    public SearchPlacesListener(SearchActivity view){
        activity = view;
        newplaces = new ArrayList<>();
    }
    @Override
    public void onComplete(@NonNull Task<HashMap<String, Object>> task) {
        HashMap<String, Object> result = task.getResult();
        ArrayList<HashMap<String, Object>> places = (ArrayList<HashMap<String, Object>>) result.get("data");
        ParserJson parser = new ParserJson(places);
        newplaces = parser.getPlaces();
        activity.places = newplaces;
        activity.ShowSearchResult(activity.places);
    }
}
