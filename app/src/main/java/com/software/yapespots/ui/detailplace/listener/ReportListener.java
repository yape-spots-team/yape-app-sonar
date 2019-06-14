package com.software.yapespots.ui.detailplace.listener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.software.yapespots.R;

import java.util.HashMap;

public class ReportListener implements OnCompleteListener<HashMap<String, Object>> {
    Context context;

    public ReportListener(Context context) {
        this.context = context;
    }

    @Override
    public void onComplete(@NonNull Task<HashMap<String, Object>> task) {
        if (!task.isSuccessful()) {
            Exception exception = task.getException();

            Log.e("Firebase::getPlaces::", "getPlaces:onFailure", exception);
            Toast.makeText(this.context, R.string.detail_place_report_denied, Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this.context, R.string.detail_place_report_ok, Toast.LENGTH_SHORT).show();
    }
}
