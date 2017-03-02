package com.example.fsaibene.appproductos.login.usecases;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.fsaibene.appproductos.login.data.IUsersRepository;
import com.example.fsaibene.appproductos.login.usecases.ILoginInteractor;
import com.google.common.base.Preconditions;

/**
 * Created by fsaibene on 2/3/2017.
 */

public class LoginInteractor {
    public final IUsersRepository mUsersRepository;

    public LoginInteractor(IUsersRepository usersRepository) {
        mUsersRepository = Preconditions.checkNotNull(usersRepository);
    }
    public class LoginInteractorImpl implements ILoginInteractor {

        @Override
        public void login(String email, String password, final OnLoginFinishedListener callback) {
            boolean formatProblems = false; //formatProblems será una bandera que determinará si tanto el correo como la contraseña pasaron las verificaciones.

            //Validar correo
            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                callback.onEmailError();
                formatProblems = true;
            }
            if (TextUtils.isEmpty(password)){
                callback.onPasswordError();
                formatProblems = true;
            }
            if (!formatProblems){
                mUsersRepository.auth(email, password, new IUsersRepository.OnAuthenticateListener() {
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
}
