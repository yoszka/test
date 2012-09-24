package pl.xt.jokii.adapter;

import java.util.Calendar;
import java.util.List;

import pl.xt.jokii.db.CarServEntry;
import pl.xt.jokii.carserv.R;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventAdapter extends BaseAdapter {
	final LayoutInflater inflater;
	final List<CarServEntry> entries;
	private Resources appResource = null;
	
	public EventAdapter(List<CarServEntry> entries, LayoutInflater inflater)
	{
		this.entries  = entries;
		this.inflater = inflater;		
	}
	
	public void setResource(Resources res)
	{
		this.appResource = res;
	}

	public int getCount() {
		return this.entries.size();
	}

	public CarServEntry getItem(int position) {
		return this.entries.get(position);
	}

	public long getItemId(int position) {
		return getItem(position).getId();
		//return this.entries.get(position).getId();
	}
	

	public View getView(int position, View wierszView, ViewGroup parent) {
		
		
		if (wierszView == null) {
			wierszView = inflater.inflate(R.layout.list_item, null);
		}		
		
		if(this.appResource == null)
		{
			throw new NullPointerException("Resource was used but never set before");
		}
		
		// TYPE
		TextView textViewTyp = (TextView)wierszView.findViewById(R.id.textViewTyp);		
		String[] entryType = this.appResource.getStringArray(R.array.entry_types);
		//textViewTyp.setText(entryType[this.entries.get(position).getType()]);
		textViewTyp.setText(entryType[getItem(position).getType()]);
		
		// HEADER
    	TextView textViewHeader = (TextView)wierszView.findViewById(R.id.textViewNaglowek);		
    	//textViewHeader.setText(this.entries.get(position).getHeader()+""); 		
    	textViewHeader.setText(getItem(position).getHeader()+"");
    	
    	// MILEAGE
    	TextView textViewMileage = (TextView)wierszView.findViewById(R.id.textViewMileage);		
    	//textViewMileage.setText(this.entries.get(position).getMileage()+" km");
    	textViewMileage.setText(getItem(position).getMileage()+" km");
    	
    	// DATE
    	TextView textViewDate = (TextView)wierszView.findViewById(R.id.textViewDate);		
		Calendar calRestored = Calendar.getInstance();
		//calRestored.setTimeInMillis(this.entries.get(position).getDate());            	
		calRestored.setTimeInMillis(getItem(position).getDate());
		
    	textViewDate.setText(calRestored.get(Calendar.DAY_OF_MONTH)+"."+(calRestored.get(Calendar.MONTH)+1)+"."+calRestored.get(Calendar.YEAR));    	
		
		
		return wierszView;
	}

}
