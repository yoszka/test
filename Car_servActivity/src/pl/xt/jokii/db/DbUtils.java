package pl.xt.jokii.db;

import pl.xt.jokii.db.CarServProviderMetaData.CarServTableMetaData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public abstract class DbUtils {
	private static boolean DBG = false;
	
	/**
	 * Update given entry id in data base with data from carServEntry
	 * @param _ID			- id of entry in data base
	 * @param carServEntry	- new values for update
	 */
	public static void updateEntryDB(Context ctx, long id, CarServEntry carServEntry)
	{	
		//String strFilter = "_ID = "+id;
		ContentValues args = new ContentValues();
		args.put(CarServTableMetaData.SERVICE_HEADER, 	carServEntry.getHeader());
		args.put(CarServTableMetaData.SERVICE_DATE, 	carServEntry.getDate());
		args.put(CarServTableMetaData.SERVICE_MILEAGE,  carServEntry.getMileage());
		args.put(CarServTableMetaData.SERVICE_TYPE, 	carServEntry.getType());
		args.put(CarServTableMetaData.SERVICE_EXPIRED, 	(carServEntry.isExpired())?1:0 );
		
		if(DBG) Log.v("EXPIRED upd", ((carServEntry.isExpired())?1:0)+"");
		
		ctx.getContentResolver().update(Uri.withAppendedPath(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, id+""), args, null, null);			
	}
	
	
	
	
	/**
	 * Update given entry id in data base with data from carServEntry
	 * @param _ID			- id of entry in data base
	 * @param carServEntry	- new values for update
	 */
	public static void insertEntryDB(Context ctx, CarServEntry carServEntry)
	{	
		//String strFilter = "_ID = "+id;
		ContentValues args = new ContentValues();
		args.put(CarServTableMetaData.SERVICE_HEADER, 	carServEntry.getHeader());
		args.put(CarServTableMetaData.SERVICE_DATE, 	carServEntry.getDate());
		args.put(CarServTableMetaData.SERVICE_MILEAGE,  carServEntry.getMileage());
		args.put(CarServTableMetaData.SERVICE_TYPE, 	carServEntry.getType());
		args.put(CarServTableMetaData.SERVICE_EXPIRED, 	(carServEntry.isExpired())?1:0 );
		
		if(DBG) Log.v("EXPIRED new", ((carServEntry.isExpired())?1:0)+"");
		
		ctx.getContentResolver().insert(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, args);		
	}	
	
	
	
	
	/**
	* Get complete entry from bata base
	* @param _ID			- elemnet id from data base
	* @return CarServEntry - entry from DB
	*/
	public static CarServEntry getEntryFromDB(Context ctx, long id)
	{
		CarServEntry 	carServEntry 	= new CarServEntry();

	    //Cursor cursor = baza.rawQuery("SELECT * FROM CarEvents WHERE _ID = "+id+"",null);
	  	Cursor cursor = ctx.getContentResolver().query(Uri.withAppendedPath(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, id+""), null, null, null, null);

	    if(cursor.moveToFirst())			//Metoda zwraca FALSE jesli cursor jest pusty
	    { 
	    	
	    	carServEntry.setId		(cursor.getInt		(cursor.getColumnIndex(CarServTableMetaData._ID)));
	    	carServEntry.setHeader	(cursor.getString	(cursor.getColumnIndex(CarServTableMetaData.SERVICE_HEADER)));
	    	carServEntry.setMileage	(cursor.getInt		(cursor.getColumnIndex(CarServTableMetaData.SERVICE_MILEAGE)));
	    	carServEntry.setType	(cursor.getInt		(cursor.getColumnIndex(CarServTableMetaData.SERVICE_TYPE)));
	    	carServEntry.setDate	(cursor.getLong		(cursor.getColumnIndex(CarServTableMetaData.SERVICE_DATE)));	
	    	carServEntry.setExpired (cursor.getInt		(cursor.getColumnIndex(CarServTableMetaData.SERVICE_EXPIRED)) == 1 );
	    }
	    else
	    {		      
	      Log.e("ERROR getEntryFromDB", "cursor pusty");         	 
	    }

	    cursor.close();		

	    return carServEntry;
	}	
	
	/**
	 * Get entries from data base and put to CarServResultsSet
	 * @return CarServResultsSet filed with entries from data base, null if none entry was retrieved 
	 */
	public static CarServResultsSet retrieveResultSet(Context ctx){
		CarServResultsSet resultsSet = new CarServResultsSet();
		resultsSet.init();
		
	   	 Cursor cursor = ctx.getContentResolver().query(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, null, null, null, null); // wszystkie kolumny, bez kluzuli WHERE, bez WHERE argumentów, bez sortowania	       
	     
	     if(cursor.moveToFirst())			// FALSE if cursor is empty
	     { 
	         do
	         {
	        	CarServEntry carServEntryTmp;
	        	
	        	carServEntryTmp = new CarServEntry();		        	
	        	
	        	carServEntryTmp.setId		(cursor.getLong		(cursor.getColumnIndex(CarServTableMetaData._ID)));
	        	carServEntryTmp.setHeader	(cursor.getString	(cursor.getColumnIndex(CarServTableMetaData.SERVICE_HEADER)));
	        	carServEntryTmp.setMileage	(cursor.getInt		(cursor.getColumnIndex(CarServTableMetaData.SERVICE_MILEAGE)));
	        	carServEntryTmp.setType		(cursor.getInt		(cursor.getColumnIndex(CarServTableMetaData.SERVICE_TYPE)));
	        	carServEntryTmp.setDate		(cursor.getLong		(cursor.getColumnIndex(CarServTableMetaData.SERVICE_DATE)));
	        	carServEntryTmp.setExpired  (cursor.getInt		(cursor.getColumnIndex(CarServTableMetaData.SERVICE_EXPIRED)) == 1 );     	

	        	resultsSet.addEnd(carServEntryTmp);
	        	
	         }while(cursor.moveToNext()); 	// FALSE if cursor go after last row in data base
	     }
	     else
	     {
	    	 cursor.close();
	    	 return null;
	     }
		 
	    cursor.close();
		return resultsSet;
	}
	
	
	/**
	 * Send message to external server
	 * @param mesage
	 */
	public static void sendViaPOST(String mesage)
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
