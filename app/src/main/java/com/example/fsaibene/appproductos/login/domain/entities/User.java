package com.example.fsaibene.appproductos.login.domain.entities;

/**
 * Created by fsaibene on 2/3/2017.
 */

public class User {
    private String mEmail, mPassword;

    public User(String mail, String password) {
        mEmail = mail;
        mPassword = password;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }
}
