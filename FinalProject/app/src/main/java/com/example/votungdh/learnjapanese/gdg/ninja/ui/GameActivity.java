package com.example.votungdh.learnjapanese.gdg.ninja.ui;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.votungdh.learnjapanese.gdg.ninja.R;
import com.example.votungdh.learnjapanese.gdg.ninja.framework.BaseActivity;
import com.example.votungdh.learnjapanese.gdg.ninja.navigate.NavigationBar;
import com.example.votungdh.learnjapanese.gdg.ninja.navigate.NavigationManager;
import com.example.votungdh.learnjapanese.gdg.ninja.util.App;
import com.example.votungdh.learnjapanese.gdg.ninja.util.ShareUtils;

import java.io.File;
import java.io.FileOutputStream;

public class GameActivity extends BaseActivity {
	private NavigationBar mNaviBar;
	private View screenView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.ac_game);
		initView();
		showListGameFragment();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// FacebookUtil.onActivityResult(this, requestCode, resultCode, data);
	}

	public NavigationManager getNavigationManager() {
		return mNaviManager;
	}

	public void takeScreenShot(ShareUtils.SHARE_TYPE shareType) {
		new TakeScreenShot(shareType).execute();
	}

	// PrntScr your phone
	public class TakeScreenShot extends AsyncTask<Void, Void, Void> {
		AlertDialog replaceDialog;
		ShareUtils.SHARE_TYPE shareType;
		Bitmap b;

		public TakeScreenShot(ShareUtils.SHARE_TYPE shareType) {
			super();
			this.shareType = shareType;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			replaceDialog = dialog;
			dialog = new ProgressDialog(GameActivity.this);
			dialog.setMessage(App.getContext().getResources()
					.getString(R.string.please_wait));
			dialog.setCancelable(false);
			dialog.show();
			b = Bitmap.createBitmap(screenView.getWidth(),
					screenView.getHeight(), Config.ARGB_8888);

			Canvas canvas = new Canvas(b);
			screenView.draw(canvas);
		}

		@Override
		protected Void doInBackground(Void... params) {

			File file = new File(ShareUtils.DEFAULT_SCREENSHOT_PATH);
			try {
				file.createNewFile();
				FileOutputStream ostream = new FileOutputStream(file);

				b.compress(CompressFormat.PNG, 80, ostream);
				ostream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (shareType == ShareUtils.SHARE_TYPE.FACEBOOK)
				ShareUtils.postPhotoToFacebook(GameActivity.this, new File(
						ShareUtils.DEFAULT_SCREENSHOT_PATH));
			else
				ShareUtils.postPhotoToGoogle(GameActivity.this, new File(
						ShareUtils.DEFAULT_SCREENSHOT_PATH));
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (dialog.isShowing())
				dialog.dismiss();
			dialog = replaceDialog;
		}

	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	private void initView() {
		mNaviManager = new NavigationManager(this);
		mNaviBar = (NavigationBar) findViewById(R.id.navigation_bar);
		mNaviBar.initNaviBar(mNaviManager);
		screenView = findViewById(R.id.btn_choose_from_galary);
	}

	private void showListGameFragment() {
		ListGameFragment fragment = new ListGameFragment();
		mNaviManager.showPage(fragment, "");
	}
}