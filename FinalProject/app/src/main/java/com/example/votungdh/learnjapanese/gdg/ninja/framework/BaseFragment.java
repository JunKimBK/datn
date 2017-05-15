package com.example.votungdh.learnjapanese.gdg.ninja.framework;

/**
 * Created by VoTungDH on 17/03/13.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.Fragment;

import com.example.votungdh.learnjapanese.gdg.ninja.navigate.NavigationManager;
import com.example.votungdh.learnjapanese.gdg.ninja.ui.GameActivity;
import com.example.votungdh.learnjapanese.gdg.ninja.util.ShareUtils;

/** Base fragment of the framework */
public class BaseFragment extends Fragment{
	protected AlertDialog dialog;
	protected NavigationManager mNaviManager;
	private Activity mActivity;
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		if(activity instanceof GameActivity)
			mActivity = activity;
			mNaviManager = ((GameActivity) activity).getNavigationManager();
	}
	
	public void takeScreenShot(ShareUtils.SHARE_TYPE shareType) {
		((GameActivity) mActivity).takeScreenShot(shareType);
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
		}
	}
}