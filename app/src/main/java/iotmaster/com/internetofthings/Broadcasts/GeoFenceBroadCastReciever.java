package iotmaster.com.internetofthings.Broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import iotmaster.com.internetofthings.UserInterface.NotificationUtils;

/**
 * Created by Abhishek on 19/07/2017.
 */

public class GeoFenceBroadCastReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationUtils.GeoNotification(context);
    }
}
