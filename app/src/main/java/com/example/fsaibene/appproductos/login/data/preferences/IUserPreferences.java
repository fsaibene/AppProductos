package com.example.fsaibene.appproductos.login.data.preferences;

import com.example.fsaibene.appproductos.login.domain.entities.User;

/**
 * Created by fsaibene on 2/3/2017.
 */

public interface IUserPreferences {

    void save(User user);

    void delete();

    boolean isLoggedIn();

    String getAccessToken();
}
