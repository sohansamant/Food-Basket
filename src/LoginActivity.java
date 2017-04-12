package com.cs442.ssamant4.foodbasket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity
{
    EditText ET_NAME,ET_PASS;
    String login_name,login_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ET_NAME = (EditText)findViewById(R.id.user_name);
        ET_PASS = (EditText)findViewById(R.id.user_pass);
    }

    public void userReg(View view)
    {
        startActivity(new Intent(this,Register.class));
        //Toast.makeText(this,"Registration Successful you can now login!!",Toast.LENGTH_LONG).show();
    }
    public void userLogin(View view)
    {
        login_name = ET_NAME.getText().toString();
        login_pass = ET_PASS.getText().toString();
        String method = "login";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,login_name,login_pass);
    }
}
