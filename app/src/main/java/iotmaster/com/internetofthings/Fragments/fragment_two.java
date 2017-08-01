package iotmaster.com.internetofthings.Fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import iotmaster.com.internetofthings.Broadcasts.AlarmBroadCast;
import iotmaster.com.internetofthings.R;

/**
 * Created by Abhishek on 30/07/2017.
 */

public class fragment_two extends android.support.v4.app.Fragment {
    TextView txt;
    TimePickerDialog timePickerDialog;
    TextView save;
    TimePickerDialog.OnTimeSetListener timeSetListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        txt = (TextView) view.findViewById(R.id.addAlarm);
        save = (TextView) view.findViewById(R.id.save_alarm);
        txt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c1 = Calendar.getInstance();
                int hr = c1.get(Calendar.HOUR_OF_DAY);
                int min = c1.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        timeSetListener,
                        hr,
                        min,
                        true);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        timeSetListener = new OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(getContext(), hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
            }
        };

        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                makeAlarm();
            }
        });
        return view;


    }

    private void makeAlarm() {

        Intent intent = new Intent(getContext(), AlarmBroadCast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 45, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5 * 1000, 3000, pendingIntent);
    }


}
