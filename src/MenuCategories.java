package com.cs442.ssamant4.foodbasket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class MenuCategories extends AppCompatActivity {

    SharedPreferences mpref;
    SharedPreferences login_info;
    SharedPreferences.Editor editor;
    String userType;
    String menu_type;
    Spinner s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_categories);

        mpref=getSharedPreferences("User_Info",0);

        userType=mpref.getString("user_type","UsernotIdentified");
        //login_info = getSharedPreferences("User_Info", Context.MODE_PRIVATE);
        editor=mpref.edit();
        s=(Spinner)findViewById(R.id.spinner);


    }
    public void BreakfastMenu(View v){

        editor.putString("menu_type","BreakFast");
        editor.apply();
        Intent i=new Intent(this,ImageListView.class);
        menu_type=s.getSelectedItem().toString();
        i.putExtra("Type",menu_type);


        startActivity(i);

    }
    public void LunchMenu(View v){
        editor.putString("menu_type","Lunch");
        editor.apply();
        Intent i=new Intent(this,ImageListView.class);
        menu_type=s.getSelectedItem().toString();
        i.putExtra("Type",menu_type);
        startActivity(i);
    }
    public void DinnerMenu(View v){
        editor.putString("menu_type","Dinner");
        editor.apply();
        Intent i=new Intent(this,ImageListView.class);
        menu_type=s.getSelectedItem().toString();
        i.putExtra("Type",menu_type);
        startActivity(i);
    }
    public void SnacksMenu(View v){
        editor.putString("menu_type","Snacks");
        editor.apply();
        Intent i=new Intent(this,ImageListView.class);
        menu_type=s.getSelectedItem().toString();
        i.putExtra("Type",menu_type);
        startActivity(i);
    }

}
