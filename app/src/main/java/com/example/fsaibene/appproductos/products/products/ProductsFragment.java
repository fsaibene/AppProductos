package com.example.fsaibene.appproductos.products.products;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fsaibene.appproductos.R;
import com.example.fsaibene.appproductos.di.DependencyProvider;
import com.example.fsaibene.appproductos.login.LoginActivity;
import com.example.fsaibene.appproductos.products.products.domain.model.Product;
import com.example.fsaibene.appproductos.products.products.domain.model.ProductsAdapter;
import com.example.fsaibene.appproductos.products.products.domain.model.ProductsAdapter.*;
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
    private ProductsPresenter mProductsPresenter;
    private ProductItemListener mItemListener = new ProductItemListener() {
        @Override
        public void onProductClick(Product clickedNote) {
            // Aquí lanzarías la pantalla de detalle del producto
            Toast.makeText(getActivity().getBaseContext(), "Click on item: "+clickedNote.getCode(),Toast.LENGTH_SHORT).show();
        }
    };
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment newInstance() {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductsAdapter = new ProductsAdapter(new ArrayList<Product>(0), mItemListener);

        setHasOptionsMenu(true);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_products, container, false);

        mProductsList = (RecyclerView) root.findViewById(R.id.products_list);
        mEmptyView = root.findViewById(R.id.noProducts);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);

        setUpProductsList();
        setUptRefreshLayout();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState == null){
            mProductsPresenter.loadProducts(false);
        }
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
                new InfiniteScrollListener(lm, mProductsAdapter) {
                    @Override
                    public void onLoadMore() {
                        mProductsPresenter.loadProducts(false);
                    }
                }
        );
    }

    @Override
    public void showProducts(List<Product> products) {
        mProductsAdapter.replaceData(products);

        mProductsList.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingState(final boolean show) {
        if(getView() == null){
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
        mProductsList.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProductsError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProductsPage(List<Product> products) {

        mProductsAdapter.addData(products);
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
        mProductsPresenter = (ProductsPresenter) checkNotNull(presenter);//Check
    }

    @Override
    public void showLoginScreen() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_log_out) {
            mProductsPresenter.logOut();
        }
        return true;
    }
}
