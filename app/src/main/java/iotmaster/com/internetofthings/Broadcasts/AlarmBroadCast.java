package iotmaster.com.internetofthings.Broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Abhishek on 09/07/2017.
 */

public class AlarmBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      /*  NetworkUtils.getdata(context, "0");
        NotificationUtils.alarmNotification(context);*/
        Toast.makeText(context,"Alarm Broadcast",Toast.LENGTH_LONG).show();

    }
}
