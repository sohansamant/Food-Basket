package com.cs442.ssamant4.foodbasket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainMenu extends AppCompatActivity
{

    private ImageButton imgbtnAdd;
    private Button btnbrkfast;
    private Button btnLunch;
    private Button btnDinr;
    private Button btnSnacks;
    private Button btn_nearby_places;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        imgbtnAdd = (ImageButton) findViewById(R.id.imgbtnAdd);
        btnbrkfast = (Button) findViewById(R.id.btnbrkfast);
        btnLunch = (Button) findViewById(R.id.btnLunch);
        btnDinr = (Button) findViewById(R.id.btnDinr);
        btnSnacks = (Button) findViewById(R.id.btnSnacks);
        btn_nearby_places = (Button) findViewById(R.id.btn_nearby_places);

//add slider
        imgbtnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String data = "Description of advertisment";
                Intent intent = new Intent(getApplicationContext(), AdDetails.class);
                startActivity(new Intent(MainMenu.this, AdDetails.class));
            }
        });
    }

    public void ChangeFragment(View view)
    {
        switch (view.getId())
        {
            //if(view == findViewById(R.id.btnbrkfast)){
            case R.id.btnbrkfast:
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.putExtra("Button", "btnBrkfst");
                startActivity(intent);
                break;

            //if(view == findViewById(R.id.btnLunch)){
            case R.id.btnLunch:
                Intent intent2 = new Intent(getApplicationContext(), Main2Activity.class);
                intent2.putExtra("Button", "btnLunch");
                startActivity(intent2);
                break;


            //if(view == findViewById(R.id.btnDinr)){
            case R.id.btnDinr:
                Intent intent3 = new Intent(getApplicationContext(), Main2Activity.class);
                intent3.putExtra("Button", "btnDinr");
                startActivity(intent3);
                break;


            //if(view == findViewById(R.id.btnSnacks)){
            case R.id.btnSnacks:
                Intent intent4 = new Intent(getApplicationContext(), Main2Activity.class);
                intent4.putExtra("Button", "btnSnacks");
                startActivity(intent4);
                break;

        }


    }
}
