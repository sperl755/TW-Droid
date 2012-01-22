package com.staff;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListJobs extends Activity {
	/*
	private ImageButton job1;
	private TextView jobtitle1;
	private TextView jobtitle2;
	private TextView jobtitle3;
	private TextView jobtitle4;
	private TextView jobtitle5;
	private TextView jobtitle6;
	private TextView jobtitle7;
	private TextView jobtitle8;
	private TextView jobtitle9;
	private TextView jobtitle10;
	private TextView jobtitle11;
	private TextView jobtitle12;
	private TextView jobtitle13;
	private TextView jobtitle14;
	private TextView jobtitle15;
	private TextView company1;
	private TextView company2;
	private TextView company3;
	private TextView company4;
	private TextView company5;
	private TextView company6;
	private TextView company7;
	private TextView company8;
	private TextView company9;
	private TextView company10;
	private TextView company11;
	private TextView company12;
	private TextView company13;
	private TextView company14;
	private TextView company15;
	private TextView dist1;
	private TextView dist2;
	private TextView dist3;
	private TextView dist4;
	private TextView dist5;
	private TextView dist6;
	private TextView dist7;
	private TextView dist8;
	private TextView dist9;
	private TextView dist10;
	private TextView dist11;
	private TextView dist12;
	private TextView dist13;
	private TextView dist14;
	private TextView dist15;
	*/
	private EditText searchEdit;
	private String searchstuff;
	private Button searchbutton;
	private Button removebutton;
	private TableLayout searchtable;
    private static View child;
    private static TextView jobtitle;
    private static TextView jobdesc;
    private static TextView distance;


	



	//private static String[] jobfeed;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listjobs);

		
		/*
		 * SETTING TEXTVIEWS & MORE
		 */
		Typeface hm = Typeface.createFromAsset(getAssets(),"fonts/hm.ttf");
	    Typeface hb = Typeface.createFromAsset(getAssets(),"fonts/hb.ttf");
		/*
		jobtitle1=(TextView)this.findViewById(R.id.jobtitle1);
		jobtitle1.setTypeface(hm);
		jobtitle2=(TextView)this.findViewById(R.id.jobtitle2);
		jobtitle2.setTypeface(hm);
		jobtitle3=(TextView)this.findViewById(R.id.jobtitle3);
		jobtitle3.setTypeface(hm);
		jobtitle4=(TextView)this.findViewById(R.id.jobtitle4);
		jobtitle4.setTypeface(hm);
		jobtitle5=(TextView)this.findViewById(R.id.jobtitle5);
		jobtitle5.setTypeface(hm);
		jobtitle6=(TextView)this.findViewById(R.id.jobtitle6);
		jobtitle6.setTypeface(hm);
		jobtitle7=(TextView)this.findViewById(R.id.jobtitle7);
		jobtitle7.setTypeface(hm);
		jobtitle8=(TextView)this.findViewById(R.id.jobtitle8);
		jobtitle8.setTypeface(hm);
		jobtitle9=(TextView)this.findViewById(R.id.jobtitle9);
		jobtitle9.setTypeface(hm);
		jobtitle10=(TextView)this.findViewById(R.id.jobtitle10);
		jobtitle10.setTypeface(hm);
		jobtitle11=(TextView)this.findViewById(R.id.jobtitle11);
		jobtitle11.setTypeface(hm);
		jobtitle12=(TextView)this.findViewById(R.id.jobtitle12);
		jobtitle12.setTypeface(hm);
		jobtitle13=(TextView)this.findViewById(R.id.jobtitle13);
		jobtitle13.setTypeface(hm);
		jobtitle14=(TextView)this.findViewById(R.id.jobtitle14);
		jobtitle14.setTypeface(hm);
		jobtitle15=(TextView)this.findViewById(R.id.jobtitle15);
		jobtitle15.setTypeface(hm);
		company1=(TextView)this.findViewById(R.id.company1);
		company1.setTypeface(hm);
		company2=(TextView)this.findViewById(R.id.company2);
		company2.setTypeface(hm);
		company3=(TextView)this.findViewById(R.id.company3);
		company3.setTypeface(hm);
		company4=(TextView)this.findViewById(R.id.company4);
		company4.setTypeface(hm);
		company5=(TextView)this.findViewById(R.id.company5);
		company5.setTypeface(hm);
		company6=(TextView)this.findViewById(R.id.company6);
		company6.setTypeface(hm);
		company7=(TextView)this.findViewById(R.id.company7);
		company7.setTypeface(hm);
		company8=(TextView)this.findViewById(R.id.company8);
		company8.setTypeface(hm);
		company9=(TextView)this.findViewById(R.id.company9);
		company9.setTypeface(hm);
		company10=(TextView)this.findViewById(R.id.company10);
		company10.setTypeface(hm);
		company11=(TextView)this.findViewById(R.id.company11);
		company11.setTypeface(hm);
		company12=(TextView)this.findViewById(R.id.company12);
		company12.setTypeface(hm);
		company13=(TextView)this.findViewById(R.id.company13);
		company13.setTypeface(hm);
		company14=(TextView)this.findViewById(R.id.company14);
		company14.setTypeface(hm);
		company15=(TextView)this.findViewById(R.id.company15);
		company15.setTypeface(hm);
		dist1=(TextView)this.findViewById(R.id.dist1);
		dist1.setTypeface(hm);
		dist2=(TextView)this.findViewById(R.id.dist2);
		dist2.setTypeface(hm);
		dist3=(TextView)this.findViewById(R.id.dist3);
		dist3.setTypeface(hm);
		dist4=(TextView)this.findViewById(R.id.dist4);
		dist4.setTypeface(hm);
		dist5=(TextView)this.findViewById(R.id.dist5);
		dist5.setTypeface(hm);
		dist6=(TextView)this.findViewById(R.id.dist6);
		dist6.setTypeface(hm);
		dist7=(TextView)this.findViewById(R.id.dist7);
		dist7.setTypeface(hm);
		dist8=(TextView)this.findViewById(R.id.dist8);
		dist8.setTypeface(hm);
		dist9=(TextView)this.findViewById(R.id.dist9);
		dist9.setTypeface(hm);
		dist10=(TextView)this.findViewById(R.id.dist10);
		dist10.setTypeface(hm);
		dist11=(TextView)this.findViewById(R.id.dist11);
		dist11.setTypeface(hm);
		dist12=(TextView)this.findViewById(R.id.dist12);
		dist12.setTypeface(hm);
		dist13=(TextView)this.findViewById(R.id.dist13);
		dist13.setTypeface(hm);
		dist14=(TextView)this.findViewById(R.id.dist14);
		dist14.setTypeface(hm);
		dist15=(TextView)this.findViewById(R.id.dist15);
		dist15.setTypeface(hm);
		*/
		  searchtable = (TableLayout)this.findViewById(R.id.searchtable);

		
	
		
	       searchEdit = (EditText)this.findViewById(R.id.editSearch);
	       searchEdit.requestFocus();
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
	        searchEdit.setOnKeyListener(new EditText.OnKeyListener(){
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if(keyCode == KeyEvent.KEYCODE_ENTER){
                    	((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(searchEdit.getWindowToken(), 0);
                    	searchstuff = searchEdit.getText().toString();           
					  SearchMaps.search(searchstuff, "", "" , "", ListJobs.this);
					try {
       			} catch (Exception e) {
       				e.printStackTrace();
       			}
                }
                    return false;
                
                }

            });
		}

	@Override
	 protected void onResume() {
	  // TODO Auto-generated method stub
	  super.onResume();
	  
	  searchtable.removeAllViewsInLayout();
	  searchtable.removeAllViews();
	 
	  	List<String> companyfeed = SearchMaps.companies;
		List<String>jobfeed = SearchMaps.jobtitles;
		final List<String> distfeed = SearchMaps.distances;
		final List<String> idfeed = SearchMaps.jobids;
		for (int i=0;i<jobfeed.size();i++){
			  final int z = i;
			   child = getLayoutInflater().inflate(R.layout.row, null);
		       jobtitle = (TextView)child.findViewById(R.id.jobtitle);
		       jobdesc = (TextView)child.findViewById(R.id.jobdesc);
		       distance = (TextView)child.findViewById(R.id.jobAppStatus2);
		       child.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						startJobApply(idfeed.get(z),distfeed.get(z));
					}
				});
		       searchtable.addView(child);	        
		       jobtitle.setText(jobfeed.get(i));
		       jobdesc.setText(companyfeed.get(i));
		       String tempdistance =distfeed.get(i);
			   tempdistance = tempdistance.substring(0, tempdistance.indexOf(".")+2);
		       distance.setText(tempdistance);
		       
		       if (i==jobfeed.size()-1) {
		       ImageView applybg = (ImageView)child.findViewById(R.id.applybg);
		       Drawable d = getResources().getDrawable(R.drawable.module_row_last);
		       applybg.setBackgroundDrawable(d);
			
	 }
		}
	}
	public void startJobApply(String id,String distance) {
		 Intent intent =new Intent(this, JobApply.class);
		   intent.putExtra("jobid", id);
		   intent.putExtra("distance", distance);
 		this.startActivity(intent);
	}
}

