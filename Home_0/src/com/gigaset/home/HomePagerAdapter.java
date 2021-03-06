package com.gigaset.home;

import com.gigaset.home.Home.ApplicationsAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class HomePagerAdapter extends PagerAdapter {
	private Context context;
	private ApplicationsAdapter appAdapter;
	private final static int NUMBER_OF_SCREENS 	= 3;
	final static int SCREEN_LEFT 		= 0;
	final static int SCREEN_CENTER 		= 1;
	final static int SCREEN_RIGHT 		= 2;


	public int getCount() {
		return NUMBER_OF_SCREENS;
	}
	
	/**
	 * Public constructor get in argument ApplicationsAdapter to inflate applications grid in center screen
	 * @param appAdapter - adapter for applications grid
	 */
	HomePagerAdapter(Context context, ApplicationsAdapter appAdapter)	// On create object set adapter for main screen applications GridView
	{
		this.context 	= context;
		this.appAdapter = appAdapter;
	}
	
    /**
     * Starts the selected activity/application in the grid view.
     */
    class ApplicationLauncher implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            ApplicationInfo app = (ApplicationInfo) parent.getItemAtPosition(position);
            //Log.e("ApplicationLauncher", app.intent+"");
            app.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(app.intent);            
        }
    }	

	public Object instantiateItem(View collection, int position) {
		
		if(this.appAdapter == null)
		{
			throw new NullPointerException("appAdapter wasn't set");
		}

		LayoutInflater inflater = (LayoutInflater) collection.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		int resId = 0;
		switch (position) {
		case SCREEN_LEFT:
			resId = R.layout.left;
			break;
		case SCREEN_CENTER:
			resId = R.layout.home_main_panel;
			break;
		case SCREEN_RIGHT:
			resId = R.layout.right;
			break;
		}

		View view = inflater.inflate(resId, null);											
		
		if(SCREEN_CENTER == position)						// For center screen with GridView
		{
			GridView gridView = (GridView) view.findViewById(R.id.all_apps);
			
			if(null != gridView)							// if retrieve will success
			{
				gridView.setAdapter(this.appAdapter);
				gridView.setSelection(0);				
				gridView.setOnItemClickListener(new ApplicationLauncher() );
			}
		}


		((ViewPager) collection).addView(view, 0);

		return view;
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);

	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View) arg1);

	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub

	}

}
