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
	private String jobs;
	private String appliedjobs;
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
	protected String doInBackground(String... params) {
		try {
		username = StaffTasks.getInfo(params[0], mContext);
		userpic = StaffTasks.getUserPic();
		connnum = StaffTasks.getFriendCount(params[0]);
		jobs = StaffTasks.jobDiscovery(mContext);
		publishProgress(10);
		appliedjobs = StaffTasks.viewAppliedJobs(mContext);
		publishProgress(20);
		String e = StaffTasks.getMessages(mContext);
		String g = StaffTasks.viewContracts(mContext);
		String h = StaffTasks.getAvailability(mContext);
		String i = StaffTasks.getProfDetails(mContext);
		String j = StaffTasks.getProposals(mContext);
		//Bitmap[] b = StaffTasks.getFriendReel(mContext);
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
		if (values[0] == 10) {
			DashboardActivity.parseResponse(jobs, 0);
			mButton.setText(connnum);
			uname.setText(username);
			pic.setImageBitmap(userpic);
		}if (values[0] == 20) {
			//CheckIn.parseResponse(appliedjobs);
		}

	}

	@Override
	protected void onPostExecute(String b) {
	}	

	public void onCancel(DialogInterface arg0) {
		this.cancel(true);

	}

}
