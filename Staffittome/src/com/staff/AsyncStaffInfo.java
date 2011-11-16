package com.staff;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class AsyncStaffInfo extends AsyncTask<String, Integer, String> implements
		OnCancelListener {
	private final Context mContext;
	private String connnum; 
	private Bitmap userpic;
	private final Button mButton;
	private final TextView uname;
	private final ImageView pic;
	private String username;


	public AsyncStaffInfo( Context context, Button button, TextView text, ImageView imageview) {
		super();
		mContext = context;
		mButton = button;
		uname = text; 
		pic = imageview;
	}
	@Override
	/*
	 * This class will work along staffservice, yet it will only be a one time instance use, it will log in, fetch info, and instanstiate the StaffTasks class
	 * StaffService will take care of all the other functions that will need reloading asynchronously once the activities are loaded individualy.
	 */
	
	protected String doInBackground(String... params) {
		try {
		username = StaffTasks.getInfo(params[0], mContext);
		publishProgress(30);
		userpic = StaffTasks.getUserPic();
		connnum = StaffTasks.getFriendCount(params[0]);
		publishProgress(90);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (FacebookError e) {
				e.printStackTrace();
			}		
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		 //initialize progress bar
		((Activity) mContext).getWindow().setFeatureInt(
				Window.FEATURE_PROGRESS, 0);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		((Activity) mContext).getWindow().setFeatureInt(
				Window.FEATURE_PROGRESS, values[0]);
		if (values[0] == 30) {
		}if (values[0] == 90) {
			// Do nothing
		}

	}

	@Override
	protected void onPostExecute(String b) {
		mButton.setText(connnum);
		pic.setImageBitmap(userpic);
		uname.setText(username);
	}	

	public void onCancel(DialogInterface arg0) {
		this.cancel(true);

	}

}
