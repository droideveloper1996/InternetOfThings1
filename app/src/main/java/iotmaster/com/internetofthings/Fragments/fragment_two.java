package iotmaster.com.internetofthings.Fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

import iotmaster.com.internetofthings.Broadcasts.AlarmBroadCast;
import iotmaster.com.internetofthings.R;
import iotmaster.com.internetofthings.data.AlamContract.AlamEntry;

/**
 * Created by Abhishek on 30/07/2017.
 */

public class fragment_two extends android.support.v4.app.Fragment {
    TextView txt;
    TimePickerDialog timePickerDialog;
    TextView save;
    TextView cancel;
    static int c1 = -1, c2 = -1;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    CheckBox checkBox5;
    CheckBox checkBox6;
    CheckBox checkBox7;
    CheckBox checkBoxAll;
    ArrayList<Integer> list;
    Switch actionSwitch;

    public static final String ACTION_ON = "1";
    public static final String ACTION_OFF = "0";

    TimePickerDialog.OnTimeSetListener timeSetListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        txt = (TextView) view.findViewById(R.id.addAlarm);
        Time time = new Time(System.currentTimeMillis());
        cancel = (TextView) view.findViewById(R.id.cancel);


        checkBoxAll = (CheckBox) view.findViewById(R.id.checkboxAll);

        checkBox1 = (CheckBox) view.findViewById(R.id.checkbox1);
        checkBox2 = (CheckBox) view.findViewById(R.id.checkbox2);
        checkBox3 = (CheckBox) view.findViewById(R.id.checkbox3);
        checkBox4 = (CheckBox) view.findViewById(R.id.checkbox4);
        checkBox5 = (CheckBox) view.findViewById(R.id.checkbox5);
        checkBox6 = (CheckBox) view.findViewById(R.id.checkbox6);
        checkBox7 = (CheckBox) view.findViewById(R.id.checkbox7);

        actionSwitch = (Switch) view.findViewById(R.id.switch_action);
        list = new ArrayList<>();

