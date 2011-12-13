package com.staff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class StaffActivity extends Activity {
    /** Called when the activity is first created. */

	private ImageButton btnFacebook;

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        setContentView(R.layout.main);
	        btnFacebook = (ImageButton)findViewById(R.id.facebook_button);

	        btnFacebook.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					startActivity(new Intent(StaffActivity.this, FacebookActivity.class));

				}
			});
	        
	    }	 
	 

}
	