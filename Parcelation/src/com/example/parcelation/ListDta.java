package com.example.parcelation;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

public class ListDta {
	private List<EntryDta> list;
	
	/**
	 * Default constructor
	 */
	public ListDta() {
		list = new ArrayList<EntryDta>();
	}
	
	/**
	 * Constructor. Create list from Bundles array
	 * @param listArray
	 */
	public ListDta(Bundle[] listArray) {
		this();
		for(Bundle entry : listArray){
			addEntry(entry);
		}
	}
	
	
	/**
	 * Add entry to list. Entry is Bundle, 
	 * field name is a key value is Bundle, 
	 * which consist of int and string extras. 
	 * @param entry
	 */
	public void addEntry(Bundle entry){
		list.add(new EntryDta(entry));
	}
	
	
	public void addEntry(EntryDta entry){
		list.add(entry);
	}
	
	
	/**
	 * Return list as array of Bundles.
	 * @return
	 */
	public Bundle[] getListAsArray(){
		Bundle[] listArray;
		int count = list.size();
		
		if(count == 0){
			return null;
		}
		
		listArray = new Bundle[count];
		
		for(int i = 0; i < count; i++){
			listArray[i] = list.get(i).getAsBundle();
		}
				
		return listArray;
	}	
	
	
	
	/**
	 * Get list size.
	 * @return
	 */
	public int getListSize(){
		return list.size();
	}
	
	
	// Entry should be separate object which store data to Bundle too, 
	// but have additional methods to set string, int values, get entry as bundle,
	// construct entry from bundle, get names of fields as String[]
	/**
	 * Get single entry from list
	 * @param index
	 * @return
	 */
	public EntryDta getEntry(int index){
		return list.get(index);
	}
}
