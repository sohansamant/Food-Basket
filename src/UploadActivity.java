package com.cs442.ssamant4.foodbasket;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public static final String UPLOAD_URL = "http://10.0.2.2/PhotoUpload/upload.php";
    public static final String UPLOAD_KEY = "image";
    String ItemName,MenuName;
    int ItemPrice;
    int ItemQuantity;
    String user_type;
    String ItemIngrediants;
    String Upload_Item_Name="item_name";
    String Upload_Item_Price="item_price";
    String Upload_Item_Quantity="item_quantity";
    String Upload_Item_Ingrediants="item_ingrediants";
    String Upload_Menu_Type="menu_type";
    String Upload_Hotel_Type="hotel_type";
    String hotel_type;



    private int PICK_IMAGE_REQUEST = 1;

    private Button buttonChoose;
    private Button buttonUpload;
    private Button buttonRestaurantfood;
    private Button buttonHomefood;
    private EditText etItemName,etItemPrice,etItemQuantity,etIngrediants;
    private Spinner spMenutype;

    private ImageView imageView;

    private Bitmap bitmap;

    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        pref=getSharedPreferences("User_Info",0);
        user_type=pref.getString("user_type","user");
        //Toast.makeText(this,user_type,Toast.LENGTH_SHORT).show();
        Log.i("My message",user_type);
        if(user_type.equals("vendor"))
        {
            Log.i("My Message","I came inside if of upload activity");
            hotel_type="home";
        }
        else
        {
            Log.i("My Message","I came inside else block of upload activity");
            hotel_type="restaurant";
        }
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
       // buttonRestaurantfood = (Button) findViewById(R.id.Restaurant_food);
       // buttonHomefood=(Button)findViewById(R.id.Home_food);
        etItemName=(EditText)findViewById(R.id.etItemName);
        etItemPrice=(EditText)findViewById(R.id.etItemPrice);
        etItemQuantity=(EditText)findViewById(R.id.etItemQuantity);
        etIngrediants=(EditText)findViewById(R.id.etIngrediants);
        spMenutype=(Spinner)findViewById(R.id.spinner);

        imageView = (ImageView) findViewById(R.id.imageView2);
        System.out.print("on activity result");
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        buttonRestaurantfood.setOnClickListener(this);
        buttonHomefood.setOnClickListener(this);
    }
    private void showFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        ItemName=etItemName.getText().toString();
        ItemPrice=Integer.parseInt(etItemPrice.getText().toString());
        ItemQuantity=Integer.parseInt(etItemQuantity.getText().toString());
        ItemIngrediants=etIngrediants.getText().toString();
        MenuName=spMenutype.getSelectedItem().toString();
        class UploadImage extends AsyncTask<Bitmap,Void,String>{

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();


            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                loading = ProgressDialog.show(UploadActivity.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_KEY, uploadImage);
                data.put(Upload_Item_Name, ItemName);
                data.put(Upload_Item_Price, Integer.toString(ItemPrice));
                data.put(Upload_Item_Quantity, Integer.toString(ItemQuantity));
                data.put(Upload_Item_Ingrediants,ItemIngrediants);
                data.put(Upload_Menu_Type,MenuName);
                data.put(Upload_Hotel_Type,hotel_type);


                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }

        if(v == buttonUpload){
            Log.i("my message","inside upload");
            uploadImage();
        }

        if(v == buttonRestaurantfood){
            viewImage("restaurant");
        }
        if(v== buttonHomefood){
            viewImage("home");
        }
    }

    private void viewImage(String s) {
        Intent i = new Intent(this, ImageListView.class);
        i.putExtra("Type",s);
        startActivity(i);
    }
}
