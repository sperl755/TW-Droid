package com.staff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.*;
import com.facebook.android.Facebook.*;
import com.staff.DashboardActivity.ResponseReceiver;

public class HomePage extends Activity {
    private JSONObject json_data = null;
	private ImageView user_picture = null;
	private Button logout;
	private String name;
	private String email = "N/A";
	private String first_name = "N/A";
	private String last_name = "N/A";
	private String birthday = "N/A";
	private String locale = "N/A";
	private String gender = "N/A";
	private String facebook_uid;
	private String facebook_session_key;
	private String id;
	private String link = "N/A";
	private String staffkey;
	FacebookActivity fb1 = new FacebookActivity();
    Facebook facebook = new Facebook("187212574660004");
    private ImageButton availableOn;
    private ImageButton availableOff;
    private String key;
    private String access_token;
    private String staffuser;
    private int numfriends;
    private Button connectionsButton;
	private TextView uname;
	private TextView unameSummaryText;
	private TextView unameSummaryValue;
	private TextView unameJobSummaryText;
	private TextView unameJobSkillJob1;
	private TextView unameJobSkillRate1;
	private ImageButton unameJobSkillJob1Button;
	private TextView unameJobSkillJob2;
	private TextView unameJobSkillRate2;
	private ImageButton unameJobSkillJob2Button;
	private TextView unameExperienceText;
	private TextView unameJobExperience1;
	private TextView unameJobExperienceCompany1;
	private ImageButton unameJobExperience1Button;
	private TextView unameJobExperience2;
	private TextView unameEducation1;
	private TextView unameEducationSchool1;
	private TextView unameEducation2;
	private TextView unameEducationSchool2;
	private TextView unameJobExperienceCompany2;
	private ImageButton unameJobExpereince2Button;
	private String profdetails;
    private ResponseReceiver receiver;
    private ProgressDialog progDailog;
    private FrameLayout frameprof;
    private static final int PROGRESS = 0x1;
    private final Handler uiHandler=new Handler();
    private boolean isUpdateRequired=false;
    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private LinearLayout profproglin;
    private String done;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile);
        /*
         * GETTING STUFF FOR TYPEFACES/FUNCTIONALITY 
         */
        //Declaring Fonts
        Typeface hm = Typeface.createFromAsset(getAssets(),
        "fonts/hm.ttf");
        Typeface hb = Typeface.createFromAsset(getAssets(),
        "fonts/hb.ttf");
        
        connectionsButton=(Button)this.findViewById(R.id.connectionsButton);
        connectionsButton.setTypeface(hm);
        uname=(TextView)this.findViewById(R.id.uname);
        uname.setTypeface(hm);
        unameSummaryValue=(TextView)this.findViewById(R.id.unameSummaryValue);
        unameSummaryValue.setTypeface(hm);
        unameJobSummaryText=(TextView)this.findViewById(R.id.unameJobSummaryText);
        unameJobSummaryText.setTypeface(hb);
        unameSummaryText=(TextView)this.findViewById(R.id.unameSummaryText);
        unameSummaryText.setTypeface(hb);
        unameJobSkillJob1=(TextView)this.findViewById(R.id.unameJobSkillJob1);
        unameJobSkillJob1.setTypeface(hm);
        unameJobSkillJob1=(TextView)this.findViewById(R.id.unameJobSkillJob1);
        unameJobSkillJob1.setTypeface(hm);
        unameJobSkillRate1=(TextView)this.findViewById(R.id.unameJobSkillRate1);
        unameJobSkillRate1.setTypeface(hm);
        unameJobSkillJob1Button=(ImageButton)this.findViewById(R.id.unameJobSkillJob1Button);
        unameJobSkillJob2=(TextView)this.findViewById(R.id.unameJobSkillJob2);
        unameJobSkillJob2.setTypeface(hm);
        unameJobSkillRate2=(TextView)this.findViewById(R.id.unameJobSkillRate2);
        unameExperienceText=(TextView)this.findViewById(R.id.unameExperienceText);
        unameExperienceText.setTypeface(hb);
        unameJobExperience1=(TextView)this.findViewById(R.id.unameJobExperience1);
        unameJobExperience1.setTypeface(hm);
        unameJobExperienceCompany1=(TextView)this.findViewById(R.id.unameJobExperienceCompany1);
        unameJobExperienceCompany1.setTypeface(hm);
        unameJobExperience1Button=(ImageButton)this.findViewById(R.id.unameJobExperience1Button);
        unameJobExperience2=(TextView)this.findViewById(R.id.unameJobExperience2);
        unameJobExperience2.setTypeface(hm);
        unameJobExperienceCompany2=(TextView)this.findViewById(R.id.unameJobExperienceCompany2);
        unameJobExperienceCompany2.setTypeface(hm);
        unameJobExpereince2Button=(ImageButton)this.findViewById(R.id.unameJobExperience2Button);
        unameJobExperienceCompany2.setTypeface(hm);
        unameJobExpereince2Button=(ImageButton)this.findViewById(R.id.unameJobExperience2Button);
        user_picture=(ImageView)this.findViewById(R.id.userpic);
        user_picture.setScaleType( ScaleType.CENTER_CROP );
        unameEducation1 = (TextView)this.findViewById(R.id.unameEducation1);
        unameEducation1.setTypeface(hm);
        unameEducation2 = (TextView)this.findViewById(R.id.unameEducation2);
        unameEducation2.setTypeface(hm);
        unameEducationSchool1 = (TextView)this.findViewById(R.id.unameEducationSchool1);
        unameEducationSchool1.setTypeface(hm);
        unameEducationSchool2 = (TextView)this.findViewById(R.id.unameEducationSchool2);
        unameEducationSchool2.setTypeface(hm);


        
        /*
         * GETTING KEYS
         */
  		SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(HomePage.this); 
        final DashboardActivity dash = new DashboardActivity();

        access_token = prefs.getString("access_token", null);
	    name = prefs.getString("name", null);
	    uname.setText(name);

	    unameSummaryText.setText(name+"'s Summary");
  	    unameJobSummaryText.setText(name+"'s Capabilities");
  	    unameExperienceText.setText(name+"'s Experience");
        /*
         * Stuff From Tab
         * 
         */
  	    
	    profproglin = (LinearLayout)this.findViewById(R.id.profproglin);//
	      frameprof = (FrameLayout)this.findViewById(R.id.profframe);
	      mProgress = (ProgressBar) findViewById(R.id.profprog);
	      frameprof.setVisibility(View.INVISIBLE);
	    
	      try{
	          new Thread(){
	              public void run() {
	                  initializeApp();
	                  uiHandler.post( new Runnable(){
	                      @Override
	                      public void run() {
	                          if(isUpdateRequired){
	                          }else{
	                        	  user_picture.setImageBitmap(TabMain.userpic);
	                        	  connectionsButton.setText(TabMain.connnum);
	                        	  profproglin.setVisibility(View.GONE);
	                        	  frameprof.setVisibility(View.VISIBLE);
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
  	    
  	  
  
  	    /*
  	     * SETTING BUTTONS ETC 
  	     */
        
        
        logout = (Button)this.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(HomePage.this); 
                Editor editor = prefs.edit();
                editor.remove("access_token");
                editor.commit();
                finish();
            }
        });

        availableOn = (ImageButton)this.findViewById(R.id.availableOn);
        availableOff = (ImageButton)this.findViewById(R.id.availableOff);
    
     
    	availableOff.setVisibility(View.INVISIBLE);
        key = prefs.getString("staffkey", null);
        availableOn.setOnClickListener(new View.OnClickListener()
        
        {
            public void onClick(View v)
            {
                availableOn.setVisibility(View.INVISIBLE);
          	  availableOff.setVisibility(View.VISIBLE);
          	  TabMain.available = false;
          	  sendAvail(false);

            }
        });
        availableOff.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
          	  availableOff.setVisibility(View.INVISIBLE);
                availableOn.setVisibility(View.VISIBLE);
            	  TabMain.available = true;
          	 sendAvail(true);

            }
        });
 
        connectionsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(HomePage.this, StaffToFriend.class));
			}
		});
        
        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);
        Intent msgIntent = new Intent(this, StaffService.class);
	      msgIntent.putExtra(StaffService.PARAM_IN_MSG, "runHome");
	      startService(msgIntent);
	      
  }
	   
    @Override
    protected void onResume() {
        super.onResume();
        checkAvailability();

  }

    public void sendAvail(boolean bool){
		IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
	    filter.addCategory(Intent.CATEGORY_DEFAULT);
	    receiver = new ResponseReceiver();
	    registerReceiver(receiver, filter);
	    final Intent msgIntent = new Intent(this, StaffService.class);
		if (bool == true){
		    msgIntent.putExtra(StaffService.PARAM_IN_MSG, "availOn");
		} else if (bool == false){
		    msgIntent.putExtra(StaffService.PARAM_IN_MSG, "availOff");
		}
	    startService(msgIntent);	
	}
    
    
	public void checkAvailability(){
		if (TabMain.available == true ){
        	  availableOff.setVisibility(View.INVISIBLE);
              availableOn.setVisibility(View.VISIBLE);
		} else {
			 availableOn.setVisibility(View.INVISIBLE);
	       	  availableOff.setVisibility(View.VISIBLE);
		}
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
	        	//Get & Set User Profile Picture                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	        	
	        	//Get & Set User Connection #
	        	if (extras.getString("connnum")!=null) {
	        	String connnum = extras.getString("connnum");
	        	connectionsButton.setText(connnum);
	        	}

	        	if (extras.getString("profdetails")!=null) {
		        	parseResponse(extras.getString("profdetails")); 
	        	}
	        	done = "done";
	        	
	        }
	        
	    }
    
    
	 public void parseResponse(String responseBody) {
		 Log.d("TAG", "IN PARSE RESPONSE FOR HOMEPAGE: "+responseBody);
		 JSONObject exp; 
		 JSONObject cap; 
		 JSONObject edu;
		 JSONArray jexperiences; 
		 JSONArray jcapabilities; 
		 JSONArray jeducations; 
		 JSONObject json_data = null;
		 JSONTokener tokener = new JSONTokener(responseBody);
		 try {
			json_data = new JSONObject(tokener);
			jexperiences = json_data.getJSONArray("experiences");
			for (int i=0;i<jexperiences.length();i++){
				exp = jexperiences.getJSONObject(i);
				String title = exp.getString("title");
				String description = exp.getString("description");
				setExps(title,description,i);
			}
			jcapabilities = json_data.getJSONArray("capabilities");
			for (int i=0;i<jcapabilities.length();i++){
				cap = jcapabilities.getJSONObject(i);
				String title = cap.getString("title");
				String price = cap.getString("price");
				setCaps(title,price,i);
			}
			jeducations = json_data.getJSONArray("educations");
			for (int i=0;i<jeducations.length();i++){
				edu = jeducations.getJSONObject(i);
				String organization = edu.getString("organization");
				String end_year = edu.getString("end_year");
				String degree = edu.getString("major");
				setEdus(organization,end_year,degree,i);
			}
		 } catch (JSONException e) {
			e.printStackTrace();
		}
	}
	 
	 	public void setEdus(String organization, String end_year, String degree, int i){
	 		if (i == 0) {
	 			unameEducation1.setText(degree);
	 			unameEducationSchool1.setText(organization+" "+end_year);
			} else if (i == 1) {
				unameEducation2.setText(degree);
				unameEducationSchool2.setText(organization+" "+end_year);
			}
			else if (i>2) {
				Toast.makeText(getApplicationContext(), "Make more rows for capabilities", 0).show();
			}
				
			}
		public void setCaps(String title, String price, int i) {
			if (i == 0) {
				unameJobSkillJob1.setText(title);
				unameJobSkillRate1.setText("$"+price+"/hr");
			} else if (i == 1) {
				unameJobSkillJob2.setText(title);
				unameJobSkillRate2.setText("$"+price+"/hr");
			}
			else if (i>2) {
				Toast.makeText(getApplicationContext(), "Make more rows for capabilities", 0).show();
			}
				
			}
		public void setExps(String title, String description, int i) {
			if (i == 0) {
				unameJobExperience1.setText(title);
				unameJobExperienceCompany1.setText(description);
			} else if (i == 1) {
				unameJobExperience2.setText(title);
				unameJobExperienceCompany2.setText(description);
			}
			else if (i>2) {
				Toast.makeText(getApplicationContext(), "Make more rows for capabilities", 0).show();
			}
				
			}
}

