package com.cs442.ssamant4.foodbasket;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewFullImage extends AppCompatActivity
{
    private ImageView imageView;
    private TextView item_name;
    private TextView item_price;
    private TextView item_ingrediants;

    Button plus;
    Button minus;
    Button cart;
    int q=0;
    TextView quantity;

    Handling_items handling_items;


    String product_name; // to be used in cart screen
    String cost;  // to be used in cart screen
    String quant; // to be used in cart screen
    int price=0; // to be used in cart screen
    String s1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_image);
        Intent intent = getIntent();
        int b = intent.getIntExtra(ImageListView.BITMAP_ID,0);
        int  n= intent.getIntExtra("Name_id",0);
        int p = intent.getIntExtra("Price_id",0);
        final int in = intent.getIntExtra("Ingrediants_id",0);

        plus=(Button) findViewById(R.id.plus);
        minus=(Button) findViewById(R.id.minus);
        cart=(Button) findViewById(R.id.add_to_cart);

        quantity=(TextView) findViewById(R.id.quantity_number);

        imageView = (ImageView) findViewById(R.id.imageViewFull);
        item_name=(TextView)findViewById(R.id.Item_name);
        item_price=(TextView)findViewById(R.id.Item_price);
        item_ingrediants=(TextView)findViewById(R.id.Item_ingrediants);

        imageView.setImageBitmap(GetAllImages.bitmaps[b]);
        item_name.setText(GetAllImages.Names[n]);
        item_ingrediants.setText(GetAllImages.ingrediants[in]);
        item_price.setText(GetAllImages.prices[p]);

        quant=quantity.getText().toString(); //quantity converted in string
        q=Integer.parseInt(quant);  // quantity converted in integer

        product_name=item_name.getText().toString(); // name to be used in cart screen

        cost=item_price.getText().toString(); // used for sending it to cart screen
        price=Integer.parseInt(cost); // price converted to int



        plus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                q++;
                price=price*q;
                quant=String.valueOf(q);
                quantity.setText(quant);
            }
        });

        minus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(q>0)
                {
                    q--;
                    quant=String.valueOf(q);
                    quantity.setText(quant);
                }
                else
                {
                    quantity.setText(quant);
                }
            }
        });

        cart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                price=price*q;

                /*AlertDialog.Builder builder= new AlertDialog.Builder(ViewFullImage.this);

                builder.setTitle("");
                builder.setMessage("Your Food added to Bakset");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

                builder.show(); */


                Intent intent=new Intent(ViewFullImage.this, Cart.class);
                intent.putExtra("product name", product_name);
                intent.putExtra("price",price);
                startActivity(intent);


            }
        });

    }

    public void shareText(View view)
    {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "Your shearing message goes here";
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "So Awesome Food at very afforable price such as "+product_name;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Food Basket! Food for all the hoogers ");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
