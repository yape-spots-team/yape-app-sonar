package com.software.yapespots.ui.search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class FirstListenerHolder implements View.OnClickListener{
    Context thisContext;
    String lat;
    String lng;

    public FirstListenerHolder(Context ctx,String lat, String lng){
        this.thisContext=ctx;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + lat + "," + lng));
        thisContext.startActivity(intent);
    }
}
