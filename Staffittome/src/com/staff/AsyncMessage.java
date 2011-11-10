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

public class AsyncMessage extends AsyncTask<String, Integer, Void> implements
		OnCancelListener {
	//private ProgressDialog progressDialog;
	private final Context mContext;

	public AsyncMessage(Context context) {
		super();
		mContext = context;
	}

	@Override
	protected Void doInBackground(String... parameters) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		publishProgress(5000);
		String messagedata = MessageInbox.getMessages(mContext);
		publishProgress(10000);
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// initialize progress bar
		((Activity) mContext).getWindow().setFeatureInt(
				Window.FEATURE_PROGRESS, 0);

		// start a status dialog
		//progressDialog = ProgressDialog.show(mContext, "Please be patient !",
		//		"Getting Messages", true, true, this);

	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		((Activity) mContext).getWindow().setFeatureInt(
				Window.FEATURE_PROGRESS, values[0]);
	}
	@Override
	protected void onPostExecute(Void v) {
		MessageInbox.parseMessage();
		//progressDialog.dismiss();
		
	}

	public void onCancel(DialogInterface arg0) {
		this.cancel(true);

	}//

}
