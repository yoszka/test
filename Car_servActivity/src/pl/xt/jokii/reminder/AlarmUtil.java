package pl.xt.jokii.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmUtil {
	public static String ALARM_TEXT 	= "ALARM_TEXT";
	public static String ALARM_ENTRY_ID = "ALARM_ENTRY_ID";
	private static int idxAlarmMessage = 0;
	
	public static void setAlarm(Context context, long id, String text, long time){
		 Intent intent = new Intent(context, ReminderAlarm.class);
		 intent.putExtra(ALARM_ENTRY_ID, 	id);				// send entry id to update (set expired)
		 intent.putExtra(ALARM_TEXT, 		text);				// send entry id to update (set expired)
		 
		 PendingIntent sender = PendingIntent.getBroadcast(context, ++idxAlarmMessage, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		 
		 // Get the AlarmManager service
		 AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		 am.set(AlarmManager.RTC_WAKEUP, time, sender);		
	}

}
