package com.example.navdrawerdemo;

import java.io.File;

public class CatObject {
    private String catName;
    private int imageName;


    public CatObject(String name, int imageName){
        this.catName=name;
        this.imageName= imageName;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getImageName() {
        return imageName;
    }

    public void setImageName(int imageName) {
        this.imageName = imageName;
    }
}
