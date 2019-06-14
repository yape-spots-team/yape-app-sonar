package com.software.yapespots.model;


public class Login {
    //private int id;
    private String contrasena = "123456";

    public boolean verifyLogin(String contrasenna) {
        return contrasenna.equals(contrasena);
    }
}