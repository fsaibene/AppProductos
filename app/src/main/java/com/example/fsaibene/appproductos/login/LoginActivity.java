package com.example.fsaibene.appproductos.login;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fsaibene.appproductos.R;
import com.example.fsaibene.appproductos.login.presentation.LoginFragment;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_login_container);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_login_container, loginFragment)
                    .commit();
        }
    }

    // <<create>> LoginFragment
    // <<create>> LoginInteractor
    // <<create>> LoginPresenter
}
