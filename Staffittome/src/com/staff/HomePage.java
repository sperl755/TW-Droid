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
import android.graphics.drawable.Drawable;
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
import android.widget.TableLayout;
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
    private static TableLayout capTable;
    private static TableLayout expTable;
    private static TableLayout eduTable;
    private static View child1;
    private static View child2;
    private static View child3; 
    private static TextView attr1;
    private static TextView attr2;
    private static TextView attr3;
    private static TextView attr4;
    private static TextView attr5;
    private static TextView attr6;
    private FrameLayout noCaps;
    private FrameLayout noEdus;
    private FrameLayout noExps;
    

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
        unameExperienceText=(TextView)this.findViewById(R.id.unameExperienceText);
        unameExperienceText.setTypeface(hb);
        capTable = (TableLayout)this.findViewById(R.id.capTable);
        eduTable = (TableLayout)this.findViewById(R.id.eduTable);
        expTable = (TableLayout)this.findViewById(R.id.expTable);
        user_picture=(ImageView)this.findViewById(R.id.userpic);
        user_picture.setScaleType( ScaleType.CENTER_CROP );
        noCaps = (FrameLayout)this.findViewById(R.id.noCaps);
        noEdus = (FrameLayout)this.findViewById(R.id.noEdus);
        noExps = (FrameLayout)this.findViewById(R.id.noExps);

	

        
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
				String description = exp.getString("company_name");
				setExps(title,description,i,jexperiences.length());
			}
			jcapabilities = json_data.getJSONArray("capabilities");
			for (int i=0;i<jcapabilities.length();i++){
				cap = jcapabilities.getJSONObject(i);
				String title = cap.getString("title");
				String price = cap.getString("price");
				setCaps(title,price,i,jcapabilities.length());
			}
			jeducations = json_data.getJSONArray("educations");
			for (int i=0;i<jeducations.length();i++){
				edu = jeducations.getJSONObject(i);
				String organization = edu.getString("organization");
				String end_year = edu.getString("end_year");
				String degree = edu.getString("major");
				setEdus(organization,end_year,degree,i,jeducations.length());
			}
		 } catch (JSONException e) {
			e.printStackTrace();
		}
	}
	 
	 	public void setEdus(String organization, String end_year, String degree, int i, int length){
			   if (length != 0) {
				   noEdus.setVisibility(View.INVISIBLE);
			   }

			   if (i==0) {
				   eduTable.removeAllViewsInLayout();
				   eduTable.removeAllViews();
			   }
			   child1 = getLayoutInflater().inflate(R.layout.profilerow, null);
		       attr1 = (TextView)child1.findViewById(R.id.attr1);
		       attr2 = (TextView)child1.findViewById(R.id.attr2);
		       child1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(getApplicationContext(), "LOCAL DORDIE", 0).show();
					}
				});
		       eduTable.addView(child1);	        
		       attr1.setText(degree);
		       attr2.setText(organization+" "+end_year);
		       
		       if (i==length-1) {
		       ImageView applybg = (ImageView)child1.findViewById(R.id.prowbg);
		       Drawable d = getResources().getDrawable(R.drawable.module_row_last);
		       applybg.setBackgroundDrawable(d);
		       //tablelayout1.getChildAt(num);
		       }
			}
		
		public void setCaps(String title, String price, int i, int length) {
			   if (length != 0) {
				   noCaps.setVisibility(View.INVISIBLE);
			   }

			   if (i==0) {
				   capTable.removeAllViewsInLayout();
				    capTable.removeAllViews();
			   }
			   child2 = getLayoutInflater().inflate(R.layout.profilerow, null);
		       attr3 = (TextView)child2.findViewById(R.id.attr1);
		       attr4 = (TextView)child2.findViewById(R.id.attr2);
		       child2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(getApplicationContext(), "LOCAL DORDIE", 0).show();
					}
				});
		       capTable.addView(child2);	        
		       attr3.setText(title);
		       attr4.setText(price);
		       
		       if (i==length-1) {
		       ImageView applybg = (ImageView)child2.findViewById(R.id.prowbg);
		       Drawable d = getResources().getDrawable(R.drawable.module_row_last);
		       applybg.setBackgroundDrawable(d);
		       }
			}
		
		public void setExps(String title, String description, int i, int length) {
			   if (length != 0) {
				   noExps.setVisibility(View.INVISIBLE);
			   }

			   if (i==0) {
				   expTable.removeAllViewsInLayout();
				   expTable.removeAllViews();
			   }
			   child3 = getLayoutInflater().inflate(R.layout.profilerow, null);
		       attr5 = (TextView)child3.findViewById(R.id.attr1);
		       attr6 = (TextView)child3.findViewById(R.id.attr2);
		       child3.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(getApplicationContext(), "LOCAL DORDIE", 0).show();
					}
				});
		       expTable.addView(child3);	        
		       attr5.setText(title);
		       attr6.setText(description);
		       
		       if (i==length-1) {
		       ImageView applybg = (ImageView)child3.findViewById(R.id.prowbg);
		       Drawable d = getResources().getDrawable(R.drawable.module_row_last);
		       applybg.setBackgroundDrawable(d);
		       }
			}
		@Override
		public void onBackPressed() {
			TabMain.setTab();
		}
}

