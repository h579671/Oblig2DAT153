package com.example.navdrawerdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;
import androidx.room.TypeConverter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class AboutUs extends AppCompatActivity{
    DrawerLayout drawerLayout;
    List<CatObject> catList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        drawerLayout = findViewById(R.id.drawer_layout);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        CatDAO catDAO  = db.catDAO();


        catList= new ArrayList<>();
//        catList.add(new CatObject("bengal cat", R.drawable.bengal_icon));
//        catList.add(new CatObject("persian cat", R.drawable.persian_icon));
//        catList.add(new CatObject("siameser cat",R.drawable.siameser_cat_cart));


        catList = catDAO.getAll();


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
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").allowMainThreadQueries().build();
        CatDAO catDAO  = db.catDAO();

        TableLayout table = (TableLayout) findViewById(R.id.table_main);
        for (int i = 0; i < catList.size(); i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT));

            ImageView iView = new ImageView(this);
            iView.setImageBitmap(frombyte(catList.get(i).getImageName()));
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(100, 80);
            iView.setLayoutParams(layoutParams);
            TextView text = new TextView(this);
            text.setText(catList.get(i).getCatName());
     //       text.setGravity(Gravity.CENTER);

            final int i2 = i;

            Button btn= new Button(this);
            btn.setId(i);
            btn.setText("DELETE");
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    table.removeView(tr);
                    catDAO.delete(catList.get(i2));
                }
            });
            tr.addView(iView);
            tr.addView(text);
            tr.addView(btn);

            table.addView(tr);
        }
    }

    @TypeConverter
    public byte[] frombit(Bitmap bitmap){

//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 10, out);
//        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
//

        int size     = bitmap.getRowBytes() * bitmap.getHeight();

        ByteBuffer b = ByteBuffer.allocate(size);
        bitmap.copyPixelsToBuffer(b);

        byte[] bytes = new byte[size];

        try {

            b.get(bytes, 0, bytes.length);

        } catch (BufferUnderflowException e) {

            // always happens

        }

        return bytes;

    }

    @TypeConverter
    public Bitmap frombyte(byte[] bytes){

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 10, out);
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));





        return decoded;
    }

}