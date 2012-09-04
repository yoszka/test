/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


    // TODO
/*
 * 1) Add View with current display and internal name indicator
 * 2) Add View with icon + new message counter indicator
 * 3) Implement multi window (3) HOME SCREEN
 * 4) Add custom Status Bar (?)
 * 5) Create "Main Menu" Activity with given applications
 * 6) Create "Main Menu Setup" on long press main screen
 * 7) Transplant APKs projects to ALion Build project
 */

package com.gigaset.home;



import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.gigaset.home.R;


public class Home extends Activity {
    private static ArrayList<ApplicationInfo> mApplications;

    private ViewPager 	myPager;
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);	//Select the default key handling for this activity

        setContentView(R.layout.home);

        getWindow().setBackgroundDrawableResource(R.drawable.main_screen_background);

        loadApplications(true);

        //bindApplications();
        //bindButtons();
        
        // Set up page adapter
        HomePagerAdapter adapter = new HomePagerAdapter(getApplicationContext() ,new ApplicationsAdapter(this, mApplications));
        myPager = (ViewPager) findViewById(R.id.three_panel_pager);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(HomePagerAdapter.SCREEN_CENTER);         
    }

    /**
     * Handle reopen this Activity
     */
    @Override
    protected void onNewIntent(Intent intent) 
    {
        super.onNewIntent(intent);

        // Close the menu
        if (Intent.ACTION_MAIN.equals(intent.getAction())) 
        {
            getWindow().closeAllPanels();
        }
    }

    @Override
    public void onDestroy() 
    {
        super.onDestroy();

        // Remove the callback for the cached drawables or we leak
        // the previous Home screen on orientation change
        final int count = mApplications.size();
        for (int i = 0; i < count; i++) 
        {
            mApplications.get(i).icon.setCallback(null);
        }


    }



//    /**
//     * Creates a new applications adapter for the grid view and registers it.
//     */
//    private void bindApplications() 
//    {
//        if (mGrid == null) 
//        {
//            mGrid = (GridView) findViewById(R.id.all_apps);
//        }
//        mGrid.setAdapter(new ApplicationsAdapter(this, mApplications));
//        mGrid.setSelection(0);
//
//    }

