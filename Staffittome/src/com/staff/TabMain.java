package com.staff;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.TabWidget;

public class TabMain extends TabActivity {

	private TabHost mTabHost;
	private Button notification;
	private Button notificationoff;
	private void setupTabHost() {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// construct the tabhost
		setContentView(R.layout.homepage);

		setupTabHost();
		mTabHost.getTabWidget().setDividerDrawable(R.drawable.menu_seperator);

	    addActivityTab(new TextView(this), R.drawable.home, new Intent(TabMain.this, DashboardActivity.class));
		addActivityTab(new TextView(this), R.drawable.search, new Intent(TabMain.this, SearchTabMain.class));
		addActivityTab(new TextView(this), R.drawable.staff_out, new Intent(TabMain.this, SYourself.class));
		addActivityTab(new TextView(this), R.drawable.jobs, new Intent(TabMain.this, CheckIn.class));
		addActivityTab(new TextView(this), R.drawable.profile, new Intent(TabMain.this, HomePage.class));
	
	
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
					startActivity(new Intent(TabMain.this, MessageInbox.class));
	          }
	      });
	      notificationoff.setOnClickListener(new OnClickListener() {
	          public void onClick(View v) {
					startActivity(new Intent(TabMain.this, MessageInbox.class));
	          }
	      });
	
	}
    private void addActivityTab(final View view, int iconResource, Intent intent) {
		View tabview = createTabView(mTabHost.getContext(), iconResource);
		TabSpec setContent = mTabHost.newTabSpec("").setIndicator(tabview)
				.setContent(intent);
		mTabHost.addTab(setContent);
	}
    
    // Use this method if you only want the tab to execute a method
    private void addMethodTab(final View view, int iconResource) {
		View tabview = createTabView(mTabHost.getContext(), iconResource);

		TabSpec setContent = mTabHost.newTabSpec("").setIndicator(tabview)
				.setContent(new TabContentFactory() {
					public View createTabContent(String tag) {
						return view;
					}
				});
		mTabHost.addTab(setContent);

	}
    
    private  View createTabView(final Context context, int iconResource) {
    	Typeface hb = Typeface.createFromAsset(getAssets(),
	      "fonts/hb.ttf");
		View view = LayoutInflater.from(context)
				.inflate(R.layout.main_tabs_bg, null);
		//TextView tv = (TextView) view.findViewById(R.id.tabsText);
		//tv.setTypeface(hb);
		//tv.setText(text);

		ImageView icon = (ImageView) view.findViewById(R.id.tabsIcon);
		icon.setImageResource(iconResource);

		return view;
	}
}


