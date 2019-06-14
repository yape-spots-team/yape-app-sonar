package com.software.yapespots.ui.filter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.software.yapespots.model.Place;
import com.software.yapespots.ui.filter.view.FilterView;

import java.util.Collection;

public class FilterActivity extends AppCompatActivity implements FilterView {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
    }

    public void colorButton() {

    }

    public void showPlaces(Collection<Place> places) {

    }

    public void showWheel() {

    }

    public boolean displayView() {
        return false;
    }
}
