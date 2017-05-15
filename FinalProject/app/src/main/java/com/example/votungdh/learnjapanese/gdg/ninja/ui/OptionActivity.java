package com.example.votungdh.learnjapanese.gdg.ninja.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.votungdh.learnjapanese.gdg.ninja.R;
import com.example.votungdh.learnjapanese.gdg.ninja.framework.BaseActivity;
import com.example.votungdh.learnjapanese.gdg.ninja.util.ConfigPreference;
import com.example.votungdh.learnjapanese.gdg.ninja.util.SoundUtils;

public class OptionActivity extends BaseActivity implements OnClickListener {
	ImageView mBtnSound, mBtnAbout, mBtnBack;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.ac_option);

		mBtnSound = (ImageView) findViewById(R.id.btn_left_navi);
		mBtnAbout = (ImageView) findViewById(R.id.btn_right_navi);
		mBtnBack = (ImageView) findViewById(R.id.btn_start);
		mBtnAbout.setOnClickListener(this);
		mBtnBack.setOnClickListener(this);
		mBtnSound.setOnClickListener(this);

		updateSoundBtn();
	}

	private void updateSoundBtn() {
		if (ConfigPreference.getInstance().isSoundOn()) {
			mBtnSound.setImageResource(R.drawable.on);
		} else
			mBtnSound.setImageResource(R.drawable.off);
	}

	@Override
	public void onClick(View v) {
		int viewID = v.getId();
		if(viewID == R.id.btn_left_navi){
			if (ConfigPreference.getInstance().isSoundOn()) {
				ConfigPreference.getInstance().turnSoundOff();
			} else {
				ConfigPreference.getInstance().turnSoundOn();
				SoundUtils.getInstance().play(SoundUtils.SOUND_NAME.OTHER_BTN);
			}
			updateSoundBtn();
		}
		if(viewID == R.id.btn_right_navi){
			SoundUtils.getInstance().play(SoundUtils.SOUND_NAME.OTHER_BTN);
			Intent intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
		}
		if(viewID == R.id.btn_start){
			SoundUtils.getInstance().play(SoundUtils.SOUND_NAME.OTHER_BTN);
			finish();
		}
	}
}