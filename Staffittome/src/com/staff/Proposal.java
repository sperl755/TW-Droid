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

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Proposal extends Activity{
	private String capabilities;
	private String rate;
	private String email;
	private String subject;
	private String message;
	private EditText messagebox;
	private EditText emailedit;
	private SeekBar seekRate;
	private ImageButton sendproposal;
	private TextView seekRateValue;
	private String name;
	private String staffkey;
	private EditText proposalSubject;
	private String value;
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.staffmessage);
	       
	       emailedit = (EditText)this.findViewById(R.id.emailedit);
	       seekRate = (SeekBar)this.findViewById(R.id.seekRate);
	       sendproposal = (ImageButton)this.findViewById(R.id.sendProposalButton);	
	       seekRateValue = (TextView)this.findViewById(R.id.seekRateValue);
	       messagebox =(EditText)this.findViewById(R.id.proposalEdit);
	       proposalSubject = (EditText)this.findViewById(R.id.proposalSubject);
	       seekRate.setMax(100);
	       seekRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
	       public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                        {
	    	        	int real_value = (seekRate.getProgress()+8);
	    	            value = Integer.toString(real_value);
	    	            seekRateValue.setText("     $"+value); 
	    	            }
	                    public void onStartTrackingTouch(SeekBar seekBar) { }
	    	            public void onStopTrackingTouch(SeekBar seekBar) { }
	    	            });
	       
	       
			SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(Proposal.this); 
		    name = prefs.getString("name", null);
	        staffkey = prefs.getString("staffkey", null);
 
	       sendproposal.setOnClickListener(new View.OnClickListener()
	        {
	            public void onClick(View v)
	            {
	            	email=emailedit.getText().toString();
	            	message=messagebox.getText().toString();
	            	subject=proposalSubject.getText().toString();
	            	rate = value;
	     	       //sendEmail(email,"You've received a proposal from "+name+" on Staffittome!","You've recieved a proposal from"+name+" from Staffittome! "+name+"'s additional message: "+message);
	    	       sendProposal("Java Developing", rate, email, subject, message);

	            }
	        });
	       
	       
	   }
	private void sendProposal(String capabilities2, String rate2,
			String email2, String subject2, String message2) {
	        //Log.d("TAG",key);
	        MyHttpClient client = new MyHttpClient(Proposal.this);
	        
	        HttpPost post = new HttpPost("https://helium.staffittome.com/apis/create_proposal");
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("session_key", staffkey));
	        nameValuePairs.add(new BasicNameValuePair("capability_id",capabilities2));
	        nameValuePairs.add(new BasicNameValuePair("payment_method","1"));
	        nameValuePairs.add(new BasicNameValuePair("rate",rate2));
	        nameValuePairs.add(new BasicNameValuePair("email",email2));
	        nameValuePairs.add(new BasicNameValuePair("subject",subject2));
	        nameValuePairs.add(new BasicNameValuePair("message",message2));
	        
	        try {
	        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			ResponseHandler<String> responseHandler=new BasicResponseHandler();
			String responseBody = client.execute(post, responseHandler);
			Log.d("TAG","TEST RESULTS FROM HTTPS SEND PROPOSAL IS: "+responseBody);
			Toast.makeText(getApplicationContext(), responseBody, 0).show();
			
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}

	
	}
	private void sendEmail(String recipient, String subject, String message) {
	    try {
	        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
	        emailIntent.setType("plain/text");
	        if (recipient != null)  emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{recipient});
	        if (subject != null)    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
	        if (message != null)    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);

	        startActivity(Intent.createChooser(emailIntent, "Send mail..."));

	    } catch (ActivityNotFoundException e) {
	        // cannot send email for some reason
	    }
	}
}
