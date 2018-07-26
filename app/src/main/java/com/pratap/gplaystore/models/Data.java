package com.pratap.gplaystore.models;

import java.util.List;

/**
 * Created by pratap.kesaboyina on 30-11-2015.
 */
public class Data {



    private String cat_name;
    private List<products> product;

    public Data() {
    }

    public Data(String cat_name, List<products> product) {
        this.cat_name = cat_name;
        this.product = product;
    }


    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public List<products> getProduct() {
        return product;
    }

    public void setProduct(List<products> product) {
        this.product = product;
    }
}
