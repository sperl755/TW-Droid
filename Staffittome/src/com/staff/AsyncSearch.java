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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class AsyncSearch extends AsyncTask<String, Integer, String> implements
		OnCancelListener {
	private ProgressDialog progressDialog;
	private final Context mContext;
	private String mresults;


	public AsyncSearch(String results, Context context) {
		super();
		mContext = context;
		mresults = results;
		results = "";
		
	}

	protected String doInBackground(String... params) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		publishProgress(5000);
		String s = SearchMaps.search(params[0],params[1],params[2],params[3],mContext);
		publishProgress(10000);
		return s;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// initialize progress bar
		//((Activity) mContext).getWindow().setFeatureInt(
		//		Window.FEATURE_PROGRESS, 0);

		// start a status dialog
		//progressDialog = new ProgressDialog(mContext);
		//progressDialog = ProgressDialog.show(mContext, "Please be patient !",
		//		"Doing your search", true, true, this);

	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		((Activity) mContext).getWindow().setFeatureInt(
				Window.FEATURE_PROGRESS, values[0]);
	}

	@Override
	protected void onPostExecute(String b) {
		//progressDialog.dismiss();
		mresults = b;
	}

	public void onCancel(DialogInterface arg0) {
		// we UI has received the order to cancel, let's notify the worker
		// thread to cancel NOW !
		this.cancel(true);

	}

}
