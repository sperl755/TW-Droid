package com.staff;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.staff.CheckIn.ResponseReceiver;

import android.R.drawable;
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
import android.location.Location;
import android.location.LocationManager;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;


public class DashboardActivity extends Activity {
	private ImageView user_picture_one = null;
	private ImageView user_picture_two = null;
	private ImageView user_picture;
	private ImageButton invitePromote1;
	private ImageButton invitePromote2;
	private Button notification;
	private TextView buildNetwork;
	private static TextView uname;
	private ImageButton shuffle;
	private TextView jobSugges;
	private static TextView jobSuggesTitle1;
	private static TextView jobSuggesComp1;
	private TextView jobSuggesDist1;
	private static TextView jobSuggesTitle2;
	private static TextView jobSuggesComp2;
	private TextView jobSuggesDist2;
	private static TextView jobSuggesTitle3;
	private static TextView jobSuggesComp3;
	private TextView jobSuggesDist3;
	private static TextView jobSuggesTitle4;
	private static TextView jobSuggesComp4;
	private TextView jobSuggesDist4;
	private static TextView namePromote1;
	private static TextView namePromote2;
	private String id;
	private String id2;
	private String friendname1;
	private String friendname2;
    private JSONObject json_data = null;
    private JSONObject json_data_two = null;
    private String facebookkey;
    private Location location;
    private String access_token;
    private Button connButton;
    FacebookActivity fb1 = new FacebookActivity();
	Facebook facebook = new Facebook("187212574660004");
	private String name;
	private String facebook_uid;
	private String email;
	private String first_name;
	private String last_name;
	private String link;
	private String birthday;
	private String gender;
	private String locale;
	private static String staffkey;
	private String facebook_session_key;
	private int numfriends;
	private ImageButton availableOn;
	private ImageButton availableOff;
	private static String key;
	private static String user_id;
	private String json;
	private boolean logindone = false;
	private ImageButton jDiscLeft;
	private ImageButton jDiscRight;
	private static String discjobs;
	private static String jobid1;
	private static String jobid2;
	private static String jobid3;
	private static String jobid4;
	private ImageButton disc1;
	private ImageButton disc2;
	private ImageButton disc3;
	private ImageButton disc4;
	private ImageButton endorse1;
	private ImageButton endorse2;
	private ImageView randDisc1;
	private ImageView randDisc2;
	private ImageView randDisc3;
	private ImageView randDisc4;
	private Drawable r1;
	private Drawable r2;
	private Drawable r3;
	private Drawable r4;
    private static ResponseReceiver receiver;
    private ProgressDialog progDailog;
    private FrameLayout framedash;
    private static final int PROGRESS = 0x1;
    private final Handler uiHandler=new Handler();
    private boolean isUpdateRequired=false;
    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private LinearLayout dashproglin;
	
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
      setContentView(R.layout.dashboard);
      Log.d("TAG", "BEFORE LOCATION MANAGER");
      
      /*
      progDailog = ProgressDialog.show(this, "Loading",
              "Dashboard Information Only", true);
      new Thread() {
          public void run() {
              try {
            	  while (jobid4==null) {
            	  }
              } catch (Exception e) {
              }
              progDailog.dismiss();
          }
      }.start();
      */
      dashproglin = (LinearLayout)this.findViewById(R.id.dashproglin);
      framedash = (FrameLayout)this.findViewById(R.id.dashframe);
      mProgress = (ProgressBar) findViewById(R.id.dashProg);
      framedash.setVisibility(View.INVISIBLE);
      
