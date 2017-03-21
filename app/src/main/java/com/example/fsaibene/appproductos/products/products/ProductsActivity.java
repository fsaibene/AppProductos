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

import com.example.fsaibene.appproductos.ProductsMvpController;
import com.example.fsaibene.appproductos.R;
import com.example.fsaibene.appproductos.di.DependencyProvider;
import com.example.fsaibene.appproductos.login.LoginActivity;
import com.example.fsaibene.appproductos.login.data.preferences.UserPrefs;
import com.example.fsaibene.appproductos.login.presentation.LoginFragment;
import com.example.fsaibene.appproductos.products.products.ProductsFragment;
import com.example.fsaibene.appproductos.util.ActivityUtils;

public class ProductsActivity extends AppCompatActivity {
    private final static String STATE_PRODUCT_CODE = "state.product_code";

    private Toolbar mToolbar;

    private ProductsMvpController mProductsMvpController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Redirección al Login
        if (!UserPrefs.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_products);

        // Referencias UI
        if (!ActivityUtils.isTwoPane(this)) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setUpToolbar();
        }

        // Obtener código del producto guardado en cambios de configuración.
        // Este no se usará a menos que uses master-detail en ambas orientaciones.
        String productCode = null;
        if (savedInstanceState != null) {
            productCode = savedInstanceState.getString(STATE_PRODUCT_CODE);
        }

        // Crear controlador de productos
        mProductsMvpController = ProductsMvpController.createProductsMvp(this, productCode);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PRODUCT_CODE, mProductsMvpController.getProductCode());
        super.onSaveInstanceState(outState);
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_products, menu);
        return true;
    }

}
