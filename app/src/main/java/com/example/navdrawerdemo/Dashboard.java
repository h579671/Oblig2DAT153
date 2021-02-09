package com.example.navdrawerdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    public static final int GET_FROM_GALLERY = 3;
    DrawerLayout drawerLayout;
    ImageView newCat;

    public List<CatObject> catList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        CatDAO catDAO  = db.catDAO();

        catList = new ArrayList<>();
        catList = catDAO.getAll();

        drawerLayout = findViewById(R.id.drawer_layout);

    }
    public void clickMenu(View view){
        //open drawer
        MainActivity.openDrawer(drawerLayout);
    }
    public void clickLogo(View view){
        //close drawer
        MainActivity.closeDrawer(drawerLayout);
    }
    public void clickHome(View view){
        //redirect home
        MainActivity.redirectActivity(this, MainActivity.class);
    }
    public void clickDashBoard(View view){
        //recreate activity
        recreate();
    }
    public void clickAboutUs(View view){
        //redirect to about us
        MainActivity.redirectActivity(this, AboutUs.class);
    }
    public void clickLogout(View view){
        //close app
        MainActivity.logout(this);
    }
    @Override
    protected void onPause(){
        super.onPause();
        //close drawer
        MainActivity.closeDrawer(drawerLayout);
    }

    public void uploadPicture(View view) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            newCat = (ImageView) findViewById(R.id.newCatImage);
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                newCat.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void addCat (View View) {

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        CatDAO catDAO  = db.catDAO();

        //finding new name
        EditText mEdit = (EditText) findViewById(R.id.catNameInput);
        String newCatName = mEdit.getText().toString();
        TextView catName= (TextView) findViewById(R.id.catNameOfAddedCat);
        catName.setText(newCatName);
        newCat.getDrawable();



        ImageView imgV= (ImageView) findViewById(R.id.CatImage);
        imgV.setImageDrawable(newCat.getDrawable());


        System.out.println("ID navnet er: " + R.id.newCatImage);

        CatObject uploadedCat= new CatObject(catList.size()+1, newCatName, R.id.newCatImage);
        catList.add(uploadedCat);
        catDAO.insertAll(uploadedCat);
        //test
        System.out.println(newCatName +newCat.getDrawable() );


    }



}