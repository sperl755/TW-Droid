package com.staff;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ListJobs extends Activity {
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

		
		job1 = (ImageButton)this.findViewById(R.id.imageButton451);
		job1.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(ListJobs.this, JobApply.class));
		}
	});
		String[] jobfeed = SearchTabMain.jobs;
		for (int i=0;i<jobfeed.length;i++){
			if (i==0){
				jobtitle1.setText(jobfeed[i]);
			} else if (i==1) {
				jobtitle2.setText(jobfeed[i]);
			} else if (i==2) {
				jobtitle3.setText(jobfeed[i]);
			} else if (i==3) {
				jobtitle4.setText(jobfeed[i]);
			} else if (i==4) {
				jobtitle5.setText(jobfeed[i]);
			} else if (i==5) {
				jobtitle6.setText(jobfeed[i]);
			} else if (i==6) {
				jobtitle7.setText(jobfeed[i]);
			} else if (i==7) {
				jobtitle8.setText(jobfeed[i]);
			} else if (i==8) {
				jobtitle9.setText(jobfeed[i]);
			} else if (i==9) {
				jobtitle10.setText(jobfeed[i]);
			} else if (i==10) {
				jobtitle11.setText(jobfeed[i]);
			} else if (i==11) {
				jobtitle12.setText(jobfeed[i]);
			} else if (i==12) {
				jobtitle13.setText(jobfeed[i]);
			} else if (i==13) {
				jobtitle14.setText(jobfeed[i]);
			} else if (i==14) {
				jobtitle15.setText(jobfeed[i]);
			} else if (i==15) {
			}
			
		}
		
}
}