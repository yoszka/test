package pl.xt.jokii.carserv;

import java.util.Calendar;

import pl.xt.jokii.db.CarServEntry;
import pl.xt.jokii.db.CarServProviderMetaData;
import pl.xt.jokii.db.CarServProviderMetaData.CarServTableMetaData;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class NewEntry extends Activity{
	private int selectedType = 0;
	private long EntryId = 0;
	private CarServEntry carServEntry = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.add_new_entry);	
		
		Intent intent = getIntent();     
		EntryId = intent.getLongExtra(Car_servActivity.EDIT_ENTRY_RES, 0);	
		
		if(0 != EntryId)
		{
			carServEntry = getEntryFromDB(EntryId);
		}
		
		// SPINNER ***********************************************************************************************
		//String[] items = new String[] {"Inne", "Olej", "P³yn hamulcowy", "Opony", "Filtr powietrza", "Rozrz¹d"};
		String[] items = getResources().getStringArray(R.array.entry_types);
		Spinner spinner = (Spinner) findViewById(R.id.spinnerTypeChooser);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
		spinner.setAdapter(adapter);
	
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
				selectedType = pos;
				//Toast.makeText(getApplicationContext(), "wybrano "+selectedType, Toast.LENGTH_SHORT).show();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				selectedType = 0;
				//Toast.makeText(getApplicationContext(), "Nie wybrano ", Toast.LENGTH_SHORT).show();
				
			}
		});
		// *******************************************************************************************************
		
		// Set vlaues to edit
		if(null != carServEntry)
		{
			// Type -------------------------------------------------------------------------------------------------
			spinner.setSelection(carServEntry.getType());
			
			// Header -----------------------------------------------------------------------------------------------
			EditText 		editTextOpis 	= (EditText)findViewById(R.id.editWindowDescription);				 
			editTextOpis.setText(carServEntry.getHeader()+"");
			 
			// Mileage ----------------------------------------------------------------------------------------------
			EditText 		editTextMileage	= (EditText)findViewById(R.id.editMileage);
			editTextMileage.setText(carServEntry.getMileage()+"");	
			 
			 // Expiration date -------------------------------------------------------------------------------------
			DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker1);
			
			Calendar calRestored = Calendar.getInstance();
			calRestored.setTimeInMillis(carServEntry.getDate());
			
			datePicker.init(calRestored.get(Calendar.YEAR), calRestored.get(Calendar.MONTH), calRestored.get(Calendar.DAY_OF_MONTH), null);			 
		}
		

		
		Button buttonOk = (Button)findViewById(R.id.buttonOK);
		buttonOk.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				 // Header -----------------------------------------------------------------------------------------------
				 EditText 		editTextOpis 	= (EditText)findViewById(R.id.editWindowDescription);				 
				 String   		header 			= editTextOpis.getText().toString();
				 
				 // Mileage ----------------------------------------------------------------------------------------------
				 EditText 		editTextMileage	= (EditText)findViewById(R.id.editMileage);
				 int 			mileage			= 0;
				 
				 if(editTextMileage.getText().toString().length() > 0)
				 {
					 mileage = Integer.parseInt(editTextMileage.getText().toString());
				 }
				 
				 // DatePicker ********************************************************************************************
				 DatePicker datePicker 	= (DatePicker) findViewById(R.id.datePicker1);				 
				 Calendar 	cal 		= Calendar.getInstance();
				 
				 cal.set(Calendar.YEAR, 		datePicker.getYear());
				 cal.set(Calendar.MONTH, 		datePicker.getMonth());
				 cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
				 
				 long dateStamp = cal.getTimeInMillis();
				 // -------------------------------------------------------------------------------------------------------
				 
				 
				 //DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker1);
				 //String tmpDate = datePicker.getDayOfMonth()+"";
				 //Toast.makeText(getApplicationContext(), "Dzieñ "+tmpDate, Toast.LENGTH_SHORT).show();
				
				 //tmpDate = datePicker.getMonth()+"";
				// Toast.makeText(getApplicationContext(), "Miesi¹æ "+tmpDate, Toast.LENGTH_SHORT).show();
				 
				 //tmpDate = datePicker.getYear()+"";
				 //Toast.makeText(getApplicationContext(), "Rok "+tmpDate, Toast.LENGTH_SHORT).show();	
				 
				 //Calendar cal = Calendar.getInstance();
				 //cal.set(Calendar.YEAR, datePicker.getYear());
				 //cal.set(Calendar.MONTH, datePicker.getMonth());
				 //cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
				 //long datePubStamp = cal.getTimeInMillis();
				 
				 //Toast.makeText(getApplicationContext(), "TimeInMillis "+dateStamp, Toast.LENGTH_SHORT).show();	
				 /*
				 Calendar calRestored = Calendar.getInstance();
				 calRestored.setTimeInMillis(datePubStamp);
				 int tmpInt = calRestored.get(Calendar.DAY_OF_MONTH);
				 Toast.makeText(getApplicationContext(), "Dzieñ "+tmpInt, Toast.LENGTH_SHORT).show();
				 
				 tmpInt = cal.get(Calendar.MONTH);
				 Toast.makeText(getApplicationContext(), "Miesi¹æ "+tmpInt, Toast.LENGTH_SHORT).show();	
				 
				 tmpInt = cal.get(Calendar.YEAR);
				 Toast.makeText(getApplicationContext(), "Miesi¹æ "+tmpInt, Toast.LENGTH_SHORT).show();				 
				 */
				 // *******************************************************************************************************
				 
