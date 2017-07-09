package iotmaster.com.internetofthings;


import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

/**
 * Created by Abhishek on 08/07/2017.
 */

public class DeviceStatusCheck {

    int REMINDER_TIME_INTERVAL=15;
    int REMINDER_TIME_SECONDS= (int) TimeUnit.MINUTES.toSeconds(REMINDER_TIME_INTERVAL);
    public static final String JOB_TAG="check_device_status";

    private static boolean sInitialized;

   synchronized public static void checkStatus(Context context)
    {
        if(sInitialized) return;
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job myJob = dispatcher.newJobBuilder()
                // the JobService that will be called
                .setService(FirebaseJobService.class)
                // uniquely identifies the job
                .setTag(JOB_TAG)
                // one-off job
                .setRecurring(true)
                // don't persist past a device reboot
                .setLifetime(Lifetime.FOREVER)
                // start between 0 and 15 minutes (900 seconds)
                .setTrigger(Trigger.executionWindow(0, 1))
                // overwrite an existing job with the same tag
                .setReplaceCurrent(true)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                // constraints that need to be satisfied for the job to run
                .setConstraints(
                        // only run on an unmetered network

                        // only run when the device is charging
                         Constraint.ON_ANY_NETWORK

                )
                .build();
        dispatcher.schedule(myJob);
        sInitialized=true;
    }
}
