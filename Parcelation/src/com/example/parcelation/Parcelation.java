package com.example.parcelation;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Parcelation extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_parcelation, menu);
        return true;
    }
    
    
    public void onClickSecondActivity(View v){
    	Intent intent = new Intent(this, SecondActivity.class);
    	ParcelTest pt = new ParcelTest();
    	
    	Bundle entry = new Bundle();
    	entry.putString("1", "Jeden");
    	pt.addEntry(entry);
    	
    	entry = new Bundle();
    	entry.putString("2", "Dwa");
    	pt.addEntry(entry);
    	
    	entry = new Bundle();
    	entry.putString("3", "Trzy");
    	pt.addEntry(entry);
    	
    	entry = new Bundle();
    	entry.putString("4", "Cztery");
    	pt.addEntry(entry);    	
    	
    	intent.putExtra("aaa", pt);
    	startActivity(intent);
    }
}
