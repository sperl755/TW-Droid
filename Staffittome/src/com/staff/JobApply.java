package com.staff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

import com.facebook.android.FacebookError;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class JobApply extends Activity {
	private ImageButton back;
	private TextView jobTitle1;
	private TextView company;
	private Button apply;
	private TextView distanceValue;
	private TextView distanceText;
	private TextView jobSummaryText;
	private TextView jobSummaryValue;
	private TextView basicInfoText;
	private TextView locationText;
	private TextView jobTypeText;
	private TextView locationValue;
	private TextView jobTypeValue;
	private TextView compensationText;
	private TextView timeText;
	private TextView compensationValue;
	private TextView timeValue;
	private TextView jobSkillsText;
	private TextView jobSkillsReq1;
	private TextView jobSkillsReq1Value;
	private String jobid;
	private String distance;
	/*
	private TextView jobSkillsReq2;
	private TextView jobSkillsReq2Value;
	private TextView jobSkillsReq3;
	private TextView jobSkillsReq3Value;
	private TextView jobSkillsReq4;
	private TextView jobSkillsReq4Value;
	private TextView jobSkillsReq5;
	private TextView jobSkillsReq5Value;
	private TextView jobSkillsReq6;
	private TextView jobSkillsReq6Value;
	private TextView jobSkillsReq7;
	private TextView jobSkillsReq7Value;
	private TextView jobSkillsReq8;
	private TextView jobSkillsReq8Value;
	*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jobapply);
	      
		/*
		 * SETTING TYPEFACES GOD DAMN 
		 */
		
	      
		//Declaring Fonts
		Typeface hm = Typeface.createFromAsset(getAssets(),"fonts/hm.ttf");
	    Typeface hb = Typeface.createFromAsset(getAssets(),"fonts/hb.ttf");
	      
		jobTitle1 =(TextView)this.findViewById(R.id.jobTitle1);
		jobTitle1.setTypeface(hm);
		company =(TextView)this.findViewById(R.id.company);
		company.setTypeface(hm);
		apply =(Button)this.findViewById(R.id.applyButton);
		apply.setTypeface(hm);
		distanceValue =(TextView)this.findViewById(R.id.distanceValue);
		distanceValue.setTypeface(hb);
		distanceText =(TextView)this.findViewById(R.id.distanceText);
		distanceText.setTypeface(hm);
		jobSummaryText =(TextView)this.findViewById(R.id.jobSummaryText);
		jobSummaryText.setTypeface(hb);
		jobSummaryValue =(TextView)this.findViewById(R.id.jobSummaryValue);
		jobSummaryValue.setTypeface(hm);
		basicInfoText =(TextView)this.findViewById(R.id.basicInfoText);
		basicInfoText.setTypeface(hb);
		locationText =(TextView)this.findViewById(R.id.locationText);
		locationText.setTypeface(hm);
		jobTypeText =(TextView)this.findViewById(R.id.jobTypeText);
		jobTypeText.setTypeface(hm);
		locationValue =(TextView)this.findViewById(R.id.locationValue);
		locationValue.setTypeface(hm);
		compensationText =(TextView)this.findViewById(R.id.compensationText);
		compensationText.setTypeface(hm);
		compensationValue = (TextView)this.findViewById(R.id.compensationValue);
		compensationValue.setTypeface(hm);
		jobTypeValue =(TextView)this.findViewById(R.id.jobTypeValue);
		jobTypeValue.setTypeface(hm);
		timeText =(TextView)this.findViewById(R.id.timeText);
		timeText.setTypeface(hm);
		timeValue =(TextView)this.findViewById(R.id.timeValue);
		timeValue.setTypeface(hm);
		jobSkillsText =(TextView)this.findViewById(R.id.jobSkillsText);
		jobSkillsText.setTypeface(hb);
		jobSkillsReq1 =(TextView)this.findViewById(R.id.jobSkillsReq1);
		jobSkillsReq1.setTypeface(hm);
		jobSkillsReq1Value =(TextView)this.findViewById(R.id.jobSkillsReq1Description);
		jobSkillsReq1Value.setTypeface(hm);
		
		/*
		 * GETTING JOB ID FROM SEARCHMAPS/ITEMIZED OVERLAY
		 */
		
		Bundle extras = getIntent().getExtras();
		jobid = extras.getString("jobid");
		if (distance==null){
		distance = "44.3"; }
		else {
		distance = extras.getString("distance");
		distance = distance.substring(0, distance.indexOf(".")+2);
		}
		/*
		 * GETTING JOB DETAILS
		 */
		
    	getJobDetail(jobid);

		/*.
		 * SETTING FUNCTIONALITY
		 */
		
		   back = (ImageButton)this.findViewById(R.id.backInbox);
	       back.setOnClickListener(new View.OnClickListener()
	        {
	            public void onClick(View v)
	            {
	            	JobApply.this.finish();
	            }
	        });
	       apply.setOnClickListener(new View.OnClickListener()
	        {
	            public void onClick(View v)
	            {
	            	applyJob(jobid);
	            }
	        });
	   }
	public void applyJob(String jobid2) {
		SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(JobApply.this); 
        String key = prefs.getString("staffkey", null);
        Log.d("TAG",key);
        MyHttpClient client = new MyHttpClient(getApplicationContext());
        
        HttpPost post = new HttpPost("https://helium.staffittome.com/apis/submit_application");
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("session_key", key));
        nameValuePairs.add(new BasicNameValuePair("job_id", jobid2));


        try {
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		ResponseHandler<String> responseHandler=new BasicResponseHandler();
		String responseBody = client.execute(post, responseHandler);
		Log.d("TAG","TEST RESULTS FROM HTTPS APPLY JOB WITH JOBID: "+jobid2+"is : "+responseBody);
		if (responseBody.equals("Application has been successfully submitted.")){
			Toast.makeText(getApplicationContext(), "Application submitted successfully", 0).show();
		}
		else if (responseBody.equals("You already have applied on this job.")){
			Toast.makeText(getApplicationContext(), "You've already applied for this job", 0).show();
		}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	public void getJobDetail(String jobid) {
    	    String responseBody = "bannana";
    	    String t = "true";
    	    try {
    		URI url = new URI("https://helium.staffittome.com/apis/"+jobid+"/job");
    		
    	    HttpGet get = new HttpGet(url);
    	    HttpClient client = new MyHttpClient(getApplicationContext());
    	    ResponseHandler<String> responseHandler=new BasicResponseHandler();
    	    responseBody = client.execute(get, responseHandler);
    	 	
    	    Log.d("TAG", "RESPONSE STRING FROM HTTPS GET JOB DETAIL IS: "+responseBody);
    	    parseJob(responseBody);
    		} catch (ClientProtocolException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		} catch (ParseException e) {
    			e.printStackTrace();
    	    } catch (URISyntaxException e) {
    			e.printStackTrace();
    		} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}		
	 public void parseJob(String stuff) throws JSONException {
		 JSONArray skills;
		 JSONObject json_data = null;
		 JSONTokener tokener = new JSONTokener(stuff);
         json_data = new JSONObject(tokener);
		  
         Log.d("TAG", "IN PARSE JOB");
         //jsearch = jresult.getJSONArray("");

		 //for (int i=0; i<jresult.length(); i++) { //Runs through the Job postings for as long as the array is

		//	  json_data = jresult.getJSONObject(i); //For each json object
   	  
         //JSONObject job = json_data.getJSONObject(""); // for each json object within that object named job, parse the below

         JSONObject locationData = json_data.getJSONObject("location");
         JSONObject json_loc_data = locationData.getJSONObject("location");
         String state = json_loc_data.getString("state");
         String city = json_loc_data.getString("city");
         //String address1 = json_loc_data.getString("address1");
         //String phone = json_loc_data.getString("phone");
         //String loc_created_at = json_loc_data.getString("created_at");
         //String zip = json_loc_data.getString("zip");
         //String location_id = json_loc_data.getString("id");
        
         
         if (city.length()>8) {
        	  String citsub = city.substring(0, 7);
        	  String cit1 = citsub+"..";
              locationValue.setText(cit1+" ,"+state);
        	  } else {
        	   locationValue.setText(city+" ,"+state);
        	  }

         JSONObject companyAttributes = json_data.getJSONObject("company");
         String companyThumbUrl = companyAttributes.getString("thumb_photo");
         
         
         String thecompany = json_data.getString("company");
   	  		if (thecompany.length()>16) {
    	  String compsub = thecompany.substring(0, 15);
    	  String comp1 = compsub+"..";
    	  company.setText(comp1);
    	  } else {
    	  company.setText(thecompany);
    	  }
         String industry = json_data.getString("industry");
         String description = json_data.getString("description");
         String cost_method = json_data.getString("cost_method");
         String time_details = json_data.getString("time_details");

         //String created_at = json_data.getString("created_at");
         String jobtype = json_data.getString("jobtype");
         String title = json_data.getString("title");
	  		if (title.length()>16) {
 	  String titsub = title.substring(0, 15);
 	  String tit1 = titsub+"..";
 	  jobTitle1.setText(tit1);
 	  } else {
 		 jobTitle1.setText(title);
 	  }
         //String id = json_data.getString("id");
         //jobTitle1.setText(title);
         //company.setText(thecompany);
         distanceValue.setText(distance+" mi");
         //locationValue.setText(city+" ,"+state);
         jobTypeValue.setText(jobtype);
         jobSummaryValue.setText(description); //FOR NWO
         compensationValue.setText(cost_method);
         if (time_details.equals("null")) {
        	 timeValue.setText("N/A");
         }
         else {
             timeValue.setText(time_details);
         }
         skills = json_data.getJSONArray("skills"); // Comon someone fill out a proper thing...

         /*
         company.setTypeface(hm);

 		locationText =(TextView)this.findViewById(R.id.locationText);
 		locationText.setTypeface(hm);
 		jobTypeText =(TextView)this.findViewById(R.id.jobTypeText);
 		jobTypeText.setTypeface(hm);
 		locationValue =(TextView)this.findViewById(R.id.locationValue);
 		locationValue.setTypeface(hm);
 		compensationText =(TextView)this.findViewById(R.id.compensationText);
 		compensationText.setTypeface(hm);
 		compensationValue = (TextView)this.findViewById(R.id.compensationValue);
 		compensationValue.setTypeface(hm);
 		jobTypeValue =(TextView)this.findViewById(R.id.jobTypeValue);
 		jobTypeValue.setTypeface(hm);
 		timeText =(TextView)this.findViewById(R.id.timeText);
 		timeText.setTypeface(hm);
 		timeValue =(TextView)this.findViewById(R.id.timeValue);
 		timeValue.setTypeface(hm);
 		jobSkillsText =(TextView)this.findViewById(R.id.jobSkillsText);
 		jobSkillsText.setTypeface(hb);
 		jobSkillsReq1 =(TextView)this.findViewById(R.id.jobSkillsReq1);
 		jobSkillsReq1.setTypeface(hm);
 		jobSkillsReq1Value =(TextView)this.findViewById(R.id.jobSkillsReq1Description);
 		jobSkillsReq1Value.setTypeface(hm);
 		
 		*/
	 }
	 }
	 
        