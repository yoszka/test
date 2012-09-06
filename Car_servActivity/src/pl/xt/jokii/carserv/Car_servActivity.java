package pl.xt.jokii.carserv;

import java.util.ArrayList;
import pl.xt.jokii.adapter.EventAdapter;
import pl.xt.jokii.db.CarServEntry;
import pl.xt.jokii.db.CarServProviderMetaData;
import pl.xt.jokii.db.CarServProviderMetaData.CarServTableMetaData;
import pl.xt.jokii.db.CarServResultsSet;



import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Car_servActivity extends Activity {
	protected final static  String DB_NAME 				= "mojaBaza";
	protected final static  String NEW_ENTRY_RES 		= "new_entry";
	protected final static  String EDIT_ENTRY_RES 		= "edit_entry";
	protected final static  String SHOW_ITEM_RES 		= "item";
	protected final static  String DELETE_ITEM_RES 		= "delete_item";
	protected final static  String UPDATE_ITEM_RES 		= "update_item";
	protected final static  int ADD_NEW_ENTRY_REQUEST 	= 1364;						// random number being a key for result
	protected final static  int DELETE_ENTRY_REQUEST 	= 1365;						// random number being a key for result
	protected final static  int EDIT_ENTRY_REQUEST 		= 1366;						// random number being a key for result
	protected final static  int UPDATE_ENTRY_REQUEST 	= 1367;						// random number being a key for result
	private ListView listView							= null;
	protected static CarServResultsSet resultsSet		= new CarServResultsSet();
	private ArrayList<CarServEntry> listEntries 		= new ArrayList<CarServEntry>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);   	    	        
        setContentView(R.layout.main);
        
        Cursor cursor = null;        
        this.listView = (ListView)findViewById(R.id.listView1);       
        
        resultsSet.init();
        
        // BUTTON Add New ***************************************************************
