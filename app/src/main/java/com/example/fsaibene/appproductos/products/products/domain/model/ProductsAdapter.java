package com.example.fsaibene.appproductos.products.products.domain.model;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fsaibene.appproductos.*;
import com.example.fsaibene.appproductos.products.products.DataLoading;

import static com.google.common.base.Preconditions.checkNotNull;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by fsaibene on 7/2/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DataLoading{

    private List<Product> mProducts;
    private ProductItemListener mItemListener;
    private boolean mLoading;
    private boolean mMoreData;
    private final static int TYPE_PRODUCT = 1;
    private final static int TYPE_LOADING_MORE = 2;
    public ProductsAdapter(List<Product> products, ProductItemListener itemListener) {
        setList(products);
        mItemListener = itemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;

        if (viewType == TYPE_LOADING_MORE){
            view = inflater.inflate(R.layout.item_loading_footer, parent, false);
            return new LoadingMoreHolder(view);
        }

        view = inflater.inflate(R.layout.item_product, parent, false);
        return new ProductsHolder(view, mItemListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_PRODUCT:
                Product product = mProducts.get(position);
                ProductsHolder productsHolder = (ProductsHolder) viewHolder;
                productsHolder.price.setText(product.getFormatedPrice());
                productsHolder.name.setText(product.getName());
                productsHolder.unitsInStock.setText(product.getFormattedUnitsInStock());
                Glide.with(viewHolder.itemView.getContext())
                        .load(product.getImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(productsHolder.featuredImage);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHolder((LoadingMoreHolder) viewHolder, position);
                break;
        }
    }

    private void bindLoadingViewHolder(LoadingMoreHolder viewHolder, int position) {
        viewHolder.progress.setVisibility((position > 0 && mLoading && mMoreData) ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, List<Object> payloads) {
        switch (getItemViewType(position)) {
            case TYPE_PRODUCT:
                Product product = mProducts.get(position);
                ProductsHolder productsHolder = (ProductsHolder) viewHolder;
                productsHolder.price.setText(product.getFormatedPrice());
                productsHolder.name.setText(product.getName());
                productsHolder.unitsInStock.setText(product.getFormattedUnitsInStock());
                Glide.with(viewHolder.itemView.getContext())
                        .load(product.getImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(productsHolder.featuredImage);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHolder((LoadingMoreHolder) viewHolder, position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getDataItemCount() && getDataItemCount() > 0){
            return TYPE_PRODUCT; }
        return TYPE_LOADING_MORE;
    }

    public void replaceData(List<Product> notes){
        setList(notes);
        notifyDataSetChanged();
    }
    public void setList(List<Product> notes) {
        mProducts = checkNotNull(notes);
    }
    public void addData(List<Product> products){
        mProducts.addAll(products);
    }

    @Override
    public int getItemCount() {
        return getDataItemCount();
    }

    public Product getItem(int position){
        return mProducts.get(position);
    }
    public int getDataItemCount(){
        return mProducts.size();
    }

    @Override
    public boolean isLoadingData() {
        return mLoading;
    }

    @Override
    public boolean isThereMoreData() {
        return mMoreData;
    }

    public class ProductsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;
        public TextView price;
        public TextView unitsInStock;
        public ImageView featuredImage;

        private ProductItemListener mItemListener;

        public ProductsHolder(View itemView, ProductItemListener listener){
            super(itemView);
            mItemListener = listener;
            name = (TextView) itemView.findViewById(R.id.product_name);
            price = (TextView) itemView.findViewById(R.id.product_price);
            unitsInStock = (TextView) itemView.findViewById(R.id.units_in_stock);
            featuredImage = (ImageView) itemView.findViewById(R.id.product_featured_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Product product = getItem(position);
            mItemListener.onProductClick(product);
        }
    }
    public interface ProductItemListener {
        void onProductClick(Product clickedNote);
    }

    private class LoadingMoreHolder extends RecyclerView.ViewHolder {
        public ProgressBar progress;

        public LoadingMoreHolder(View view) {
            super(view);
            progress = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }
    public void dataStartedLoading() {
        if (mLoading) return;
        mLoading = true;
        Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                notifyItemInserted(getLoadingMoreItemPosition());
            }
        });

    }

    public void dataFinishedLoading() {
        if (!mLoading) return;
        final int loadingPos = getLoadingMoreItemPosition();
        mLoading = false;
        Handler h = new Handler();
        h.post(new Runnable() {
            @Override
            public void run() {
                notifyItemRemoved(loadingPos);
            }
        });

    }

    public void setMoreData(boolean more) {
        mMoreData = more;
    }

    private int getLoadingMoreItemPosition() {
        return mLoading ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

}
