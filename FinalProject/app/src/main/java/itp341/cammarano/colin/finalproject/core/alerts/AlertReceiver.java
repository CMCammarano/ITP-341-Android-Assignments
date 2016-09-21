package itp341.cammarano.colin.finalproject.core.alerts;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlertReceiver extends BroadcastReceiver {

	public static String NOTIFICATION_ID = "edu.usc.itp341.newday-id";
	public static String NOTIFICATION = "edu.usc.itp341.newday";

	public void onReceive(Context context, Intent intent) {

		// Once the alarm is received, wake up the device
		NotificationManager notificationManager = (NotificationManager)context.getSystemService (Context.NOTIFICATION_SERVICE);
		Notification notification = intent.getParcelableExtra (NOTIFICATION);
		int id = intent.getIntExtra (NOTIFICATION_ID, 0);
		notificationManager.notify (id, notification);
	}
}