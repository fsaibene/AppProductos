package com.example.fsaibene.appproductos.products.products;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.fsaibene.appproductos.R;
import com.example.fsaibene.appproductos.di.DependencyProvider;
import com.example.fsaibene.appproductos.login.LoginActivity;
import com.example.fsaibene.appproductos.productdetail.ProductDetailActivity;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.example.fsaibene.appproductos.products.products.domain.model.ProductsAdapter;
import com.example.fsaibene.appproductos.products.products.domain.model.ProductsAdapter.*;
import com.example.fsaibene.appproductos.util.ActivityUtils;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment implements ProductsMvp.View{

    private RecyclerView mProductsList;
    private ProductsAdapter mProductsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mEmptyView;
    private ProductsAdapter.ProductItemListener mItemListener =
            new ProductsAdapter.ProductItemListener() {
                @Override
                public void onProductClick(Product clickedProduct) {
                    mProductsPresenter.openProductDetails(clickedProduct.getCode());
                }
            };

    private ProductsMvp.Presenter mProductsPresenter;

    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment newInstance() {
        return new ProductsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductsAdapter = new ProductsAdapter(new ArrayList<Product>(0), mItemListener);

        //setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_products, container, false);

        if(ActivityUtils.isTwoPane(getActivity())){
            Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbarList);
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        }
        mProductsList = (RecyclerView) root.findViewById(R.id.products_list);
        mEmptyView = root.findViewById(R.id.noProducts);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);

        setUpProductsList();
        setUptRefreshLayout();
        if (savedInstanceState != null) {
            hideList(false);
        }
        return root;
    }


    private void setUptRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mProductsPresenter.loadProducts(true);
            }
        });
    }

    private void setUpProductsList() {
        mProductsList.setAdapter(mProductsAdapter);
        mProductsList.setHasFixedSize(true);

        final LinearLayoutManager lm = (LinearLayoutManager) mProductsList.getLayoutManager();

        mProductsList.addOnScrollListener(
                new InfiniteScrollListener(mProductsAdapter, lm) {
                    @Override
                    public void onLoadMore() {
                        mProductsPresenter.loadProducts(false);
                    }
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("List Fragment", "Resume");
        mProductsPresenter.loadProducts(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_log_out) {
            mProductsPresenter.logOut();
        }
        return false;
    }

    @Override
    public void showProducts(List<Product> products) {
        mProductsAdapter.replaceData(products);
        hideList(false);
    }

    @Override
    public void showLoadingState(final boolean show) {
        if (getView() == null) {
            return;
        }

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(show);
            }
        });
    }

    @Override
    public void showEmptyState() {
        hideList(true);
    }

    @Override
    public void showProductsError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void showProductsPage(List<Product> products) {
        mProductsAdapter.addData(products);
        hideList(false);
    }

    @Override
    public void showLoadMoreIndicator(boolean show) {
        if (!show) {
            mProductsAdapter.dataFinishedLoading();
        } else {
            mProductsAdapter.dataStartedLoading();
        }
    }

    @Override
    public void allowMoreData(boolean allow) {
        mProductsAdapter.setMoreData(allow);
    }

    @Override
    public void setPresenter(ProductsMvp.Presenter presenter) {
        mProductsPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public ProductsMvp.Presenter getPresenter() {
        return mProductsPresenter;
    }

    @Override
    public void showLoginScreen() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void showProductDetailScreen(String productCode) {
        Intent i = new Intent(getActivity(), ProductDetailActivity.class);
        i.putExtra(ProductDetailActivity.EXTRA_PRODUCT_CODE, productCode);
        startActivity(i);
    }


    private void hideList(boolean hide) {
        mProductsList.setVisibility(hide ? View.GONE : View.VISIBLE);
        mEmptyView.setVisibility(hide ? View.VISIBLE : View.GONE);
    }


}
