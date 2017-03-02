package com.example.fsaibene.appproductos.login.usecases;

/**
 * Created by fsaibene on 2/3/2017.
 */

public interface ILoginInteractor {
    void login(String email, String password, OnLoginFinishedListener callback);

    interface OnLoginFinishedListener {
        void onEmailError();
        void onPasswordError();
        void onError(String error);
        void onSuccess();
    }
}
