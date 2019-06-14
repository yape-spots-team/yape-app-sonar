package com.software.yapespots.ui.advancedFilter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.software.yapespots.R;
import com.software.yapespots.ui.advancedFilter.listener.SeekBarListener;
import com.software.yapespots.ui.advancedFilter.presenter.AdvancedFilterPresenter;
import com.software.yapespots.ui.advancedFilter.presenter.AdvancedFilterPresenterImpl;
import com.software.yapespots.ui.advancedFilter.view.AdvancedFilterView;

import java.util.HashMap;

public class AdvancedFilterActivity extends AppCompatActivity implements AdvancedFilterView {

    private TextView distanceText;
    private SeekBar distanceBar;
    private Spinner disponibilidad;
    SeekBarListener listener;
    AdvancedFilterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advancedfilter);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        presenter = new AdvancedFilterPresenterImpl(this);
        distanceText = findViewById(R.id.progressid);
        distanceBar = findViewById(R.id.seekBar);
        listener = new SeekBarListener(distanceText);
        distanceBar.setOnSeekBarChangeListener(listener);
        disponibilidad = findViewById(R.id.disponibilidad);

    }

    public void sendDistance() {
        presenter.modifyDistance(listener.getProgress());
    }

    public void getSpinner() {
        String text = disponibilidad.getSelectedItem().toString();
        presenter.setAvailable(text);
    }

    public void colorButton(View view) {
        resetButtons(view);
        view.setSelected(!view.isSelected());
        if (view.isSelected()) {
            presenter.setType(view);
        } else {
            presenter.unSelectType();
        }
    }

    public void resetButtons(View view) {
        presenter.resetButtons(view,getBaseContext());
    }

    public void reset(View view) {
        resetButtons(view);
        presenter.resetall(distanceText,distanceBar,disponibilidad);
    }

    public void goToSearch(View view) {
        sendDistance();
        getSpinner();
        HashMap<String, Object> filters = presenter.getAdvancedFilters();
        /*String text=filters.get("radius").toString()+filters.get("type").toString()+filters.get("opennow").toString();
        Toast.makeText(this,text , Toast.LENGTH_SHORT).show();*/
        reset(view);
        Intent intent = new Intent();
        intent.putExtra("radius",filters.get("radius").toString());
        intent.putExtra("type", filters.get("type").toString());
        intent.putExtra("opennow",filters.get("opennow").toString());
        setResult(this.RESULT_OK,intent);
        finish();
    }

    @Override
    public void getBack(View view) {
        //Toast.makeText(this,"getback" , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        setResult(this.RESULT_CANCELED,intent);
        finish();
    }

    public View findView(int resID) {
        return findViewById(resID);
    }

    public boolean displayView() {
        return false;
    }
}
