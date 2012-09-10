package pl.xt.jokii.carserv;

import java.util.Calendar;

import pl.xt.jokii.db.CarServEntry;
import pl.xt.jokii.db.CarServProviderMetaData;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class EntryDetail extends Activity{
	long EntryID = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.entry_detail);
		
		Bundle extras = getIntent().getExtras();
		EntryID = extras.getLong(Car_servActivity.SHOW_ITEM_RES);
	     
	     Log.v("EntryDetail", "EntryID = "+EntryID);
	     
     	 Cursor cursor = getContentResolver().query(Uri.withAppendedPath(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, EntryID+""), null, null, null, null);
       
         if(cursor.moveToFirst())			// FALSE if cursor jest empty
         { 
        	String RowString = cursor.getString(cursor.getColumnIndex("Header"));
			
        	TextView tv = (TextView)findViewById(R.id.textViewDetailHeader);
			tv.setText(RowString);	  
			
        	TextView tv2 = (TextView)findViewById(R.id.textViewDetailType);	        	
        	String[] entryType = getResources().getStringArray(R.array.entry_types);	        	
        	tv2.setText(entryType[cursor.getInt(cursor.getColumnIndex("Type"))]);		
        	
			RowString = cursor.getString(cursor.getColumnIndex("Mileage"));
        	TextView tv3 = (TextView)findViewById(R.id.textViewDeteailMileage);
        	tv3.setText(RowString);	
        		        	
        	long dateStamp = cursor.getLong(cursor.getColumnIndex("Date"));	 
        	DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerDetail);

			Calendar calRestored = Calendar.getInstance();
			calRestored.setTimeInMillis(dateStamp);				
			
			datePicker.init(calRestored.get(Calendar.YEAR), calRestored.get(Calendar.MONTH), calRestored.get(Calendar.DAY_OF_MONTH), null);
         }
         else
         {		      
        	Log.e("ERROR EntryDEtail", "cursor pusty"); 
        	TextView tv = (TextView)findViewById(R.id.textViewDetailHeader);
			tv.setText("cursor pusty");	        	 
         }
         
         cursor.close(); 			
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
    	menu.add(Menu.NONE, Menu.FLAG_APPEND_TO_GROUP+1, Menu.FIRST,   "Edit");
    	//menu.add(Menu.NONE, Menu.FLAG_APPEND_TO_GROUP+2, Menu.FIRST+1, "Exit");
    	
    	// show menu
    	return true;		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
    	switch(item.getItemId())
    	{    	
    	case (Menu.FLAG_APPEND_TO_GROUP+1):
    		Intent intent = new Intent(getApplicationContext(), NewEntry.class);
    		intent.putExtra(Car_servActivity.EDIT_ENTRY_RES, EntryID);
    		startActivityForResult(intent, Car_servActivity.EDIT_ENTRY_REQUEST);
    		break;    	
//    	case (Menu.FLAG_APPEND_TO_GROUP+2):
//    		Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show();
//    		break; 
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    	
    	return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK) 
		{
			//if(requestCode == Car_servActivity.UPDATE_ENTRY_REQUEST)
			if(requestCode == Car_servActivity.EDIT_ENTRY_REQUEST)
			{
				long updatedId = data.getLongExtra(Car_servActivity.UPDATE_ITEM_RES, -1);

				CarServEntry carServEntry = Car_servActivity.resultsSet.getEntryById(updatedId);				
				
				
	        	TextView tv = (TextView)findViewById(R.id.textViewDetailHeader);
				tv.setText(carServEntry.getHeader()+"");	  
				
	        	TextView tv2 = (TextView)findViewById(R.id.textViewDetailType);	        	
	        	String[] entryType = getResources().getStringArray(R.array.entry_types);	        	
	        	tv2.setText(entryType[carServEntry.getType()]+"");		
	        	
	        	TextView tv33 = (TextView)findViewById(R.id.textViewDeteailMileage);
				tv33.setText(carServEntry.getMileage()+"");
				
	        	long dateStamp = carServEntry.getDate();	 
	        	DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerDetail);

				Calendar calRestored = Calendar.getInstance();
				calRestored.setTimeInMillis(dateStamp);
				
				datePicker.init(calRestored.get(Calendar.YEAR), calRestored.get(Calendar.MONTH), calRestored.get(Calendar.DAY_OF_MONTH), null);						
			}
		}
	    else
	    {
	    	Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
	    }		
	}

}
