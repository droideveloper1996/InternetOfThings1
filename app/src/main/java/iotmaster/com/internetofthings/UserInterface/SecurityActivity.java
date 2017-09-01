package iotmaster.com.internetofthings.UserInterface;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.messaging.FirebaseMessaging;

import iotmaster.com.internetofthings.R;

public class SecurityActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean currentState = sharedpreferences.getBoolean("state", false);
        final Editor editor = sharedpreferences.edit();


        View v = (View) findViewById(R.id.text);
        ToggleButton security_switch = (ToggleButton) findViewById(R.id.security_switch);

        if (!currentState) {
            security_switch.setChecked(false);
        } else {
            security_switch.setChecked(true);
        }

        security_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    editor.putBoolean("state", true);
                    editor.commit();
                    FirebaseMessaging.getInstance().subscribeToTopic("security");
                    Toast.makeText(getApplicationContext(), "Security Active", Toast.LENGTH_LONG).show();
                } else {

                    editor.putBoolean("state", false);
                    editor.commit();
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("security");
                    Toast.makeText(getApplicationContext(), "Security Inactive", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
