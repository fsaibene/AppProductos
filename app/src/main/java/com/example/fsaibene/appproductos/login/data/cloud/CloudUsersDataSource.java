package com.example.fsaibene.appproductos.login.data.cloud;

import android.os.Handler;

import com.example.fsaibene.appproductos.login.domain.entities.User;

/**
 * Created by fsaibene on 2/3/2017.
 */

public class CloudUsersDataSource implements ICloudUsersDataSource {

    private static CloudUsersDataSource INSTANCE;
    // Credenciales de prueba
    private static final String sDummyEmail = "test@test.com";
    private static final String sDummyPassword = "testpassword";

    public CloudUsersDataSource(){
    }
    public static CloudUsersDataSource getInstance(){
        if (INSTANCE == null){
            INSTANCE = new CloudUsersDataSource();
        }
        return INSTANCE;
    }
    @Override
    public void auth(final String email, final String password, final ICloudUsersDataSource.UserServiceCallback callback) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sDummyEmail.equals(email) && sDummyPassword.equals(password)){
                    callback.onAuthFinished(new User(email, password));// Si pass y user coinciden entra
                }else {
                    callback.onAuthFailed("El correo o contraseña no coinciden con tu registro");//Si no muestra este error
                }
            }
        }, 2000);
    }
}
