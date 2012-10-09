package com.example.testsomefunct;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	AlertDialog.Builder alertBuilder;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		

		//declared as final to be able to reference it in inner 
		//class declarations of the handlers 
		
		alertBuilder = new AlertDialog.Builder(this);
		alertBuilder.setTitle("Title Alert Dialog");
		alertBuilder.setMessage("Alert's body");
		alertBuilder.setIcon(android.R.drawable.ic_dialog_alert);

		alertBuilder.setPositiveButton("OK", new OnClickListener() {


			public void onClick(DialogInterface arg0, int arg1) {
				TextView tv = (TextView) findViewById(R.id.textView1);
				tv.setText("Clicked OK");
			}
		});
		
		
		alertBuilder.setNegativeButton("NOK", new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				TextView tv = (TextView) findViewById(R.id.textView1);
				tv.setText("Clicked NOK");
			}
		});
		
		alertBuilder.setNeutralButton("NEUTRAL", new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				TextView tv = (TextView) findViewById(R.id.textView1);
				tv.setText("Clicked NEUTRAL");
				AlertDialog alertDialog = alertBuilder.create();
				alertDialog.cancel();				
			}
		});
		
		alertBuilder.setOnCancelListener(new OnCancelListener() {
			
			public void onCancel(DialogInterface dialog) {
				TextView tv = (TextView) findViewById(R.id.textView1);
				tv.setText("Canceled");
			}
		});
		
//		alertBuilder.show();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    public void onClickOpenDialog(View v){
    	alertBuilder.show();
    }
    
    public void onClickOpen2(View v){
    	Intent intent = new Intent(this, SecondActivity.class);
    	intent.putExtra("TEXT", intent.toString());
    	startActivityForResult(intent, 123456);
//    	startActivity();
    }    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if(resultCode == RESULT_OK){
    		if(requestCode == 123456){
//    			data.getStringArrayExtra("TEXT");
    			Toast.makeText(this, data.getStringExtra("TEXT"), Toast.LENGTH_SHORT).show();
    		}
    	}else{
    		Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
    		Log.i("MainActivity", data.getStringExtra("TEXT")+"");
    	}
    }
}
