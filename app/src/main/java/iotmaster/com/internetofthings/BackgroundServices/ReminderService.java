package iotmaster.com.internetofthings.BackgroundServices;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Abhishek on 21/07/2017.
 */

public class ReminderService extends IntentService {

    public ReminderService() {
        super("MyClass");
    }

    @Override
    protected void onHandleIntent( Intent intent) {

        String action=intent.getAction();

        ReminderHelper.executeTask(this,action);
    }
}
