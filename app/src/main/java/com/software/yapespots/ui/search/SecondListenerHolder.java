package com.software.yapespots.ui.search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class SecondListenerHolder implements View.OnClickListener{
    Context thisContext;
    String phone;
    public SecondListenerHolder(Context ctx, String number){
        this.thisContext=ctx;
        this.phone = number;
    }
    @Override
    public void onClick(View v) {
        thisContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null)));
    }
}