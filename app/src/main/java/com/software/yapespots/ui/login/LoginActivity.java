package com.software.yapespots.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.software.yapespots.R;
import com.software.yapespots.ui.home.HomeActivity;
import com.software.yapespots.ui.login.view.LoginView;
import com.software.yapespots.ui.login.presenter.LoginPresenter;
import com.software.yapespots.ui.login.presenter.LoginPresenterImpl;
import com.software.yapespots.ui.map.MapActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private LoginPresenter presenter;
    private Button[] numeros;
    private ImageButton gomap;

    private ImageView[] circulos;
    public int cont = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenterImpl(this);
        setContentView(R.layout.activity_login);
        circulos = new ImageView[6];
        numeros = new Button[10];
        circulos[0] = findViewById(R.id.cir1);
        circulos[1] = findViewById(R.id.cir2);
        circulos[2] = findViewById(R.id.cir3);
        circulos[3] = findViewById(R.id.cir4);
        circulos[4] = findViewById(R.id.cir5);
        circulos[5] = findViewById(R.id.cir6);

        numeros[0] = findViewById(R.id.num_0);
        numeros[1] = findViewById(R.id.num_1);
        numeros[2] = findViewById(R.id.num_2);
        numeros[3] = findViewById(R.id.num_3);
        numeros[4] = findViewById(R.id.num_4);
        numeros[5] = findViewById(R.id.num_5);
        numeros[6] = findViewById(R.id.num_6);
        numeros[7] = findViewById(R.id.num_7);
        numeros[8] = findViewById(R.id.num_8);
        numeros[9] = findViewById(R.id.num_9);
        gomap = findViewById(R.id.gomap);
        init();
    }

    public void resetCircles() {
        for (int i = 0; i < 6; i++) {
            circulos[i].setImageResource(R.drawable.login_circle_empty);
        }
        cont = 0;
    }

    public void fillCircle() {
        circulos[cont].setImageResource(R.drawable.login_circle_full);
    }

    private void changeToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("logged", true);
        startActivity(intent);
    }

    private void init() {
        numeros[0].setOnClickListener(view -> {
            if (presenter.desbloqueo("0")) {
                changeToHome();
            }
        });
        numeros[1].setOnClickListener(view -> {
            if (presenter.desbloqueo("1")) {
                changeToHome();
            }
        });
        numeros[2].setOnClickListener(view -> {
            if (presenter.desbloqueo("2")) {
                changeToHome();
            }
        });
        numeros[3].setOnClickListener(view -> {
            if (presenter.desbloqueo("3")) {
                changeToHome();
            }
        });
        numeros[4].setOnClickListener(view -> {
            if (presenter.desbloqueo("4")) {
                changeToHome();
            }
        });
        numeros[5].setOnClickListener(view -> {
            if (presenter.desbloqueo("5")) {
                changeToHome();
            }
        });
        numeros[6].setOnClickListener(view -> {
            if (presenter.desbloqueo("6")) {
                changeToHome();
            }
        });
        numeros[7].setOnClickListener(view -> {
            if (presenter.desbloqueo("7")) {
                changeToHome();
            }
        });
        numeros[8].setOnClickListener(view -> {
            if (presenter.desbloqueo("8")) {
                changeToHome();
            }
        });
        numeros[9].setOnClickListener(view -> {
            if (presenter.desbloqueo("9")) {
                changeToHome();
            }
        });
        gomap.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, MapActivity.class);
            intent1.putExtra("logged", false);
            startActivity(intent1);
        });
    }

    @Override
    public boolean displayView() {
        return false;
    }
}
