package com.staff;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ManualTime extends Activity {
	private static final int PICTURE_RESULT = 0;
	private TextView jobtitle;
	private TextView jobdescription;
	private Button picture;
	private Button video;
	private ToggleButton manAudio;
	private Button submit;
	private Button cancel;
	private Uri imageUri;
	private ImageView v;
	private MediaRecorder recorder = new MediaRecorder();
	private TextView recordStatus;
	private Chronometer chron;
	private MediaPlayer player;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manualtime);
        chron = (Chronometer)this.findViewById(R.id.chronometer1);
        recordStatus = (TextView)this.findViewById(R.id.recordStatus);

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        File outputFile = getFileStreamPath("output.amr");
        Uri target = Uri.parse(outputFile.getAbsolutePath());
        recorder.setOutputFile(outputFile.getAbsolutePath());
        try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        picture = (Button)this.findViewById(R.id.picButton); 
        picture.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	startCamera();
                }
        });
        video = (Button)this.findViewById(R.id.vidButton);
        video.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            		//Do Something
            	}
        });
        manAudio = (ToggleButton)this.findViewById(R.id.manAudio);
        manAudio.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                if (manAudio.isChecked()) {
                	chron.start();
                	recorder.start();
                	} else {
                		chron.stop();
                		recorder.stop();
                		recorder.release();
                        Toast.makeText(ManualTime.this, "Replaying Your Recorded Message", Toast.LENGTH_SHORT).show();
                		  player = new MediaPlayer();
                	        try {
                	         File inputFile = getFileStreamPath("output.amr");
                	         Uri inputFilePath = Uri.parse(inputFile.getAbsolutePath());

                	         File file = new File(inputFilePath.getPath());
                	         FileInputStream fis = new FileInputStream(file);
                	         player.setDataSource(fis.getFD());
                	         player.setLooping(false);

                	         player.prepare();
                	         player.start();
                	         player.stop();
                	         player.reset();
                	        }
                	        catch (IllegalArgumentException e) { }
                	        catch (IllegalStateException e) { }
                	        catch (IOException e) { }
                	        
                	        
                		
                		}
            }
        });
        submit = (Button)this.findViewById(R.id.subMan);
        submit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            		//Do Something
            	}
        });
        cancel = (Button)this.findViewById(R.id.canMan);
        cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	ManualTime.this.finish();
            }
        });
	}
	
	public void startCamera() {

		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);   
        this.startActivityForResult(camera, PICTURE_RESULT);
	}


	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PICTURE_RESULT) //
            if (resultCode == Activity.RESULT_OK) {
               // Display image received on the view
                Bundle b = data.getExtras(); // Kept as a Bundle to check for other things in my actual code
                Bitmap pic = (Bitmap) b.get("data");

                if (pic != null) { // Display your image in an ImageView in your layout (if you want to test it)
                    v = (ImageView) this.findViewById(R.id.manImage);
                    v.setImageBitmap(pic);
                    v.invalidate();
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED) { finish();}
   }
	}
