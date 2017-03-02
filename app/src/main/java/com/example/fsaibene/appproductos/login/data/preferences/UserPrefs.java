package com.example.fsaibene.appproductos.login.data.preferences;

import com.example.fsaibene.appproductos.login.domain.entities.User;

/**
 * Created by fsaibene on 2/3/2017.
 */

public class UserPrefs  implements IUserPreferences{

    private static UserPrefs INSTANCE;
    private UserPrefs() {
        
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void delete() {

    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }
}
