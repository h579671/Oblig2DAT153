package com.example.navdrawerdemo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.File;

@Entity
public class CatObject {

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "cat_name")
    private String catName;

    //old
//    @ColumnInfo(name = "image_name")
//    private int imageName;

    @ColumnInfo(name = "image_name", typeAffinity = ColumnInfo.BLOB)
    private byte[] imageName;






    public CatObject(int uid, String catName, byte[] imageName){
        this.uid = uid;
        this.catName = catName;
        this.imageName = imageName;

    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public byte[] getImageName() {
        return imageName;
    }

    public void setImageName(byte[] imageName) {
        this.imageName = imageName;
    }

    public int getUid(){ return uid; }

    public void setUid(int uid) { this.uid = uid; }
}
