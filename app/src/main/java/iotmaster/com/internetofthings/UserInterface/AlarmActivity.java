package iotmaster.com.internetofthings.UserInterface;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

import iotmaster.com.internetofthings.Broadcasts.AlarmBroadCast;
import iotmaster.com.internetofthings.R;

import static android.widget.Toast.makeText;
import static java.lang.Integer.parseInt;

public class AlarmActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    AlarmManager alarmManager;
    Long intervalFrequency = 0l;
    public static final String TAG = "AlarmActivity";
    EditText intr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        Button button = (Button) findViewById(R.id.setTime);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        intr = (EditText) findViewById(R.id.interval);
        final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButton.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

                    makeText(AlarmActivity.this, "On", Toast.LENGTH_SHORT).show();

                } else {
                    toggleButton.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                    makeText(AlarmActivity.this, "off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = timePicker.getCurrentHour();
                int min = timePicker.getCurrentMinute();
                int inter = -1;
                try {
                    inter = parseInt(intr.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    makeText(AlarmActivity.this, "Problem Setting alarm", Toast.LENGTH_LONG).show();
                    inter = 24;
                } finally {
                    makeText(getApplicationContext(), "Current Time = " + timePicker.getCurrentHour() + ":" + timePicker.getMinute(), Toast.LENGTH_SHORT).show();
                    setAlarm(hour, min);
                }
                finish();
            }


        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.timer_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void setAlarm(int hour, int min) {

        Intent intent = new Intent(this, AlarmBroadCast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                intervalFrequency, pendingIntent);
        Log.i("AlarmActivity", intervalFrequency.toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        long timX = Long.parseLong(intr.getText().toString());

        switch (position) {
            case 0:
                intervalFrequency = timX * 3600 * 1 * 1000;
                break;
            case 1:
                intervalFrequency = timX * 60 * 1000;
                break;
            case 2:
                intervalFrequency = timX * 1000;
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        makeText(AlarmActivity.this, "Select Interval From Menu", Toast.LENGTH_SHORT).show();
    }
}