      try{
          new Thread(){
              public void run() {
                  initializeApp();
                  uiHandler.post( new Runnable(){
                      @Override
                      public void run() {
                          if(isUpdateRequired){
                              //TODO:
                          }else{
                        	  dashproglin.setVisibility(View.GONE);
                              framedash.setVisibility(View.VISIBLE);
                          }
                      }
                  } );
              }
              public void initializeApp(){
            	  while (jobid4==null) {
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
       * STARTING SERVICE TO GET STUFF
       */
      

      
      LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);    
      location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);  
      
      //my.
      //Declaring Fonts
      Typeface hm = Typeface.createFromAsset(getAssets(),
      "fonts/hm.ttf");
      Typeface hb = Typeface.createFromAsset(getAssets(),
      "fonts/hb.ttf");
      
      //Setting fonts
      uname = (TextView)this.findViewById(R.id.uname);
      uname.setTypeface(hm);
      shuffle = (ImageButton)this.findViewById(R.id.Shuffle);
      connButton = (Button)this.findViewById(R.id.connectionsButton);
      connButton.setTypeface(hm);
      buildNetwork = (TextView)this.findViewById(R.id.bldNetwork);
      buildNetwork.setTypeface(hb);
      jobSugges = (TextView)this.findViewById(R.id.jobSugges);
      jobSugges.setTypeface(hb);
      jobSuggesTitle1 = (TextView)this.findViewById(R.id.suggesJobTitle1);
      jobSuggesTitle1.setTypeface(hm);
      jobSuggesComp1 = (TextView)this.findViewById(R.id.suggesJobComp1);
      jobSuggesComp1.setTypeface(hm);
      jobSuggesDist1 = (TextView)this.findViewById(R.id.jobDist1);
      jobSuggesDist1.setTypeface(hm);
      jobSuggesTitle2 = (TextView)this.findViewById(R.id.suggesJobTitle2);
      jobSuggesTitle2.setTypeface(hm);
      jobSuggesComp2 = (TextView)this.findViewById(R.id.suggesJobComp2);
      jobSuggesComp2.setTypeface(hm);
      jobSuggesDist2 = (TextView)this.findViewById(R.id.jobDist2);
      jobSuggesDist2.setTypeface(hm);
      jobSuggesTitle3 = (TextView)this.findViewById(R.id.suggesJobTitle3);
      jobSuggesTitle3.setTypeface(hm);
      jobSuggesComp3 = (TextView)this.findViewById(R.id.suggesJobComp3);
      jobSuggesComp3.setTypeface(hm);
      jobSuggesDist3 = (TextView)this.findViewById(R.id.jobDist3);
      jobSuggesDist3.setTypeface(hm);
      jobSuggesTitle4 = (TextView)this.findViewById(R.id.suggesJobTitle4);
      jobSuggesTitle4.setTypeface(hm);
      jobSuggesComp4 = (TextView)this.findViewById(R.id.suggesJobComp4);
      jobSuggesComp4.setTypeface(hm);
      jobSuggesDist4 = (TextView)this.findViewById(R.id.jobDist4);
      jobSuggesDist4.setTypeface(hm);
      jDiscLeft =(ImageButton)this.findViewById(R.id.jDiscLeft);
      jDiscRight =(ImageButton)this.findViewById(R.id.jDiscRight);
      disc1 = (ImageButton)this.findViewById(R.id.disc1);
      disc2 = (ImageButton)this.findViewById(R.id.disc2);
      disc3 = (ImageButton)this.findViewById(R.id.disc3);
      disc4 = (ImageButton)this.findViewById(R.id.disc4);
      randDisc1 =(ImageView)this.findViewById(R.id.randDisc1);
      randDisc2 =(ImageView)this.findViewById(R.id.randDisc2);
      randDisc3 =(ImageView)this.findViewById(R.id.randDisc3);
      randDisc4 =(ImageView)this.findViewById(R.id.randDisc4);
      endorse1 = (ImageButton)this.findViewById(R.id.endorse1);
      endorse2 = (ImageButton)this.findViewById(R.id.endorse2);
      namePromote1 = (TextView) this.findViewById(R.id.promoteName1);
      namePromote1.setTypeface(hm);
      namePromote2 = (TextView) this.findViewById(R.id.promoteName2);
      namePromote2.setTypeface(hm);
      user_picture = (ImageView)this.findViewById(R.id.userpic);
      user_picture.setScaleType( ScaleType.CENTER_CROP );


      r1 =getResources().getDrawable(R.drawable.icon_connection);
      r2 =getResources().getDrawable(R.drawable.icon_following);
      r3 =getResources().getDrawable(R.drawable.icon_location_dash);
      r4 =getResources().getDrawable(R.drawable.icon_profile);
      randDisc1.setBackgroundDrawable(r4);
      int result = 1 + (int) ( Math.random()*(5 - 1) );
    	  Log.d("TAG", "RANDOM NUMBER IS "+Integer.toString(result));
    	  if (result ==1) {
    		  randDisc1.setBackgroundDrawable(r1);
    		  randDisc2.setBackgroundDrawable(r2);
    		  randDisc3.setBackgroundDrawable(r3);
    		  randDisc3.setBackgroundDrawable(r4);
    	  } else if (result ==2) {
    		     randDisc1.setBackgroundDrawable(r2);
    		     randDisc2.setBackgroundDrawable(r3);
    		     randDisc3.setBackgroundDrawable(r4);
    		     randDisc4.setBackgroundDrawable(r1); 
    	  }else if (result ==3) {
 		     randDisc1.setBackgroundDrawable(r3);
		     randDisc2.setBackgroundDrawable(r1);
		     randDisc3.setBackgroundDrawable(r2);
		     randDisc4.setBackgroundDrawable(r4); 
	  }else if (result ==4) {
		     randDisc1.setBackgroundDrawable(r1);
		     randDisc2.setBackgroundDrawable(r4);
		     randDisc3.setBackgroundDrawable(r1);
		     randDisc4.setBackgroundDrawable(r3); 
	  }
    	  
    	/*
    	 * GET FB KEY  
    	 */
    		SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()); 
            access_token = prefs.getString("access_token", null);
              	  
    /*
     * Starting the ASYNC STAFF INFO	  
     */
    	  
    	 AsyncStaffInfo async = new AsyncStaffInfo(this, connButton, uname, user_picture); 
    	 async.execute(access_token);
    	 

    //
    	  
    	  
      /*
       *SETTING LOADING VALUES 
       */
		jobSuggesTitle1.setText("Loading...");
		jobSuggesComp1.setText(".....");
		jobSuggesTitle2.setText("Loading...");
		jobSuggesComp2.setText(".....");
		jobSuggesTitle3.setText("Loading...");
		jobSuggesComp3.setText(".....");
		jobSuggesTitle4.setText("Loading...");
		jobSuggesComp4.setText(".....");
      
      
    

   
  	try {
		getFriendReel();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  	
  		/*
       * GETTING AND SETTING BUTTON/TEXTS OTHERS
       */
      disc1.setOnClickListener(new View.OnClickListener()
      {
          public void onClick(View v)
          {
          	if (jobid1!=null) {
				Intent intent = new Intent(DashboardActivity.this, JobApply.class);
			intent.putExtra("jobid", jobid1);
			DashboardActivity.this.startActivity(intent);
          	}
          }
      });
      disc2.setOnClickListener(new View.OnClickListener()
      {
          public void onClick(View v)
          {
            	if (jobid2!=null) {
    				Intent intent = new Intent(DashboardActivity.this, JobApply.class);
    			intent.putExtra("jobid", jobid2);
    			DashboardActivity.this.startActivity(intent);
            	}
          }
      });
      disc3.setOnClickListener(new View.OnClickListener()
      {
          public void onClick(View v)
          {
          	if (jobid3!=null) {
				Intent intent = new Intent(DashboardActivity.this, JobApply.class);
			intent.putExtra("jobid", jobid3);
			DashboardActivity.this.startActivity(intent);
        	}
          }
      });
      disc4.setOnClickListener(new View.OnClickListener()
      {
          public void onClick(View v)
          {
            	if (jobid4!=null) {
    				Intent intent = new Intent(DashboardActivity.this, JobApply.class);
    			intent.putExtra("jobid", jobid4);
    			DashboardActivity.this.startActivity(intent);
            	}
          }
      });
     
      jDiscLeft.setOnClickListener(new View.OnClickListener()
      {
          public void onClick(View v)
          {
        	  if (discjobs!=null){
        	  //parseResponse(discjobs,1);
        	  } else Toast.makeText(getApplicationContext(), "Jobs are still loading", 0).show();
        	  }
      });
      jDiscRight.setOnClickListener(new View.OnClickListener()
      {
          public void onClick(View v)
          {
        	  if (discjobs!=null){
        	  //parseResponse(discjobs,5);
        	  } else Toast.makeText(getApplicationContext(), "Jobs are still loading", 0).show();
          }
      });

      endorse1.setOnClickListener(new OnClickListener() {
    	     
    	  @Override
    	  public void onClick(View v) {
       	  postToWall(id);

          }
      });
      endorse2.setOnClickListener(new OnClickListener() {
    	     
    	  @Override
    	  public void onClick(View v) {
       	  postToWall(id);

          }
      });
      
      invitePromote1 = (ImageButton)this.findViewById(R.id.invPromote1);
      invitePromote1.setOnClickListener(new OnClickListener() {
     
    	  @Override
    	  public void onClick(View v) {
       	  postToWall(id2);

          }
      });
      
      availableOn = (ImageButton)this.findViewById(R.id.availableOn);
      availableOff = (ImageButton)this.findViewById(R.id.availableOff);
  	  availableOff.setVisibility(View.INVISIBLE);
  	  availableOn.setOnClickListener(new View.OnClickListener()
      {
          public void onClick(View v)
          {
              availableOn.setVisibility(View.INVISIBLE);
        	  availableOff.setVisibility(View.VISIBLE);
        	  //msgIntent.putExtra(StaffService.PARAM_IN_MSG, "availOff");
              //startService(msgIntent);
        	  
        	  Log.d("TAG", "AVAILABILITY IS NOW OFF");
          }
      });
      availableOff.setOnClickListener(new View.OnClickListener()
      {
          public void onClick(View v)
          {
        	  availableOff.setVisibility(View.INVISIBLE);
              availableOn.setVisibility(View.VISIBLE);
              //msgIntent.putExtra(StaffService.PARAM_IN_MSG, "availOn");
              //startService(msgIntent);
              Log.d("TAG", "AVAILABILITY IS NOW ON");

          }
      });

      invitePromote2 = (ImageButton)this.findViewById(R.id.invPromote2);
      invitePromote2.setOnClickListener(new OnClickListener() {
       @Override
    	  public void onClick(View v) {
      	  postToWall(id2);
       }
      });
      shuffle.setOnClickListener(new View.OnClickListener() {
//
    	  @Override
    	  public void onClick(View v) {
    		   
    		  	try {
    				getFriendReel();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		  				}
    	  }
    	);
      
      connButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(DashboardActivity.this, StaffToFriend.class));
			}
		});
      
  
	  IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
      filter.addCategory(Intent.CATEGORY_DEFAULT);
      receiver = new ResponseReceiver();
      registerReceiver(receiver, filter);
      final Intent msgIntent = new Intent(this, StaffService.class);
      msgIntent.putExtra(StaffService.PARAM_IN_MSG, "start");
      startService(msgIntent);	
      
      	}
	
	protected void onResume() {
        super.onResume();
        }
	
	  public static  void startintentServie() {
		//  if (counter == 0)
		  {
			  /*
		       * Starting the intent service
		       */
		    
			 
		    	 
		      }
	}

	public class ResponseReceiver extends BroadcastReceiver {
	        public static final String ACTION_RESP = "com.staff.intent.action.MESSAGE_PROCESSED";
	        @Override
	        public void onReceive(Context context, Intent intent) {
	        	//CheckIn check = new CheckIn();
	             HomePage home = new HomePage();
	            // Update UI, new "message" processed by SimpleIntentService
	        	
	        	Bundle extras = intent.getExtras();
	        	//Get & Set User Job Discovery
	        	if (extras.getString("discovery")!=null) {
	        	parseResponse(extras.getString("discovery"),0); 
	        	}
	        }
	        
	    }

	
	
	public void postToWall(String id) {
		 Log.d("Tests", "Testing graph API wall post");
		 SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(DashboardActivity.this); 
	     String access_token = prefs.getString("access_token", null);
	     Log.d("TAG", access_token);
       try {
              String response = facebook.request("me");
              Bundle parameters = new Bundle();
          	parameters.putString(Facebook.TOKEN, access_token);
              parameters.putString("message", "Welcome to StaffitToMe!");
              parameters.putString("description", "StaffitToMe is a marketplace with trust factors and a payment system built in for hiring on a temporary and contract basis and micro-work (less than 1 day)");
              parameters.putString("name", "You have been Staffed!");
              parameters.putString("link", "http://www.staffittome.com");
              // These are for pictures
              //parameters.putString("caption", caption);
              //parameters.putString("picture", picURL);
              response = facebook.request(id+"/feed", parameters, 
                      "POST");
              Log.d("Tests", "got response: " + response);
              if (response == null || response.equals("") || 
                      response.equals("false")) {
                 Log.v("Error", "Blank response");
              }
       } catch(Exception e) {
           e.printStackTrace();
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
	public static void parseResponse(String stuff, int z) {
		  JSONObject jresult;
          JSONArray jobsarray;
		  JSONObject json_data = null;
		  JSONTokener tokener = new JSONTokener(stuff);
          try {
			jresult = new JSONObject(tokener);
	
		  jobsarray = jresult.getJSONArray("jobs");
          Log.d("TAG", "IN PARSE RESPONSE");
  		  for (int i=z; i<jobsarray.length(); i++) { //Runs through the Job postings for as long as the array is

    	  json_data = jobsarray.getJSONObject(i); //For each json object
    	  
          //JSONObject job = json_data.getJSONObject(""); // for each json object within that object named job, parse the below

          String title = json_data.getString("title");
          String company_name = json_data.getString("company_name");
          String id = json_data.getString("id");
         setTexts(title, company_name, i, id);
			}
      	} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	}
	
	public static void setTexts(String title, String company, int i, String id){
		Log.d("TAG","I HERE IS EQUALS TO: "+i);
		if (title.length()>=21){
		title = title.substring(0, 20);
		title = title+"..";
		} else {
		if (i== 0) {
			//parseResponse(discjobs,i+1);//
		} else 
		if (i == 1 || i ==5) {
			jobSuggesTitle1.setText(title);
			jobSuggesComp1.setText(company);
			jobid1= id;
		} else if (i == 2 || i==6) {
			jobSuggesTitle2.setText(title);
			jobSuggesComp2.setText(company);
			jobid2= id;
		}
		else if (i == 3 || i==7) {
			jobSuggesTitle3.setText(title);
			jobSuggesComp3.setText(company);
			jobid3= id;
		} else if (i == 4 || i ==8) {
			jobSuggesTitle4.setText(title);
			jobSuggesComp4.setText(company);
			jobid4= id;
		}
		}
	}
	
	/*
	 * GET FRIEND REEL
	 */
	
	public void getFriendReel() throws IOException{
		Bundle params = new Bundle();
    	SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(DashboardActivity.this); 
        String access_token = prefs.getString("access_token", null);
        Log.d("TAG", access_token);
    	params.putString(Facebook.TOKEN, access_token);
        Typeface hm = Typeface.createFromAsset(getAssets(),
        "fonts/hm.ttf");
		TextView namePromote1 = (TextView) this.findViewById(R.id.promoteName1);
		namePromote1.setTypeface(hm);
		TextView namePromote2 = (TextView) this.findViewById(R.id.promoteName2);
		namePromote2.setTypeface(hm);

	    try
	    {
	    	 
	   	 
	    	  JSONObject response = Util.parseJson(facebook.request("/me/friends", params, "GET")); // Get a friend information from facebook
	    	  JSONArray jArray = response.getJSONArray("data");
	    	  int x = 0 + (int)(Math.random() * ((jArray.length() - 0) + 1));
	    	  int y = x+1;
	    	  json_data = jArray.getJSONObject(x);
	    	  id = json_data.getString("id");  // Going to use this for the promotion function, cehck this out later
	    	  friendname1 = json_data.getString("name");
	    	  Log.d("TAG", friendname1);
	    	  if (friendname1.length()>12) {
	    	  String friendsub = friendname1.substring(0, 11);
	    	  String friend1 = friendsub+"..";
	    	  namePromote1.setText(friend1);
	    	  } else
	    	  namePromote1.setText(friendname1);
	    	  Log.d("TAG", id);
	    	  //This is for the second person in the slideshow
	    	  json_data_two = jArray.getJSONObject(y);
	    	  id2 = json_data_two.getString("id"); // Going to use this for the promotion function, cehck this out later
	    	  friendname2 = json_data_two.getString("name");
	    	  Log.d("TAG", friendname2);
	    	  if (friendname2.length()>12) {
	    	  String friendsub = friendname2.substring(0, 11);
	    	  String friend2 = friendsub+"..";
	    	  namePromote2.setText(friend2);
	    	  } else
	    	  namePromote2.setText(friendname2);
	    	  Log.d("TAG", id2);
	         
	    	  user_picture_one=(ImageView)findViewById(R.id.promoteMe1);
	    	 URL img_value = null;
	    	 img_value = new URL("http://graph.facebook.com/"+id+"/picture?type=square");
	    	 Bitmap mIcon11 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
	    	 user_picture_one.setImageBitmap(mIcon11);

	    	
	    	
	    	 user_picture_two=(ImageView)findViewById(R.id.promoteMe2);
	    	 URL img_valuetwo = null;
	    	 img_valuetwo = new URL("http://graph.facebook.com/"+id2+"/picture?type=square");
	    	 Bitmap mIcon1two = BitmapFactory.decodeStream(img_valuetwo.openConnection().getInputStream());
	    	 user_picture_two.setImageBitmap(mIcon1two);
	    	 access_token = facebookkey;

	    }
	    catch (MalformedURLException e)
	    {
	        e.printStackTrace();
	    }
	    catch (JSONException e)
	    {
	        e.printStackTrace();
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    catch (FacebookError e)
	    {
	        e.printStackTrace();
	    }
	    
	}
}