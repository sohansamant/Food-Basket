package com.cs442.ssamant4.foodbasket;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.facebook.FacebookSdk;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{
    boolean flag;

   // Button skip;
    SignInButton signInButton; //Google login button
    TextView mStatusTextView;
    LoginButton loginButton; //Facebook login Button
    LoginManager loginManager;
    Button foodbasket_loginButton;


    CallbackManager callbackManager; //for FB integration

    GoogleApiClient mGoogleApiClient;
    GoogleSignInOptions gso;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        foodbasket_loginButton=(Button) findViewById(R.id.foodbasket_loginButton);

        super.onCreate(savedInstanceState);

        /* Facebook integration */
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();

        AppEventsLogger.activateApp(this);

        loginButton=(LoginButton) findViewById(R.id.login_button);//FB button initialized
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() //Fb callback action
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                startActivity(new Intent(MainActivity.this, MenuCategories.class)); // calling the main menu screen after successful FB login
                finish();
            }

            @Override
            public void onCancel()
            {
                // App code
            }

            @Override
            public void onError(FacebookException exception)
            {
                // App code
            }
        });

        //skip=(Button) findViewById(R.id.btn_skip);
        mStatusTextView=(TextView) findViewById(R.id.mStatusTextView);

        signInButton=(SignInButton) findViewById(R.id.sign_in_google);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_DARK);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // FB on click lisgtner
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() //Google onClick listner
        {
            @Override
            public void onClick(View v)
            {

                switch (v.getId())
                {
                    case R.id.sign_in_google:
                       signIn();
                        break;

                }

            }
        });

       /* skip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i1=new Intent(getApplicationContext(), signup.class);
                startActivity(i1);
            }
        }); */
    }

    public void openLogin(View v)
    {
        Intent login_intent = new Intent (getApplicationContext(), LoginActivity.class);
        startActivity(login_intent);
    }

    // [START signIn]
    private void signIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    //{END SignIn]

    //Retriving sign in result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        //Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount acct = result.getSignInAccount();
            handleSignInResult(result);
        }
    }

    // [START handleSignInResult]
   private void handleSignInResult(GoogleSignInResult result)
   {
    Log.d(TAG, "handleSignInResult:" + result.isSuccess());
       if (result.isSuccess())
        {
            // Signed in successfully, show authenticated UI.
          GoogleSignInAccount acct = result.getSignInAccount();
            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            updateUI(true);


        } else {
           //Signed out, show unauthenticated UI.
          updateUI(false);
       }
    }
    // [END handleSignInResult]

    private void updateUI(boolean signedIn)
    {
      if (signedIn)
      {
          startActivity(new Intent(MainActivity.this, MenuCategories.class));
          finish();
           findViewById(R.id.sign_in_google);
          //  findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
      }
      else
        {
          // mStatusTextView.setText("signed out");

            findViewById(R.id.sign_in_google).setVisibility(View.VISIBLE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
    }
}
