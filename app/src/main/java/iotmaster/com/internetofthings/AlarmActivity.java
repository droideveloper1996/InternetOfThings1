package iotmaster.com.internetofthings;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AlarmActivity extends AppCompatActivity {

    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        Button button = (Button) findViewById(R.id.setTime);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final EditText intr=(EditText)findViewById(R.id.interval);
        final ToggleButton toggleButton=(ToggleButton)findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    toggleButton.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

                    Toast.makeText(AlarmActivity.this,"On",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    toggleButton.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                    Toast.makeText(AlarmActivity.this,"off",Toast.LENGTH_SHORT).show();
                }
            }
        });{

        }
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = timePicker.getHour();
                int min = timePicker.getMinute();
                int inter=24;
                try {
                    inter = Integer.parseInt(intr.getText().toString());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(AlarmActivity.this,"Problem Setting alarm",Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(), "Current Time = " + timePicker.getCurrentHour() + ":" + timePicker.getMinute(), Toast.LENGTH_SHORT).show();
                setAlarm(hour, min,inter);

                finish();
            }


        });



    }

    private void setAlarm(int hour, int min,int inteval) {

        Intent intent = new Intent(this, AlarmBroadCast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * inteval * 60, pendingIntent);
    }
}
