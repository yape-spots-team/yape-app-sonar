package com.software.yapespots.ui.advancedFilter.view;
import android.view.View;

import com.software.yapespots.core.BaseView;

public interface AdvancedFilterView extends BaseView {
    void sendDistance();
    void getSpinner();
    void colorButton(View view);
    void resetButtons(View view);
    void reset(View view);
    void goToSearch(View view);
    void getBack(View view);
    View findView(int resID);
}