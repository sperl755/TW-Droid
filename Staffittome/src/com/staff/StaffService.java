package com.staff;

import java.io.IOException;


import com.facebook.android.FacebookError;
import com.staff.CheckIn.ResponseReceiver;
//import com.staff.DashboardActivity.ResponseReceiver;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;


public class StaffService extends IntentService {
    public static final String PARAM_IN_MSG = "";
    public static final String PARAM_OUT_MSG = "";
    private int counter=0;

    public StaffService() {
        super("StaffService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String msg = intent.getStringExtra(PARAM_IN_MSG);

    	SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()); 
        String access_token = prefs.getString("access_token", null);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ResponseReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        //Intent testIntent = new Intent();
        //testIntent.setAction(ResponseReceiver.ACTION_RESP);
        //testIntent.addCategory(Intent.CATEGORY_DEFAULT);
        //broadcastIntent.putExtra(PARAM_OUT_MSG, resultTxt);
        try {
        	
        if (msg.equals("runHome")){ // All things needed for the user profile
        	broadcastIntent.putExtra("profdetails", StaffTasks.getProfDetails(this));
        	broadcastIntent.putExtra("userpic", StaffTasks.getUserPic()); 
        	broadcastIntent.putExtra("connnum", StaffTasks.getFriendCount(access_token));
        	sendBroadcast(broadcastIntent);
            
            stopSelf();
        } else if (msg.equals("staffSelf")){ // All things needed for the staff yourself page
        	broadcastIntent.putExtra("proposals", StaffTasks.getProposals(this));
        	broadcastIntent.putExtra("profdetails", StaffTasks.getProfDetails(this));
        	sendBroadcast(broadcastIntent);
            stopSelf();
        } else if (msg.equals("proposal")){ // All things needed for the staff yourself page
        	broadcastIntent.putExtra("profdetails", StaffTasks.getProfDetails(this));
        	sendBroadcast(broadcastIntent);
            stopSelf();     
        } else if (msg.equals("checkIn")){ // All things needed for the check in page
        	broadcastIntent.putExtra("appliedjobs", StaffTasks.viewAppliedJobs(this));
        	broadcastIntent.putExtra("contracts", StaffTasks.viewContracts(this)); 
        	broadcastIntent.putExtra("connnum", StaffTasks.getFriendCount(access_token));
        	sendBroadcast(broadcastIntent);
            stopSelf();
        } else if (msg.equals("availOn")){ // Async way of setting availability on
        	StaffTasks.setAvailability("1", this); //Kind of Sketchy right now
        	sendBroadcast(broadcastIntent);

           // stopSelf();
                 
        } else if (msg.equals("availOff")){ // Async way of setting availability off
        	StaffTasks.setAvailability("0", this);	// Kind of sketchy right now
        	sendBroadcast(broadcastIntent);

           // stopSelf();
        
        } else if (msg.equals("start")){
        	if (counter==0){
            	Thread.sleep(4000);
        	}
        	broadcastIntent.putExtra("discovery", StaffTasks.jobDiscovery(this));
            sendBroadcast(broadcastIntent);
            counter++;
            stopSelf();
        }} catch (IOException e1) {
			e1.printStackTrace();
		} catch (FacebookError e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        stopSelf();
    
    }

}
