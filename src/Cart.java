package com.cs442.ssamant4.foodbasket;

import android.content.Intent;
import android.support.v4.text.TextDirectionHeuristicCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Cart extends AppCompatActivity
{

    TextView cart_title;
    TextView cart_price;
    Button checkout;

    String cart_product_title;
    int cart_cost;
    String cart_product_price;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cart_title=(TextView) findViewById(R.id.Cart_Title);
        cart_price=(TextView) findViewById(R.id.Cart_Price);


        Bundle extras = getIntent().getExtras();

        cart_product_title= extras.getString("product name");

        cart_cost= extras.getInt("price");

        cart_product_price=String.valueOf(cart_cost);


        cart_title.setText("Your Product: "+cart_product_title);
        cart_price.setText("Total Price of your order is $"+cart_product_price);

    }
}
