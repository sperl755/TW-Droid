package com.staff;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
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

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class StaffTasks{
	private static String key;
	private static String staffkey;
	private static String user_id;
	private static String messages;
    private static Facebook facebook = new Facebook("187212574660004");
	private static String name;
	private static String id;
	private static ImageView user_picture;
	private static String facebook_uid;
	private static String email;
	private static String first_name;
	private static String last_name;
	private static String link;
	private static String birthday = "00/00/00";
	private static String gender;
	private static String locale;
	private static int numfriends;
	private static String appliedjobs;
	private static String friendnum;
	private static String proposals;
	private static String capabilities;
	private static String profdetails;
	private static Bitmap userpic;
	
	public static String getProfDetails(Context c) {
		try {
		URI url = new URI("https://helium.staffittome.com/apis/"+user_id+"/profile_details");
		
	    HttpGet get = new HttpGet(url);
	    HttpClient client = new MyHttpClient(c);
	    ResponseHandler<String> responseHandler=new BasicResponseHandler();
	    profdetails = client.execute(get, responseHandler);
	    Log.d("TAG", "RESPONSE STRING FROM HTTPS GET PROFILE DETAILS IS: "+profdetails);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
	    } catch (URISyntaxException e) {
			e.printStackTrace();
		}
	    return profdetails;
	}
	
	public static String getProposals(Context c) {
		try {
		URI url = new URI("https://helium.staffittome.com/apis/"+user_id+"/list_proposal");
		
	    HttpGet get = new HttpGet(url);
	    HttpClient client = new MyHttpClient(c);
	    ResponseHandler<String> responseHandler=new BasicResponseHandler();
	    proposals = client.execute(get, responseHandler);
	    //parseResponse(responseBody);
	    Log.d("TAG", "RESPONSE STRING FROM HTTPS GET PROPOSAL IS: "+proposals);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
	    } catch (URISyntaxException e) {
			e.printStackTrace();
		}
	    return proposals;
	}
   
	   //Make this async or get it passed from other activity...
   private static void getCapabilities(Context c) {
		SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(c); 
        String key = prefs.getString("staffkey", null);
        Log.d("TAG",key);
    	        MyHttpClient client = new MyHttpClient(c);
    	        
    	        HttpPost post = new HttpPost("https://helium.staffittome.com/apis/capability_list");
    	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
    	        nameValuePairs.add(new BasicNameValuePair("session_key", key));
    	        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));

    	        try {
    	        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    			ResponseHandler<String> responseHandler=new BasicResponseHandler();
    			capabilities = client.execute(post, responseHandler);
    			Log.d("TAG","TEST RESULTS FROM HTTPS CAPABILITY LIST IS: "+capabilities);
    			//parseResponse(responseBody);

    			} catch (ClientProtocolException e) {
    				e.printStackTrace();
    			} catch (IOException e) {
    				e.printStackTrace();
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
   }
   public static String checkInCheckOut(Context c, String status_notes, String is_manual, String checkin_or_checkout, String start_datetime, String end_datetime, String contract_id) {
		SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(c); 
       String key = prefs.getString("staffkey", null);
       Log.d("TAG",key);
       String checkreturn=null;
   	        MyHttpClient client = new MyHttpClient(c);
   	        
   	        HttpPost post = new HttpPost("https://helium.staffittome.com/apis/checkin_checkout");
   	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
   	        nameValuePairs.add(new BasicNameValuePair("session_key", key));
   	        nameValuePairs.add(new BasicNameValuePair("status_notes", status_notes));
   	        nameValuePairs.add(new BasicNameValuePair("is_manual", is_manual));
   	        nameValuePairs.add(new BasicNameValuePair("checkin_or_checkout", checkin_or_checkout));
   	        nameValuePairs.add(new BasicNameValuePair("start_datetime", start_datetime));
   	        nameValuePairs.add(new BasicNameValuePair("end_datetime", end_datetime));
   	        nameValuePairs.add(new BasicNameValuePair("contract_id", contract_id));


   	        try {
   	        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
   			ResponseHandler<String> responseHandler=new BasicResponseHandler();
   			checkreturn = client.execute(post, responseHandler);
   			Log.d("TAG","TEST RESULTS FROM HTTPS CAPABILITY LIST IS: "+checkreturn);
   			//parseResponse(responseBody);

   			} catch (ClientProtocolException e) {
   				e.printStackTrace();
   			} catch (IOException e) {
   				e.printStackTrace();
   			} catch (ParseException e) {
   				e.printStackTrace();
   			}
   			return checkreturn;
  }
   
   
   
   private void viewApplicationStatus(String appid, Context c) {
		SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(c); 
       String key = prefs.getString("staffkey", null);
       Log.d("TAG",key);
       
       MyHttpClient client = new MyHttpClient(c);
       HttpPost post = new HttpPost("https://helium.staffittome.com/apis/"+appid+"/view_applciation");
       List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
       nameValuePairs.add(new BasicNameValuePair("session_key", key));
       try {
       post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		ResponseHandler<String> responseHandler=new BasicResponseHandler();
		String responseBody = client.execute(post, responseHandler);
		Log.d("TAG","TEST RESULTS FROM HTTPS VIEW APPLICATION STATUS WITH APP ID: "+appid+" IS: "+responseBody);
		
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}		
   
public static String jobDiscovery(Context c) {
	String discjobs = null;
    Log.d("TAG",staffkey);
    MyHttpClient client = new MyHttpClient(c);
    
    HttpPost post = new HttpPost("https://helium.staffittome.com/apis/job_suggestion");
    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
    nameValuePairs.add(new BasicNameValuePair("session_key", staffkey));

    try {
    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	ResponseHandler<String> responseHandler=new BasicResponseHandler();
	discjobs = client.execute(post, responseHandler);
	DashboardActivity.discjobs = discjobs;
	Log.d("TAG","TEST RESULTS FROM HTTPS JOB DISCOVERY IS: "+discjobs);
	//parseResponse(discjobs,0);
	} catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return discjobs;

}	

public static String getAvailability(Context c) {
    String responseBody = "bannana";
    String t = "true";
    try {
	URI url = new URI("https://helium.staffittome.com/apis/"+user_id+"/get_availability");
	
    HttpGet get = new HttpGet(url);
    HttpClient client = new MyHttpClient(c);
    ResponseHandler<String> responseHandler=new BasicResponseHandler();
    responseBody = client.execute(get, responseHandler);
 	
    Log.d("TAG", "RESPONSE STRING FROM HTTPS GET AVAILABILITY: "+responseBody);
	} catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
    } catch (URISyntaxException e) {
		e.printStackTrace();
	}
    return responseBody;
    
}	
public static String getMessages(Context c) {
    Log.d("TAG",key);
    		String inbox = null;
	        MyHttpClient client = new MyHttpClient(c);
	        HttpPost post = new HttpPost("https://helium.staffittome.com/apis/inbox");
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("session_key", key));
	        try {
	        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			ResponseHandler<String> responseHandler=new BasicResponseHandler();
			inbox = client.execute(post, responseHandler);
			Log.d("TAG","TEST RESULTS FROM HTTPS INBOX IS: "+inbox);
		
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return inbox;
}
public static String viewContracts(Context c) {
	SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(c); 
	String contracts = null; 
    String key = prefs.getString("staffkey", null);
	       Log.d("TAG",key);
	        MyHttpClient client = new MyHttpClient(c);
	        
	        HttpPost post = new HttpPost("https://helium.staffittome.com/apis/my_job_contracts");
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("session_key", key));
	        try {
	        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			ResponseHandler<String> responseHandler=new BasicResponseHandler();
			contracts = client.execute(post, responseHandler);
			Log.d("TAG","TEST RESULTS FROM HTTPS VIEW CONTRACTS IS: "+contracts);
			
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return contracts;
}
public static String viewAppliedJobs(Context c) {
	SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(c); 
    String key = prefs.getString("staffkey", null);
    appliedjobs = null;
	       Log.d("TAG",key);
	        MyHttpClient client = new MyHttpClient(c);
	        
	        HttpPost post = new HttpPost("https://helium.staffittome.com/apis/applied_jobs");
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("session_key", key));
	        try {
	        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			ResponseHandler<String> responseHandler=new BasicResponseHandler();
			appliedjobs = client.execute(post, responseHandler);
			Log.d("TAG","TEST RESULTS FROM HTTPS VIEW APPLIED JOBS IS: "+appliedjobs);
			//parseResponse(responseBody);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return appliedjobs;
}		
public static Bitmap getUserPic(){
  		userpic=null;
  	  	URL url1 = null;
  	  	try {
    
        url1 = new URL("http://graph.facebook.com/"+facebook_uid+"/picture?type=large");
  		userpic = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
  		} catch (IOException e) {
  			e.printStackTrace();
  		}
  		return userpic;
}

