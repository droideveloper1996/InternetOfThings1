package iotmaster.com.internetofthings.BackgroundServices;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import iotmaster.com.internetofthings.Network.NetworkUtils;
import iotmaster.com.internetofthings.UserInterface.MainActivity.DeviceState;
import iotmaster.com.internetofthings.UserInterface.NotificationUtils;

/**
 * Created by Abhishek on 21/07/2017.
 */

public class ReminderHelper {

    public static final String DEVICE_STATUS_CHECK = "device-status-check";

    public static final String ALARM_SERVICE = "alarm-service";

    public static final String DISMISS_NOTIFICATION = "dismiss";

    public static final String NEGATIVE_ACTION = "negative";
    public static final String POSITIVE_ACTION = "positive";

    public static final String INTENT_ACTION_POSITIVE = "iotmaster.com.internetofthings.POSITIVE";
    public static final String INTENT_ACTION_NEGATIVE = "iotmaster.com.internetofthings.NEGATIVE";


    public static void executeTask(Context context, String action) {

        if (action.equals(DISMISS_NOTIFICATION)) {

            NotificationUtils.clearAllNotifications(context);
            Log.i(
                    "ReminderHelper.class", "Notification Clear"
            );
        } else if (action.equals(POSITIVE_ACTION)) {
            Log.i(
                    "ReminderHelper.class", "Action On"
            );
            NetworkUtils.getdata(context, "1");
            Intent intent = new Intent(context, DeviceState.class);
            intent.setAction(INTENT_ACTION_POSITIVE);
            context.sendBroadcast(intent);
           // NotificationUtils.clearAllNotifications(context);

        } else if (action.equals(NEGATIVE_ACTION)) {
            Log.i(
                    "ReminderHelper.class", "Action OFF"
            );
            NetworkUtils.getdata(context, "0");
            Intent intent = new Intent(context, DeviceState.class);
            intent.setAction(INTENT_ACTION_NEGATIVE);
            context.sendBroadcast(intent);
          //  NotificationUtils.clearAllNotifications(context);

        }

    }
}
