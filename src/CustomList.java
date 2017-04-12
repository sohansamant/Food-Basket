package com.cs442.ssamant4.foodbasket;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomList extends ArrayAdapter<String> {
    private String[] names;
    private Bitmap[] bitmaps;
    private String[] ingrediants;
    private Activity context;

    public CustomList(Activity context, String[] names, Bitmap[] bitmaps, String[] ingrediants) {
        super(context, R.layout.image_list_view, names);
        this.context = context;
        this.names= names;
        this.ingrediants=ingrediants;
        this.bitmaps= bitmaps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.image_list_view, null, true);
        TextView textViewURL = (TextView) listViewItem.findViewById(R.id.textViewURL);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageDownloaded);
        String sourceString = "<b>" + names[position] + "</b> " +"<br>"+"</br>"+ ingrediants[position];
      if (Build.VERSION.SDK_INT >= 24) {
           textViewURL.setText( Html.fromHtml(sourceString, 1)); // for 24 api and more
        } else {
           textViewURL.setText( Html.fromHtml(sourceString)); // or for older api
        }

        // textViewURL.setText(names[position]+"\n"+ingrediants[position]);
        image.setImageBitmap(Bitmap.createScaledBitmap(bitmaps[position],300,500,false));
        return  listViewItem;
    }
}