public static String getInfo(String facebook_key, Context c){
  	Bundle params = new Bundle();
  	Bitmap icon = null;
  		Log.d("TAG", facebook_key);
  		params.putString(Facebook.TOKEN, facebook_key);
  		JSONObject json_data = null;

     
	    try
	    {
	    	 
	        JSONObject response = Util.parseJson(facebook.request("me", params));
	        name = response.getString("name");	        
	        id = response.getString("id");

	    	//user_picture.setScaleType( ScaleType.CENTER_CROP );
	    	//user_picture.setImageBitmap(mIcon1);
	    	 
	        facebook_uid = response.getString("id");
	        email = response.getString("email");
	        first_name = response.getString("first_name");
	        last_name = response.getString("last_name");
	        link = response.getString("link");
	        if (response.has("birthday")==true){
	        birthday = response.getString("birthday");
	        }
	        gender = response.getString("gender");
	        locale = response.getString("locale");
	    	Log.d("TAG",name);
	    	Log.d("TAG",email);
	    	Log.d("TAG",first_name);
	    	Log.d("TAG",last_name);
	    	Log.d("TAG",birthday);
	    	Log.d("TAG",locale);
	    	Log.d("TAG",gender);
	    	Log.d("TAG",facebook_uid);
	        
	   	Log.d("TAG", "RUNNING RAJ's FACEBOOK LOGIN  API");
		SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(c); 
		Editor editor = prefs.edit();
		editor.remove("staffkey");
		editor.commit();
		String responseBody = null;
		HttpPost post = new HttpPost("https://helium.staffittome.com/apis/fb_login");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(10);
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("name", name));
		nameValuePairs.add(new BasicNameValuePair("first_name", first_name));
		nameValuePairs.add(new BasicNameValuePair("last_name", last_name));
		nameValuePairs.add(new BasicNameValuePair("birthday", birthday));
		nameValuePairs.add(new BasicNameValuePair("locale", locale));
		nameValuePairs.add(new BasicNameValuePair("gender", gender));
		nameValuePairs.add(new BasicNameValuePair("facebook_uid", facebook_uid));
		nameValuePairs.add(new BasicNameValuePair("facebook_session_key", facebook_key));
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpClient client = new MyHttpClient(c);
			ResponseHandler<String> responseHandler=new BasicResponseHandler();
			responseBody = client.execute(post, responseHandler);

			JSONObject jresult;
			JSONTokener tokener = new JSONTokener(responseBody);
			jresult = new JSONObject(tokener);
			Log.d("TAG", "IN PARSE RESPONSE");

			staffkey = jresult.getString("session_key");
			user_id = jresult.getString("user_id");
	  		prefs.edit().putString("name", name).commit();
			prefs.edit().putString("facebook_uid", facebook_uid).commit();
	   	prefs.edit().putString("staffkey", staffkey).commit();
	   	prefs.edit().putString("staffuser", user_id).commit();
	       key = prefs.getString("staffkey", null);

	   	Log.d("TAG", "The StaffitToMe key for your generated session is "+staffkey+"And the userid is "+user_id);
		} catch (ParseException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		} catch (JSONException e) {
		e.printStackTrace();
	    }
	    catch (FacebookError e)
	    {
	        e.printStackTrace();
	    }
		return name;
	}



