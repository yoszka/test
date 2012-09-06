package pl.xt.jokii.carserv;

import java.util.Calendar;

import pl.xt.jokii.db.CarServEntry;
import pl.xt.jokii.db.CarServProviderMetaData;
import pl.xt.jokii.db.PostDevice;
import pl.xt.jokii.db.CarServProviderMetaData.CarServTableMetaData;
import pl.xt.jokii.utils.Connectivity;

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
	private String[] items;
	private Activity activity = this;
	
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
		//String[] items = new String[] {"Inne", "Olej", "P�yn hamulcowy", "Opony", "Filtr powietrza", "Rozrz�d"};
		items 							= getResources().getStringArray(R.array.entry_types);
		Spinner spinner 				= (Spinner) findViewById(R.id.spinnerTypeChooser);
		ArrayAdapter<String> adapter 	= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
		spinner.setAdapter(adapter);
	
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
				selectedType = pos;
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				selectedType = 0;				
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

			 		 ContentValues args = new ContentValues();
					 args.put(CarServTableMetaData.SERVICE_HEADER, 	header);
					 args.put(CarServTableMetaData.SERVICE_DATE, 	dateStamp);
					 args.put(CarServTableMetaData.SERVICE_MILEAGE, mileage);
					 args.put(CarServTableMetaData.SERVICE_TYPE, 	selectedType);
					 
					 getContentResolver().insert(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, args);	
					 
					 if((new Connectivity(activity)).isOnline())
					 {
						 // ******* send via POST **********
						 StringBuilder str = new StringBuilder();
						 str.append("HEADER: " 		+ header 				+ "\n");
						 str.append("MILEAGE: " 	+ mileage 				+ "\n");
						 str.append("DATESTAMP: "	+ dateStamp 			+ "\n");
						 str.append("TYPE: " 		+ items[selectedType] 	+ "\n");					 
						 sendViaPOST(str.toString());
						 // ******* send via POST ********** end
					 }
			         //TODO
		   	    	 Cursor cursor = getContentResolver().query(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, new String[]{CarServTableMetaData._ID}, null, null, CarServTableMetaData._ID + " DESC"); // kolumna "_id", bez kluzuli WHERE, bez WHERE argument�w, sortowanie DESC
		   	       
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
												                     
			     	NewEntry.this.finish(); 
				 }
							
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

	    //Cursor cursor = baza.rawQuery("SELECT * FROM CarEvents WHERE _ID = "+id+"",null);
	  	Cursor cursor = getContentResolver().query(Uri.withAppendedPath(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, id+""), null, null, null, null);

	    if(cursor.moveToFirst())			//Metoda zwraca FALSE jesli cursor jest pusty
	    { 
	    	
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

	    return carServEntry;
	}
	
	
	/**
	 * Update given entry id in data base with data from carServEntry
	 * @param _ID			- id of entry in data base
	 * @param carServEntry	- new values for update
	 */
	private void updateEntryDB(long id, CarServEntry carServEntry)
	{	
		
		//String strFilter = "_ID = "+id;
		ContentValues args = new ContentValues();
		args.put(CarServTableMetaData.SERVICE_HEADER, 	carServEntry.getHeader());
		args.put(CarServTableMetaData.SERVICE_DATE, 	carServEntry.getDate());
		args.put(CarServTableMetaData.SERVICE_MILEAGE, carServEntry.getMileage());
		args.put(CarServTableMetaData.SERVICE_TYPE, 	carServEntry.getType());
		
		getContentResolver().update(Uri.withAppendedPath(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, id+""), args, null, null);			
	}
	
	/**
	 * Send message to external server
	 * @param mesage
	 */
	private void sendViaPOST(String mesage)
	{
		if(mesage.length() > 0)
		{
			PostDevice postDevice = new PostDevice("http://www.testeruploadu.w8w.pl/note/index.php?action=new");
			
			postDevice.addParameter("tekst"		, mesage);
			postDevice.addParameter("pas"  		, "carserv");
			postDevice.addParameter("nots_add"	, "tak");
			
			postDevice.send();
		}
	}

}