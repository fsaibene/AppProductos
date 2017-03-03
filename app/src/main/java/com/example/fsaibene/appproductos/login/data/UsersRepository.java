package com.example.fsaibene.appproductos.login.data;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.fsaibene.appproductos.R;
import com.example.fsaibene.appproductos.login.data.cloud.ICloudUsersDataSource;
import com.example.fsaibene.appproductos.login.data.preferences.IUserPreferences;
import com.example.fsaibene.appproductos.login.domain.entities.User;
import com.google.common.base.Preconditions;

/**
 * Created by fsaibene on 2/3/2017.
 */

public class UsersRepository implements IUsersRepository {

    // Relaciones de composición
    private final ICloudUsersDataSource mUserService;
    private final IUserPreferences mUserPreferences;

    private final Context mContext;

    public UsersRepository(ICloudUsersDataSource userService,
                           IUserPreferences userPreferences,
                           Context controllerContext) {
        mUserService = Preconditions.checkNotNull(userService);
        mUserPreferences = Preconditions.checkNotNull(userPreferences);
        mContext = Preconditions.checkNotNull(controllerContext);
    }

    @Override
    public void auth(String email, String password, final OnAuthenticateListener callback) {
        if (!isNetworkAvailable()) {
            callback.onError(mContext.getString(R.string.error_network));
            return;
        }

        mUserService.auth(email, password,
                new ICloudUsersDataSource.UserServiceCallback() {
                    @Override
                    public void onAuthFinished(User user) {
                        mUserPreferences.save(user);
                        callback.onSuccess();
                    }

                    @Override
                    public void onAuthFailed(String error) {
                        callback.onError(error);
                    }
                });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void closeSession() {
        mUserPreferences.delete();
    }

}
