package pl.xt.jokii.db;

import java.util.ArrayList;

public class CarServResultsSet {
	private ArrayList<CarServEntry> entries = new ArrayList<CarServEntry>();
	
	
	/**
	 * Clear all entries
	 * @return
	 */
	public void init() {
		this.entries.clear();
	}	
	 
	/**
	 * Getter for entries
	 * @return
	 */
	public ArrayList<CarServEntry> getEntries() {
		return entries;
	}

	/**
	 * Setter for entries
	 * @param entries
	 */
	public void setEntries(ArrayList<CarServEntry> entries) {
		this.entries = entries;
	}	
	
	/**
	 * Get Headers list
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getHeadersList()
	{
		ArrayList<String>  headersList = new ArrayList<String>();
		
		for(int i = 0; i < this.entries.size(); i++)
		{
			headersList.add(this.entries.get(i).getHeader());
		}	
		
		return headersList;
	}
	
	/**
	 * Update entry with given id
	 * @param entryId		- key id to find entry
	 * @param carServEntry	- entry with new parameters
	 */
	public void updateEntry(long entryId, CarServEntry carServEntry)
	{
		CarServEntry e = getEntryById(entryId);
		
		e.setDate		(carServEntry.getDate());
		e.setHeader		(carServEntry.getHeader());
		e.setMileage	(carServEntry.getMileage());
		e.setType		(carServEntry.getType());
		
		/*int index = 0;
		
		for(CarServEntry e: this.entries)
		{
			if(e.getId() == entryId)
			{
				this.entries.get(index).setDate		(carServEntry.getDate());
				this.entries.get(index).setHeader	(carServEntry.getHeader());
				this.entries.get(index).setMileage	(carServEntry.getMileage());
				this.entries.get(index).setType		(carServEntry.getType());
				break;
			}
			index++;
		}
		*/
	}
	
	/**
	 * Get entry by data base id
	 * @param id			- data base id
	 * @return CarServEntry	- entry with all data
	 */
	public CarServEntry getEntryById(long id)
	{
		CarServEntry carServEntry = null;
		
		for(CarServEntry e: this.entries)
		{
			if(e.getId() == id)
			{
				carServEntry = e;
				break;
			}
		}		
		return carServEntry;
	}
	
	/**
	 * Add entry at the end of list
	 * @param e new entry
	 */
	public void addEnd(CarServEntry e)
	{
		entries.add(e);
	}

	/**
	 * Add entry at the begining of the list
	 * @param e new entry
	 */
	public void addBegin(CarServEntry e)
	{
		ArrayList<CarServEntry> entriesTmp = new ArrayList<CarServEntry>();
    	
		entriesTmp.add(e);
		entriesTmp.addAll(entries);
		entries.clear();
		entries.addAll(entriesTmp);		
	}
	
	/**
	 * Delete entry with given id
	 * @param entryId entry to delete
	 */
	public void deleteEntryId(long entryId)
	{
		int index = 0;
		
		for(CarServEntry e: entries)
		{
			if(e.getId() == entryId)
			{
				entries.remove(index);
				break;
			}
			index++;
		}
	}
	
	/**
	 * Create standard array from list
	 */
	private CarServEntry[] createArrayFromList(ArrayList<CarServEntry> inputList)
	{
		CarServEntry[] entriesArr = new CarServEntry[entries.size()];
		
		for(int i = 0; i < inputList.size(); i++)
		{
			entriesArr[i] = inputList.get(i);
		}
		
		return entriesArr;
	}
	
	/**
	 * Ceate list from array
	 */
	private ArrayList<CarServEntry> createListFromArray(CarServEntry[] inputArray)
	{
		ArrayList<CarServEntry> entriesList = new ArrayList<CarServEntry>();
		
		for(int i = 0; i < inputArray.length; i++)
		{
			entriesList.add(inputArray[i]);
		}
		
		return entriesList;
	}
	
