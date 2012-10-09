package pl.xt.jokii.reminder;

import pl.xt.jokii.carserv.Car_servActivity;
import pl.xt.jokii.carserv.EntryDetail;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ReminderAlarm extends BroadcastReceiver{
	private NotificationManager notificationManager;
	private Context mContext;
	public static String ALARM_MESSAGE = "ALARM_MESSAGE";
	private int requestCode = 0;

	@Override
	public void onReceive(Context context, Intent intent) {				
		// Otwieranie okna dialogowego z info o alarmie, updatowanie entrysa
		long entryId = 0;
		mContext = context;
		if(intent != null){
			entryId = intent.getLongExtra(ALARM_MESSAGE, 0);
		}
		
		Log.w("RECEIVER", "ALARM: " + entryId);				
		
		Toast.makeText(context, "Car_Serv ALARM EXPIRED for ID: " + entryId, Toast.LENGTH_LONG).show();
		
		notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		displayNotificationMessage("Car_Serv ALARM expired", entryId);		
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
		
		Intent intent = new Intent(mContext, EntryDetail.class);									// new Intent(mContext, Car_servActivity.class)
//	    Intent intent = new Intent()
//		intent.setAction("pl.xt.jokii.carserv.action.EntryDetail");
		intent.setAction(intent.toString());		
		intent.putExtra(Car_servActivity.SHOW_ITEM_RES, entryId); // extras.getLong(Car_servActivity.SHOW_ITEM_RES);
						
//		PendingIntent contentIntent = PendingIntent.getActivity(mContext, ++requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, ++requestCode, intent, 0);
		
		notification.setLatestEventInfo(mContext, "Car_Serv", message, contentIntent);
		
		notificationManager.notify(android.R.id.message, notification);		// android.R.id.message -> some ID can be random number
		
		
		
		
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
