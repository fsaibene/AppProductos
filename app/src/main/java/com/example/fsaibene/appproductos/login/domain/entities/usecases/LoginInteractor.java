package com.example.fsaibene.appproductos.login.domain.entities.usecases;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.fsaibene.appproductos.login.data.IUsersRepository;
import com.google.common.base.Preconditions;

/**
 * Created by fsaibene on 2/3/2017.
 */

public class LoginInteractor implements ILoginInteractor {

    // Relación de composición
    private final IUsersRepository mUsersRepository;

    public LoginInteractor(IUsersRepository usersRepository) {
        mUsersRepository = Preconditions.checkNotNull(usersRepository);
    }

    @Override
    public void login(String email, String password, final OnLoginFinishedListener callback) {
        boolean formatProblems = false;

        // Validar correo
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            callback.onEmailError();
            formatProblems = true;
        }

        // Validar contraseña
        if (TextUtils.isEmpty(password)) {
            callback.onPasswordError();
            formatProblems = true;
        }

        // Realizar autorización
        if (!formatProblems) {
            mUsersRepository.auth(email, password,
                    new IUsersRepository.OnAuthenticateListener() {
                        @Override
                        public void onSuccess() {
                            callback.onSuccess();
                        }

                        @Override
                        public void onError(String error) {
                            callback.onError(error);
                        }
                    });
        }
    }
}
