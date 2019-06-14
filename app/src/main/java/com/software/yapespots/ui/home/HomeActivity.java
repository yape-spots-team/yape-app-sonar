package com.software.yapespots.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.software.yapespots.R;
import com.software.yapespots.ui.home.presenter.HomePresenter;
import com.software.yapespots.ui.home.presenter.HomePresenterImpl;
import com.software.yapespots.ui.home.view.HomeView;
import com.software.yapespots.ui.map.MapActivity;

public class HomeActivity extends AppCompatActivity implements HomeView {
    private HomePresenter presenter;
    boolean log;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log = getIntent().getBooleanExtra("logged", false);
        System.out.println("valor de log: " + log);
        setContentView(R.layout.activity_home);
        presenter = new HomePresenterImpl();
        presenter.setView(this);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.home_navigation_drawer_open, R.string.home_navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_map == item.getItemId()) {
            startMapScreen();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startMapScreen() {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("logged", log);
        System.out.println("valor de log: " + log);
        startActivity(intent);
    }

    public boolean displayView() {
        return false;
    }
}
