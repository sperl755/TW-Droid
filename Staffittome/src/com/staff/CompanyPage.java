package com.staff;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;




public class CompanyPage extends Activity {
    private ResponseReceiver receiver;
	private String done; 
	private String companyid;
	private TextView coTitle;
	private TextView smallCoDescription;
	private TextView coSummary;
	private TextView coSummaryText;
	private TextView coLocation; 
	private TextView coDistance;
	private TextView coDistanceMiles;
	private TextView coBasicInfo;
	private TextView coSize;
	private TextView coJobType;
	private TextView coJobTypeValue;
	private TextView coSizeValue;
	private TextView coJobOpenings;
	private TextView coOpeningsPos1;
	private TextView coOpeningsPos1Desc;


	   public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.company);
	       
	       /*
	        * GET COMPANY ID FROM INTENET EXTRAS
	        */
	       
	       Bundle extras = getIntent().getExtras();
	       companyid = extras.getString("id");
	       
	       Log.d("TAG", "EXTRA GOT FROM JOB APPLY ID IS : "+companyid);
	       
	       /*
	        * SET FONTS 
	        */
	       
	       
	       Typeface hm = Typeface.createFromAsset(getAssets(),"fonts/hm.ttf");
		    Typeface hb = Typeface.createFromAsset(getAssets(),"fonts/hb.ttf");
		      
		    coTitle =(TextView)this.findViewById(R.id.coTitle);
		    coTitle.setTypeface(hb);
		    smallCoDescription =(TextView)this.findViewById(R.id.smallCoDescription);
		    smallCoDescription.setTypeface(hm);
		    coSummary =(TextView)this.findViewById(R.id.coSummary);
		    coSummary.setTypeface(hb);
		    coSummaryText =(TextView)this.findViewById(R.id.coSummaryText);
		    coSummaryText.setTypeface(hm);
		    coLocation =(TextView)this.findViewById(R.id.coLocation);
		    coLocation.setTypeface(hm);
		    coDistance =(TextView)this.findViewById(R.id.coDistance);
		    coDistance.setTypeface(hm);
		    coDistanceMiles =(TextView)this.findViewById(R.id.coDistanceMiles);
		    coDistanceMiles.setTypeface(hb);	    
		    coBasicInfo =(TextView)this.findViewById(R.id.coBasicInfo);
		    coBasicInfo.setTypeface(hb);
		    coJobType =(TextView)this.findViewById(R.id.coJobType);
		    coJobType.setTypeface(hm);
		    coJobTypeValue =(TextView)this.findViewById(R.id.coJobTypeValue);
		    coJobTypeValue.setTypeface(hm);
		    coSize =(TextView)this.findViewById(R.id.coSize);
		    coSize.setTypeface(hm);
		    coSizeValue =(TextView)this.findViewById(R.id.coSizeValue);
		    coSizeValue.setTypeface(hm);
		    coJobOpenings =(TextView)this.findViewById(R.id.coJobOpenings);
		    coJobOpenings.setTypeface(hb);
		    coOpeningsPos1 =(TextView)this.findViewById(R.id.coOpeningsPos1);
		    coOpeningsPos1.setTypeface(hm);
		    coOpeningsPos1Desc =(TextView)this.findViewById(R.id.coOpeningsPos1Desc);
		    coOpeningsPos1Desc.setTypeface(hm);

		    

	        coLocation.setText(extras.getString("city")+extras.getString("state"));
	        smallCoDescription.setText(extras.getString("industry"));
	       /*
	        * SEND INTENT TO SERVICE REGARDING OUR NEEDS
	        */
	       
	        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
	        filter.addCategory(Intent.CATEGORY_DEFAULT);
	        receiver = new ResponseReceiver();
	        registerReceiver(receiver, filter);
	        Intent msgIntent = new Intent(this, StaffService.class);
		      msgIntent.putExtra("companyid", companyid);
		      msgIntent.putExtra(StaffService.PARAM_IN_MSG, "runCompany");
		      startService(msgIntent);
		      Log.d("TAG","TEST TEST TEST TEST TEST TEST");
	   }
	   
	   
	   public class ResponseReceiver extends BroadcastReceiver {
	        public static final String ACTION_RESP = "com.staff.intent.action.MESSAGE_PROCESSED";
	        @Override
	        public void onReceive(Context context, Intent intent) {
	        	
	        	Bundle extras = intent.getExtras();
	        	
	        	if (extras.getString("companyinfo")!=null) {
	        	String companyinfo = extras.getString("companyinfo");
	        	parseResponse(companyinfo);
	        	}
	        	done = "done";
	     
	        }
	        
	    }
	  public void parseResponse(String companyinfo){
		  JSONObject json_data = null;
			 JSONTokener tokener = new JSONTokener(companyinfo);
	         try {
				json_data = new JSONObject(tokener);

			  
	         Log.d("TAG", "IN PARSE JOB");

	         JSONObject jsoncompany = json_data.getJSONObject("company");
	         String coname = jsoncompany.getString("name");
	         coTitle.setText(coname);
	         String size = jsoncompany.getString("size");
	         coSizeValue.setText(size);
	         String description = jsoncompany.getString("description");
	         coSummaryText.setText(description);
	         String company_type = jsoncompany.getString("company_type");
	         if (company_type!=null) {
	         coJobTypeValue.setText(company_type);
	         }
	         //String coname = jsoncompany.getString("name");

	         
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         
	   }
}
