/*
 * Road Quiz
 * 
 * Developed by Victor Cheong
 * http://www.victorcheong.org
 * vche036@gmail.com
 * 
 */
package com.android.roadquiz;

import com.android.roadquiz.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Title extends Activity {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.title);
	}
	
	public void begin(View v) {
	    Intent StartGameIntent = new Intent(Title.this, RoadQuiz.class);
		startActivity(StartGameIntent);
	}
}
