package com.ninh.kenhvlog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.blundell.tut.domain.Library;
import com.blundell.tut.domain.Video;
import com.blundell.tut.service.task.GetYouTubeUserVideosTask;
import com.blundell.tut.service.task.GetYouTubeUserVideoshuyme;
import com.blundell.tut.service.task.GetYouTubeUserVideosToanShinoda;
import com.blundell.tut.service.task.GetYouTubeUserVideoslamvietanh;
import com.blundell.tut.ui.widget.VideosListView;
import com.example.slidemenu.MenuActivity;
import com.example.slidemenu.SlideoutActivity;
import com.ninh.kenhvlog.R;

public class VideoActivity_Lamvietanh extends Activity {

	private Context _context;
	private Activity _activity;
	private VideosListView listView;
	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_lva);
		_context = VideoActivity_Lamvietanh.this;
		_activity = this;
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
						Intent intent = new Intent(VideoActivity_Lamvietanh.this,
								helpscr.class);
						startActivity(intent);
					}
				});
		listView = (VideosListView) findViewById(R.id.videosListView2);
		getUserYouTubeFeed(listView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				final Video vi = (Video) arg0.getItemAtPosition(position);

				Uri uri = Uri.parse(vi.getUrl());
				String v = uri.getQueryParameter("v");
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse("vnd.youtube:" + v));
				intent.putExtra("VIDEO_ID", v);
				startActivity(intent);

			}
		});
	}

	public void getUserYouTubeFeed(View v) {
		// We start a new task that does its work on its own thread
		// We pass in a handler that will be called when the task has finished
		// We also pass in the name of the user we are searching YouTube for
		progressDialog = ProgressDialog.show(this, "", "loading...");
		new Thread(new GetYouTubeUserVideoslamvietanh(responseHandler, "blundellp"))
				.start();

	}

	// This is the handler that receives the response when the YouTube task has
	// finished
	Handler responseHandler = new Handler() {
		public void handleMessage(Message msg) {
			populateListWithVideos(msg);
		};
	};

	private void populateListWithVideos(Message msg) {
		// Retreive the videos are task found from the data bundle sent back
		final Library lib = (Library) msg.getData().get(
				GetYouTubeUserVideosTask.LIBRARY);
		// Because we have created a custom ListView we don't have to worry
		// about setting the adapter in the activity
		// we can just call our custom method with the list of items we want to
		// display
		runOnUiThread(new Runnable() {

			public void run() {
				if (progressDialog != null)
					progressDialog.dismiss();
				listView.setVideos(lib.getVideos());
			}
		});
	}

	@Override
	protected void onStop() {
		// Make sure we null our handler when the activity has stopped
		// because who cares if we get a callback once the activity has stopped?
		// not me!
		responseHandler = null;
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onSlideOut() {
		int width = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 56, getResources()
						.getDisplayMetrics());
		if (findViewById(R.id.content) != null) {
			SlideoutActivity.prepare(_activity, R.id.content, width);
		} else {
			SlideoutActivity.prepare(_activity, android.R.id.content, width);
		}
		Intent intent = new Intent(_context, MenuActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
		overridePendingTransition(0, 0);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		MainActivity.loadContent(this, getClass().getSimpleName());
		super.onResume();
	}
	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
//	        .setTitle("Closing Activity")
	        .setMessage("Bạn có muốn đóng ứng dụng Vlog Việt")
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            finish();    
	        }

	    })
	    .setNegativeButton("No", null)
	    .show();
	}
}
