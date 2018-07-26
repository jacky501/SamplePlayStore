package com.pratap.gplaystore.models;

/**
 * Created by pratap.kesaboyina on 01-12-2015.
 */
public class products {


    private String name;
    private String images;


    public products() {
    }

    public products(String name, String image) {
        this.name = name;
        this.images = image;
    }


    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