//				 if(header.length() > 0)
//				 {
					 Intent 		data 			= new Intent();
					 
					 if(null != carServEntry)
					 {
						 carServEntry.setType(selectedType);
						 carServEntry.setHeader(header);
						 carServEntry.setMileage(mileage);
						 carServEntry.setDate(dateStamp);
						 //TODO
						 updateEntryDB(EntryId, carServEntry);
						 
						 Car_servActivity.resultsSet.updateEntry(EntryId, carServEntry);
						 
		   	        	 data.putExtra(Car_servActivity.UPDATE_ITEM_RES, EntryId);
		   	        	 NewEntry.this.setResult(Activity.RESULT_OK, data);						 
						 
						 NewEntry.this.finish();
					 }
					 else
					 {
//						 SQLiteDatabase baza 			= null;
						 //Intent 		data 			= new Intent();
						 //data.putExtra(Car_servActivity.NEW_ENTRY_RES, opis);
						
//					     try
//					     {        			         
//					         baza = getApplicationContext().openOrCreateDatabase(Car_servActivity.DB_NAME, MODE_PRIVATE, null);
					         // MODE_PRIVATE for the default operation
					          
					         //baza.execSQL("CREATE TABLE IF NOT EXISTS CarEvents (Name VARCHAR, Date VARCHAR)");
					          			         
					         //baza.execSQL("INSERT INTO CarEvents  Values(NULL, '"+header+"',	"+dateStamp+", 	"+mileage+", 	"+selectedType+");");
					         
					 		 ContentValues args = new ContentValues();
					 		 //args.put(CarServProviderMetaData.CarServTableMetaData._ID, 	"NULL");
							 args.put(CarServTableMetaData.SERVICE_HEADER, 	header);
							 args.put(CarServTableMetaData.SERVICE_DATE, 	dateStamp);
							 args.put(CarServTableMetaData.SERVICE_MILEAGE, mileage);
							 args.put(CarServTableMetaData.SERVICE_TYPE, 	selectedType);
							 
							 getContentResolver().insert(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, args);					         
					         
					         //NewEntry.this.setResult(Activity.RESULT_OK, data);
					         
					         //Toast.makeText(getApplicationContext(), "INSERT INTO CarEvents  Values(NULL, '"+opis+"',	"+dateStamp+", 	"+mileage+", 	"+selectedType+");", Toast.LENGTH_LONG).show();
					         
					         
					         
					         
//					   	     try
//					   	     {        		   	        				   	       
					   	         //Cursor cursor = baza.rawQuery("SELECT _ID FROM CarEvents ORDER by _ID DESC",null);	// LIMIT 1
					   	    	 Cursor cursor = getContentResolver().query(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, new String[]{CarServTableMetaData._ID}, null, null, CarServTableMetaData._ID + " DESC"); // kolumna "_id", bez kluzuli WHERE, bez WHERE argumentów, sortowanie DESC
					   	       
					   	         if(cursor.moveToFirst())			//Metoda zwraca FALSE jesli cursor jest pusty
					   	         { 		   	        	 
					   	        	data.putExtra(Car_servActivity.NEW_ENTRY_RES, cursor.getInt(cursor.getColumnIndex(CarServTableMetaData._ID)));
					   	        	NewEntry.this.setResult(Activity.RESULT_OK, data);
					   	         }
					   	         else
					   	         {		      
					   	        	Log.e("ERROR GET CURSOR ID", "cursor pusty");       	 
					   	         }   
					   	         cursor.close();
					   	         
					   	      //Cursor cursor = theDatabase.query(DATABASE_TABLE, columns,null, null, null, null, null);
					   	      //cursor.moveToLast();
//					   	     }
//					   	     catch(SQLiteException e) 
//					   	     {
//					   	        	Log.e(getClass().getSimpleName(), "Could not create or Open the database"); 
//					   	     }				         
//					        }
//					      catch(SQLiteException e) 
//					        {
//					        	Log.e(getClass().getSimpleName(), "Could Open the database");
//					        	NewEntry.this.setResult(Activity.RESULT_CANCELED, data);
//					        }
//					      finally 
//					        {
//								baza.close();
//					        }
														                     
					     	NewEntry.this.finish(); 
					 }
							
