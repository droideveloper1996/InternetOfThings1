package iotmaster.com.internetofthings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Abhishek on 09/07/2017.
 */

public class AlarmBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkUtils.getdata(context, "0");
        NotificationUtils.alarmNotification(context);

    }
}
