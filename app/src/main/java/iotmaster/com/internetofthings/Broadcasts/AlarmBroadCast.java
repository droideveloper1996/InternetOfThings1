package iotmaster.com.internetofthings.Broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import iotmaster.com.internetofthings.BackgroundServices.ReminderHelper;
import iotmaster.com.internetofthings.Network.NetworkUtils;
import iotmaster.com.internetofthings.UserInterface.MainActivity.DeviceState;

/**
 * Created by Abhishek on 09/07/2017.
 */

public class AlarmBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      /*  NetworkUtils.getdata(context, "0");
        NotificationUtils.alarmNotification(context);*/
        String action = intent.getAction();
        // NotificationUtils.alarmNotification(context);
        NetworkUtils.getdata(context, action);
        Intent i = new Intent(context, DeviceState.class);
        if (action.equals("1")) {
            i.setAction(ReminderHelper.INTENT_ACTION_POSITIVE);
        } else if (action.equals("0")) {
            i.setAction((ReminderHelper.INTENT_ACTION_NEGATIVE));
        }
        context.sendBroadcast(i);
       // Toast.makeText(context, "Alarm Broadcast" + action, Toast.LENGTH_LONG).show();

    }
}
