package iotmaster.com.internetofthings.BackgroundServices;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import iotmaster.com.internetofthings.R;

/**
 * Created by Abhishek on 06/08/2017.
 */

public class WidgetBackground extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public static final String ACTION_TURN_ON = "ON";
    public static final String ACTION_TURN_OFF = "OFF";
    public static final String ACTION_UPDATE_WIDGET = "update";

    public static void startActionWaterPlants(Context context) {
        Intent intent = new Intent(context, WidgetBackground.class);
        intent.setAction(ACTION_TURN_ON);
        context.startService(intent);
    }

    public static void startActionUpdateWidget(Context context) {
        Intent in = new Intent(context, WidgetBackground.class);
        in.setAction(ACTION_UPDATE_WIDGET);
        context.startService(in);

    }

    public WidgetBackground() {
        super("WidgetBackground");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i("This", "Fired onIntentHandle");
        if (intent != null) {
            final String action = intent.getAction();
            Log.i("This", action);

            if (action.equals(ACTION_TURN_ON)) {
                makeNetworkCall();
            } else if (action.equals(ACTION_UPDATE_WIDGET)) {
                updateWidget();
            }
        }
    }

    private  void updateWidget() {
        Log.i("This","UpdateWidget");
        AppWidgetManager ap = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = ap.getAppWidgetIds(new ComponentName(this, WidgetBackground.class));
        int imgRes = (R.drawable.yellow);
        //DeviceWidgetProvider.updateWidgets(this, ap, imgRes, appWidgetIds);
    }

    private void makeNetworkCall() {

        Log.i("This", "Fired Network Call");
    }
}
