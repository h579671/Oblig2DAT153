package com.example.navdrawerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ArrayList<CatObject> catList;
    public String scoreTxt="Score";
    public CatObject currentCat;
    public int correctAns=0;
    public int totalAns=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        catList= new ArrayList<>();
        catList.add(new CatObject("bengal cat", R.drawable.bengal_icon));
        catList.add(new CatObject("persian cat", R.drawable.persian_icon));
        catList.add(new CatObject("siameser cat",R.drawable.siameser_cat_cart));
        getRandomImage();
    }
    private void getRandomImage(){
        ImageView mImageView = (ImageView)findViewById(R.id.myImageView);
        CatObject catRandObj= getRandomElement(catList);
        currentCat= catRandObj;
        int catObjectImageCode= catRandObj.getImageName(); //catList.get(imageId).getImageName()
        mImageView.setBackgroundResource(catObjectImageCode);
    }
    public CatObject getRandomElement(ArrayList<CatObject> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public void clickMenu(View view){
        openDrawer(drawerLayout);
    }
    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer((GravityCompat.START));
    }
    public void clickLogo(View view){
        //close drawer
        closeDrawer(drawerLayout);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        //close drawer layout
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            //close drawer if open
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void clickHome(View view){
        //recreate activity
        recreate();
    }
    public void clickDashBoard(View view){
        //redirect to dashboard
        redirectActivity(this, Dashboard.class);
    }
    public void clickAboutUs(View view){
        redirectActivity(this, AboutUs.class);
    }
    public void clickLogout(View view){
        logout(this);
    }
    public static void logout(Activity activity){
        //alert dialog
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        builder.setTitle("Exit");
        builder.setMessage("Do you want to exit app?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //finish activity
                activity.finishAffinity();
                //exit app
                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dont exit
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    public static void redirectActivity(Activity activity, Class aclass) {
        Intent intent = new Intent(activity, aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
    @Override
    protected void onPause(){
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);
    }

    public void answerQuestion(View view) {
        //if correct or not
        final EditText editText1= findViewById(R.id.editText1);
        String myString = editText1.getText().toString();
        if(myString!=null){
            myString= myString.toLowerCase();
        }

        totalAns=totalAns+1;
        if(myString.equals(currentCat.getCatName())){
            correctAns=correctAns+1;
            increaseScore();
        }else{
            showAns();
        }
        increaseScore();
        getRandomImage();
    }

    public void increaseScore() {
        TextView displayInteger = (TextView) findViewById(
                R.id.score);
        displayInteger.setText("Score: "+ correctAns+"/"+totalAns );
    }
    public void showAns() {
        TextView displayInteger = (TextView) findViewById(
                R.id.answer);
        displayInteger.setText("Correct answer: "+ currentCat.getCatName() );
    }
}