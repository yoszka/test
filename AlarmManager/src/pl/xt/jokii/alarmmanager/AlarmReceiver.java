package pl.xt.jokii.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver  {
	public static final String ALARM_MESSAGE = "alarm_message"; 

	@Override
	public void onReceive(Context context, Intent intent) {

	   // display received message
	   try {
		     Bundle bundle = intent.getExtras();
		     String message = bundle.getString(ALARM_MESSAGE);
		     Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		    } catch (Exception e) {
		     Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
		     e.printStackTrace();		 
		    }
	   
	   // Run some activity
	   try {
		     Bundle bundle = intent.getExtras();
		     String message = bundle.getString(ALARM_MESSAGE);
		     
		     Intent newIntent = new Intent(context, AlarmSender.class);
		     newIntent.putExtra(ALARM_MESSAGE, message);
		     newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		     context.startActivity(newIntent);
		    } catch (Exception e) {
		     Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
		     e.printStackTrace();
		 
		    }	   
		
	}
}
