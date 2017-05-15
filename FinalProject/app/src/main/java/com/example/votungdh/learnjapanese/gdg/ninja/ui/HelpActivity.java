package com.example.votungdh.learnjapanese.gdg.ninja.ui;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.votungdh.learnjapanese.gdg.ninja.R;
import com.example.votungdh.learnjapanese.gdg.ninja.framework.BaseActivity;
import com.example.votungdh.learnjapanese.gdg.ninja.util.SoundUtils;

public class HelpActivity extends BaseActivity {

	ImageView mImgPlayButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_help);

		mImgPlayButton = (ImageView) findViewById(R.id.btn_start);
		mImgPlayButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SoundUtils.getInstance().play(SoundUtils.SOUND_NAME.OTHER_BTN);
				finish();
			}
		});
	}
}
