package com.example.jni1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public native void nativeFromJava();
	public native String nativeStringFromJava();
	public native int nativePassingArray(int[] is);
	public native int[] nativeRetArray(int size);
	
    /* this is used to load the 'example_jni' library on application
     * startup. The library has already been unpacked into
     * /data/data/com.example.jni1/lib/example_jni.so at
     * installation time by the package manager.
     */
    static {
        System.loadLibrary("example_jni");
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		showToast();
	}

	
	void invoke(){
		StringBuilder sb = new StringBuilder();
		TextView tv = (TextView) findViewById(R.id.textView);
		
		sb.append(nativeStringFromJava());
		sb.append(" array length: ").append(nativePassingArray(new int[2]));
		
		sb.append("\nsome Array: ");
		for(int i : nativeRetArray(3)){
			sb.append("[").append(i).append("]");
		}

		tv.setText(sb.toString());
		nativeFromJava();
		Log.v(MainActivity.class.getSimpleName(), "invoke() D");
	}
	
	public void onClickNative(View v){
		invoke();
		Toast.makeText(this, nativeStringFromJava(), 1).show();
	}

}
