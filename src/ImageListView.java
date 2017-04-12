package com.cs442.ssamant4.foodbasket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class  ImageListView extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    SharedPreferences pref;

    String  Item_type,item_category;
    public static final String GET_IMAGE_URL="http://10.0.2.2/PhotoUpload/getAllImages.php";

    public GetAllImages getAllImages;

    public static final String BITMAP_ID = "BITMAP_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list_view);
        pref=getSharedPreferences("User_Info",0);
        item_category=pref.getString("menu_type","BreakFast");
        //Toast.makeText(this,item_category,Toast.LENGTH_SHORT).show();
        Intent i=getIntent();
        Item_type=i.getStringExtra("Type");
        //Toast.makeText(this,Item_type,Toast.LENGTH_SHORT).show();
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getURLs();
    }

    private void getImages(){
        class GetImages extends AsyncTask<Void,Void,Void>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ImageListView.this,"Downloading images...","Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();
                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
                CustomList customList = new CustomList(ImageListView.this,GetAllImages.Names,GetAllImages.bitmaps,GetAllImages.ingrediants);
                listView.setAdapter(customList);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    getAllImages.getAllImages();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        GetImages getImages = new GetImages();
        getImages.execute();
    }

    private void getURLs() {
        class GetURLs extends AsyncTask<String,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ImageListView.this,"Loading...","Please Wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                getAllImages = new GetAllImages(s);
                getImages();
            }

            @Override
            protected String doInBackground(String... strings) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    //httpURLConnection.setDoInput(true);
                    OutputStream OS = con.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("item_type", "UTF-8") + "=" + URLEncoder.encode(Item_type, "UTF-8") + "&" +
                            URLEncoder.encode("item_category", "UTF-8") + "=" + URLEncoder.encode(item_category, "UTF-8") ;
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetURLs gu = new GetURLs();
        gu.execute(GET_IMAGE_URL);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Intent intent = new Intent(this, ViewFullImage.class);
        intent.putExtra(BITMAP_ID,i);
        intent.putExtra("Name_id",i);
        intent.putExtra("Price_id",i);
        intent.putExtra("Ingrediants_id",i);
        startActivity(intent);
    }
}