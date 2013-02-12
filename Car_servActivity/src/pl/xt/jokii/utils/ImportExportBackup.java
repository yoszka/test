package pl.xt.jokii.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.os.Environment;

public class ImportExportBackup {
	private static final String BACKUP_FILE_NAME = "CarServBackup.txt";

	public static void writeToBackupFile(String newFileContent){
		BufferedWriter out = null;
		try {
			File root = Environment.getExternalStorageDirectory();
//			File backupFile = new File(Environment.getExternalStorageDirectory().getPath()+"/"+BACKUP_FILE_NAME);
			File backupFile = new File(root, BACKUP_FILE_NAME);
			FileWriter filewriter = new FileWriter(backupFile);
			
			out = new BufferedWriter(filewriter);
			out.write(newFileContent);
            
			
//			backupFile.createNewFile();
//			FileOutputStream outFile = new FileOutputStream(backupFile);
//			OutputStreamWriter SdCardWriter = new OutputStreamWriter(outFile);
//			SdCardWriter.append("Some text to write " + Math.random()*1000);
//			SdCardWriter.close();
//			outFile.close();
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
			throw new RuntimeException("Can't read from file", e);
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
}
