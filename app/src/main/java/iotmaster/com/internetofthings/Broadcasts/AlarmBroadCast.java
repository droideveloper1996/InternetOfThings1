package iotmaster.com.internetofthings.Broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import iotmaster.com.internetofthings.Network.NetworkUtils;
import iotmaster.com.internetofthings.UserInterface.NotificationUtils;

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
