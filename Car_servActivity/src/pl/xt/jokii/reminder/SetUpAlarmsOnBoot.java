package pl.xt.jokii.reminder;

import java.util.Calendar;
import pl.xt.jokii.db.CarServEntry;
import pl.xt.jokii.db.CarServResultsSet;
import pl.xt.jokii.db.DbUtils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SetUpAlarmsOnBoot extends BroadcastReceiver{
	public static int idxAlarmMessage = 0;

	@Override
	public void onReceive(Context context, Intent intent) {
		CarServResultsSet resultsSet = new DbUtils(context.getContentResolver()).retrieveResultSet();
		if(resultsSet != null){
			Log.v("RECEIVER", "Boot, entries: " + resultsSet.getEntries().size());
			
			// get a Calendar object with current time
			Calendar cal = Calendar.getInstance();
			// add 5 minutes to the calendar object
//		 cal.add(Calendar.MINUTE, 10);
			cal.add(Calendar.SECOND, 15);
			
			Log.v("RECEIVER", "Time: " + cal.getTimeInMillis());
			
			for(CarServEntry entry : resultsSet.getEntries()){
				if(!entry.isExpired()){
					if(entry.getDate() < (cal.getTimeInMillis()/*+1000000000*/)  ){		// Alarm time expired but wasn't shown to user
						AlarmUtil.setAlarm(context, entry.getId(), entry.getHeader(), cal.getTimeInMillis());		// Show alarm from 15 second
//					AlarmUtil.setAlarm(context, data.getTime());
						Log.i("RECEIVER", "ALARM time expired: " + entry.getDate());
					}else{
						AlarmUtil.setAlarm(context, entry.getId(), entry.getHeader(), entry.getDate());				// alarm time not expired yet - set up timer
						Log.i("RECEIVER", "ALARM sheduled: " + entry.getDate());
					}
				}else{
					Log.i("RECEIVER", "ALARM expired: " + entry.getDate());
				}
			}
		}
	}
	
	
//	private void setAlarm(Context context, long id, String text, long time){
//		 Intent intent = new Intent(context, ReminderAlarm.class);
//		 intent.putExtra(ReminderAlarm.ALARM_ENTRY_ID, 	id);				// send entry id to update (set expired)
//		 intent.putExtra(ReminderAlarm.ALARM_TEXT, 		text);				// send entry id to update (set expired)
//		 
//		 PendingIntent sender = PendingIntent.getBroadcast(context, ++idxAlarmMessage, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//		 
//		 // Get the AlarmManager service
//		 AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//		 am.set(AlarmManager.RTC_WAKEUP, time, sender);		
//	}

}
