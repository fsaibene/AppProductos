package com.example.fsaibene.appproductos.login.data.cloud;

import com.example.fsaibene.appproductos.login.domain.entities.User;

/**
 * Created by fsaibene on 2/3/2017.
 */

public interface ICloudUsersDataSource {
    void auth(String name, String password,UserServiceCallback callback);
    interface UserServiceCallback {
        void onAuthFinished(User user);
        void onAuthFailed(String error);
    }
}
