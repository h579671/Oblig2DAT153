package com.example.navdrawerdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class AboutUs extends AppCompatActivity{
    DrawerLayout drawerLayout;
    ArrayList<CatObject> catList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        drawerLayout = findViewById(R.id.drawer_layout);



        catList= new ArrayList<>();
        catList.add(new CatObject("bengal cat", R.drawable.bengal_icon));
        catList.add(new CatObject("persian cat", R.drawable.persian_icon));
        catList.add(new CatObject("siameser cat",R.drawable.siameser_cat_cart));
        dynamicTable();
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
        //redirect activity to dashboard
        MainActivity.redirectActivity(this, Dashboard.class);
    }
    public void clickAboutUs(View view){
        //recreate activyty
        recreate();
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
    public void dynamicTable() {
        TableLayout table = (TableLayout) findViewById(R.id.table_main);
        for (int i = 0; i < catList.size(); i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT));

            ImageView iView = new ImageView(this);
            iView.setImageResource(catList.get(i).getImageName());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(100, 80);
            iView.setLayoutParams(layoutParams);
            TextView text = new TextView(this);
            text.setText(catList.get(i).getCatName());
     //       text.setGravity(Gravity.CENTER);

            Button btn= new Button(this);
            btn.setId(i);
            btn.setText("DELETE");
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    table.removeView(tr);
                }
            });
            tr.addView(iView);
            tr.addView(text);
            tr.addView(btn);

            table.addView(tr);
        }
    }
}