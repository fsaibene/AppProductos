package com.example.fsaibene.appproductos.products.products;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fsaibene.appproductos.R;
import com.example.fsaibene.appproductos.di.DependencyProvider;
import com.example.fsaibene.appproductos.login.LoginActivity;
import com.example.fsaibene.appproductos.login.data.preferences.UserPrefs;
import com.example.fsaibene.appproductos.login.presentation.LoginFragment;
import com.example.fsaibene.appproductos.products.products.ProductsFragment;

public class ProductsActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ProductsFragment mProductsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Redirección al Login
        if (!UserPrefs.getInstance().isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_products);

        // Referencias UI
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mProductsFragment = (ProductsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.products_container);

        // Setup
        setUpToolbar();
        setUpProductsFragment();

        // Crear presentador
        new ProductsPresenter(
                DependencyProvider.provideProductsRepository(this),
                DependencyProvider.provideUsersRepository(this),
                mProductsFragment);
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
    }
    private void setUpProductsFragment() {
        if (mProductsFragment == null) {
            mProductsFragment = ProductsFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.products_container, mProductsFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