//    /**
//     * Binds actions to the various buttons.
//     */
//    private void bindButtons() 
//    {
//        mGrid.setOnItemClickListener(new ApplicationLauncher());
//    }



    /**
     * Catch keys to avoid back through HOME in Back Stack
     */
	@Override
    public boolean dispatchKeyEvent(KeyEvent event) 
    {
        if (event.getAction() == KeyEvent.ACTION_DOWN) 
        {
            switch (event.getKeyCode()) 
            {
                case KeyEvent.KEYCODE_BACK:
                	return true;
                case KeyEvent.KEYCODE_HOME:
                	return true;
            }
        } 
        else if (event.getAction() == KeyEvent.ACTION_UP)
        {
            switch (event.getKeyCode()) 
            {
                case KeyEvent.KEYCODE_BACK:
                    if (!event.isCanceled()) 
                    {
                        // Do BACK behavior.
                    }
				return true;
                case KeyEvent.KEYCODE_HOME:
                    if (!event.isCanceled()) 
                    {
                        // Do HOME behavior.
                    	myPager.setCurrentItem(HomePagerAdapter.SCREEN_CENTER);
                    }
				return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }
    
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//    	Toast.makeText(this, "onKeyDown", Toast.LENGTH_LONG).show();  
//        if ((keyCode == KeyEvent.KEYCODE_HOME)) {
//            Toast.makeText(this, "You pressed the home button!", Toast.LENGTH_LONG).show();                     
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }    
 
    /**
     * Loads the list of installed applications in mApplications.
     */
    private void loadApplications(boolean isLaunching) 
    {
        
    	if (isLaunching && mApplications != null) 
        {
            return;
        }

        PackageManager manager = getPackageManager();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final List<ResolveInfo> apps = manager.queryIntentActivities(mainIntent, 0);
        Collections.sort(apps, new ResolveInfo.DisplayNameComparator(manager));

        if (apps != null) 
        {
            final int count = apps.size();

            if (mApplications == null) 
            {
                mApplications = new ArrayList<ApplicationInfo>(count);
            }
            mApplications.clear();
            
            ApplicationInfo application;
            // ********************* ADD Key Pad **********************************
            application = new ApplicationInfo();
            application.title = "Key Pad";
            application.icon  = application.icon = getResources().getDrawable(R.drawable.main_screen_iocon_key_pad);
            application.setActivity(new ComponentName(
                    "com.android.contacts",
                    "com.android.contacts.activities.DialtactsActivity"),
                    Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);            
            mApplications.add(application);
            
            // ********************* ADD Messages *********************************
            application = new ApplicationInfo();
            application.title = "Messages";
            application.icon  = application.icon = getResources().getDrawable(R.drawable.main_screen_icon_messages);
            application.setActivity(new ComponentName(
                    "com.android.mms",
                    "com.android.mms.ui.ConversationList"),
                    Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);            
            mApplications.add(application);   
            
            // ********************* ADD Contacts *********************************
            application = new ApplicationInfo();
            application.title = "Contacts";
            application.icon  = application.icon = getResources().getDrawable(R.drawable.main_screen_icon_contacts);
            application.setActivity(new ComponentName(
                    "com.android.contacts",
                    "com.android.contacts.activities.PeopleActivity"),
                    Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);            
            mApplications.add(application);  
            
            // ********************* ADD Call Log *********************************
            // http://hi-android.info/src/index.html            
            application = new ApplicationInfo();
            application.title = "Call List";
            application.icon = getResources().getDrawable(R.drawable.main_screen_icon_call_log);           
            application.setActivityWithAction(Intent.ACTION_VIEW, CallLog.Calls.CONTENT_TYPE);          
            mApplications.add(application);   
            
            // ********************* ADD Browser **********************************
            application = new ApplicationInfo();
            application.title = "Browser";
            application.icon  = application.icon = getResources().getDrawable(R.drawable.main_screen_icon_browser);
            application.setActivity(new ComponentName(
                    "com.android.browser",
                    "com.android.browser.BrowserActivity"),
                    Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);            
            mApplications.add(application);   
            
            // ********************* ADD Call Settings ****************************
            application = new ApplicationInfo();
            application.title = "Call Settins";
            application.icon  = application.icon = getResources().getDrawable(R.drawable.main_screen_icon_call_settings);
            application.setActivity(new ComponentName(
                    "com.android.settings",
                    "com.android.settings.Settings"),
                    Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);            
            mApplications.add(application);            
            
//            
//            for (int i = 0; i < count; i++) 
//            {
//                ApplicationInfo application = new ApplicationInfo();
//                ResolveInfo info = apps.get(i);
//
//                application.title = info.loadLabel(manager);
//                application.setActivity(new ComponentName(
//                        info.activityInfo.applicationInfo.packageName,
//                        info.activityInfo.name),
//                        Intent.FLAG_ACTIVITY_NEW_TASK
//                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                
//                Log.v("Home APP", "package neme= "+info.activityInfo.applicationInfo.packageName);
//                Log.v("Home APP", "neme= "+info.activityInfo.name);
//                Log.v("Home APP", "title= "+application.title);
//                
//                
//                application.icon = info.activityInfo.loadIcon(manager);
//                
//                // Replace Icons
//                if((application.title+"").equals("Browser")) 	application.icon = getResources().getDrawable(R.drawable.main_screen_icon_browser);
//                if((application.title+"").equals("Phone")) 		application.icon = getResources().getDrawable(R.drawable.main_screen_iocon_key_pad);
//                if((application.title+"").equals("People")) 	application.icon = getResources().getDrawable(R.drawable.main_screen_icon_contacts);
//                if((application.title+"").equals("Messaging")) 	application.icon = getResources().getDrawable(R.drawable.main_screen_icon_messages);
//                if((application.title+"").equals("Settings")) 	application.icon = getResources().getDrawable(R.drawable.main_screen_icon_call_settings);
//                //if((application.title+"").equals("Call Log")) application.icon = getResources().getDrawable(R.drawable.main_screen_icon_call_log);
//                
//                // Add Application
//                if((application.title+"").equals("Browser")) 	mApplications.add(application);
//                if((application.title+"").equals("Phone"))	 	mApplications.add(application);
//                if((application.title+"").equals("People")) 	mApplications.add(application);
//                if((application.title+"").equals("Messaging")) 	mApplications.add(application);
//                if((application.title+"").equals("Settings")) 	mApplications.add(application);
//
//            }
            

        }
    }



    /**
     * GridView adapter to show the list of all installed applications.
     */
    class ApplicationsAdapter extends ArrayAdapter<ApplicationInfo> 
    {
        private Rect mOldBounds = new Rect();

        public ApplicationsAdapter(Context context, ArrayList<ApplicationInfo> apps)
        {
            super(context, 0, apps);
        }

        @SuppressWarnings("deprecation")
		@Override
        public View getView(int position, View convertView, ViewGroup parent) 
        {
            final ApplicationInfo info = mApplications.get(position);

            if (convertView == null) 
            {
                final LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.application, parent, false);
            }

            Drawable icon = info.icon;

            if (!info.filtered) 
            {
//                final Resources resources = getContext().getResources();
//                int width = 42;//(int) resources.getDimension(android.R.dimen.app_icon_size);
//                int height = 42;//(int) resources.getDimension(android.R.dimen.app_icon_size);

                // Real size from resource
//                int width = 500;//(int) resources.getDimension(android.R.dimen.app_icon_size);
//                int height = 500;//(int) resources.getDimension(android.R.dimen.app_icon_size);    
                
//                // Real size from resource
//                int width = (int) resources.getDimension(android.R.dimen.app_icon_size);
//                int height =(int) resources.getDimension(android.R.dimen.app_icon_size);                 

                final int iconWidth = icon.getIntrinsicWidth();
                final int iconHeight = icon.getIntrinsicHeight();
                
//                final int iconWidth = 75;
//                final int iconHeight = 75;

                if (icon instanceof PaintDrawable) 
                {
                    PaintDrawable painter = (PaintDrawable) icon;
                    //painter.setIntrinsicWidth(width);
                    //painter.setIntrinsicHeight(height);
                    painter.setIntrinsicWidth(iconWidth);
                    painter.setIntrinsicHeight(iconHeight);                    
                }

//                if (width > 0 && height > 0 && (width < iconWidth || height < iconHeight)) 
//                {
//                    final float ratio = (float) iconWidth / iconHeight;
//
//                    if (iconWidth > iconHeight) 
//                    {
//                        height = (int) (width / ratio);
//                    } else if (iconHeight > iconWidth)
//                    {
//                        width = (int) (height * ratio);
//                    }
//
//                    final Bitmap.Config c =
//                            icon.getOpacity() != PixelFormat.OPAQUE ?
//                                Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
//                    final Bitmap thumb = Bitmap.createBitmap(width, height, c);
//                    final Canvas canvas = new Canvas(thumb);
//                    canvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG, 0));
//                    // Copy the old bounds to restore them later
//                    // If we were to do oldBounds = icon.getBounds(),
//                    // the call to setBounds() that follows would
//                    // change the same instance and we would lose the
//                    // old bounds
//                    mOldBounds.set(icon.getBounds());
//                    icon.setBounds(0, 0, width, height);
//                    icon.draw(canvas);
//                    icon.setBounds(mOldBounds);
//                    icon = info.icon = new BitmapDrawable(thumb);
//                    info.filtered = true;
//                }
            }

            final TextView textView = (TextView) convertView.findViewById(R.id.label);
            textView.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
            textView.setText(info.title);

            return convertView;
        }
    }



//    /**
//     * Starts the selected activity/application in the grid view.
//     */
//    private class ApplicationLauncher implements AdapterView.OnItemClickListener 
//    {
//        public void onItemClick(AdapterView parent, View v, int position, long id) 
//        {
//            ApplicationInfo app = (ApplicationInfo) parent.getItemAtPosition(position);
//            startActivity(app.intent);
//        }
//    }

  
    
    /**
     * Callback function for center button
     * @param v - related View
     */
    public void allApplicationButton(View v)
    {
    	Intent showCallLogIntent = new Intent();
    	showCallLogIntent.setAction(Intent.ACTION_VIEW);
    	showCallLogIntent.setType(CallLog.Calls.CONTENT_TYPE);
    	startActivity(showCallLogIntent);
    }
    

}
