package com.example.votungdh.learnjapanese.gdg.ninja.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.votungdh.learnjapanese.gdg.ninja.R;
import com.example.votungdh.learnjapanese.gdg.ninja.adapter.ListCategoriesAdapter;
import com.example.votungdh.learnjapanese.gdg.ninja.framework.BaseFragment;
import com.example.votungdh.learnjapanese.gdg.ninja.gameinfo.CategoriesInfo;
import com.example.votungdh.learnjapanese.gdg.ninja.navigate.NavigationBar;
import com.example.votungdh.learnjapanese.gdg.ninja.util.App;
import com.example.votungdh.learnjapanese.gdg.ninja.util.SoundUtils;

public class ListGameFragment extends BaseFragment implements
		NavigationBar.INavigationBarListener {
	private ListView mLtvCategories;
	
	private ListCategoriesAdapter mAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fg_list_game, container, false);
		initView(view);
		initData();
		return view;
	}
	
	private void initView(View view){
		mLtvCategories = (ListView) view.findViewById(R.id.ltv_list);
		mLtvCategories.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id){
				CategoriesInfo item = mAdapter.getItem(position);
				if(position != 0){
					CategoriesInfo previousItem = mAdapter
							.getItem(position - 1);
					if (previousItem.getStt() < 3) {
						SoundUtils.getInstance().play(SoundUtils.SOUND_NAME.WRONG);
						return;
					}
				}
				SoundUtils.getInstance().play(SoundUtils.SOUND_NAME.OTHER_BTN);
				ListQuestFragment fragment = ListQuestFragment.getInstance(item
						.getCateId());
				mNaviManager.showPage(fragment, "");
			}
		});
	}
	
	private void initData(){
		mAdapter = new ListCategoriesAdapter(App.getListCategories(),
				getActivity());
		mLtvCategories.setAdapter(mAdapter);
	}
	
	@Override
	public String getTitle(){
		return "List Categories";
	}
	
	@Override
	public NavigationBar.BTN_LEFT_MODE getButtonLeftMode(){
		return NavigationBar.BTN_LEFT_MODE.BACK;
	}
	
	@Override
	public void onLeftClicked(){
		mNaviManager.goBack();
	}
	
	@Override
	public NavigationBar.BTN_RIGHT_MODE getButtonRightMode(){
		return NavigationBar.BTN_RIGHT_MODE.SETTING;
	}
	
	@Override
	public void onRightClicked(){
		Intent intent = new Intent(getActivity(), HelpActivity.class);
		startActivity(intent);
	}
}