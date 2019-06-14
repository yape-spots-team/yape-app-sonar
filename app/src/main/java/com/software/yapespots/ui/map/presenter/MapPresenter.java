package com.software.yapespots.ui.map.presenter;

import com.software.yapespots.core.BasePresenter;

public interface MapPresenter extends BasePresenter {
    void onButtonClicked();
    void changeToSearch();
    void changeToFilter();
    void goDeatilView();
}
