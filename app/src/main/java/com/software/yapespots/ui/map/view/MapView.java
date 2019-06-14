package com.software.yapespots.ui.map.view;
import com.software.yapespots.core.BaseView;
import com.software.yapespots.model.Place;

import java.util.Collection;

public interface MapView extends BaseView {
    void showMap();
    void showBButtons();
    void displaySpots(Collection<Place> places);
    void displayPosition();
}