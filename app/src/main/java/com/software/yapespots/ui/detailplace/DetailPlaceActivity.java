package com.software.yapespots.ui.detailplace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.software.yapespots.R;
import com.software.yapespots.ui.detailplace.view.DetailPlaceView;

public class DetailPlaceActivity extends AppCompatActivity implements DetailPlaceView {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailplace);

        Button buttonOpenBottomSheet = findViewById(R.id.button_open_bottom_sheet);
    }

    public void OnClick(View view) {
        DetailPlaceBottomSheetDialog bottomSheet = new DetailPlaceBottomSheetDialog();
        bottomSheet.show(getSupportFragmentManager(), "detailPlaceBottomSheet");
    }

    public void placeStatus(String placeId, View view) {

    }

    public void showPhotos() {


    }

    public void showPlaceDetail() {

    }

    public void showButtons() {

    }

    public void showError() {

    }

    public boolean displayView() {
        return false;
    }
}
