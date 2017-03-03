package com.example.fsaibene.appproductos.login.presentation;
import com.example.fsaibene.appproductos.login.usecases.ILoginInteractor;
import com.example.fsaibene.appproductos.login.usecases.LoginInteractor;
import com.google.common.base.Preconditions;

/**
 * Created by fsaibene on 2/3/2017.
 */

public class LoginPresenter implements LoginMvp.Presenter, ILoginInteractor.OnLoginFinishedListener{

    private final LoginMvp.View mLoginView;
        private final LoginInteractor mLoginInteractor;

    public LoginPresenter(LoginMvp.View loginView, LoginInteractor loginInteractor) {
        mLoginView = Preconditions.checkNotNull(loginView);
        this.mLoginInteractor = Preconditions.checkNotNull(loginInteractor);
        mLoginView.setPresenter(this);
    }

    @Override
    public void validateCredentials(String email, String password) {
        mLoginView.showLoadingIndicator(true);
        mLoginInteractor.login(email, password, this);
    }

    @Override
    public void onEmailError() {
        mLoginView.showLoadingIndicator(false);
        mLoginView.showEmailError();
    }

    @Override
    public void onPasswordError() {
        mLoginView.showLoadingIndicator(false);
        mLoginView.showPasswordError();
    }

    @Override
    public void onError(String error) {
        mLoginView.showLoadingIndicator(false);
        mLoginView.showLoginError(error);
    }

    @Override
    public void onSuccess() {
        mLoginView.showLoadingIndicator(false);
        mLoginView.showProductsScreen();
    }
}
