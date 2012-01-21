package com.staff;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
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
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

public class SearchMaps extends MapActivity {
	private EditText searchEdit;
	private TextView seekValue;
	private Button searchbutton;
	private Button removebutton;
	private String searchstuff;
	private String searchResponse;
	private String latstring=null;
	private String lngstring=null;
	private String radius=null;
	private String json;
	private double latitude = 00.0000000000000;
	private double longitude = 00.0000000000000;
	private static MapView mapView;
	private SeekBar seekSalary;
	private Spinner job_type;
	private String distance;
	private GeoPoint currentCenter;
	private String[] jobselections = {"All", "Part Time", "Full Time" };
	public static String[] jobtitles;// = new String[15];
	public static String[] jobids; //= new String[15];
	public static String[] companies; //= new String[15];
	public static String[] distances; // = new String[15];

	 MyItemizedOverlay myItemizedOverlay;
	 MyLocationOverlay myLocationOverlay;
		List<Overlay> mapOverlays;

	 
	   /** Called when the activity is first created. */
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.search);
	       

	       mapView = (MapView) findViewById(R.id.mapview);
	       LinearLayout zoomLayout =(LinearLayout)findViewById(R.id.layout_zoom);  
	       View zoomView = mapView.getZoomControls(); 
	       zoomLayout.addView(zoomView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)); 
	       mapView.displayZoomControls(true);	       	  
	       
		   mapOverlays = mapView.getOverlays();
			
	       MapController mc = mapView.getController();
	       mc.setZoom(11);
	       Drawable marker=getResources().getDrawable(android.R.drawable.star_big_on);
	       int markerWidth = marker.getIntrinsicWidth();
	       int markerHeight = marker.getIntrinsicHeight();
	       marker.setBounds(0, markerHeight, markerWidth, 0);
	      
	       myItemizedOverlay = new MyItemizedOverlay(marker, mapView, "", "");
	       mapView.getOverlays().add(myItemizedOverlay);
	       
	       myLocationOverlay = new MyLocationOverlay(this, mapView);
	       mapView.getOverlays().add(myLocationOverlay);
	       mapView.postInvalidate();
	       
	       LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);    
	       Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);  
	       	       
	       if (location !=null) 
	       {
	       latitude = location.getLatitude();
	       longitude = location.getLongitude();
	       latstring = Double.toString(latitude);
	       lngstring = Double.toString(longitude);
	       GeoPoint currloc = new GeoPoint((int)(latitude * 1e6), (int)(longitude * 1e6));
	       Log.d("TAG", "CURRENT LOCATION LATITUDE IS: "+latitude+"LONGITUDE IS: "+longitude);
	       mc.animateTo(currloc);

	       }
	       else {
	       currentCenter = mapView.getMapCenter();
           latitude = currentCenter.getLatitudeE6() / 1E6;
           longitude = currentCenter.getLatitudeE6() / 1E6;
	       latstring = Double.toString(latitude);
	       lngstring = Double.toString(longitude);
	       Log.d("TAG", "LOCATION NOT FETCHING RIGHT NOW USING MAP STUFF");	       
	       }
	     
	       	/*
	       	 * SETTING CURRENT JOBS WHEN YOU FIRST ENTER THE SEARCH
	       	 */
	       int lngspan = mapView.getLongitudeSpan();   
			Log.d("TAG", "LONGITUDE SPAN IS: "+lngspan);
			distance = Double.toString((lngspan*68.97/1000000));
	       //search("", latstring, lngstring,distance, SearchMaps.this);
	       final AsyncSearch asearch = new AsyncSearch(json, this);
	       asearch.execute("",latstring,lngstring,distance);

	       
	       InputMethodManager imm = (InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE);

	       searchEdit = (EditText)this.findViewById(R.id.editSearch);
	       searchEdit.requestFocus();
	       imm.showSoftInput(searchEdit, 0);
	       getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE); 
	       
	      removebutton = (Button)this.findViewById(R.id.removeButton);
	      removebutton.setVisibility(View.INVISIBLE);
	      removebutton.setBackgroundColor(Color.TRANSPARENT);

	      searchbutton = (Button)this.findViewById(R.id.searchButton);
	      searchbutton.setBackgroundColor(Color.TRANSPARENT);
	      
	        Drawable searchSearch = getResources().getDrawable(R.drawable.search_box);
			searchEdit.setBackgroundDrawable(searchSearch );
			searchEdit.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	                InputMethodManager keyboard = (InputMethodManager)
	                getSystemService(Context.INPUT_METHOD_SERVICE);
	                keyboard.showSoftInput(searchEdit, 0); 
	            }
	        },200);

			searchEdit.setOnTouchListener(new View.OnTouchListener() {
	        	@Override
				public boolean onTouch(View arg0, MotionEvent event) {
	        		
	        		((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
	        	    .showSoftInput(searchEdit, InputMethodManager.SHOW_FORCED);
						Drawable searchRemove = getResources().getDrawable(R.drawable.search_box_remove);
						searchEdit.setBackgroundDrawable(searchRemove );
				        searchbutton.setVisibility(View.INVISIBLE);
				        removebutton.setVisibility(View.VISIBLE);

						return true;

				}
			});
	        
	        removebutton.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
			       
	            	((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);
	            	Drawable searchSearch = getResources().getDrawable(R.drawable.search_box);
					searchEdit.setBackgroundDrawable(searchSearch );
	            	removebutton.setVisibility(View.INVISIBLE);
	            	searchbutton.setVisibility(View.VISIBLE);
	            	searchEdit.setText("");
			}
	        });
	        //final AsyncSearch s = new AsyncSearch(json, this);
	        searchEdit.setOnKeyListener(new EditText.OnKeyListener(){
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if(keyCode == KeyEvent.KEYCODE_ENTER){
                    	((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);
                    	searchstuff = searchEdit.getText().toString();
                    	//if (searchstuff.equals("")|| searchstuff.equals(" ")) {
                    	//	Toast.makeText(getApplicationContext(), "Please enter a valid term", 0).show();
                    	//	Log.d("TAG", "THE ENTERED TERM IS COMPLETE BULLSHIT");
                    	//	searchEdit.setText("");
                    	mapOverlays.clear();
                    	int lngspan = mapView.getLongitudeSpan();   
					Log.d("TAG", "LONGITUDE SPAN IS: "+lngspan);
					distance = Double.toString((lngspan*68.97/1000000));
					Log.d("TAG", "THE DISTANCE OF THE SCREEN STUFF IS: "+distance);
					currentCenter = mapView.getMapCenter();
					latitude = currentCenter.getLatitudeE6() / 1E6;
					longitude = currentCenter.getLongitudeE6() / 1E6;
					latstring = Double.toString(latitude);
					lngstring = Double.toString(longitude);       	     
					Log.d("TAG", "LATITUDE OF CURRENT POSTION IS: "+latstring+" LONGITUDE OF CURRENT POSTION IS:"+lngstring+"RADIUS IS :"+distance);
					  search(searchstuff, lngstring, latstring , distance, SearchMaps.this);
					try {
       				//parseResponse(json);
       			} catch (Exception e) {
       				// TODO Auto-generated catch block
       				e.printStackTrace();
       			}
                }
                
            	//searchEdit.setText(searchstuff);
                    
                    
                    return false;
                
                }

            });
	           
	   
	 
	   }
	  
	   
	  
  
	 public String getDistance(int lngspan){
		 String span = null;
		 double whatever = lngspan/1000000;
		 long whatevround = Math.round(whatever);
		 span = Long.toString(whatevround);	
		 return span;
	 }
	 @Override
	 protected boolean isLocationDisplayed() {
	  // TODO Auto-generated method stub
	  return false;
	 }

	 @Override
	 protected boolean isRouteDisplayed() {
	  // TODO Auto-generated method stub
	  return false;
	 }
	 

	 @Override
	 protected void onResume() {
	  // TODO Auto-generated method stub
	  super.onResume();
	  myLocationOverlay.enableMyLocation();
	  myLocationOverlay.enableCompass();
	 }

	 @Override
	 protected void onPause() {
	  // TODO Auto-generated method stub
	  super.onPause();
	  myLocationOverlay.disableMyLocation();
	  myLocationOverlay.disableCompass();

	  /*
	  for (int i=0;i<jobtitles.length;i++){
		  if (jobtitles[i]!=null){
	  SearchTabMain.jobs[i] = jobtitles[i];
		  } else 
			  SearchTabMain.jobs[i]="";
		  }
	  for (int i=0;i<companies.length;i++){
		  if (companies[i]!=null){
	  SearchTabMain.companies[i] = companies[i];
		  } else 
			  SearchTabMain.companies[i]="";
		  }
	  for (int i=0;i<jobids.length;i++){
		  if (jobids[i]!=null){
	  SearchTabMain.jobids[i] = jobids[i];
		  } else 
			  SearchTabMain.jobids[i]="";
		  }
	  for (int i=0;i<distances.length;i++){
		  if (distances[i]!=null){
	  SearchTabMain.distances[i] = distances[i];
		  } else 
			  SearchTabMain.distances[i]="";
		  }
	 */
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
				//Log.d("TAG","TEST RESULTS FROM HTTPS JOB DISCOVERY IS: "+responseBody);
				parseResponse(responseBody, c);
			//responseGet = client.execute(get);
            //HttpEntity resEntityGet = responseGet.getEntity();    
            //InputStream instream = resEntityGet.getContent();
            
            //String result= convertBrToString(instream);
			//json = responseBody;
            //instream.close();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FacebookError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			return responseBody;
            }
             
	    
	  public String convertBrToString(InputStream in) 
	  {
	          
	          BufferedReader br;
	          StringBuffer outString = new StringBuffer();
	          br = new BufferedReader(new InputStreamReader(in)); 
	          try {
	        	  String read;
	        	  read = br.readLine();
	          while(read != null)
	          {
	              outString.append(read);
	              read =br.readLine();
	          }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	          return outString.toString();     
	  }

	public static void parseResponse(String stuff, Context c) throws Exception, FacebookError{
		  JSONObject jresult;
          JSONArray jobsarray;
		  JSONObject json_data = null;
		  JSONTokener tokener = new JSONTokener(stuff);
          jresult = new JSONObject(tokener);
		  jobsarray = jresult.getJSONArray("jobs");
          Log.d("TAG", "IN PARSE RESPONSE");
          //jsearch = jresult.get
          int max = jobsarray.length();
		  for (int i=0; i<jobsarray.length(); i++) { //Runs through the Job postings for as long as the array is

    	  json_data = jobsarray.getJSONObject(i); //For each json object
    	  
          //JSONObject job = json_data.getJSONObject(""); // for each json object within that object named job, parse the below

          String job_type_title = json_data.getString("job_type_title");
          String start_date = json_data.getString("start_date");
          String title = json_data.getString("title");
          String created_at = json_data.getString("created_at");
          String company = json_data.getString("company_name");
          String longitude = json_data.getString("longitude");
          String latitude = json_data.getString("latitude");
          String industry_name = json_data.getString("industry_name");
          String distance = json_data.getString("distance");
          String id = json_data.getString("id");
         
          
          
          Log.d("TAG", "Longitude is: "+longitude+"Latitude is: "+latitude+" and jobid is "+id);
          
          plot(latitude, longitude, title, company, job_type_title, industry_name, id, c, max, i, distance);
          
		  }
		  }
	  public static void plot(String lat,String lng, String title, String company, String jobtype, String industryname, String jobid, Context c, int max, int i, String distance){
		  double doublat = Double.parseDouble(lat);
		  double doublng = Double.parseDouble(lng);

		  List<Overlay> mapOverlays;
		  Drawable drawable;
		  //MyItemizedOverlay itemizedOverlay;
		  String dist = distance;
		  Double dubdist = Double.parseDouble(dist);
		  long num = (Math.round(dubdist));
		  dist = Long.toString(num);
		  
		  drawable = c.getResources().getDrawable(R.drawable.map_marker);
		  MyItemizedOverlay itemizedOverlay = new MyItemizedOverlay(drawable, mapView, jobid, dist);		  
		  
		  GeoPoint myPoint1 = new GeoPoint((int)(doublat * 1e6), (int)(doublng * 1e6));
		  OverlayItem overlayItem = new OverlayItem(myPoint1, title+" at "+company,jobtype+" / "+industryname);
		  itemizedOverlay.addOverlay(overlayItem);
		  mapView.getOverlays().add(itemizedOverlay);	       
		  jobtitles[i]=title;
		  companies[i]=company;
		  distances[i]=dist;
		  jobids[i]=jobid;
	       MapController mc = mapView.getController();
	       //mc.animateTo(myPoint1);
	  }
}
	
	  
