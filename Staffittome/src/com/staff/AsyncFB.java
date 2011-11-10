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

public class AsyncFB extends AsyncTask<String, Integer, Bitmap> implements
		OnCancelListener {
	private final ImageView mImageView;
	private final Context mContext;

	public AsyncFB(ImageView imageView, Context context) {
		super();
		mImageView = imageView;
		mContext = context;
	}

	@Override
	protected Bitmap doInBackground(String... parameters) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		publishProgress(5000);
		Bitmap b = DashboardActivity.downloadBitmap(parameters[0]);
		publishProgress(10000);
		return b;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// initialize progress bar
		((Activity) mContext).getWindow().setFeatureInt(
				Window.FEATURE_PROGRESS, 0);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		((Activity) mContext).getWindow().setFeatureInt(
				Window.FEATURE_PROGRESS, values[0]);
	}

	@Override
	protected void onPostExecute(Bitmap b) {
		mImageView.setImageBitmap(b);
	}

	public void onCancel(DialogInterface arg0) {
		// we UI has received the order to cancel, let's notify the worker
		// thread to cancel NOW !
		this.cancel(true);

	}

}
