package com.example.parcelation;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends Activity{
	public static final String TAG = "SecondActivity";
	ArrayList<Bundle> lista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		ParcelTest pt = extras.getParcelable("aaa");
		lista = pt.getList();
		
		Log.i(TAG, lista+"");
		Log.i(TAG, lista.size()+"");
		
		for(Bundle entry : lista){
			Log.i(TAG, entry.getString("1")+"");
			Log.i(TAG, entry.getString("2")+"");
			Log.i(TAG, entry.getString("3")+"");
			Log.i(TAG, entry.getString("4")+"");
		}
		
	}

}
