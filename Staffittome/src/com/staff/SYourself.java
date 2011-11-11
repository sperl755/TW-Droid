package com.staff;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

import com.staff.HomePage.ResponseReceiver;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SYourself extends Activity {
	private TextView myCapabilitiesText;
	private TextView unameJobSkillJob1;
	private TextView unameJobSkillRate1;
	private ImageButton unameJobSkillJob1Button;
	private TextView unameJobSkillJob2;
	private TextView unameJobSkillRate2;
	private ImageButton unameJobSkillJob2Button;
	private TextView pendingRequestsText;
	private TextView jobApplicationsNum;
	private TextView jobAppTitle1;
	private TextView jobAppDesc1;
	private TextView jobAppStatus1;
	private ImageButton jobAppButton1;
	private TextView jobAppTitle2;
	private TextView jobAppDesc2;
	private TextView jobAppStatus2;
	private ImageButton jobAppButton2;
	private ImageButton staffbutton;
	private Object name;
	private String staffuser;
    private ResponseReceiver receiver;

	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.syourself);
	       
	       /*
	        * SETTING FONTS AND SHIT
	        */

			Typeface hm = Typeface.createFromAsset(getAssets(),"fonts/hm.ttf");
		    Typeface hb = Typeface.createFromAsset(getAssets(),"fonts/hb.ttf");
		    myCapabilitiesText=(TextView)this.findViewById(R.id.myCapabilitiesText);
		    myCapabilitiesText.setTypeface(hb);
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
	       pendingRequestsText=(TextView)this.findViewById(R.id.pendingRequestsText);
	       pendingRequestsText.setTypeface(hb);
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
	        staffbutton=(ImageButton)this.findViewById(R.id.staffbutton);
	       /*
	        * GETTING INFO
	        */
	  		SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(SYourself.this); 
		    name = prefs.getString("name", null);
	        myCapabilitiesText.setText(name+"'s Capabilities");
	       
	        /*
	         * GETTING CAPABILITIES
	         */
	        
	      	/*
	      	 * Perhaps make a progressdialog that closes when staff.returnProposal is open...
	      	 */
	        
	       /*
	        * SHOW PENDING REQUESTS
	        */
	        
	        staffbutton.setOnClickListener(new View.OnClickListener()
	        {
	            public void onClick(View v)
	            {
					startActivity(new Intent(SYourself.this, Proposal.class));
	            }
	        });
	        
	        
	        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
	        filter.addCategory(Intent.CATEGORY_DEFAULT);
	        receiver = new ResponseReceiver();
	        registerReceiver(receiver, filter);
	        Intent msgIntent = new Intent(this, StaffService.class);
		      msgIntent.putExtra(StaffService.PARAM_IN_MSG, "staffSelf");
		      startService(msgIntent);
		      
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

	        	//Get & Set User Capabilities
	        	if (extras.getString("profdetails")!=null) {
		        	seperateCaps(extras.getString("profdetails")); 
	        	}
	        	if (extras.getString("proposals")!=null){
	        		parseProposals(extras.getString("proposals"));
	        	}
	        	
	        }
	        
	    }

	   
		public void parseResponse(String stuff){
			JSONArray jresult;
	        JSONArray jsearch;
			JSONObject json_data = null;
			  JSONTokener tokener = new JSONTokener(stuff);
	        try {
				jresult = new JSONArray(tokener);
		
			  
	        Log.d("TAG", "IN PARSE RESPONSE");
	        //jsearch = jresult.getJSONArray("");

			  for (int i=0; i<jresult.length(); i++) { //Runs through the Job postings for as long as the array is

	  	  json_data = jresult.getJSONObject(i); //For each json object
	  	  String title = json_data.getString("title");
	  	  String price = json_data.getString("price");
	  	  setTexts(title, price, i);
			  }
	    	} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void setTexts(String title, String price, int i) {
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


public void parseProposals(String stuff){
	JSONArray jresult;
    JSONArray jsearch;
	JSONObject json_data = null;
	JSONObject proposal_data;
	int count = 0;
	JSONTokener tokener = new JSONTokener(stuff);
    try {
		jresult = new JSONArray(tokener);

	  
    Log.d("TAG", "IN PARSE RESPONSE");
    //jsearch = jresult.getJSONArray("");

	  for (int i=0; i<jresult.length(); i++) { //Runs through the Job postings for as long as the array is

	  json_data = jresult.getJSONObject(i); //For each json object
	  proposal_data = json_data.getJSONObject("proposal");
	  String title = proposal_data.getString("title");
	  String description_of_service = proposal_data.getString("description_of_service");
	  setProposalTexts(title, description_of_service, i);
	  count++;
	  }
	  jobApplicationsNum.setText(Integer.toString(count)+" jobs");
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public void setProposalTexts(String title, String description, int i) {
	 
	if (i == 0) {
		jobAppTitle1.setText(title);
		 if (description.length()>42) {
	    	  String descsub = description.substring(0, 41);
	    	  String desc = descsub+"..";
	    	  jobAppDesc1.setText(desc);
		 } else
			 jobAppDesc1.setText(description);
		 
	} else if (i == 1) {
		jobAppTitle2.setText(title);
		 if (description.length()>42) {
	    	  String descsub = description.substring(0, 41);
	    	  String desc = descsub+"..";
	    	  jobAppDesc2.setText(desc);
		 } else
			 jobAppDesc2.setText(description);
		 	}
	else if (i>2) {
		Toast.makeText(getApplicationContext(), "Make more rows for capabilities", 0).show();
	}
		
	}
private void seperateCaps(String responseBody) {
	 Log.d("TAG", "IN PARSE RESPONSE FOR STAFF YOURSELF: "+responseBody);
	 JSONObject cap; 
	 JSONArray jcapabilities; 
	 JSONObject json_data = null;
	 JSONTokener tokener = new JSONTokener(responseBody);
	 try {
		json_data = new JSONObject(tokener);
		jcapabilities = json_data.getJSONArray("capabilities");
		for (int i=0;i<jcapabilities.length();i++){
			cap = jcapabilities.getJSONObject(i);
			String title = cap.getString("title");
			String price = cap.getString("price");
			setTexts(title,price,i);
		}
	 } catch (JSONException e) {
		e.printStackTrace();
	}
}
}
