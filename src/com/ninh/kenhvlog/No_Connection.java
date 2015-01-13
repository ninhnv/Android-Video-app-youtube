package com.ninh.kenhvlog;

import com.example.slidemenu.MenuActivity;
import com.example.slidemenu.SlideoutActivity;
import com.ninh.kenhvlog.R;
import com.ninh.kenhvlog.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class No_Connection extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.no_internet);
		findViewById(R.id.btnOption).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						onSlideOut();
					}
				});
		findViewById(R.id.btnhelp).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(No_Connection.this,
								helpscr.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.button2).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});

	}

	public void onSlideOut() {
		int width = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 56, getResources()
						.getDisplayMetrics());
		if (findViewById(R.id.content) != null) {
			SlideoutActivity.prepare(this, R.id.content, width);
		} else {
			SlideoutActivity.prepare(this, android.R.id.content, width);
		}
		Intent intent = new Intent(this, MenuActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
		overridePendingTransition(0, 0);
	}
}
