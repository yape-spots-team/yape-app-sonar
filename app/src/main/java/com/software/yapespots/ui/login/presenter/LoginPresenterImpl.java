package com.software.yapespots.ui.login.presenter;

import com.software.yapespots.core.BaseView;
import com.software.yapespots.model.Login;

import com.software.yapespots.ui.login.LoginActivity;
import com.software.yapespots.ui.login.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginview;
    private LoginActivity login;
    private Login log;
    private String contrasena = "";

    public LoginPresenterImpl(LoginActivity view) {
        setView(view);
        this.login = view;
        log = new Login();
    }


    public boolean desbloqueo(String num) {
        login.fillCircle();
        contrasena = contrasena + num;
        if (login.cont != 5) {
            login.cont = login.cont + 1;
            return false;
        } else {
            if (log.verifyLogin(contrasena)) {
                return true;
            }
            login.resetCircles();
            contrasena = "";
            return false;
        }
    }

    @Override
    public void setView(BaseView view) {
        loginview = (LoginView) view;
    }
}
