package iotmaster.com.internetofthings.Broadcasts;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Abhishek on 01/08/2017.
 */

public class RebootBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        makeAlarm(context);

    }

    private void makeAlarm(Context context) {

        Intent intent = new Intent(context, AlarmBroadCast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 45, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5 * 1000, 3000, pendingIntent);
    }
}
