package com.staff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.staff.CheckIn.ResponseReceiver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Message extends Activity {
	private Button notification;
	private Button notificationoff;
	
	private ImageButton back;
	private int messageid;
	private String messid;
	private TextView subject;
	private TextView sender;
	private TextView recipiant;
	private TextView senderMessage1;
	private TextView senderMessage2;
	private TextView senderMessage3;
	//private TextView recipiantMessage1;
	private TextView recipiantMessage2;
	private TextView recipiantMessage3;
	private Button recipiantButton;
	private LinearLayout Layout03;
	private Button sendButton;
	private EditText sendBox;
	private String boxMessage;
	private String messagesubject;
	private String message_id;
	private String idtosendto;
    private ProgressDialog progDailog;
    private FrameLayout framemess;
    private static final int PROGRESS = 0x1;
    private final Handler uiHandler=new Handler();
    private boolean isUpdateRequired=false;
    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private LinearLayout messproglin;
    private String done;
    private int counter;
	
	
		   public void onCreate(Bundle savedInstanceState) {
		       super.onCreate(savedInstanceState);
		       setContentView(R.layout.message);
		       /*
		        * GETTING AND SETTING VIEWS ETC
		        */
		       sender= (TextView)this.findViewById(R.id.senderid);
		       //recipiant = (TextView)this.findViewById(R.id.recipientId);
		       senderMessage1 =(TextView)this.findViewById(R.id.recipientMessage1);
		       recipiantButton = (Button)this.findViewById(R.id.recipiantButton);
		       subject=(TextView)this.findViewById(R.id.messageSubject);
		       sendButton=(Button)this.findViewById(R.id.sendButton);
		       back = (ImageButton)this.findViewById(R.id.backInbox);
		       sendBox = (EditText)this.findViewById(R.id.messageBox);
		       back.setOnClickListener(new View.OnClickListener()
		        {
		            public void onClick(View v)
		            {
		            	Message.this.finish();
		            }
		        });
				notification = (Button)this.findViewById(R.id.notificationlit);
			    notificationoff = (Button)this.findViewById(R.id.notificationunlit);
				/*
				 * Default (notification is going to be set as off)
				 */
				notification.setVisibility(View.INVISIBLE);
				notification.setBackgroundColor(Color.TRANSPARENT);
				/*
				 * GETTING MESSAGE ID FROM INTENT
				 */
				Bundle extras = getIntent().getExtras();
				messid = extras.getString("messageid");
				/*
				 * Here will be the getMessages api, to check if there are any messages, if so, change the visibility of notification to VISIBLE and change nonotification to INVISIBLE
				 */
				Log.d("TAG","ABOUT TO GET DETAILS FOR MESSAGEID: "+messid);
				
				getMessageDetail(messid);
				
				
				
			    notification.setOnClickListener(new OnClickListener() {
			          public void onClick(View v) {
							startActivity(new Intent(Message.this, MessageInbox.class));
			          }
			      });
			      notificationoff.setOnClickListener(new OnClickListener() {
			          public void onClick(View v) {
							startActivity(new Intent(Message.this, MessageInbox.class));
			          }
			      });
			      sendButton.setOnClickListener(new OnClickListener() {
			          public void onClick(View v) {
			        	 boxMessage = sendBox.getText().toString();
			        	 sendMessage(boxMessage, message_id, idtosendto, "@”Job”", messagesubject);
			          }
			      });
			
			       //getMessageDetail("THIS IS A FUCKING TEST REMOVE THIS"); //Parse the getMesssages and set this for each message that is returned <3

			      messproglin = (LinearLayout)this.findViewById(R.id.messproglin);
			      framemess = (FrameLayout)this.findViewById(R.id.messframe);
			      mProgress = (ProgressBar) findViewById(R.id.messProg);
		   
		   }
		   
		   
		 /*  @Override
		    protected void onResume() {
		        super.onResume();
		        done = null;
		        checkLoading();
		  }
		  */
		  
		  public void checkLoading(){
		      framemess.setVisibility(View.INVISIBLE);
	    	  messproglin.setVisibility(View.VISIBLE);
			   try{
			          new Thread(){
			              public void run() {
			                  initializeApp();
			                  uiHandler.post( new Runnable(){
			                      @Override
			                      public void run() {
			                          if(isUpdateRequired){
			                          }else{
			                        	  messproglin.setVisibility(View.GONE);
			                        	  framemess.setVisibility(View.VISIBLE);
			                          }
			                      }
			                  } );
			              }
			              public void initializeApp(){
			            	  while (done==null) {
			            		  getMessageDetail(messid);
			            	  }
			              }
			      }.start();
			      }catch (Exception e) {}
		  }
		   
		   
		   
		   
		   
		   public void getMessageDetail(String messageid) {
		    	SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(Message.this); 
		        String key = prefs.getString("staffkey", null);
		        Log.d("TAG",key);
		        MyHttpClient client = new MyHttpClient(getApplicationContext());
		        
		        HttpPost post = new HttpPost("https://helium.staffittome.com/apis/"+messageid+"/view_message");
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		        nameValuePairs.add(new BasicNameValuePair("session_key", key));
		        try {
		        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				ResponseHandler<String> responseHandler=new BasicResponseHandler();
				String responseBody = client.execute(post, responseHandler);
				Log.d("TAG","TEST RESULTS FROM HTTPS VIEW MESSAGE DETAILS WITH MESSAGE ID "+messageid+" IS: "+responseBody);
				parseMessageDetails(responseBody);//
		        } catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				done = "done";
		    }
		   
		   public void parseMessageDetails(String stuff) throws JSONException{
				  JSONObject jresult;
				  JSONObject json_data = null;
				  JSONTokener tokener = new JSONTokener(stuff);
		          jresult = new JSONObject(tokener);//
				  
		          Log.d("TAG", "IN PARSE MESSAGE DETAILS");

				  //for (int i=0; i<jresult.length(); i++) { //Runs through the messages for as long as the array is

		    	  json_data = jresult.getJSONObject("message"); //For each json object
		    	     
		    	  /*
		    	   * "created_at":"2011-09-11T23:25:36Z",
					"body":"THis Is the body",
					"subject":"This is Subject",
					"recipient_id":1272,
					"sender_id":1273,
					"sender_name":"Anthony A Sierra",
					"recipient_name":"Shayan Yassami",
					"message_id":"2"
		    	   * 
		    	   * {
						"message":{
						"created_at":"2011-09-12T19:15:42Z",
						"body":"THis Is the body",
						"subject":"This is Subject",
						"recipient_id":208,
						"sender_id":206,
						"sender_name":"Anthony A Sierra",
						"recipient_name":"Shayan Yassami",
						"message_id":"10"
								}
						}
		    	   */
		    	  
		          String created_at = json_data.getString("created_at");
		          String abody = json_data.getString("body");
		          messagesubject = json_data.getString("subject");
		          String recipient_id = json_data.getString("recipient_id");
		          idtosendto = json_data.getString("sender_id");
		          String sender_name = json_data.getString("sender_name");
		          String recipient_name = json_data.getString("recipient_name");
		          message_id = json_data.getString("message_id");
		          setTexts(created_at, abody, messagesubject, idtosendto, sender_name, recipient_name);
		   }
		private void setTexts(String created_at, String abody, String asubject,
				String sender_id, String sender_name,String recipient_name) {
			//recipiant.setText(sender_name);
			sender.setText(sender_name);
			senderMessage1.setText(abody);
			recipiantButton.setText(sender_name);
			subject.setText(asubject);
			
		}
		private void sendMessage(String body, String messageable_id, String recipient_id, String messageable_type, String subject ){
	    	SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(Message.this); 
	        String key = prefs.getString("staffkey", null);
	        Log.d("TAG",key);
	        MyHttpClient client = new MyHttpClient(getApplicationContext());
	        
	        HttpPost post = new HttpPost("https://helium.staffittome.com/apis/create_message");
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("session_key", key));
	        nameValuePairs.add(new BasicNameValuePair("body", body));
	        nameValuePairs.add(new BasicNameValuePair("messageable_id", messageable_id));
	        nameValuePairs.add(new BasicNameValuePair("recipient_id", recipient_id));
	        nameValuePairs.add(new BasicNameValuePair("messageable_type", messageable_type));
	        nameValuePairs.add(new BasicNameValuePair("subject", subject));

	        try {
	        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			ResponseHandler<String> responseHandler=new BasicResponseHandler();
			String responseBody = client.execute(post, responseHandler);
			Log.d("TAG","SENDING A FUCKING MESSAGE TO: "+recipient_id+" WITH BODY: "+body+" AND SUBJECT"+subject+"RESPONSE IS"+responseBody);
			Toast.makeText(getApplicationContext(), "Message Sent!", 0).show();
	        } catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
}
