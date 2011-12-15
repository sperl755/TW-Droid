package com.staff;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.staff.SYourself.ResponseReceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class CheckIn extends Activity {
	private ImageView user_pic;
	private Chronometer timein;
	private TextView uname;
	private Button connectionsButton;
	private TextView myJobsText;
	private static TextView myJobsNum;
	private static TextView myJobTitle1;
	private static TextView myJobDesc1;
	private ImageButton myJobManual1;
	private ImageButton myJobCheckIn1;
	private static TextView myJobTitle2;
	private static TextView myJobDesc2;
	private ImageButton myJobCheckIn2;
	private ImageButton myJobManual2;
	private TextView jobApplicationText;
	private static TextView jobApplicationsNum;
	private static TextView jobAppTitle1;
	private static TextView jobAppDesc1;
	private TextView jobAppStatus1;
	private ImageButton jobAppButton1;
	private static TextView jobAppTitle2;
	private static TextView jobAppDesc2=null;
	private TextView jobAppStatus2;
	private ImageButton jobAppButton2;
	private ImageButton availableOn;
	private ImageButton availableOff;
	private String key;
	private String user_id;
	private String facebook_uid;
	private String name;
    private ResponseReceiver receiver;
    private ProgressDialog progDailog;
    private FrameLayout framecheck;
    private static final int PROGRESS = 0x1;
    private final Handler uiHandler=new Handler();
    private boolean isUpdateRequired=false;
    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private LinearLayout checkproglin;
    private String done;
    private int counter;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkin);
        /*
		 * SETTING TYPEFACES GOD DAMN 
		 */
      
		//Declaring Fonts
		Typeface hm = Typeface.createFromAsset(getAssets(),"fonts/hm.ttf");
	    Typeface hb = Typeface.createFromAsset(getAssets(),"fonts/hb.ttf");
        uname=(TextView)this.findViewById(R.id.uname);
        uname.setTypeface(hm);
        connectionsButton=(Button)this.findViewById(R.id.connectionsButton);
        connectionsButton.setTypeface(hm);
        myJobsText=(TextView)this.findViewById(R.id.myJobsText);
        myJobsText.setTypeface(hb);
        myJobsNum=(TextView)this.findViewById(R.id.myJobsNum);
        myJobsNum.setTypeface(hm);
        myJobTitle1=(TextView)this.findViewById(R.id.myJobTitle1);
        myJobTitle1.setTypeface(hm);
        myJobDesc1=(TextView)this.findViewById(R.id.myJobDesc1);
        myJobDesc1.setTypeface(hm);
        myJobManual1=(ImageButton)this.findViewById(R.id.myJobManual1);
        myJobCheckIn1=(ImageButton)this.findViewById(R.id.myJobCheckIn1);
        myJobTitle2=(TextView)this.findViewById(R.id.myJobTitle2);
        myJobTitle2.setTypeface(hm);
        myJobDesc2=(TextView)this.findViewById(R.id.myJobDesc2);
        myJobDesc2.setTypeface(hm);
        myJobCheckIn2=(ImageButton)this.findViewById(R.id.myJobCheckIn2);
        myJobManual2=(ImageButton)this.findViewById(R.id.myJobManual2);
        jobApplicationText=(TextView)this.findViewById(R.id.jobApplicationsText);
        jobApplicationText.setTypeface(hb);
        jobApplicationsNum=(TextView)this.findViewById(R.id.jobApplicationsNum);
        jobApplicationsNum.setTypeface(hm);
        jobAppTitle1=(TextView)this.findViewById(R.id.jobAppTitle1);
        jobAppTitle1.setTypeface(hm);
        jobAppDesc1=(TextView)this.findViewById(R.id.jobAppDesc1);
        jobAppDesc1.setTypeface(hm);
        jobAppStatus1=(TextView)this.findViewById(R.id.jobAppStatus1);
        jobAppStatus1.setTypeface(hm);
        jobAppButton1=(ImageButton)this.findViewById(R.id.jobAppButton1);
        jobAppTitle2=(TextView)this.findViewById(R.id.jobAppTitle2);
        jobAppTitle2.setTypeface(hm);
        jobAppDesc2=(TextView)this.findViewById(R.id.jobAppDesc2);
        jobAppDesc2.setTypeface(hm);
        jobAppStatus2=(TextView)this.findViewById(R.id.jobAppStatus2);
        jobAppStatus2.setTypeface(hm);
        jobAppButton2=(ImageButton)this.findViewById(R.id.jobAppButton2);
        user_pic = (ImageView)this.findViewById(R.id.userpic);
        user_pic.setScaleType( ScaleType.CENTER_CROP );
      
        availableOn = (ImageButton)this.findViewById(R.id.availableOn);
        availableOff = (ImageButton)this.findViewById(R.id.availableOff);
    	  availableOff.setVisibility(View.INVISIBLE);
  		SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(CheckIn.this); 
        key = prefs.getString("staffkey", null);
        user_id = prefs.getString("staffuser", null);
    
        connectionsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(CheckIn.this, StaffToFriend.class));
			}
		});

        
        
        jobAppButton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "This should take you to the job apply page and set the status", 0).show();
			}
		});
        jobAppButton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "This should take you to the job apply page and set the status", 0).show();
			}
		});

        /*
         * grabbing some stuff from PREFS
         */
        
	    facebook_uid = prefs.getString("facebook_uid", null);
	    name = prefs.getString("name", null);
	    uname.setText(name);
	    
	    /*
	     * Going to grab more stuff from TabMain global vars
	     */
	    checkproglin = (LinearLayout)this.findViewById(R.id.checkproglin);
	      framecheck = (FrameLayout)this.findViewById(R.id.checkframe);
	      mProgress = (ProgressBar) findViewById(R.id.checkProg);
	    
	   
	    
	    

	}
	  @Override
	    protected void onResume() {
	        super.onResume();
	        done = null;
	        sendIntent();
	        checkLoading();
	  }
	  
	  public void sendIntent(){

	        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
	        filter.addCategory(Intent.CATEGORY_DEFAULT);
	        receiver = new ResponseReceiver();
	        registerReceiver(receiver, filter);
	        Intent msgIntent = new Intent(this, StaffService.class);
		      msgIntent.putExtra(StaffService.PARAM_IN_MSG, "checkIn");
		      startService(msgIntent);      
	  }
	  
	  public void checkLoading(){
	      framecheck.setVisibility(View.INVISIBLE);
    	  checkproglin.setVisibility(View.VISIBLE);
		   try{
		          new Thread(){
		              public void run() {
		                  initializeApp();
		                  uiHandler.post( new Runnable(){
		                      @Override
		                      public void run() {
		                          if(isUpdateRequired){
		                          }else{
		                        	  user_pic.setImageBitmap(TabMain.userpic);
		                        	  connectionsButton.setText(TabMain.connnum);
		                        	  checkproglin.setVisibility(View.GONE);
		                        	  framecheck.setVisibility(View.VISIBLE);
		                          }
		                      }
		                  } );
		              }
		              public void initializeApp(){
		            	  while (done==null) {
		            		  try {
								sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
		            	  }
		              }
		      }.start();
		      }catch (Exception e) {}
	  }
	
	  public class ResponseReceiver extends BroadcastReceiver {
	        public static final String ACTION_RESP = "com.staff.intent.action.MESSAGE_PROCESSED";
	        @Override
	        public void onReceive(Context context, Intent intent) {
	        	//CheckIn check = new CheckIn();
	            // Update UI, new "message" processed by SimpleIntentService
	        	
	        	/*
	        	 * RECIEVE EXTRAS FROM INTENT 
	        	 */
	        	
	        	Bundle extras = intent.getExtras();

	        	//Get & Set User applied jobs
	        	if (extras.getString("appliedjobs")!=null) {
	        		parseResponse(extras.getString("appliedjobs")); 
	        	}
	        	// Get & Set user contracts
	        	if (extras.getString("contracts")!=null){
	        		parseContracts(extras.getString("contracts"));
	        		done = "done";
	        	}

	        }
	        
	    }
	  
	public static void parseContracts(String stuff){
		JSONArray jresult;
        JSONArray jsearch;
		JSONObject json_data = null;
		int count=0;
        Log.d("TAG", "STUFF WITHIN PARSE CONTRACTS "+stuff);

		  JSONTokener tokener = new JSONTokener(stuff);
        try {
			jresult = new JSONArray(tokener);

		  for (int i=0; i<jresult.length(); i++) { //Runs through the Job postings for as long as the array is

	  json_data = jresult.getJSONObject(i); //For each json object
	  String title = json_data.getString("title");
	  String employer_name = json_data.getString("employer_name");
	  setContracts(title, employer_name, i);
	  count++;
    }        
		  myJobsNum.setText(Integer.toString(count)+" jobs");
	} catch (JSONException e) {
		e.printStackTrace();
	}
}
	
	private static void setContracts(String title, String employer_name, int i) {
		if (i == 0) {
			myJobTitle1.setText(title);
			myJobDesc1.setText(employer_name);
			} else if (i == 1) {
			myJobTitle2.setText(title);
			myJobDesc2.setText(employer_name);
			}
			else if (i>2) {
				Log.d("TAG", "Need to make more rows for the contracts you have");
			}
				
			
	}
	public static Bitmap downloadBitmap(String url) {
	
	Bitmap bip=null;
  	URL url1 = null;
  	try {
		url1 = new URL(url);
	bip = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return bip;
	}
  	
	public static void parseResponse(String stuff){
		JSONArray jresult;
        JSONArray jsearch;
		JSONObject json_data = null;
		int count = 0;
		  JSONTokener tokener = new JSONTokener(stuff);
        try {
			jresult = new JSONArray(tokener);
	
		  
        Log.d("TAG", "IN PARSE RESPONSE");
        //jsearch = jresult.getJSONArray("");

		  for (int i=0; i<jresult.length(); i++) { //Runs through the Job postings for as long as the array is

  	  json_data = jresult.getJSONObject(i); //For each json object
  	  String title = json_data.getString("title");
  	  String applied_at = json_data.getString("applied_at");
  	  String job_id = json_data.getString("job_id");
  	  String company_name = json_data.getString("company_name");
  	  Log.d("TAG",title);
  	  Log.d("TAG",applied_at);
  	  Log.d("TAG",job_id);
  	  Log.d("TAG",Integer.toString(i));
  	 count++;
  	  setTexts(title, company_name, job_id, i);
		  }
		  jobApplicationsNum.setText(Integer.toString(count)+" jobs");
    	} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	public static void setTexts(String title, String company_name, String job_id, int i) {
		if (i == 0) {
		jobAppTitle1.setText(title);
		jobAppDesc1.setText(company_name);
		} else if (i == 1) {
		jobAppTitle2.setText(title);
		jobAppDesc2.setText(company_name);
		}
		else if (i>2) {
			Log.d("TAG", "Need to make more rows for the jobs you have applied to");
		}
			
		}
}
