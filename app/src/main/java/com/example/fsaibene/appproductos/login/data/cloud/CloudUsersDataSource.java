package com.example.fsaibene.appproductos.login.data.cloud;

import android.content.Context;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.fsaibene.appproductos.data.api.ErrorResponse;
import com.example.fsaibene.appproductos.data.api.RestService;
import com.example.fsaibene.appproductos.login.domain.entities.User;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fsaibene on 2/3/2017.
 */

public class CloudUsersDataSource implements ICloudUsersDataSource {

    private static CloudUsersDataSource INSTANCE;

    private Retrofit mRestAdapter;
    private RestService mRestClient;
    private Context ctx;

    private CloudUsersDataSource() {
        mRestAdapter = new Retrofit.Builder().
                baseUrl(RestService.APP_PRODUCTOS_SERVICE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mRestClient = mRestAdapter.create(RestService.class);
    }

    public static CloudUsersDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CloudUsersDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void auth(final String email, final String password,
                     final ICloudUsersDataSource.UserServiceCallback callback) {

        // Encodificar credenciales en Base64
        String formatCredentials = String.format("%s:%s", email, password);
        String base64Credentials = Base64.encodeToString(
                formatCredentials.getBytes(), Base64.NO_WRAP);

        // Preparar llamada de login
        Call<User> userCall = mRestClient.login("Basic " + base64Credentials);

        // Realizar petición POST a /sessions
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()){
                    ResponseBody errorBody = response.errorBody();
                    ErrorResponse errorResponse = ErrorResponse.fromErrorBody(errorBody);
                        try {
                            Log.i("TAG", errorBody.string());
                        }catch(IOException e){
                            Log.i("ERROR", e.getMessage());
                        }
                    callback.onAuthFailed(errorResponse.getMessage());

                    return;

                }
                if (response.isSuccessful()) {
                    callback.onAuthFinished(response.body());
                    return;
                }

//                ResponseBody errorBody = response.errorBody();
//                ErrorResponse errorResponse = ErrorResponse.fromErrorBody(errorBody);
//                if (errorBody.contentType().subtype().equals("json")) {
//                    callback.onAuthFailed(errorResponse.getMessage());
//                }
//                callback.onAuthFailed(response.code() + " FER " + response.message());


//                if (errorBody.contentType().subtype().equals("json")) {
//                }

                callback.onAuthFailed(response.code() + " ssFER " + response.message());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("ENTROOOO", "sabe");
                callback.onAuthFailed(t.getMessage());
            }
        });
    }
}
