package com.example.parcelation;

import android.os.Bundle;

public class FieldDta {
	public static final String FIELD_VALUE_INT	=  "FIELD_VALUE_INT";
	public static final String FIELD_VALUE_STR 	=  "FIELD_VALUE_STR";
	private String 	fieldName;
	private Integer intValue;
	private String 	strValue;
	
	public FieldDta(String name, Bundle bundleField) {
		// convert this Bundle to fields
		this(name);
		intValue = bundleField.getInt(FIELD_VALUE_INT);
		strValue = bundleField.getString(FIELD_VALUE_STR);
	}
	
	public FieldDta(String name) {
		fieldName = name;
	}
	
	public FieldDta(String name, int value) {
		fieldName = name;
		setIntContent(value);
	}
	
	public FieldDta(String name, String value) {
		fieldName = name;
		setStringContent(value);
	}
	
	public FieldDta(String name, String valueStr, int valueInt) {
		fieldName = name;
		setStringContent(valueStr);
		setIntContent(valueInt);
	}
	
	public void setStringContent(String value){
		strValue = value;
	}
	
	public String getStringContent(){
		return strValue;
	}
	
	public void setIntContent(int value){
		intValue = value;
	}	
	
	public Integer getIntContent(){
		return intValue;
	}	
	
	public Bundle getAsBundle(){
		Bundle field = new Bundle();
		
		if(intValue != null){
			field.putInt(FIELD_VALUE_INT, intValue);
		}
		
		if(strValue != null){
			field.putString(FIELD_VALUE_STR, strValue);
		} else if(intValue != null){
			field.putString(FIELD_VALUE_STR, intValue.toString());
		}
		
		return field;
	}
	
	
	public String getFieldName(){
		return fieldName;
	}
}
