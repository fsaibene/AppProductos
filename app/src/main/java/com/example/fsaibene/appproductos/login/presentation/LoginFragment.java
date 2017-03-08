package com.example.fsaibene.appproductos.login.presentation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fsaibene.appproductos.R;
import com.example.fsaibene.appproductos.products.products.ProductsActivity;
import com.google.common.base.Preconditions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements LoginMvp.View {

    private ImageView mImageLogo;
    private EditText mEmailField;
    private TextInputLayout mEmailFloatingLabel;
    private EditText mPasswordField;
    private TextInputLayout mPasswordFloatingLabel;
    private Button mButtonLogin;
    private ProgressBar mProgressLogin;

    private LoginMvp.Presenter mLoginPresenter;


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View uiRoot = inflater.inflate(R.layout.fragment_login, container, false);

        mImageLogo = (ImageView) uiRoot.findViewById(R.id.image_logo);
        mEmailField = (EditText) uiRoot.findViewById(R.id.text_field_email);
        mEmailFloatingLabel = (TextInputLayout) uiRoot.findViewById(R.id.float_label_email);
        mPasswordField = (EditText) uiRoot.findViewById(R.id.text_field_password);
        mPasswordFloatingLabel = (TextInputLayout) uiRoot.findViewById(R.id.float_label_password);
        mButtonLogin = (Button) uiRoot.findViewById(R.id.button_login);
        mProgressLogin = (ProgressBar) uiRoot.findViewById(R.id.progress_login);

        mEmailField.addTextChangedListener(new TextWatcherLabel(mEmailFloatingLabel));
        mPasswordField.addTextChangedListener(new TextWatcherLabel(mPasswordFloatingLabel));

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("asdasd", mEmailField.getText().toString() + mPasswordField.getText().toString());
//                login(mEmailField.getText().toString(), mPasswordField.getText().toString());
//            }
            @Override
            public void onClick(View view) {
                login(mEmailField.getText().toString(),
                        mPasswordField.getText().toString());
                closeKeyboard();
            }
        });
        Glide.with(this).load("file:///android_asset/fb.png").into(mImageLogo);
        return uiRoot;
    }
    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(),0);
    }

    @Override
    public void showEmailError() {
        mEmailFloatingLabel.setError(getString(R.string.error_login));
    }

    @Override
    public void showPasswordError() {
        mPasswordFloatingLabel.setError(getString(R.string.error_password));
    }

    @Override
    public void login(String email, String password) {
        mLoginPresenter.validateCredentials(email, password);
    }

    @Override
    public void showLoginError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingIndicator(boolean show) {
        mProgressLogin.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showProductsScreen() {
        startActivity(new Intent(getActivity(), ProductsActivity.class));
        getActivity().finish();
    }

    @Override
    public void setPresenter(LoginMvp.Presenter presenter) {
        mLoginPresenter = Preconditions.checkNotNull(presenter);
    }

    public class  TextWatcherLabel implements TextWatcher{

        private final TextInputLayout mFloatingLabel;

        public TextWatcherLabel(TextInputLayout floatingLabel) {
            mFloatingLabel = floatingLabel;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mFloatingLabel.setError(null);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
