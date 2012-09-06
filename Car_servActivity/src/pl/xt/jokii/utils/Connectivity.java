package pl.xt.jokii.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

public class Connectivity {
	private Activity activity;
	
	public Connectivity(Activity activity)
	{
		this.activity = activity;
	}
	
	/**
	 * Indicates whether the device is online (has an Intenet connection)
	 * To use this function need following permissions:
	 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	 * 
	 * @return true if connection is available
	 */
	public boolean isOnline() 
	{
	    boolean var = false;
	    ConnectivityManager cm = (ConnectivityManager) this.activity.getSystemService(Context.CONNECTIVITY_SERVICE);
	    if ( cm.getActiveNetworkInfo() != null ) 
	    {
	        var = true;
	    }
	    return var;
	}	

}
