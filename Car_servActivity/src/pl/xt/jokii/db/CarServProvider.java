package pl.xt.jokii.db;

import java.util.HashMap;

import pl.xt.jokii.db.CarServProviderMetaData.CarServTableMetaData;

//import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class CarServProvider extends ContentProvider{

	// Utworzenie mapy projekcji
	private static HashMap<String, String> sCarServProjectionMap;
	
	static
	{
		sCarServProjectionMap = new HashMap<String, String>();
		
		sCarServProjectionMap.put(CarServTableMetaData._ID, 			CarServTableMetaData._ID);
		
		sCarServProjectionMap.put(CarServTableMetaData.SERVICE_DATE, 	CarServTableMetaData.SERVICE_DATE);
		
		sCarServProjectionMap.put(CarServTableMetaData.SERVICE_HEADER, 	CarServTableMetaData.SERVICE_HEADER);
		
		sCarServProjectionMap.put(CarServTableMetaData.SERVICE_MILEAGE, CarServTableMetaData.SERVICE_MILEAGE);
		
		sCarServProjectionMap.put(CarServTableMetaData.SERVICE_TYPE, 	CarServTableMetaData.SERVICE_TYPE);
		
		sCarServProjectionMap.put(CarServTableMetaData.SERVICE_EXPIRED, CarServTableMetaData.SERVICE_EXPIRED);
	}
	
	// Mechanizm umo¿liwiaj¹cy identyfikacjê wzorców wszystkich przychodz¹cych identyfikatorów URI
	private static final UriMatcher sUriMatcher;
	public static final int CARSERV_COLLECTION_URI_INDICATOR 	= 1;
	public static final int CARSERV_SINGLE_URI_INDICATOR 		= 2;
	
	static
	{
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(CarServProviderMetaData.AUTHORITY, "CarEvents_", 	 CARSERV_COLLECTION_URI_INDICATOR);
		sUriMatcher.addURI(CarServProviderMetaData.AUTHORITY, "CarEvents_/#", CARSERV_SINGLE_URI_INDICATOR);
	}
	
	// zajmuje siê kwesti¹ wywo³ania zwrotnego onCreate
	
	private DatabaseHelper mDbHelper;
	
	@Override
	public boolean onCreate() {
		mDbHelper = new DatabaseHelper(getContext());
		return true;
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{

		public DatabaseHelper(Context context) {
			super(context, CarServProviderMetaData.DATABASE_NAME, null, CarServProviderMetaData.DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		// Tworzy bazê danych
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE IF NOT EXISTS "+CarServTableMetaData.TABLE_NAME+" ("
				+CarServProviderMetaData.CarServTableMetaData._ID+" INTEGER PRIMARY KEY, "
				+CarServTableMetaData.SERVICE_HEADER+" VARCHAR, "
				+CarServTableMetaData.SERVICE_DATE+" INTEGER, "
				+CarServTableMetaData.SERVICE_MILEAGE+" INTEGER, "				
				+CarServTableMetaData.SERVICE_TYPE+" INTEGER, "
				+CarServTableMetaData.SERVICE_EXPIRED+" INTEGER"
				+");");
			

			
		}

		// Zmiana wersji bazy danych
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.v("", "Aktualizacja bazy danych z wersji "+ oldVersion + " do wersji "+ newVersion + ". Wszystkie stare dane zostaj¹ usuniête");
			db.execSQL("DROP TABLE IF EXIST " + CarServTableMetaData.TABLE_NAME);
			onCreate(db);
//			db.execSQL("ALTER TABLE "+CarServProviderMetaData.CARSERV_TABLE_NAME+" ADD "+CarServTableMetaData.SERVICE_EXPIRED+" INTEGER");	// Cause SQLiteException Can't upgrade read-only data base
		}
		
	}

	
	
	
	
	@Override
	public String getType(Uri uri) {
		
		switch(sUriMatcher.match(uri))
		{
		case CARSERV_COLLECTION_URI_INDICATOR:
			return CarServTableMetaData.CONTENT_TYPE;
			
		case CARSERV_SINGLE_URI_INDICATOR:
			return CarServTableMetaData.CONTENT_ITEM_TYPE;
			
		default:
			throw new IllegalArgumentException("Nieznany ident. URI " + uri);
		}
	}	
	
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		switch(sUriMatcher.match(uri))
		{
		case CARSERV_COLLECTION_URI_INDICATOR:
			qb.setTables(CarServTableMetaData.TABLE_NAME);
			qb.setProjectionMap(sCarServProjectionMap);
			break;
			
		case CARSERV_SINGLE_URI_INDICATOR:
			qb.setTables(CarServTableMetaData.TABLE_NAME);
			qb.setProjectionMap(sCarServProjectionMap);
			qb.appendWhere(CarServTableMetaData._ID + "=" + uri.getPathSegments().get(1));
			break;
			
		default:
			throw new IllegalArgumentException("Nieznany ident. URI" + uri);
		}
		
		// Je¿eli kolejnoœæ sortowania nie jest oreœlona, nale¿y skorzystaæ z domyœlnej wartoœci
		String orderBy;
		
		if(TextUtils.isEmpty(sortOrder))
		{
			orderBy = CarServTableMetaData.DEFAULT_SORT_ORDER;
		}
		else
		{
			orderBy = sortOrder;
		}
		
		// Otwarcie bazy danych i uruchomienie kwerendy
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
		
		// ??
		//int i = c.getCount();	// Returns the numbers of rows in the cursor.
		
		// Ustawia notifier kursorowi, mówi¹cy kurosrowi,
		// który identyfikator URI ma byæ obserwowany na wypadek zmiany Ÿród³a danych
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}	
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		// Sprawdzenie ¿¹danego identyfikatora Uri
		if(sUriMatcher.match(uri) != CARSERV_COLLECTION_URI_INDICATOR)
		{
			throw new IllegalArgumentException("Nieznany ident. URI " + uri);
		}
		
		// Sprawdzenie pól danych wejœciowych - wszystkie musz¹ byæ skonfigurowane
		
		if(values.containsKey(CarServTableMetaData.SERVICE_DATE) == false)
		{
			throw new IllegalArgumentException("Nieudana próba wstawienia wiersza, brak pola Data: " + uri);
		}
		if(values.containsKey(CarServTableMetaData.SERVICE_HEADER) == false)
		{
			throw new IllegalArgumentException("Nieudana próba wstawienia wiersza, brak pola Header: " + uri);
		}	
		if(values.containsKey(CarServTableMetaData.SERVICE_MILEAGE) == false)
		{
			throw new IllegalArgumentException("Nieudana próba wstawienia wiersza, brak pola Mileage: " + uri);
		}
		if(values.containsKey(CarServTableMetaData.SERVICE_TYPE) == false)
		{
			throw new IllegalArgumentException("Nieudana próba wstawienia wiersza, brak pola Type: " + uri);
		}
		if(values.containsKey(CarServTableMetaData.SERVICE_EXPIRED) == false)
		{
			throw new IllegalArgumentException("Nieudana próba wstawienia wiersza, brak pola isExpired: " + uri);
		}		
		
		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		//long rowId = db.insert(CarServTableMetaData.TABLE_NAME, /*null*/CarServTableMetaData.SERVICE_DATE, values);		// ?? CarServTableMetaData.SERVICE_DATE maybe null instead
		long rowId = db.insert(CarServTableMetaData.TABLE_NAME, null, values);		// ?? CarServTableMetaData.SERVICE_DATE maybe null instead
		
		if(rowId > 0)
		{
			Uri insertedUri = ContentUris.withAppendedId(CarServTableMetaData.CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(insertedUri, null);						// powiadomienie, ¿e baza danych siê zmieni³a
			return insertedUri;
		}
		
		throw new IllegalArgumentException("Nieudana próba umieszczenia wiersza w " + uri);
	}	
	
	
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		int count;
		
		switch(sUriMatcher.match(uri))
		{
		case CARSERV_COLLECTION_URI_INDICATOR:
			count = db.update(CarServTableMetaData.TABLE_NAME, values, selection, selectionArgs);
			break;
		case CARSERV_SINGLE_URI_INDICATOR:
			String rowId = uri.getPathSegments().get(1);
			count = db.update(CarServTableMetaData.TABLE_NAME, values, 
					CarServTableMetaData._ID + "=" + rowId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), 
					selectionArgs);
			break;
			
		default:
			throw new IllegalArgumentException("Nieznany ident. URI " + uri);
		}
		
		getContext().getContentResolver().notifyChange(uri, null);						// powiadomienie, ¿e baza danych siê zmieni³a
		
		return count;
	}
	
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		SQLiteDatabase db = mDbHelper.getWritableDatabase();
		int count;
		
		switch(sUriMatcher.match(uri))
		{
		case CARSERV_COLLECTION_URI_INDICATOR:
			count = db.delete(CarServTableMetaData.TABLE_NAME, selection, selectionArgs);
			break;
			
		case CARSERV_SINGLE_URI_INDICATOR:
			String rowId = uri.getPathSegments().get(1);
			count = db.delete(CarServTableMetaData.TABLE_NAME, 					
					CarServTableMetaData._ID + "=" + rowId + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), 
					selectionArgs);
			break;
			
		default:
			throw new IllegalArgumentException("Nieznany ident. URI " + uri);			
		}
		
		getContext().getContentResolver().notifyChange(uri, null);						// powiadomienie, ¿e baza danych siê zmieni³a
		
		return count;
	}








}
