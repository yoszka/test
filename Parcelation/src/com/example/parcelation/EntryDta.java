package com.example.parcelation;

import java.util.List;

import android.os.Bundle;

public class EntryDta {
	private List<FieldDta> fields;
	
	public EntryDta(Bundle entry) {
		// parse this Bundle to separate separate fields
	}
	
	public Bundle getAsBundle(){
		Bundle entry = new Bundle();
		
		for(FieldDta field : fields){
			entry.putBundle(field.getFieldName(), field.getAsBundle());
		}		
		
		return entry;		
	}
	
	public void addField(FieldDta field){
		fields.add(field);
	}
}
