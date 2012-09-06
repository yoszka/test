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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.entry_detail);
		
		Bundle extras = getIntent().getExtras();
		//String message = extras.getString(Car_servActivity.SHOW_ITEM_RES);
		EntryID = extras.getLong(Car_servActivity.SHOW_ITEM_RES);
		
		//TextView tv = (TextView)findViewById(R.id.textView1);
		//tv.setText(message);

	     //SQLiteDatabase baza = null;
	     
	     Log.v("EntryDetail", "EntryID = "+EntryID);
	     
//	     try
//	     {        
//	         
//	         baza = this.openOrCreateDatabase(Car_servActivity.DB_NAME, MODE_PRIVATE, null);
	         // MODE_PRIVATE for the default operation
	          
	         
	         //baza.execSQL("DROP TABLE CarEvents");
	         //baza.execSQL("CREATE TABLE IF NOT EXISTS CarEvents (_ID INTEGER PRIMARY KEY, Header VARCHAR, Date INTEGER, Mileage INTEGER, Type INTEGER)");
	          
	         // Wykonaæ tylko raz póŸniej zakomentowaæ razem z DELETE w finally
	         /*
	         baza.execSQL("INSERT INTO CarEvents  Values(NULL, 'Pierwszy','2012-08-17');");
	         baza.execSQL("INSERT INTO CarEvents  Values(NULL, 'Drugi','2012-08-18');");
	         baza.execSQL("INSERT INTO CarEvents  Values(NULL, 'Trzeci','2012-08-19');");
	         */
	       
	         //Cursor cursor = baza.rawQuery("SELECT * FROM CarEvents WHERE _ID = "+EntryID+"",null);
	     	 Cursor cursor = getContentResolver().query(Uri.withAppendedPath(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, EntryID+""), null, null, null, null);
	         //Log.v("EntryDetail", "SELECT * FROM CarEvents WHERE _ID = "+EntryID+"");
	       
	         if(cursor.moveToFirst())			//Metoda zwraca FALSE jesli cursor jest pusty
	         { 
	        	 /*
		         do
		         {
		        	String Name = cursor.getString(cursor.getColumnIndex("Header"));
		        	results.add(Name);
		         }while(cursor.moveToNext()); 	//Metoda zwraca FALSE wówczas gdy cursor przejdzie ostatni wpis
		         */
	        	 
	        	String RowString = cursor.getString(cursor.getColumnIndex("Header"));
				
	        	TextView tv = (TextView)findViewById(R.id.textViewDetailHeader);
				tv.setText(RowString);	  
				
				//RowString = cursor.getString(cursor.getColumnIndex("Type"));
	        	TextView tv2 = (TextView)findViewById(R.id.textViewDetailType);	        	
	        	String[] entryType = getResources().getStringArray(R.array.entry_types);	        	
	        	tv2.setText(entryType[cursor.getInt(cursor.getColumnIndex("Type"))]);		
	        	
				RowString = cursor.getString(cursor.getColumnIndex("Mileage"));
	        	TextView tv3 = (TextView)findViewById(R.id.textViewDeteailMileage);
	        	tv3.setText(RowString);	
	        	
	        	//int dateStamp = cursor.getInt(cursor.getColumnIndex("Date"));	        	
	        	long dateStamp = cursor.getLong(cursor.getColumnIndex("Date"));	 
	        	DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerDetail);

				Calendar calRestored = Calendar.getInstance();
				calRestored.setTimeInMillis(dateStamp);
				
				//Toast.makeText(getApplicationContext(), "DateStamp "+dateStamp, Toast.LENGTH_SHORT).show();
				//Toast.makeText(getApplicationContext(), "Year "+calRestored.get(Calendar.YEAR), Toast.LENGTH_SHORT).show();
				
				datePicker.init(calRestored.get(Calendar.YEAR), calRestored.get(Calendar.MONTH), calRestored.get(Calendar.DAY_OF_MONTH), null);
				//datePicker.init(2012, 5, 15, null);
				//int tmpInt = calRestored.get(Calendar.DAY_OF_MONTH);
				//Toast.makeText(getApplicationContext(), "Dzieñ "+tmpInt, Toast.LENGTH_SHORT).show();
	        	
	         }
	         else
	         {		      
	        	Log.e("ERROR EntryDEtail", "cursor pusty"); 
	        	TextView tv = (TextView)findViewById(R.id.textViewDetailHeader);
				tv.setText("cursor pusty");	        	 
	         }
	         
	         cursor.close(); 
//	        }
//	        catch(SQLiteException e) 
//	        {
//	        	Log.e(getClass().getSimpleName(), "Could not create or Open the database"); 
//	        }
//	        finally 
//	        {
//				baza.close();
//	        }			
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
    	menu.add(Menu.NONE, Menu.FLAG_APPEND_TO_GROUP+1, Menu.FIRST,   "Edit");
    	//menu.add(Menu.NONE, Menu.FLAG_APPEND_TO_GROUP+2, Menu.FIRST+1, "Exit");
    	
    	// wyœwietl menu
    	return true;		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
    	switch(item.getItemId())
    	{    	
    	case (Menu.FLAG_APPEND_TO_GROUP+1):
    		Intent intent = new Intent(getApplicationContext(), NewEntry.class);
    		intent.putExtra(Car_servActivity.EDIT_ENTRY_RES, EntryID);
    		//startActivity(intent);
    		startActivityForResult(intent, Car_servActivity.EDIT_ENTRY_REQUEST);
    		//Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
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
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK) 
		{
			//if(requestCode == Car_servActivity.UPDATE_ENTRY_REQUEST)
			if(requestCode == Car_servActivity.EDIT_ENTRY_REQUEST)
			{
				long updatedId = data.getLongExtra(Car_servActivity.UPDATE_ITEM_RES, -1);

				CarServEntry carServEntry = Car_servActivity.resultsSet.getEntryById(updatedId);				
				
				//setContentView(R.layout.entry_detail);
				
	        	TextView tv = (TextView)findViewById(R.id.textViewDetailHeader);
				tv.setText(carServEntry.getHeader()+"");	  
				
	        	TextView tv2 = (TextView)findViewById(R.id.textViewDetailType);	        	
	        	String[] entryType = getResources().getStringArray(R.array.entry_types);	        	
	        	tv2.setText(entryType[carServEntry.getType()]+"");		
	        	/*
	        	TextView tv3 = (TextView)findViewById(R.id.textViewDeteailMileage);
	        	tv3.setText(carServEntry.getMileage());	
	        	*/
	        	
	        	TextView tv33 = (TextView)findViewById(R.id.textViewDeteailMileage);
				tv33.setText(carServEntry.getMileage()+"");
				
	        	long dateStamp = carServEntry.getDate();	 
	        	DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerDetail);

				Calendar calRestored = Calendar.getInstance();
				calRestored.setTimeInMillis(dateStamp);
				
				datePicker.init(calRestored.get(Calendar.YEAR), calRestored.get(Calendar.MONTH), calRestored.get(Calendar.DAY_OF_MONTH), null);	
				/**/					
			}
		}
	    else
	    {
	    	Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
	    }		
	}

}
