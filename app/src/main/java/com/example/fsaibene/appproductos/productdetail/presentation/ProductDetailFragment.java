package com.example.fsaibene.appproductos.productdetail.presentation;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fsaibene.appproductos.R;
import com.example.fsaibene.appproductos.products.products.ProductsActivity;
import com.example.fsaibene.appproductos.products.products.ProductsMvp;
import com.google.common.base.Preconditions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment implements ProductDetailMvp.View {
    private ImageView mImageProduct;
    private View mDetailContainer;
    private TextView mProductName;
    private TextView mProductPrice;
    private TextView mProductStock;
    private TextView mProductDescription;
    private ProgressBar mProgressView;

    // Miembros composición
    private ProductDetailMvp.Presenter mProductDetailPresenter;
    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public static ProductDetailFragment newInstance() {
        return new ProductDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null){
            return null;
        }
        View root = inflater.inflate(R.layout.fragment_product_detail, container, false);
        if (!(getActivity() instanceof ProductsActivity)) {
            //No setear toolbar si el fragmente vive en ProductsActivity
            setToolbar(root);
        }

        mDetailContainer = root.findViewById(R.id.container_product_detail_views);
        mImageProduct = (ImageView) root.findViewById(R.id.image_product);
        mProductName = (TextView) root.findViewById(R.id.text_product_name);
        mProductPrice = (TextView) root.findViewById(R.id.text_product_price);
        mProductStock = (TextView) root.findViewById(R.id.text_product_stock);
        mProductDescription = (TextView) root.findViewById(R.id.text_product_description);
        mProgressView = (ProgressBar) root.findViewById(R.id.progress_indicator);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getView() != null) {
            mProductDetailPresenter.loadProduct();
        }
    }

    private void setToolbar(View root){
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbarDetail);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        ab.setTitle(null);
    }

    @Override
    public void showImage(String imageUrl) {
        Glide.with(this).load(imageUrl).into(mImageProduct);
    }

    @Override
    public void showName(String name) {
        mProductName.setText(name);
    }

    @Override
    public void showPrice(String price) {
        mProductPrice.setText(price);
    }

    @Override
    public void showUnitsInStock(String unitsInStock) {
        mProductStock.setText(unitsInStock);
    }

    @Override
    public void showDescription(String description) {
        mProductDescription.setText(description);
    }

    @Override
    public void showProgressIndicator(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE :View.GONE);
        mDetailContainer.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showLoadError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyState() {
        mProductName.setText("");
        mProductPrice.setText("");
        mProductStock.setText("");
        mProductDescription.setText("");
        mDetailContainer.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(ProductDetailMvp.Presenter productDetailPresenter) {
        mProductDetailPresenter = Preconditions.checkNotNull(productDetailPresenter);
    }
    @Override
    public boolean isActive() {
        return isAdded();
    }
    public interface FragmentTransmitter {
        void passProductImage(String imageUrl);
    }
}
