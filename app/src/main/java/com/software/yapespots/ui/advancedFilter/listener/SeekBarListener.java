package com.software.yapespots.ui.advancedFilter.listener;

import android.widget.SeekBar;
import android.widget.TextView;

public class SeekBarListener implements SeekBar.OnSeekBarChangeListener {

    TextView distanceText;
    int oldProgress;

    public SeekBarListener(TextView distancia) {
        distanceText = distancia;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        distanceText.setText("" + i + "m");
        oldProgress = i;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public Integer getProgress() {
        return oldProgress;
    }
}
