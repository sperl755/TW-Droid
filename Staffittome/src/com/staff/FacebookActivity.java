package com.staff;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import com.facebook.android.*;
import com.facebook.android.Facebook.*;

public class FacebookActivity extends Activity {
    Facebook facebook = new Facebook("187212574660004");
    public String token;
    public String[] permissions = new String[] { "email", "publish_stream", "offline_access"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(FacebookActivity.this); 
        String access_token = prefs.getString("access_token", null); 
        Long expires = prefs.getLong("access_expires", -1);


        if (access_token != null && expires != -1)
        {
        	Log.d("TAG", "Facebook Authentification Complete");
			startActivity(new Intent(FacebookActivity.this, TabMain.class));
            facebook.setAccessToken(access_token);
            facebook.setAccessExpires(expires);
           
        }


        if (!facebook.isSessionValid())
        {
            facebook.authorize(FacebookActivity.this, permissions, new DialogListener() {
            public void onComplete(Bundle values) {
            	Log.d("TAG", "Facebook Authentification Complete");
            	saveToken();
				startActivity(new Intent(FacebookActivity.this, TabMain.class));
            }
            
            public void onFacebookError(FacebookError error) {}

            
            public void onError(DialogError e) {}

            
            public void onCancel() {}
        });
    }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }
    public void saveToken(){
    	token = facebook.getAccessToken();
    	long token_expires = facebook.getAccessExpires();

    	SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(FacebookActivity.this);

    	prefs.edit().putLong("access_expires", token_expires).commit();

    	prefs.edit().putString("access_token", token).commit();
    	Log.d("TAG", "The facebook token is: "+token);
    }
    }

