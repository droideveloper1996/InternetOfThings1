package iotmaster.com.internetofthings.FireBaseCloudMessaging;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Abhishek on 24/07/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public static  final String TAG="";
    public static  final String MyPREFERENCES="token";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d ("Refreshed: " , refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        addTokentoPrefrences(refreshedToken);

    }



    private void addTokentoPrefrences(String token)
    {
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Editor editor = sharedpreferences.edit();
        editor.putString("fcm_token", token);
        editor.apply();
    }
}
