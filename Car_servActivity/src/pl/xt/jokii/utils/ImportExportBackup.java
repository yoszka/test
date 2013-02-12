package pl.xt.jokii.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.xt.jokii.db.CarServEntry;
import pl.xt.jokii.db.CarServResultsSet;
import pl.xt.jokii.db.DbUtils;
import pl.xt.jokii.db.CarServProviderMetaData.CarServTableMetaData;
import pl.xt.jokii.reminder.AlarmUtil;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class ImportExportBackup {
	private static final String TAG = "ImportExportBackup";
	public static final String BACKUP_FILE_NAME = "CarServBackup.txt";

	public static void writeToBackupFile(String newFileContent){
		BufferedWriter out = null;
		try {
			File root = Environment.getExternalStorageDirectory();
			File backupFile = new File(root, BACKUP_FILE_NAME);
			FileWriter filewriter = new FileWriter(backupFile);
			
			out = new BufferedWriter(filewriter);
			out.write(newFileContent);
            
		} catch (Exception e) {
			throw new RuntimeException("Can't write to file", e);
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					throw new RuntimeException("Can't close file", e);
				}
			}
		}
	}
	
	
	/**
	 * Read backup data from file
	 * @return Backup data file content or null if file doesn't exist
	 */
	public static String readFromBackupFile(){
		BufferedReader fileReader = null;
		try {
			File backupFile = new File(Environment.getExternalStorageDirectory().getPath()+"/"+BACKUP_FILE_NAME);
			FileInputStream inFile = new FileInputStream(backupFile);
			fileReader = new BufferedReader(new InputStreamReader(inFile));
			String dataRow = "";
			String buffer = "";
			while ((dataRow = fileReader.readLine()) != null) {
				buffer += dataRow + "\n";
			}
			return buffer;
		} catch (Exception e) {
			//throw new RuntimeException("Can't read from file", e);
			Log.e(TAG, "Can't read from file (file doesn't exist)");
			return null;
		}finally{
			if(fileReader != null){
				try {
					fileReader.close();
				} catch (IOException e) {
					throw new RuntimeException("Can't close file", e);
				}
			}
		}
	}
	
	
	/**
	 * Create backup content to store to backup file
	 * @param ctx
	 * @return
	 */
	public static String createBackup(Context ctx){
		CarServResultsSet resultsSet = new DbUtils(ctx.getContentResolver()).retrieveResultSet();
		ArrayList<CarServEntry> entries = resultsSet.getEntries();
		JSONArray jArrayAll = new JSONArray();
		
		for(CarServEntry entry: entries){
    		JSONObject jsonEntry = new JSONObject();
    		
    		try {
				jsonEntry.put(CarServTableMetaData.SERVICE_HEADER, 	entry.getHeader());		// String
				jsonEntry.put(CarServTableMetaData.SERVICE_MILEAGE, entry.getMileage());	// int
				jsonEntry.put(CarServTableMetaData.SERVICE_TYPE, 	entry.getType());		// int
				jsonEntry.put(CarServTableMetaData.SERVICE_DATE, 	entry.getDate());		// Long
				jsonEntry.put(CarServTableMetaData.SERVICE_EXPIRED, entry.isExpired());		// Boolean
			} catch (JSONException e) {
				e.printStackTrace();
			}	
    		
    		jArrayAll.put(jsonEntry);
		}
		
		return jArrayAll.toString();
	}
	
	
	/**
	 * Restore entries from backup. 
	 * This method read data from JSON formatted backup data string and add as new entries to DataBase.
	 * This method don't check for duplicates.
	 * @param ctx - context to get ContentResolver
	 * @param backupData - JSON formatted backup data
	 */
	public static void restoreFromBackup(Context ctx, String backupData){
		ArrayList<CarServEntry> entries = getEntriesFromBackup(backupData);
		DbUtils dbu = new DbUtils(ctx.getContentResolver());
		for(CarServEntry entry : entries){
			dbu.insertEntryDB(entry);
			
			 // Set up Alarm
			 AlarmUtil.setAlarm(ctx, entry.getId(), entry.getHeader(), entry.getDate());
		}
	}
	
	
	/**
	 * Retrieve entries from JSON formated "backupData" String
	 * @param backupData - JSON formatted backup data
	 * @return List of retrieved entries
	 */
	public static ArrayList<CarServEntry> getEntriesFromBackup(String backupData){
		CarServEntry carServEntry;
		ArrayList<CarServEntry> entries = new ArrayList<CarServEntry>();
		
		try {
			JSONArray jArrayReaded = new JSONArray(backupData);
			int arrayLength = jArrayReaded.length();
			JSONObject entryElement;
			
			for(int i = 0; i < arrayLength; i++){
				entryElement =  (JSONObject) jArrayReaded.get(i);
				carServEntry = new CarServEntry();
				
				carServEntry.setHeader	(entryElement.getString	(CarServTableMetaData.SERVICE_HEADER));
				carServEntry.setMileage	(entryElement.getInt	(CarServTableMetaData.SERVICE_MILEAGE));
				carServEntry.setType	(entryElement.getInt	(CarServTableMetaData.SERVICE_TYPE));
				carServEntry.setDate	(entryElement.getLong	(CarServTableMetaData.SERVICE_DATE));
				carServEntry.setExpired	(entryElement.getBoolean(CarServTableMetaData.SERVICE_EXPIRED));
				
				entries.add(carServEntry);
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		return entries;
	}
}
