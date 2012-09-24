package pl.xt.jokii.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class CarServProviderMetaData {
	public static final String AUTHORITY = "pl.xt.jokii.db.CarServProvider";
	
	public static final String DATABASE_NAME = "carservice.db";
	public static final int DATABASE_VERSION = 2;
	public static final String CARSERV_TABLE_NAME = "CarEvents_";
	
	private CarServProviderMetaData(){};
	
	public static final class CarServTableMetaData implements BaseColumns 
	{
		private CarServTableMetaData(){};
		public static final String TABLE_NAME = "CarEvents_";
		
		// definicja identyfikatora URI dla typu MIME
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/CarEvents_");
		
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.androidcarserv.carevents";
		
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.androidcarserv.carevents";
		
		public static final String DEFAULT_SORT_ORDER = "_id DESC";
		
		// Dodatkowe kolumny
		//public static final String _ID 				= "_ID";		// long		- dostarczane wutomatyczenie poprzez implementacjê klasy BaseColumns
		
		public static final String SERVICE_HEADER 	= "Header";		// String
		
		public static final String SERVICE_TYPE 	= "Type";		// int
			
		public static final String SERVICE_MILEAGE 	= "Mileage";	// int
		
		public static final String SERVICE_DATE 	= "Date";		// long
		
		public static final String SERVICE_EXPIRED 	= "isExpired";	// boolean
		
		//
		//public static final String CREATED_DATE = "created";
		
		//public static final String MODIFIED_DATE = "modified";
		
	}
}
