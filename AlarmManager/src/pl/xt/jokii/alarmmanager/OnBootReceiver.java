package pl.xt.jokii.alarmmanager;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class OnBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		setScheduleTask(context);
		
	}
	
	
	/**
	 * Configure scheduled task
	 */
	private void setScheduleTask(Context context)
	{
		// get a Calendar object with current time
		 Calendar cal = Calendar.getInstance();
		 // add 5 minutes to the calendar object
		 //cal.add(Calendar.MINUTE, 10);
		 cal.add(Calendar.SECOND, 30);
		 
		 Intent intent = new Intent(context, AlarmReceiver.class);
		 intent.putExtra(AlarmReceiver.ALARM_MESSAGE, "Jokii is great");
		 
		 PendingIntent sender = PendingIntent.getBroadcast(context, AlarmSender.IDX_ALARM_MESSAGE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		 
		 // Get the AlarmManager service
		 AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		 am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
		 //am.setRepeating(AlarmManager.RTC_WAKEUP, 5*1000, 5*1000, sender);
		 //am.setInexactRepeating(AlarmManager.RTC_WAKEUP, 5*1000, 5*1000, sender);
		 Log.v("setScheduleTask", "Task scheduled");
		 Toast.makeText(context, "Task scheduled", Toast.LENGTH_SHORT).show();
	}	

}
