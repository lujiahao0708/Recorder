package com.example.recorder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class About extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//»•µÙ±ÍÃ‚¿∏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about);
	}

}
