package com.software.yapespots.ui.filter.presenter;

import com.software.yapespots.core.BasePresenter;
import com.software.yapespots.model.Place;

import java.util.Collection;

public interface FilterPresenter extends BasePresenter {
    Collection<Place> applyFilter(Collection<Place> places);
}