package com.staff;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

public class StaffToFriend extends ListActivity {
	private String[] names;
	private String name;
	private String id;
	private JSONArray jArray;
	private JSONObject json_data;
	private String idsend;
	private AutoCompleteTextView autosearch;
    Facebook facebook = new Facebook("187212574660004");

	/** Called when the activity is first created. */
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		// Create an array of Strings, that will be put to our ListActivity

		// Use your own layout and point the adapter to the UI elements which
		// contains the label
		try {
			getFriendArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.setListAdapter(new ArrayAdapter<String>(this, R.layout.stafftofriend, R.id.label, names));

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
  	  	try {
			json_data = jArray.getJSONObject(position);
			idsend = json_data.getString("id");
			Log.d("TAG", "THE FACEBOOK ID OF THE NIGGRO IS: "+idsend);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	     
		//Toast.makeText(this, "You selected: " + keyword, Toast.LENGTH_LONG)
		//		.show();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Want To Staff "+keyword+" ?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               postToWall(idsend); 
		        	   StaffToFriend.this.finish();
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.show();

	}

	
	public void getFriendArray() throws IOException{
		Bundle params = new Bundle();
		SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(StaffToFriend.this); 
	    String access_token = prefs.getString("access_token", null);
        Log.d("TAG", access_token);
    	params.putString(Facebook.TOKEN, access_token);

        

	    try
	    {
	    	 
	    	  JSONObject response = Util.parseJson(facebook.request("/me/friends", params, "GET")); // Get a friend information from facebook
	    	  jArray = response.getJSONArray("data");
	    	  names = new String[jArray.length()];
	    	  for (int i=0; i<jArray.length(); i++){
	    	  json_data = jArray.getJSONObject(i);
	    	  id = json_data.getString("id");  // Going to use this for the promotion function, cehck this out later
	    	  name = json_data.getString("name");
	    	  Log.d("TAG", name);
	    	  Log.d("TAG", id);
	    	  names[i] = name;
	    	  }
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
	public void postToWall(String id) {
		 Log.d("Tests", "Testing graph API wall post");
		 SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(StaffToFriend.this); 
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
	
}