//				 }
//				 else
//				 {
//					 Toast.makeText(getApplicationContext(), "Nie wype³niono pola nag³ówek", Toast.LENGTH_LONG).show();
//				 }
			}
		});
	}
	
	/**
	* Get complete entry from bata base
	* @param _ID			- elemnet id from data base
	* @return CarServEntry - entry from DB
	*/
	private CarServEntry getEntryFromDB(long id)
	{
	  CarServEntry 	carServEntry 	= new CarServEntry();
	  //SQLiteDatabase 	baza 		= null;


//	  try
//	  {        
	    // MODE_PRIVATE for the default operation
	    //baza = getApplicationContext().openOrCreateDatabase(Car_servActivity.DB_NAME, MODE_PRIVATE, null);         

	    //Cursor cursor = baza.rawQuery("SELECT * FROM CarEvents WHERE _ID = "+id+"",null);
	  	Cursor cursor = getContentResolver().query(Uri.withAppendedPath(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, id+""), null, null, null, null);

	    if(cursor.moveToFirst())			//Metoda zwraca FALSE jesli cursor jest pusty
	    { /*	    	        	 
	      carServEntry.setId		(cursor.getInt		(cursor.getColumnIndex("_ID")));
	      carServEntry.setHeader	(cursor.getString	(cursor.getColumnIndex("Header")));
	      carServEntry.setMileage	(cursor.getInt		(cursor.getColumnIndex("Mileage")));
	      carServEntry.setType		(cursor.getInt		(cursor.getColumnIndex("Type")));
	      carServEntry.setDate		(cursor.getLong		(cursor.getColumnIndex("Date")));*/
	    	
	    	carServEntry.setId		(cursor.getInt		(cursor.getColumnIndex(CarServTableMetaData._ID)));
	    	carServEntry.setHeader	(cursor.getString	(cursor.getColumnIndex(CarServTableMetaData.SERVICE_HEADER)));
	    	carServEntry.setMileage	(cursor.getInt		(cursor.getColumnIndex(CarServTableMetaData.SERVICE_MILEAGE)));
	    	carServEntry.setType	(cursor.getInt		(cursor.getColumnIndex(CarServTableMetaData.SERVICE_TYPE)));
	    	carServEntry.setDate	(cursor.getLong		(cursor.getColumnIndex(CarServTableMetaData.SERVICE_DATE)));	    	
	    }
	    else
	    {		      
	      Log.e("ERROR getEntryFromDB", "cursor pusty");         	 
	    }

	    cursor.close();

//	  }
//	  catch(SQLiteException e) 
//	  {
//	    Log.e(getClass().getSimpleName(), "Could not create or Open the database"); 
//	  }
//	  finally 
//	  {
//	    baza.close();
//	  }			


	  return carServEntry;
	}
	
	
	/**
	 * Update given entry id in data base with data from carServEntry
	 * @param _ID			- id of entry in data base
	 * @param carServEntry	- new values for update
	 */
	private void updateEntryDB(long id, CarServEntry carServEntry)
	{
//	  SQLiteDatabase 	baza 		= null;
//	  
//	  try
//	  {        
	    // MODE_PRIVATE for the default operation
		//baza = getApplicationContext().openOrCreateDatabase(Car_servActivity.DB_NAME, MODE_PRIVATE, null);
		
		
		//String strFilter = "_ID = "+id;
		ContentValues args = new ContentValues();
		args.put(CarServTableMetaData.SERVICE_HEADER, 	carServEntry.getHeader());
		args.put(CarServTableMetaData.SERVICE_DATE, 	carServEntry.getDate());
		args.put(CarServTableMetaData.SERVICE_MILEAGE, carServEntry.getMileage());
		args.put(CarServTableMetaData.SERVICE_TYPE, 	carServEntry.getType());
		
		getContentResolver().update(Uri.withAppendedPath(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, id+""), args, null, null);
		
		//baza.update(table, values, whereClause, whereArgs)
		
		//baza.update("CarEvents", args, strFilter, null);

	    // baza.execSQL("CREATE TABLE IF NOT EXISTS CarEvents (_ID INTEGER PRIMARY KEY, Header VARCHAR, Date INTEGER, Mileage INTEGER, Type INTEGER)");
	    //Cursor cursor = baza.rawQuery("UPDATE CarEvents SET Header = '"+carServEntry.getHeader()+"', Date = "+carServEntry.getDate()+", Mileage = "+carServEntry.getMileage()+", Type = "+carServEntry.getType()+" WHERE _ID = "+_ID+"",null);

//	  }
//	  catch(SQLiteException e) 
//	  {
//	    Log.e(getClass().getSimpleName(), "Could not Open or update the database"); 
//	  }
//	  finally 
//	  {
//	    baza.close();
//	  }		
	}

}
