package iotmaster.com.internetofthings;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
/**
 * Created by Abhishek on 29/06/2017.
 */

public class NotificationUtils {
    private Context mContext;
    public static final int PENDING_INTENT_CODE = 5543;
    public static final int NOTIFICATION_CODE = 3434;

    NotificationUtils(Context context) {
        this.mContext = context;
    }

    public static void installNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(ContentIntent(context))
                .setAutoCancel(true)
                .setContentText("Better Way to Control your Devices..")
                .setLargeIcon(bitmap(context))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Welcome to IoT")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        ("Internet of Things")));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_CODE, builder.build());
    }

    public static PendingIntent ContentIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(context, PENDING_INTENT_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

    public static Bitmap bitmap(Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        return bitmap;
    }
}