public static String getFriendCount(String facebook_key) throws IOException, FacebookError{
 Bundle params = new Bundle();
 friendnum = null;
 Log.d("TAG", "INSIDE CONNECTION #");
	params.putString(Facebook.TOKEN, facebook_key);
    try
    {
    	  JSONObject response = Util.parseJson(facebook.request("/me/friends", params, "GET")); // Get a friend information from facebook
    	  JSONArray jArray = response.getJSONArray("data");
    	  numfriends = jArray.length();
    	  friendnum = Integer.toString(numfriends);
    	  Log.d("TAG","FRIEND COUNT IS: "+numfriends);
    }
    	  catch (JSONException e)
  	    {
  	        e.printStackTrace();
  	    }		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FacebookError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return friendnum;
    }

public static void setAvailability(String availstatus, Context c) {
	SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(c); 
    String key = prefs.getString("staffkey", null);
    Log.d("TAG",key);
    MyHttpClient client = new MyHttpClient(c);
    
    HttpPost post = new HttpPost("https://helium.staffittome.com/apis/available");
    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
    nameValuePairs.add(new BasicNameValuePair("session_key", key));
    nameValuePairs.add(new BasicNameValuePair("lat","34.4358333"));
    nameValuePairs.add(new BasicNameValuePair("long","-119.8266667"));
    nameValuePairs.add(new BasicNameValuePair("is_available",availstatus));

    try {
    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	ResponseHandler<String> responseHandler=new BasicResponseHandler();
	String responseBody = client.execute(post, responseHandler);
	Log.d("TAG","TEST RESULTS FROM HTTPS AVAILABILITY IS: "+responseBody);
	
	} catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}


}

