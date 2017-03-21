package com.example.fsaibene.appproductos.products.products.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by fsaibene on 7/2/2017.
 */

public class Product {
    @SerializedName("code")
    private String mCode;

    @SerializedName("name")
    private String mName;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("brand")
    private String mBrand;

    @SerializedName("price")
    private float mPrice;

    @SerializedName("unitsInStock")
    private int mUnitsInStock;

    @SerializedName("imageUrl")
    private String mImageUrl;

    public Product(float price, String name, String imageUrl){
        mCode = UUID.randomUUID().toString();
        mPrice = price;
        mName = name;
        mImageUrl = imageUrl;
    }

    public Product(String code, String name, String description,
                   String brand, float price, int unitsInStock, String imageUrl) {
        mCode = code;
        mName = name;
        mDescription = description;
        mBrand = brand;
        mPrice = price;
        mUnitsInStock = unitsInStock;
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
    public int getUnitsInStock(){
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
