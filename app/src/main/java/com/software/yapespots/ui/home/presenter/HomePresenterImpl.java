package com.software.yapespots.ui.home.presenter;

import com.software.yapespots.core.BaseView;
import com.software.yapespots.ui.home.view.HomeView;

public class HomePresenterImpl implements HomePresenter {
    private HomeView view;

    public void setView(BaseView view) {
        this.view = (HomeView) view;
    }
}
