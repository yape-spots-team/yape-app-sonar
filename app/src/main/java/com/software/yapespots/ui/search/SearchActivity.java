package com.software.yapespots.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.software.yapespots.model.Place;
import com.software.yapespots.R;
import com.software.yapespots.R.layout;
import com.software.yapespots.repository.firebase.functions.FirebaseFunction;
import com.software.yapespots.ui.advancedFilter.AdvancedFilterActivity;
import com.software.yapespots.ui.search.listener.SearchPlacesListener;
import com.software.yapespots.ui.search.presenter.SearchPresenter;
import com.software.yapespots.ui.search.presenter.SearchPresenterImpl;
import com.software.yapespots.ui.search.view.SearchView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView {
    HashMap<String,Object> jason;
    SearchPresenter presenter;
    double[] location;
    private FirebaseFunction firebaseInteractor = new FirebaseFunction();
    public List<Place> places = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_search);
        presenter = new SearchPresenterImpl(this);
        places = new ArrayList<>();
        HashMap<String,Object> jason = new HashMap<>();
        location = new double[2];
        jason.put("region","pe");
        EditText myTextBox = findViewById(R.id.editText2);
        TextView.OnEditorActionListener listenerText = new SearchPresenterImpl(this);
        myTextBox.setOnEditorActionListener(listenerText);

    }

    public void onClick(View view){
        presenter.onButtonPressed(view,places);
        String type =presenter.getActualType();
        jason.put("type",type);


    }
    public String getCurrentType(){
        return presenter.getActualType();
    }

    public void ShowSearchResult(List<Place> places){
        ListView lv = findViewById(R.id.listview);
        ArrayList<String> placesResultName = new ArrayList<>();
        for(int i = 0; i<places.size();i++)
        {
            String placeName = places.get(i).getName();
            if(placeName.length()>21){
                placeName=placeName.substring(0,21)+"...";
            }
            placesResultName.add(placeName);
        }
        ListAdapter lvAdapter =new PlaceAdapter(this, R.layout.placeviewer, placesResultName, places);
        lv.setAdapter(lvAdapter);
    }

    public void onMoreFiltersButtonPressed() {
        Intent intent = new Intent(this, AdvancedFilterActivity.class);
        startActivityForResult(intent,1);
    }

    public void recentSearchedPlaces(Collection<Place> places) {
    /* not implemented yet */
    }

    @Override
    public View findView(int resID) {
        return findViewById(resID);
    }

    @Override
    public void SearchSpots(TextView textView) {
        String text =textView.getText().toString();
        jason.put("query",text);
        SearchPlacesListener listener = new SearchPlacesListener(this);
        firebaseInteractor.withData(jason).searchPlaces(listener);
        ShowSearchResult(places);
    }

    public boolean displayView() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String radius = "radius";
        String type = "type";
        String opennow = "opennow";
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if(resultCode == SearchActivity.RESULT_OK){
                jason.put(radius,Integer.valueOf(data.getStringExtra(radius)));
                jason.put(type,data.getStringExtra(type));
                jason.put(opennow,Boolean.valueOf(data.getStringExtra(opennow)));
            }
            if (resultCode == SearchActivity.RESULT_CANCELED) {
                jason.put(type,getCurrentType());
            }


        }
    }

    public void goBackMap(){
        Intent intent = new Intent();
        setResult(this.RESULT_OK,intent);
        finish();
    }
}
