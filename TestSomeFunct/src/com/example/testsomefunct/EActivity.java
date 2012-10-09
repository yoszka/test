package com.example.testsomefunct;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class EActivity extends Activity {
	private boolean mPaused = false;
	private boolean mCondition = false;
	private ArrayList<SomeClass> mEArrayList = new ArrayList<SomeClass>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		
		Toast.makeText(this, intent.getStringExtra("TEXT"), Toast.LENGTH_SHORT).show();
	}
	
	public SomeClass getSomeClass(){
		SomeClass obj = new SomeClass();
		obj.napis	= "napis";
		obj.liczba 	= 44;
		
		mEArrayList.add(obj);
		obj.napis += "||";
		return obj;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if(mPaused && mCondition){
			Log.v("EActivity", "RELOAD DATA");
		}
	}
	
	
	protected void useReloadCondition(boolean condition){
		this.mCondition = condition;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        Log.v("EActivity", "onKeyDown|KEYCODE_BACK");
	        Intent data = new Intent();
	        data.putExtra("TEXT", "Some values");
	        setResult(RESULT_OK, data);
//	        return true;
	    }
		return super.onKeyDown(keyCode, event);
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		
		mPaused = true;
		
		for(SomeClass obj : mEArrayList){
			obj.napis += "_";
		}
		
//		for(SomeClass obj : mEArrayList){
//			Log.w("EActivity", obj.napis);
//		}		
	}
	
	
	public class SomeClass{
		public String napis;
		public Integer liczba;
	}
}