//        Button buttonAddNew = (Button)findViewById(R.id.buttonAdd);
//        buttonAddNew.setOnClickListener(new View.OnClickListener() {
//			
//			public void onClick(View v) {
//				Intent intent = new Intent(getApplicationContext(), NewEntry.class);
//				startActivityForResult(intent, ADD_NEW_ENTRY_REQUEST);
//				
//			}
//		});
	     
	    	 cursor = getContentResolver().query(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, null, null, null, null); // wszystkie kolumny, bez kluzuli WHERE, bez WHERE argument�w, bez sortowania	       
	       
	         if(cursor.moveToFirst())			//Metoda zwraca FALSE jesli cursor jest pusty
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
		        	
		        	resultsSet.addEnd(carServEntryTmp);
		        	
		         }while(cursor.moveToNext()); 	//Metoda zwraca FALSE w�wczas gdy cursor przejdzie ostatni wpis
	         }
	         else
	         {
	        	 Log.v("cursor", "NO DATA, creating some");	        	 
        	 
		 		 ContentValues args = new ContentValues();

				 args.put(CarServTableMetaData.SERVICE_HEADER, 	"Pierwszy");
				 args.put(CarServTableMetaData.SERVICE_DATE, 	935412);
				 args.put(CarServTableMetaData.SERVICE_MILEAGE, 100);
				 args.put(CarServTableMetaData.SERVICE_TYPE, 	0);
				 
				 getContentResolver().insert(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, args);
				 
		         cursor = getContentResolver().query(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, null, null, null, null); // wszystkie kolumny, bez kluzuli WHERE, bez WHERE argument�w, bez sortowania
		         
		         if(cursor.moveToFirst())			//Metoda zwraca FALSE jesli cursor jest pusty
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
			        	
			        	resultsSet.addEnd(carServEntryTmp);
	        	
			         }while(cursor.moveToNext()); 	//Metoda zwraca FALSE w�wczas gdy cursor przejdzie ostatni wpis
		         }		         
	         }
	         
	         
	         this.listView.setOnItemLongClickListener(new OnItemLongClickListener() {

					public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {

						Intent intent = new Intent(getApplicationContext(), DialogActivity.class);
						intent.putExtra(DELETE_ITEM_RES, resultsSet.getEntries().get(position).getId());
						startActivityForResult(intent, DELETE_ENTRY_REQUEST);
						return true;
					}
			});
	         
	         this.listView.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
						
						long idT = resultsSet.getEntries().get(position).getId();
						Intent intent = new Intent(getApplicationContext(), EntryDetail.class);
						
						intent.putExtra(SHOW_ITEM_RES, idT);
						
						Log.v("Show detail", idT + ", position: "+position);
						
						startActivity(intent);
					}
			});
	         
	    	listEntries.clear();	    
	    	listEntries.addAll(resultsSet.getEntries());
	         
         	EventAdapter ea = new EventAdapter(listEntries, getLayoutInflater());
         	ea.setResource(getResources());
         	this.listView.setAdapter(ea);
	          

	        cursor.close();            
    }
    
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);

	    // If the request went well (OK) and the request was ADD_NEW_ENTRY_REQUEST
	    if (resultCode == Activity.RESULT_OK) 
	    {
	    	if(requestCode == ADD_NEW_ENTRY_REQUEST)
	    	{
		    	// Add the newest on Top
		    	int newId = data.getIntExtra(NEW_ENTRY_RES, -1);
		    	
		    	if(newId != -1)
		    	{
		    		 Cursor cursor = null;		
			   	    	 
			   	    	 // Stworzenie URI na potrzeby zapytania			   	    	
			   	    	 cursor = getContentResolver().query(Uri.withAppendedPath(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, newId+""), null, null, null, null);			   	       
			   	       
			   	         if(cursor.moveToFirst())			//Metoda zwraca FALSE jesli cursor jest pusty
			   	         { 		   	        	 
				        	CarServEntry carServEntryTmp;
				        	
				        	carServEntryTmp = new CarServEntry();

				        	carServEntryTmp.setId		(cursor.getInt		(cursor.getColumnIndex(CarServTableMetaData._ID)));
				        	carServEntryTmp.setHeader	(cursor.getString	(cursor.getColumnIndex(CarServTableMetaData.SERVICE_HEADER)));
				        	carServEntryTmp.setMileage	(cursor.getInt		(cursor.getColumnIndex(CarServTableMetaData.SERVICE_MILEAGE)));
				        	carServEntryTmp.setType		(cursor.getInt		(cursor.getColumnIndex(CarServTableMetaData.SERVICE_TYPE)));
				        	carServEntryTmp.setDate		(cursor.getLong		(cursor.getColumnIndex(CarServTableMetaData.SERVICE_DATE)));				        					        	
				        	
				        	resultsSet.addEnd(carServEntryTmp);
				        	sortByDateDesc();
			   	         }
			   	         else
			   	         {		      
			   	        	Log.e("ERROR NEW_ENTRY_RES", "cursor pusty");       	 
			   	         }   	          			 
		   				cursor.close();
		    	}		    	
		    	
		    	updateListView();
	    	}
	    	else if (requestCode == DELETE_ENTRY_REQUEST)
	    	{
	    		long EntryId = data.getLongExtra(DELETE_ITEM_RES, 0);
	    		
    			getContentResolver().delete(Uri.withAppendedPath(CarServProviderMetaData.CarServTableMetaData.CONTENT_URI, EntryId+""), null, null);
	    		
	    		resultsSet.deleteEntryId(EntryId);
	    		sortByDateDesc();
	    		Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();	    		
	    	}
	    }
	    else
	    {
	    	Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
	    	String[] s = new String[2];
	    	s[0].toUpperCase();				// Cause NullPionterException
	    }    	
    }

    
    // BUTTONs ***********************************************************************
    /**
     * Callback method defined by the View
     * "Add new"
     * @param v
     */
    public void onAddNewEntry(View v)
    {
		Intent intent = new Intent(getApplicationContext(), NewEntry.class);
		startActivityForResult(intent, ADD_NEW_ENTRY_REQUEST);       	
    } 
    
    /**
     * Callback method defined by the View
     * "Date"
     * @param v
     */
    public void sortByDate(View v)
    {
    	sortByDateDesc();
    }  
    
    /**
     * Callback method defined by the View
     * "Mileage"
     * @param v
     */
    public void sortByMileage(View v)
    {
    	sortByMileageDesc();
    }
    
    /**
     * Callback method defined by the View
     * "Expired"
     * @param v
     */
    public void sortByExpired(View v)
    {
    	sortByExpiredAsc();
    }  
    // BUTTONs *********************************************************************** END
    
    /**
     * Update list view
     */
    private void updateListView()
    {
    	listEntries.clear();    
    	listEntries.addAll(resultsSet.getEntries());    	
    	this.listView.invalidateViews();    	
    }
     
    
    /**
     * Sort entries by added date DESC
     */
    private void sortByDateDesc()
    {
    	resultsSet.sortByIdDesc();
    	updateListView();
    }  
    
    /**
     * Sort entries by mileage DESC
     */
    private void sortByMileageDesc()
    {
    	resultsSet.sortByMileageDesc();
    	updateListView();
    }
    
    /**
     * Sort entries by expired date DESC
     */
//    private void sortByExpiredDesc()
//    {
//    	resultsSet.sortByDateDesc();
//    	updateListView();
//    }    
    
    /**
     * Sort entries by expired date ASC
     */
    private void sortByExpiredAsc()
    {
    	resultsSet.sortByDateAsc();
    	updateListView();
    }    
    
    @Override
    protected void onResume() {
    	super.onResume();
    	updateListView();
    }
    
}