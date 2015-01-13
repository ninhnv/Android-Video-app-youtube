package com.example.slidemenu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import com.ninh.kenhvlog.R;

public class SlideoutHelper {

	private static Bitmap sCoverBitmap = null;
	private static int sWidth = -1;

	public static void prepare(Activity activity, int id, int width) {
		if (sCoverBitmap != null) {
			if(sCoverBitmap.isRecycled())
				sCoverBitmap.recycle();
		}
		final ScreenShot screenShot = new ScreenShot(activity, id);
		sCoverBitmap = screenShot.snap();
		sWidth = width;
		//pActivity = activity;
	}

	public SlideoutHelper(Activity activity) {
		this(activity, false);
	}
	
	public SlideoutHelper(Activity activity, boolean reverse) {
		mActivity = activity;
		mReverse = reverse;
	}

	public void activate() {
		mActivity.setContentView(R.layout.slideout);
		mCover = (ImageView) mActivity.findViewById(R.id.slidedout_cover);
		mCover.setImageBitmap(sCoverBitmap);
		mCover.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				close();
			}
		});
		int x = (int) (sWidth * 1.2f);
		
		if (mReverse) {
			@SuppressWarnings("deprecation")
			final android.widget.AbsoluteLayout.LayoutParams lp = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, x, 0);
			mActivity.findViewById(R.id.slideout_placeholder).setLayoutParams(lp);
		} else{
			@SuppressWarnings("deprecation")
			final android.widget.AbsoluteLayout.LayoutParams lp = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 0, 0);
			mActivity.findViewById(R.id.slideout_placeholder).setLayoutParams(lp);
		}
		initAnimations();
	}

	public void open() {
	    if(!isPlayAnimation) {
	        isPlayAnimation = true;
	        mCover.startAnimation(mStartAnimation);
	    }
		
	}

	public void close() {
	    if(!isPlayAnimation) {
	        isPlayAnimation = true;
	        mCover.startAnimation(mStopAnimation);
	    }
	} 
	private AnimationListener mStopListener;
	public void close(AnimationListener listener) {
	    if(!isPlayAnimation) {
	        isPlayAnimation = true;
	        mStopListener = listener;
	        mCover.startAnimation(mStopAnimation);
	    }
	} 
	/*public void closeActivity(){
		pActivity.finish();
	}*/
	
	protected void initAnimations() {
		@SuppressWarnings("deprecation")
		int displayWidth = ((WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
		final int shift = (mReverse ? -1 : 1) * (sWidth - displayWidth);
		mStartAnimation = new TranslateAnimation(
				TranslateAnimation.ABSOLUTE, 0,
				TranslateAnimation.ABSOLUTE, -shift,
				TranslateAnimation.ABSOLUTE, 0,
				TranslateAnimation.ABSOLUTE, 0
				);

		mStopAnimation = new TranslateAnimation(
				TranslateAnimation.ABSOLUTE, 0,
				TranslateAnimation.ABSOLUTE, shift,
				TranslateAnimation.ABSOLUTE, 0,
				TranslateAnimation.ABSOLUTE, 0
				);
		mStartAnimation.setDuration(DURATION_MS);
		mStartAnimation.setFillAfter(true);
		mStartAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			    isPlayAnimation = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mCover.setAnimation(null);
				@SuppressWarnings("deprecation")
				final android.widget.AbsoluteLayout.LayoutParams lp = new android.widget.AbsoluteLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, -shift, 0);
				mCover.setLayoutParams(lp);
				new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        isPlayAnimation = false;
                        
                    }
                });
			}
		});

		mStopAnimation.setDuration(DURATION_MS);
		mStopAnimation.setFillAfter(true);
		mStopAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				if(mStopListener != null) {
					mStopListener.onAnimationStart(animation);
				}
			    isPlayAnimation = true;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				if(mStopListener != null) {
					mStopListener.onAnimationRepeat(animation);
				}
			}

			@Override
			public void onAnimationEnd(final Animation animation) {
				mActivity.finish();
				mActivity.overridePendingTransition(0, 0);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        isPlayAnimation = false;
        				if(mStopListener != null) {
        					mStopListener.onAnimationEnd(animation);
        					mStopListener = null;
        				}
                    }
                });
			}
			
		});
	}

	public static final int DURATION_MS = 200;
	private ImageView mCover;
	private Activity mActivity;
	//private static Activity pActivity;
	private boolean mReverse = false;
	private boolean isPlayAnimation = false;
	private Animation mStartAnimation;
	private Animation mStopAnimation;
}
