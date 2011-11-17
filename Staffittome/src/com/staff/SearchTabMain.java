package com.staff;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;


public class SearchTabMain extends TabActivity {
	public static String[] jobs = new String[15];
	public static String[] jobids = new String[15];
	public static String[] companies = new String[15];
	public static String[] distances = new String[15];

	private TabHost mTabHost;

	private void setupTabHost() {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// construct the tabhost
		setContentView(R.layout.searchtab);

		
		setupTabHost();
		mTabHost.getTabWidget().setDividerDrawable(R.drawable.menu_seperator);

    	addActivityTab(new TextView(this), "Map", new Intent(SearchTabMain.this, SearchMaps.class));
		addActivityTab(new TextView(this), "List",  new Intent(SearchTabMain.this, ListJobs.class));
		addActivityTab(new TextView(this), "Options", new Intent(SearchTabMain.this, JobOptions.class));

	}
	private void addActivityTab(final View view, final String tag, Intent intent) {
		View tabview = createTabView(mTabHost.getContext(), tag);
		 
		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview)
				.setContent(intent);
		mTabHost.addTab(setContent);

	}
	 private View createTabView(final Context context, final String text) {
		 Typeface hb = Typeface.createFromAsset(getAssets(),
	      "fonts/hb.ttf");
			View view = LayoutInflater.from(context)
					.inflate(R.layout.tabs_bg, null);
			TextView tv = (TextView) view.findViewById(R.id.tabsText);
			tv.setTypeface(hb);
			tv.setText(text);


			return view;
		}
	   
}

