package com.example.fsaibene.appproductos.productdetail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.fsaibene.appproductos.R;
import com.example.fsaibene.appproductos.di.DependencyProvider;
import com.example.fsaibene.appproductos.productdetail.presentation.ProductDetailFragment;
import com.example.fsaibene.appproductos.productdetail.presentation.ProductDetailPresenter;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailFragment.FragmentTransmitter{

    private ImageView mProductImage;
    public static final String EXTRA_PRODUCT_CODE = "extra.product_code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        //Crear frag
        ProductDetailFragment fragment = (ProductDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_product_detail_container);

        if (fragment == null){
            fragment = ProductDetailFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_product_detail_container, fragment)
                    .commit();
        }
        // Obtener código del producto enviado desde la actividad de productos String productCode
        String productCode = getIntent().getStringExtra(EXTRA_PRODUCT_CODE);

        // Crear presentador
        ProductDetailPresenter detailPresenter =
                new ProductDetailPresenter(productCode, fragment,
                        DependencyProvider.provideGetProducts(getApplicationContext()));

        fragment.setPresenter(detailPresenter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void passProductImage(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .into(mProductImage);
    }

}
