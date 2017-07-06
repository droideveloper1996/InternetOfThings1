package iotmaster.com.internetofthings;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Abhishek on 29/06/2017.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Internet-of-Things";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String DEVICE_UNIQUE_KEY = "unique-key";

    public static final String USERNAME = "username";

    public static final String SKIP_LOGIN = "true";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setUniqueKey(String uniqueKey) {
        if (uniqueKey != null && uniqueKey != "") {
            editor.putString(DEVICE_UNIQUE_KEY, uniqueKey);
            editor.commit();
        }
    }

    public String getKey() {
        return pref.getString(DEVICE_UNIQUE_KEY, "");
    }

    public void loginSuccessful(String username, Boolean response) {
        if (username != null && username != "") {
            editor.putString(USERNAME, username);
            editor.putBoolean(SKIP_LOGIN, response);
            editor.commit();
        }
    }

    public boolean isSuccessfulLogin(){
        return pref.getBoolean(SKIP_LOGIN,false);
    }

}