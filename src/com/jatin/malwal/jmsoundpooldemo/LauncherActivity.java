package com.jatin.malwal.jmsoundpooldemo;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

public class LauncherActivity extends Activity implements OnTouchListener {
	private SoundPool soundPool;
	final static int MAX_STREAMS = 5;
	private boolean isLoaded = false;
	private int soundOneID;
	private int soundTwoID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		((RelativeLayout) findViewById(R.id.bg)).setOnTouchListener(this);
		// Set the hardware buttons to control the music
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		// Load the sound
		soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
				isLoaded = true;
			}
		});
		soundOneID = soundPool.load(this, R.raw.shoot_1, 1);
		soundTwoID = soundPool.load(this, R.raw.shoot_2, 1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launcher, menu);
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			float volume = actualVolume / maxVolume;

			if (isLoaded) {
				soundPool.play(soundOneID, volume, volume, 1, 0, 1f);
			}
		}
		return false;
	}

}
