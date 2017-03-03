package com.example.fsaibene.appproductos.login.data.preferences;

import android.content.Intent;

import com.example.fsaibene.appproductos.login.domain.entities.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by fsaibene on 2/3/2017.
 */

public class UserPrefs  implements IUserPreferences{

    private static UserPrefs INSTANCE;
    private static final HashMap<String, String> DUMMY_USER_PREFERENCES = new LinkedHashMap<>();//Aca se almacenan los datos de preferencia
    private boolean mIsLoggedIn;
    private UserPrefs() {

    }

    public static UserPrefs getInstance(){
        if (INSTANCE == null){
            INSTANCE = new UserPrefs();
        }
        return INSTANCE;
    }

    @Override
    public void save(User user) {
        mIsLoggedIn = true;
        DUMMY_USER_PREFERENCES.put("Correo", user.getEmail());
        DUMMY_USER_PREFERENCES.put("Contraseña", user.getPassword());
    }

    @Override
    public void delete() {
        mIsLoggedIn = false;
        DUMMY_USER_PREFERENCES.clear();
    }

    @Override
    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }
}
