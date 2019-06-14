package com.software.yapespots.ui.advancedFilter.presenter;
import android.content.Context;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.software.yapespots.ui.filter.presenter.FilterPresenter;

import java.util.HashMap;

public interface AdvancedFilterPresenter extends FilterPresenter {
    void modifyDistance(Integer newRadius);
    void setType(View view);
    void setAvailable(String available);
    void resetButtons(View newview, Context baseContext);
    void resetall(TextView text, SeekBar bar, Spinner dropdown);
    String getDistance();
    void unSelectType();
    HashMap<String,Object> getAdvancedFilters();
}