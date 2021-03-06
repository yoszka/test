package pl.xt.jokii.alarmmanager;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class AlarmSender extends Activity{
	public static final int IDX_ALARM_MESSAGE = 192837;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
	}
	
	/**
	 * Configure scheduled task
	 */
	private void setScheduleTask()
	{
		// get a Calendar object with current time
		 Calendar cal = Calendar.getInstance();
		 // add 5 minutes to the calendar object
		 cal.add(Calendar.MINUTE, 10);
		 //cal.add(Calendar.SECOND, 15);
		 
		 Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
		 intent.putExtra(AlarmReceiver.ALARM_MESSAGE, "Jokii is great");
		 
		 PendingIntent sender = PendingIntent.getBroadcast(this, IDX_ALARM_MESSAGE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		 
		 // Get the AlarmManager service
		 AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		 am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
		 //am.setRepeating(AlarmManager.RTC_WAKEUP, 5*1000, 5*1000, sender);
		 //am.setInexactRepeating(AlarmManager.RTC_WAKEUP, 5*1000, 5*1000, sender);
		 Log.v("setScheduleTask", "Task scheduled");
		 Toast.makeText(getApplicationContext(), "Task scheduled", Toast.LENGTH_SHORT).show();
	}
	
	
	
	/**
	 * Callback method for button click 
	 * @param v - related view
	 */
	public void onClickScheduleEvent(View v)
	{
		setScheduleTask();
	}

}
