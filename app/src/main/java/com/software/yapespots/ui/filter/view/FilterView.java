package com.software.yapespots.ui.filter.view;
import java.util.Collection;
import com.software.yapespots.core.BaseView;
import com.software.yapespots.model.Place;

public interface FilterView extends BaseView {
    void colorButton();
    void showPlaces(Collection<Place> places);
    void showWheel();
}
