/**
 * In Class 08
 * Group Name- Samir Agrawal, Elijah Jesalva, Martin Miller.
 */
package com.example.inclass08;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends Activity implements OnClickListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "Au8oi6MBVG7dlFlvh6it2hhpt";
    private static final String TWITTER_SECRET = "h8DeSRt3Uh6hu7hHAyBVUquvV2MmcGHBp0LsKCKOnUJSNCtCSM";


//    private Button loginButton;
    private Button loginButtonRegular;
    private Button signUpButton;
    private EditText email;
    private EditText password;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        FacebookSdk.sdkInitialize(getApplicationContext());
        Parse.initialize(this, "dTUhGKdOP8M2KMIyxSUFE0Qa1j9fCqF7MbXbayR5", "VSgC26ctuUJbTD1Rk8giVADTJtdSUxHKBZKHTv9q");

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            Intent intent = new Intent(this, Messaging.class);
            startActivity(intent);
            finish();
        } else {
            // show the signup or login screen
            setContentView(R.layout.activity_login);

            loginButtonRegular = (Button) findViewById(R.id.buttonLogin);
            loginButtonRegular.setOnClickListener(this);
            signUpButton = (Button) findViewById(R.id.buttonCreateNewAccount);
            signUpButton.setOnClickListener(this);
            email = (EditText) findViewById(R.id.editTextEmail);
            password = (EditText) findViewById(R.id.editTextPassword);
        }

        // getActionBar().setTitle("Login");
    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password,
                new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            // Hooray! The user is logged in.
                            System.out.println("@@ login success");
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                            ;
                            Intent intent = new Intent(LoginActivity.this, Messaging.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Signup failed. Look at the ParseException to see
                            // what happened.
                            System.out.println("@@ Error: " + e.getMessage());
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            ;
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                String uname = email.getText().toString().trim();
                String psw = password.getText().toString().trim();
                if (TextUtils.isEmpty(uname) || TextUtils.isEmpty(psw)) {
                    Toast.makeText(LoginActivity.this, R.string.please_fill_all_the_details, Toast.LENGTH_LONG).show();
                    return;
                } else {
                    login(uname, psw);
                }
                break;
            case R.id.buttonCreateNewAccount:
                Intent intent = new Intent(this, SignUp.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent(this,Messaging.class);
        startActivity(intent);
        finish();
    }
}