        String t = time.toString();
        txt.setText(t.substring(0, t.lastIndexOf(':')));
        save = (TextView) view.findViewById(R.id.save_alarm);
        txt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c1 = Calendar.getInstance();
                int hr = c1.get(Calendar.HOUR_OF_DAY);
                int min = c1.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
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
                String m = "";
                String hour = "";
                if (minute < 9) {
                    m = "0" + minute;
                } else {
                    m = String.valueOf(minute);
                }
                if (hourOfDay < 9) {
                    hour = "0" + hourOfDay;
                } else {
                    hour = String.valueOf(hourOfDay);
                }
                txt.setText(hour + ":" + m);
                c1 = hourOfDay;
                c2 = minute;
            }
        };

        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO when RepeatAll is checked
                //TODO Add to AlarmDataBase.
                if (checkBoxAll.isChecked()) {
                    if (c1 != -1 && c2 != -1) {
                        makeAlarm(c1, c2);
                    } else {
                        Toast.makeText(getContext(), "Error Adding Alarm", Toast.LENGTH_LONG).show();

                    }
                } else {
                    //TODO  when Specific days are permitted
                    if (checkBox7.isChecked()) {
                        Log.i("AlarmSetup", "1");
                        makeAlarmDaySpecific(1, c1, c2);

                    }
                    if (checkBox1.isChecked()) {
                        Log.i("AlarmSetup", "2");

                        makeAlarmDaySpecific(2, c1, c2);

                    }
                    if (checkBox2.isChecked()) {
                        Log.i("AlarmSetup", "3");

                        makeAlarmDaySpecific(3, c1, c2);

                    }
                    if (checkBox3.isChecked()) {
                        Log.i("AlarmSetup", "4");

                        makeAlarmDaySpecific(4, c1, c2);

                    }
                    if (checkBox4.isChecked()) {
                        Log.i("AlarmSetup", "5");

                        makeAlarmDaySpecific(5, c1, c2);

                    }
                    if (checkBox5.isChecked()) {
                        Log.i("AlarmSetup", "6");

                        makeAlarmDaySpecific(6, c1, c2);

                    }
                    if (checkBox6.isChecked()) {
                        Log.i("AlarmSetup", "7");

                        makeAlarmDaySpecific(7, c1, c2);

                    }


                    if (!checkBox1.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked()
                            && !checkBox4.isChecked() && !checkBox5.isChecked() && !checkBox6.isChecked() &&
                            !checkBox7.isChecked()) {
                        Toast.makeText(getContext(), "Please Select Days", Toast.LENGTH_LONG).show();
                    }

                }

                addToDB();
                getActivity().finish();

            }


        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        checkBoxAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkAll();
                } else if (!isChecked) {

                    uncheckAll();

                }
            }
        });


        return view;


    }

    private void makeAlarm(int hr, int min) {

        //TODO: Change Third Parameter to 24 hour recurring time.
        Log.i("AlarmFragment", hr + ":" + min);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        //  calendar.set(Calendar.DAY_OF_WEEK,);
        calendar.set(Calendar.HOUR_OF_DAY, hr);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);


        Intent intent = new Intent(getContext(), AlarmBroadCast.class);
        if (actionSwitch.isChecked()) {
            intent.setAction(ACTION_ON);
        } else {
            intent.setAction(ACTION_OFF);
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 45, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 5 * 1000, AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    void checkAll() {
        checkBox1.setChecked(true);
        checkBox2.setChecked(true);
        checkBox3.setChecked(true);
        checkBox4.setChecked(true);
        checkBox5.setChecked(true);
        checkBox6.setChecked(true);
        checkBox7.setChecked(true);

    }

    void uncheckAll() {
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        checkBox4.setChecked(false);
        checkBox5.setChecked(false);
        checkBox6.setChecked(false);
        checkBox7.setChecked(false);
    }

    private void makeAlarmDaySpecific(int a, int hr, int min) {

        //TODO: Change Third Parameter to 24 hour recurring time.
        Log.i("AlarmFragment", hr + ":" + min);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK, a);
        calendar.set(Calendar.HOUR_OF_DAY, hr);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);


        Intent intent = new Intent(getContext(), AlarmBroadCast.class);
        if (actionSwitch.isChecked()) {
            intent.setAction(ACTION_ON);
        } else {
            intent.setAction(ACTION_OFF);
        }
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 45, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 5 * 1000, AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    public void addToDB() {
        ContentResolver contentResolver = getContext().getContentResolver();
        ContentValues values = new ContentValues();
        String action = "off";
        String day1 = "false", day2 = "false", day3 = "false",
                day4 = "false", day5 = "false", day6 = "false", day7 = "false";
        if (actionSwitch.isChecked()) {
            action = "on";
        }

        if (checkBox7.isChecked()) {

            day1 = "true";
        }
        if (checkBox1.isChecked()) {
            day2 = "true";
        }
        if (checkBox2.isChecked()) {
            day3 = "true";

        }
        if (checkBox3.isChecked()) {

            day4 = "true";

        }
        if (checkBox4.isChecked()) {

            day5 = "true";

        }
        if (checkBox5.isChecked()) {
            day6 = "true";


        }
        if (checkBox6.isChecked()) {
            day7 = "true";

        }

        values.put(AlamEntry.TIME, txt.getText().toString());
        values.put(AlamEntry.ACTIVE, "true");

        values.put(AlamEntry.ACTION, action);

        values.put(AlamEntry.REPEAT, "true");

        values.put(AlamEntry.STATUS, "active");

        values.put(AlamEntry.MONDAY, day2);
        values.put(AlamEntry.TUESDAY, day3);
        values.put(AlamEntry.WEDNESDAY, day4);
        values.put(AlamEntry.THURSDAY, day5);
        values.put(AlamEntry.FRIDAY, day6);
        values.put(AlamEntry.SATURDAY, day7);
        values.put(AlamEntry.SUNDAY, day1);


        Uri uri = contentResolver.insert(AlamEntry.CONTENT_URI, values);
        if (uri != null) {
            Toast.makeText(getContext(), "Successful Insertion", Toast.LENGTH_LONG).show();
        }
    }
}



