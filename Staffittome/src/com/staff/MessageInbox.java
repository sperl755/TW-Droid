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
import com.staff.CheckIn.ResponseReceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MessageInbox extends Activity {
	private static String messages;
	private ImageButton back;
	private Button notification;
	private Button notificationoff;
	private static TextView messagedesc1;
	private static TextView messagedesc2;
	private static TextView messagedesc3;
	private static TextView messagedesc4;
	private static TextView messagedesc5;
	private static TextView messagedesc6;
	private static TextView messagedesc7;
	private TextView notificationsinbox;
	private Button messageButton1;
	private Button messageButton2;
	private Button messageButton3;
	private Button messageButton4;
	private Button messageButton5;
	private Button messageButton6;
	private Button messageButton7;
	private Context c;
	private static String messageid1;
	private static String messageid2;
	private static String messageid3;
	private static String messageid4;
	private static String messageid5;
	private static String messageid6;
	private static String messageid7;
	private static String key;
	private static TableLayout messageTable; 
    private static View child;
    private static TextView messdesc;
    private ResponseReceiver receiver;



	   public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.inbox);
	       
	       /*
	        * FONT SETTTING SECTION...
	        */
	        Typeface hm = Typeface.createFromAsset(getAssets(),
	        "fonts/hm.ttf");
	        Typeface hb = Typeface.createFromAsset(getAssets(),
	        "fonts/hb.ttf");
	        /*
	        messagedesc1=(TextView)this.findViewById(R.id.messagedesc1);
	        messagedesc1.setTypeface(hm);
	        messagedesc2=(TextView)this.findViewById(R.id.messagedesc2);
	        messagedesc2.setTypeface(hm);
	        messagedesc3=(TextView)this.findViewById(R.id.messagedesc3);
	        messagedesc3.setTypeface(hm);
	        messagedesc4=(TextView)this.findViewById(R.id.messagedesc4);
	        messagedesc4.setTypeface(hm);
	        messagedesc5=(TextView)this.findViewById(R.id.messagedesc5);
	        messagedesc5.setTypeface(hm);
	        messagedesc6=(TextView)this.findViewById(R.id.messagedesc6);
	        messagedesc6.setTypeface(hm);
	        messagedesc7=(TextView)this.findViewById(R.id.messagedesc7);
	        messagedesc7.setTypeface(hm);
	        notificationsinbox=(TextView)this.findViewById(R.id.notificationsinbox);
	        notificationsinbox.setTypeface(hm);
	        messageButton1=(Button)this.findViewById(R.id.messageButton1);
	        messageButton2=(Button)this.findViewById(R.id.messageButton2);
	        messageButton3=(Button)this.findViewById(R.id.messageButton3);
	        messageButton4=(Button)this.findViewById(R.id.messageButton4);
	        messageButton5=(Button)this.findViewById(R.id.messageButton5);
	        messageButton6=(Button)this.findViewById(R.id.messageButton6);
	        messageButton7=(Button)this.findViewById(R.id.messageButton7);
	        */
	        messageTable = (TableLayout)this.findViewById(R.id.messageTable);
	        
	    	SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(MessageInbox.this); 
	        key = prefs.getString("staffkey", null);
	        
	       
	       back = (ImageButton)this.findViewById(R.id.backInbox);
	       back.setOnClickListener(new View.OnClickListener()
	        {
	            public void onClick(View v)
	            {
	            	MessageInbox.this.finish();
	            }
	        });
	       /*

	       messageButton1.setOnClickListener(new View.OnClickListener()
	        {
	            public void onClick(View v)
	            {
	            	if (messageid1!=null) {
					Intent intent = new Intent(MessageInbox.this, Message.class);
					intent.putExtra("messageid", messageid1);
					MessageInbox.this.startActivity(intent);
	            	}
	            }
	        });
	       messageButton2.setOnClickListener(new View.OnClickListener()
	        {
	            public void onClick(View v)
	            {
	            	if (messageid1!=null) {
					Intent intent = new Intent(MessageInbox.this, Message.class);
					intent.putExtra("messageid", messageid2);
					MessageInbox.this.startActivity(intent);
	            	}
	            }
	        });
	       messageButton3.setOnClickListener(new View.OnClickListener()
	        {
	            public void onClick(View v)
	            {
	            	if (messageid1!=null) {
						Intent intent = new Intent(MessageInbox.this, Message.class);
					intent.putExtra("messageid", messageid3);
					MessageInbox.this.startActivity(intent);
	            	}
	            }
	        });
	       messageButton4.setOnClickListener(new View.OnClickListener()
	        {
	            public void onClick(View v)
	            {
	            	if (messageid1!=null) {
						Intent intent = new Intent(MessageInbox.this, Message.class);
					intent.putExtra("messageid", messageid4);
					MessageInbox.this.startActivity(intent);
	            	}
	            }
	        });
	       messageButton5.setOnClickListener(new View.OnClickListener()
	        {
	            public void onClick(View v)
	            {
	            	if (messageid1!=null) {
						Intent intent = new Intent(MessageInbox.this, Message.class);
					intent.putExtra("messageid", messageid5);
					MessageInbox.this.startActivity(intent);
	            	}
	            }
	        });
	       messageButton6.setOnClickListener(new View.OnClickListener()
	        {
	            public void onClick(View v)
	            {
	            	if (messageid1!=null) {
						Intent intent = new Intent(MessageInbox.this, Message.class);
					intent.putExtra("messageid", messageid6);
					MessageInbox.this.startActivity(intent);
	            	}
	            }
	        });
	       messageButton7.setOnClickListener(new View.OnClickListener()
	        {
	            public void onClick(View v)
	            {
	            	if (messageid1!=null) {
						Intent intent = new Intent(MessageInbox.this, Message.class);
					intent.putExtra("messageid", messageid7);
					MessageInbox.this.startActivity(intent);
	            	}
	            }
	        });
	        */
	   	notification = (Button)this.findViewById(R.id.notificationlit);
	    notificationoff = (Button)this.findViewById(R.id.notificationunlit);
		/*
		 * Default (notification is going to be set as off)
		 */
		notification.setVisibility(View.INVISIBLE);
		notification.setBackgroundColor(Color.TRANSPARENT);
		/*
		 * Here will be the getMessages api, to check if there are any messages, if so, change the visibility of notification to VISIBLE and change nonotification to INVISIBLE
		 */
		
		
		
	    notification.setOnClickListener(new OnClickListener() {
	          public void onClick(View v) {
					startActivity(new Intent(MessageInbox.this, MessageInbox.class));
	          }
	      });
	      notificationoff.setOnClickListener(new OnClickListener() {
	          public void onClick(View v) {
					startActivity(new Intent(MessageInbox.this, MessageInbox.class));
	          }
	      });
	
	      /*
	       * GETTING MESSAGES
	       */
	   }
	   
	   @Override
	    protected void onResume() {
	        super.onResume();
	        //done = null;
		    sendIntent();
	  }
		  public void sendIntent(){

		        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
		        filter.addCategory(Intent.CATEGORY_DEFAULT);
		        receiver = new ResponseReceiver();
		        registerReceiver(receiver, filter);
		        Intent msgIntent = new Intent(this, StaffService.class);
			    msgIntent.putExtra(StaffService.PARAM_IN_MSG, "inbox");
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
	        	 *///
	        	
	        	Bundle extras = intent.getExtras();

	        	//Get & Set User applied jobs
	        	if (extras.getString("inbox")!=null) {
	        		parseInbox(extras.getString("inbox")); 
	        	
	        	//done = "done";
	        	}

	        }
	        
	    }
	   
	   public  void parseInbox(String inbox) {
			  JSONArray jresult;
			  JSONObject json_data = null;
			  JSONTokener tokener = new JSONTokener(inbox);
	          try {
				jresult = new JSONArray(tokener);
	
	          Log.d("TAG", "IN PARSE MESSAGE RESPONSE");

			  for (int i=0; i<jresult.length(); i++) { //Runs through the messages for as long as the array is

	    	  json_data = jresult.getJSONObject(i); //For each json object
	    	     
	          String body = json_data.getString("body");
	          String subject = json_data.getString("subject");
	          String sender_name = json_data.getString("sender_name");
	          String created_at = json_data.getString("created_at");
	          String id = json_data.getString("id");
	          setTexts(body, subject, sender_name,id, i, jresult.length());
			} 
	  		} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
	   }
	  
	   private  void setTexts(String body, String subject, String sender_name,
			final String id, int i, int length) {
	
		   if (i==0) {
			   messageTable.removeAllViewsInLayout();
			   messageTable.removeAllViews();
		   }
		   child = getLayoutInflater().inflate(R.layout.messagerow, null);
		   messdesc = (TextView)child.findViewById(R.id.messagedesc1);
	       child.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), "Messaage id for this message is: "+id, 0).show();
				}
			});
	       messageTable.addView(child);	        
	       messdesc.setText(sender_name+": "+body);
	       
	       if (i==length-1) {
	       ImageView applybg = (ImageView)child.findViewById(R.id.inboxrowbg);
	       Drawable d = getResources().getDrawable(R.drawable.module_row_last);
	       applybg.setBackgroundDrawable(d);
	       }
	}
}

