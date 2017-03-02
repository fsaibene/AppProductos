package com.example.fsaibene.appproductos.login.data;

/**
 * Created by fsaibene on 2/3/2017.
 */

public interface IUsersRepository {
    void auth(String email, String password, OnAuthenticateListener callback);

    void closeSession();

    interface OnAuthenticateListener{
        void onSuccess();
        void onError(String error);
    }
}
