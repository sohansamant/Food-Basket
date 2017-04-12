package com.cs442.ssamant4.foodbasket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
/**
 * Created by prabeesh on 7/14/2015.
 */
public class
BackgroundTask extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;
    SharedPreferences login_info;
    SharedPreferences mpref;
    SharedPreferences.Editor editor;
    String login_name,login_pass;
    public AsyncResponse delegate = null;


    String response="Successful";
    BackgroundTask(Context ctx)
    {
        this.ctx =ctx;
    }
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        login_info = ctx.getSharedPreferences("User_Info", Context.MODE_PRIVATE);
        mpref=ctx.getSharedPreferences("User_Info",0);
        editor=login_info.edit();

    }
    @Override
    protected String doInBackground(String... params) {
        String reg_url = "http://10.0.2.2/webapp/register.php";
        String login_url = "http://10.0.2.2/webapp/login.php";
        String checkout_url = "http://10.0.2.2/webapp/Checkout.php";
        String method = params[0];

        if (method.equals("register"))
        {
            alertDialog.setTitle("SignUp Information....");
            String name = params[1];
            String user_pass = params[2];
            String user_email = params[3];
            String user_address=params[4];
            String user_type="user";
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                //httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8") + "&" +
                        URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(user_email, "UTF-8") + "&" +
                        URLEncoder.encode("user_address", "UTF-8") + "=" + URLEncoder.encode(user_address, "UTF-8")  + "&" +
                        URLEncoder.encode("user_type", "UTF-8") + "=" + URLEncoder.encode(user_type, "UTF-8") ;

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
               /* InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();*/
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                // return response;
                return "Registration Success...";
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        else if(method.equals("login"))
        {
            alertDialog.setTitle("Login Information....");
            login_name = params[1];
            login_pass = params[2];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("login_name","UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
                        URLEncoder.encode("login_pass","UTF-8")+"="+URLEncoder.encode(login_pass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                String line = "";
                String response="";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line.toString().trim();
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("Checkout"))
        {
            alertDialog.setTitle("Login Information....");
            String login_name = mpref.getString("user_name"," ");
            String login_pass = mpref.getString("user_pass"," ");
            try {
                URL url = new URL(checkout_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("login_name","UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
                        URLEncoder.encode("login_pass","UTF-8")+"="+URLEncoder.encode(login_pass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                String line = "";
                String response="";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line.toString().trim();
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String result) {
       /* if(result.equals("Registration Success..."))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }*/
        Log.i("My message","inside post execute");
        if(result.trim().equalsIgnoreCase("user"))
        {
            editor.putString("user_name", login_name);
            editor.putString("user_pass",login_pass);
            editor.putString("user_type",result);
            editor.apply();


            Intent i;
            i = new Intent(ctx,MenuCategories.class);
            ctx.startActivity(i);

        }
        else if(result.trim().equalsIgnoreCase("admin") || result.trim().equalsIgnoreCase("vendor"))
        {
            editor.putString("user_name", login_name);
            editor.putString("user_pass",login_pass);
            editor.putString("user_type",result);
            editor.apply();

         Intent   i = new Intent(ctx,UploadActivity.class);
            ctx.startActivity(i);
            //i = new Intent(ctx,trail2.class);
            //ctx.startActivity(i);

        }
        else if(result.trim().equalsIgnoreCase("Username or Password is incorrect"))
        {
            alertDialog.setMessage(result);
            alertDialog.show();


        }

        //alertDialog.setMessage(result);
        //alertDialog.show();

        //  alertDialog.setMessage(result);
        //alertDialog.show();
       /* else
        {
            alertDialog.setMessage(result);
            alertDialog.show();
        }*/
    }
}

