package iotmaster.com.internetofthings.UserInterface;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import iotmaster.com.internetofthings.R;

/**
 * Created by Abhishek on 02/07/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        addPreferencesFromResource(R.xml.preference);
    }
}
