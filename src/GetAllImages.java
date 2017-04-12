package com.cs442.ssamant4.foodbasket;

/**
 * Created by suman on 09-11-2016.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class GetAllImages {

    public static String[] imageURLs;
    public static Bitmap[] bitmaps;
    public static String[] Names;
    public static String[] prices;
    public static String[] quantitys;
    public static String[] ids;
    public static String[] ingrediants;

    public static final String JSON_ARRAY="result";
    public static final String IMAGE_URL = "url";
    public static final String Names_data = "Name";
    public static final String id_data = "id";
    public static final String price_data = "Price";
    public static final String quantity_data = "Quantity";
    public static final String ingrediants_data = "Ingrediants";


    private String json;
    private JSONArray urls;

    public GetAllImages(String json){
        this.json = json;
        try {
            JSONObject jsonObject = new JSONObject(json);
            urls = jsonObject.getJSONArray(JSON_ARRAY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImage(JSONObject jo){
        URL url = null;
        Bitmap image = null;
        try {
            url = new URL(jo.getString(IMAGE_URL));
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void getAllImages() throws JSONException {
        bitmaps = new Bitmap[urls.length()];

        imageURLs = new String[urls.length()];
        Names = new String[urls.length()];
        prices = new String[urls.length()];
        quantitys = new String[urls.length()];
        ids = new String[urls.length()];
        ingrediants = new String[urls.length()];


        for(int i=0;i<urls.length();i++){
            imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_URL);
            Names[i]=urls.getJSONObject(i).getString(Names_data);
            prices[i]=urls.getJSONObject(i).getString(price_data);
            quantitys[i]=urls.getJSONObject(i).getString(quantity_data);
            ids[i]=urls.getJSONObject(i).getString(id_data);
            ingrediants[i]=urls.getJSONObject(i).getString(ingrediants_data);
            JSONObject jsonObject = urls.getJSONObject(i);
            bitmaps[i]=getImage(jsonObject);
        }
    }
}