public static String getMessageDetail(String messageid, Context c) {
	SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(c); 
    String key = prefs.getString("staffkey", null);
    Log.d("TAG",key);
    MyHttpClient client = new MyHttpClient(c);
    String responseBody = null;
    
    HttpPost post = new HttpPost("https://helium.staffittome.com/apis/"+messageid+"/view_message");
    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
    nameValuePairs.add(new BasicNameValuePair("session_key", key));
    try {
    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	ResponseHandler<String> responseHandler=new BasicResponseHandler();
	responseBody = client.execute(post, responseHandler);
	Log.d("TAG","TEST RESULTS FROM HTTPS VIEW MESSAGE DETAILS WITH MESSAGE ID "+messageid+" IS: "+responseBody);
    } catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	} 
	return responseBody;
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
	public static String getCompanyDetail(String companyid, Context c) {
			String companydetail = null;
		try {
			URI url = new URI("https://helium.staffittome.com/apis/"+companyid+"/view_company");
			
		    HttpGet get = new HttpGet(url);
		    HttpClient client = new MyHttpClient(c);
		    ResponseHandler<String> responseHandler=new BasicResponseHandler();
		    companydetail = client.execute(get, responseHandler);
		    Log.d("TAG", "RESPONSE STRING FROM HTTPS VIEW COMPANY IS :"+companydetail);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
		    } catch (URISyntaxException e) {
				e.printStackTrace();
			}
		    return companydetail;
	}	
	
	 public static String search(String terms, String lng, String lat, String rad, Context c) {
	    	SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(c); 
	        String key = prefs.getString("staffkey", null);
	        Log.d("TAG",key);
	        String responseBody = null;
	        String trimmed = terms.trim();
	        String getUrl = ("https://helium.staffittome.com/apis/search/"+trimmed+"?latitude="+lat+"&longitude="+lng+"&distance="+rad);
	        Log.d("TAG","URL USED FOR SEARCHING: "+getUrl);
	        HttpClient client = new MyHttpClient(c);
	        HttpGet get = new HttpGet(getUrl);
         HttpResponse responseGet = null;
			try {
				ResponseHandler<String> responseHandler=new BasicResponseHandler();
				responseBody = client.execute(get, responseHandler);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			return responseBody;
         }
 
public String returnJobs(){
	return appliedjobs;
}
public String returnFriendNum(){
	return friendnum;
}
public String returnProposal(){
	return proposals;
}
public String returnProfInfo(){
	return profdetails;
}
public String returnCapabilities(){
	
	return capabilities;
}
public String returnMessages(){
	return messages;
}
public Bitmap returnProfImage(){
	return userpic;
}
}
