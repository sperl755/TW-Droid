package com.staff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class ListJobs extends Activity {
	private ImageButton job1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listjobs);

		job1 = (ImageButton)this.findViewById(R.id.imageButton451);
		job1.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(ListJobs.this, JobApply.class));
		}
	});
}
}