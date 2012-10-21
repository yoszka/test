package com.example.parcelation;

import java.util.ArrayList;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class ParcelTest implements Parcelable {
	private ArrayList<Bundle> list;
 
    public ParcelTest() {
    	list = new ArrayList<Bundle>();
    }
 
    public ParcelTest(Parcel in) {
    	list = new ArrayList<Bundle>();
        readFromParcel(in);
    }
 
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ParcelTest createFromParcel(Parcel in) {
            return new ParcelTest(in);
        }
 
        public ParcelTest[] newArray(int size) {
            return new ParcelTest[size];
        }
    };
 
    public int describeContents() {
        return 0;
    }
 
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(list.size());        
        for (Bundle entry: list) {
            dest.writeBundle(entry);
        }
    }
 
    public void readFromParcel(Parcel in) {
        int count = in.readInt();
        list.clear();
        for (int i = 0; i < count; i++) {
        	list.add(in.readBundle());
        }
    }
 
    public ArrayList<Bundle> getList() {
        return list;
    }
    
    public Bundle getEntry(int index) {
        return list.get(index);
    }    
 
    public void addEntry(Bundle entry) {
        list.add(entry);
    }
    
    public void putList(ArrayList<Bundle> list) {
        this.list = list;
    }    
}
