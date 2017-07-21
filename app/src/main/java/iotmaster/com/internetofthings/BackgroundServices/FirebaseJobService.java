package iotmaster.com.internetofthings.BackgroundServices;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.jobdispatcher.JobService;

import iotmaster.com.internetofthings.Network.NetworkUtils;


/**
 * Created by Abhishek on 08/07/2017.
 */

public class FirebaseJobService extends JobService {

    AsyncTask asyncTask;
    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        asyncTask=new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Context context=FirebaseJobService.this;
                Log.i("FirebaseJobDispatcher","Executed This JOB");
                NetworkUtils.checkDeviceStatus(context);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                jobFinished(job,false);
            }
        };
        asyncTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        if(asyncTask!=null)
        {
            asyncTask.cancel(true);
        }
        return true;
    }
}
