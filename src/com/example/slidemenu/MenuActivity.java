package com.example.slidemenu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.ninh.kenhvlog.R;

public class MenuActivity extends FragmentActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    mSlideoutHelper = new SlideoutHelper(this);
	    mSlideoutHelper.activate();
	   
	    //getSupportFragmentManager().beginTransaction().add(R.id.slideout_placeholder, new MenuFragment(), "menu").commit();
	    getSupportFragmentManager().beginTransaction().add(R.id.slideout_placeholder, new MenuOptionFragment(), "menu").commit();
	    mSlideoutHelper.open();
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			mSlideoutHelper.close();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	public SlideoutHelper getSlideoutHelper(){
		return mSlideoutHelper;
	}
	
	private SlideoutHelper mSlideoutHelper;
	
}