	/**
	 * Sorting list by id ASC
	 */
	public void sortByIdAsc()
	{
		CarServEntry[] entriesArr = createArrayFromList(entries);
		CarServEntry   entryTmp;
		
		for(int j = 0; j < (entries.size()-1); j++)
		{
			for(int i = 0; i < (entries.size()-1); i++)
			{
				if(entriesArr[i].getId() > entriesArr[i+1].getId())
				{
					entryTmp = entriesArr[i];
					
					entriesArr[i]   = entriesArr[i+1];		
					entriesArr[i+1] = entryTmp;
				}
			}
		}
		
		entries = createListFromArray(entriesArr);
	}
	
	/**
	 * Sorting list by id DESC
	 */
	public void sortByIdDesc()
	{
		CarServEntry[] entriesArr = createArrayFromList(entries);
		CarServEntry   entryTmp;
		
		for(int j = 0; j < (entries.size()-1); j++)
		{
			for(int i = 0; i < (entries.size()-1); i++)
			{
				if(entriesArr[i].getId() < entriesArr[i+1].getId())
				{
					entryTmp = entriesArr[i];
					
					entriesArr[i]   = entriesArr[i+1];		
					entriesArr[i+1] = entryTmp;
				}
			}
		}
		
		entries = createListFromArray(entriesArr);
	}
	
	/**
	 * Sorting list by id ASC
	 */
	public void sortByMileageAsc()
	{
		CarServEntry[] entriesArr = createArrayFromList(entries);
		CarServEntry   entryTmp;
		
		for(int j = 0; j < (entries.size()-1); j++)
		{
			for(int i = 0; i < (entries.size()-1); i++)
			{
				if(entriesArr[i].getMileage() > entriesArr[i+1].getMileage())
				{
					entryTmp = entriesArr[i];
					
					entriesArr[i]   = entriesArr[i+1];		
					entriesArr[i+1] = entryTmp;
				}
			}
		}
		
		entries = createListFromArray(entriesArr);
	}
	
	/**
	 * Sorting list by id ASC
	 */
	public void sortByMileageDesc()
	{
		CarServEntry[] entriesArr = createArrayFromList(entries);
		CarServEntry   entryTmp;
		
		for(int j = 0; j < (entries.size()-1); j++)
		{
			for(int i = 0; i < (entries.size()-1); i++)
			{
				if(entriesArr[i].getMileage() < entriesArr[i+1].getMileage())
				{
					entryTmp = entriesArr[i];
					
					entriesArr[i]   = entriesArr[i+1];		
					entriesArr[i+1] = entryTmp;
				}
			}
		}
		
		entries = createListFromArray(entriesArr);
	}	
	
	/**
	 * Sorting list by id ASC
	 */
	public void sortByDateAsc()
	{
		CarServEntry[] entriesArr = createArrayFromList(entries);
		CarServEntry   entryTmp;
		
		for(int j = 0; j < (entries.size()-1); j++)
		{
			for(int i = 0; i < (entries.size()-1); i++)
			{
				if(entriesArr[i].getDate() > entriesArr[i+1].getDate())
				{
					entryTmp = entriesArr[i];
					
					entriesArr[i]   = entriesArr[i+1];		
					entriesArr[i+1] = entryTmp;
				}
			}
		}
		
		entries = createListFromArray(entriesArr);
	}
	
	/**
	 * Sorting list by id ASC
	 */
	public void sortByDateDesc()
	{
		CarServEntry[] entriesArr = createArrayFromList(entries);
		CarServEntry   entryTmp;
		
		for(int j = 0; j < (entries.size()-1); j++)
		{
			for(int i = 0; i < (entries.size()-1); i++)
			{
				if(entriesArr[i].getDate() < entriesArr[i+1].getDate())
				{
					entryTmp = entriesArr[i];
					
					entriesArr[i]   = entriesArr[i+1];		
					entriesArr[i+1] = entryTmp;
				}
			}
		}
		
		entries = createListFromArray(entriesArr);
	}	
}
