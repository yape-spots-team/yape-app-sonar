package com.software.yapespots.ui.map.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.software.yapespots.R;
import com.software.yapespots.ui.map.MapActivity;

public class FiltersListener implements View.OnClickListener {
    private static final FiltersListener filtersListener = new FiltersListener();

    public static FiltersListener getInstance() {
        return filtersListener;
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();

        // TODO: Aquí redirige a Advanced Filter    
    }
}
