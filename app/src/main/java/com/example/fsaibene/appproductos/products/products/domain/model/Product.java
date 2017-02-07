package com.example.fsaibene.appproductos.products.products.domain.model;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by fsaibene on 7/2/2017.
 */

public class Product {
    private String mCode;
    private String mName;
    private String mDescription;
    private String mBrand;
    private float mPrice;
    private int mUnitsInStock;
    private String mImageUrl;

    public Product(float price, String name, String imageUrl){
        mCode = UUID.randomUUID().toString();
        mPrice = price;
        mName = name;
        mImageUrl = imageUrl;
    }
    public String getCode(){
        return mCode;
    }
    public String getName(){
        return mName;
    }
    public String getDescription(){
        return mDescription;
    }
    public String getBrand(){
        return mBrand;
    }
    public float getPrice(){
        return mPrice;
    }
    public String getImageUrl(){
        return mImageUrl;
    }
    public Object getUnitsInStock(){
        return mUnitsInStock;
    }
    public void setCode(String mCode) {
        this.mCode = mCode;
    }
    public void setName(String mName){
        this.mName = mName;
    }
    public void setDescription(String mDescription){
        this.mDescription = mDescription;
    }
    public void setBrand(String mBrand){
        this.mBrand = mBrand;
    }
    public void setPrice(float mPrice){
        this.mPrice = mPrice;
    }
    public void setUnitsInStock(int mUnitsInStock){
        this.mUnitsInStock = mUnitsInStock;
    }
    public void setImageUrl(String mImageUrl){
        this.mImageUrl = mImageUrl;
    }
    public String getFormatedPrice(){
        return String.format("$%s", mPrice);
    }
    public String getFormattedUnitsInStock(){
        return String.format(Locale.getDefault(), "%d u.", mUnitsInStock);
    }
}
