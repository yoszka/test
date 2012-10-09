package pl.xt.jokii.reminder;

import pl.xt.jokii.carserv.Car_servActivity;
import pl.xt.jokii.db.CarServEntry;
import pl.xt.jokii.db.DbUtils;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ReminderAlarm extends BroadcastReceiver{
	public static String ALARM_TEXT 	= "ALARM_TEXT";
	public static String ALARM_ENTRY_ID = "ALARM_ENTRY_ID";
	private NotificationManager notificationManager;
	private Context mContext;
//	private static int requestCode = 0;

	@Override
	public void onReceive(Context context, Intent intent) {				
		long entryId = 0;
		String alarmText = null;
		
		mContext = context;
		if(intent != null){
			entryId = intent.getLongExtra(ALARM_ENTRY_ID, 0);
			alarmText = intent.getStringExtra(ALARM_TEXT);
		}
		
		Log.w("RECEIVER", "ALARM: ID=" + entryId + ", text: " + alarmText);				
		
		Toast.makeText(context, "Car_Serv: " + alarmText, Toast.LENGTH_LONG).show();
		
		// Make this entry EXPIRED
		DbUtils dbUtilDriver = new DbUtils(context.getContentResolver());
		CarServEntry entry = dbUtilDriver.getEntryFromDB(entryId);			
		entry.setExpired(true);
		dbUtilDriver.updateEntryDB(entryId, entry);
		
		// Display notification in status bar
		notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		displayNotificationMessage(alarmText, entryId);		
	}
	
	
	
	/**
	 * Display notification in status bar
	 * @param message
	 */
	private void displayNotificationMessage(String message, long entryId) {
		// For API max 15
		Notification notification = new Notification(android.R.drawable.ic_notification_overlay, message, System.currentTimeMillis());
	    // Hide the notification after its selected
	    notification.flags |= Notification.FLAG_AUTO_CANCEL;	    	    
		
//		Intent intent = new Intent(mContext, EntryDetail.class);									// new Intent(mContext, Car_servActivity.class)
	    Intent intent = new Intent();
		intent.setAction("pl.xt.jokii.carserv.action.EntryDetail");		
		intent.putExtra(Car_servActivity.SHOW_ITEM_RES, entryId); // extras.getLong(Car_servActivity.SHOW_ITEM_RES);
						
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, (int)entryId, intent, PendingIntent.FLAG_ONE_SHOT);
		
		notification.setLatestEventInfo(mContext, "Car_Serv", message, contentIntent);
		notificationManager.notify((int)entryId, notification);		// android.R.id.message -> some ID can be random number
		
		
		
		
		// FOR API 16
//		Notification.Builder builder = new Notification.Builder(this);		
//		
//		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
//		
//		builder.setContentIntent(contentIntent)
//											.setSmallIcon(android.R.drawable.ic_notification_overlay)
//											.setWhen(System.currentTimeMillis())
//											.setContentText(message)
//											.setContentTitle("Background service");	
//		
//		Notification notification = builder.build();
//		
//		notificationManager.notify(android.R.id.message, notification);		// android.R.id.message -> some ID can be random number
		
	}	

}
