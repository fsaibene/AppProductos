package com.example.fsaibene.appproductos.login.data.preferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.fsaibene.appproductos.login.domain.entities.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by fsaibene on 2/3/2017.
 */

public class UserPrefs implements IUserPreferences {

    // Nombres de preferencias
    private static final String USER_PREFS_FILE_NAME = "user_prefs";
    private static final String PREF_TOKEN = "user.token";
    private static final String PREF_USER_ID = "user.id";
    private static final String PREF_USERNAME = "user.username";
    private static final String PREF_EMPLOYEE_EMP_NO = "user.employeeEmpNo";
    private static final String PREF_ROLE_ID = "user.roleId";

    // Singleton
    private static UserPrefs INSTANCE;

    // Composición
    private SharedPreferences mSharedPreferences;

    // Estado actual del usuario
    private boolean mIsLoggedIn;


    private UserPrefs(Context context) {
        mSharedPreferences = context.getApplicationContext()
                .getSharedPreferences(USER_PREFS_FILE_NAME, Context
                        .MODE_PRIVATE);

        // Resetear variables de clase al iniciar
        mIsLoggedIn = !TextUtils.isEmpty(mSharedPreferences.getString(PREF_TOKEN, null));
    }

    public static UserPrefs getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new UserPrefs(context);
        }
        return INSTANCE;
    }

    @Override
    public void save(User user) {
        if (user != null) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(PREF_USER_ID, user.getId());
            editor.putString(PREF_USERNAME, user.getUsername());
            editor.putInt(PREF_EMPLOYEE_EMP_NO, user.getEmployeeEmpNo());
            editor.putInt(PREF_ROLE_ID, user.getRoleId());
            editor.putString(PREF_TOKEN, user.getToken());
            editor.apply();

            mIsLoggedIn = true;
        }
    }

    @Override
    public void delete() {
        mIsLoggedIn = false;
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PREF_USER_ID, null);
        editor.putString(PREF_USERNAME, null);
        editor.putInt(PREF_EMPLOYEE_EMP_NO, 0);
        editor.putInt(PREF_ROLE_ID, 0);
        editor.putString(PREF_TOKEN, null);
        editor.apply();
    }

    @Override
    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    @Override
    public String getAccessToken() {
        return mSharedPreferences.getString(PREF_TOKEN, null);
    }
}
