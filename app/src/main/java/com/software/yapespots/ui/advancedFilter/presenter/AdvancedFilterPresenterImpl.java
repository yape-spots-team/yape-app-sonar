package com.software.yapespots.ui.advancedFilter.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.software.yapespots.core.BaseView;
import com.software.yapespots.model.AdvancedFilter;
import com.software.yapespots.model.Place;
import com.software.yapespots.ui.advancedFilter.AdvancedFilterActivity;
import com.software.yapespots.ui.advancedFilter.view.AdvancedFilterView;

import java.util.Collection;
import java.util.HashMap;


public class AdvancedFilterPresenterImpl implements AdvancedFilterPresenter {

    AdvancedFilterView view;
    AdvancedFilter advancedFilter;

    public AdvancedFilterPresenterImpl(AdvancedFilterActivity view) {
        setView((BaseView) view);
        advancedFilter = new AdvancedFilter();
    }

    public void setView(BaseView view) {
        this.view = (AdvancedFilterView) view;
    }

    public Collection<Place> applyFilter(Collection<Place> places) {
        return null;
    }

    public void modifyDistance(Integer newRadius) {
        advancedFilter.setDistance(newRadius);
    }

    public void setAvailable(String available) {
        if (available.equals("Todos")) {
            advancedFilter.setAvailable(false);
            return;
        }
        advancedFilter.setAvailable(true);
    }

    public void setType(View view) {
        String tagString = (String) view.getTag();
        advancedFilter.setType(tagString);
    }

    public void unSelectType() {
        advancedFilter.setType("");
    }

    public void resetButtons(View newview, Context ctx) {
        ImageButton buttonp;
        for (int i = 1; i <= 9; i++) {
            int resID = ctx.getResources().getIdentifier("advancebutton" + i, "id", ctx.getPackageName());
            buttonp = (ImageButton) (view.findView(resID));
            if (resID != newview.getId()) {
                buttonp.setSelected(false);
            }
        }
    }

    @Override
    public void resetall(TextView text, SeekBar bar, Spinner dropdown) {
        bar.setProgress(0);
        text.setText("" + 0 + "m");
        dropdown.setSelection(0);
        unSelectType();
    }

    public String getDistance() {
        return Integer.toString(advancedFilter.getDistance());
    }

    public HashMap<String, Object> getAdvancedFilters() {

        return advancedFilter.getAdvancedFilters();
    }
}
