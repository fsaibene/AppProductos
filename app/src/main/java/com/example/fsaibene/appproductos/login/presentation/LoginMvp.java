package com.example.fsaibene.appproductos.login.presentation;

/**
 * Created by fsaibene on 2/3/2017.
 */

public interface LoginMvp {
    interface View {
        void showEmailError();
        void showPasswordError();
        void login(String email, String password);
        void showLoginError(String error);
        void showLoadingIndicator(boolean show);
        void showProductsScreen();
        void setPresenter(Presenter presenter);
    }
    interface Presenter{
        void validateCredentials(String email, String password);
    }
}
