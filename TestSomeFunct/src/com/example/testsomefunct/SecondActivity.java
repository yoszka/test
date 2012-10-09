package com.example.testsomefunct;

import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends EActivity{
//	SomeClass obj1;
//	SomeClass obj2;
//	SomeClass obj3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_layout);
		
//		obj1 = getSomeClass();
//		obj2 = getSomeClass();
//		obj3 = getSomeClass();
//		
//		Log.d("SecondActivity", obj1.napis);
//		Log.d("SecondActivity", obj2.napis);
//		Log.d("SecondActivity", obj3.napis);
	}
	
//	@Override
//	protected void onPause() {
//		super.onPause();
//		
//		Log.v("SecondActivity", "onPause");
//		
//		Log.d("SecondActivity", obj1.napis);
//		Log.d("SecondActivity", obj2.napis);
//		Log.d("SecondActivity", obj3.napis);		
//	}
	
	@Override
	protected void onResume() {
		useReloadCondition(true);
		super.onResume();
	}
}
