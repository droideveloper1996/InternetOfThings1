package iotmaster.com.internetofthings.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import iotmaster.com.internetofthings.Adapters.AlarmAdapter;
import iotmaster.com.internetofthings.R;
import iotmaster.com.internetofthings.data.AlamContract.AlamEntry;

/**
 * Created by Abhishek on 30/07/2017.
 */

public class fragment_one extends android.support.v4.app.Fragment {

    ListView list;
    AlarmAdapter alarmAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_one, container, false);
        list = (ListView) v.findViewById(R.id.list);

        alarmAdapter = new AlarmAdapter(getContext(), null);
        list.setAdapter(alarmAdapter);

      //  getAlarm();
        //getLoaderManager().initLoader(11, null, this);
        return v;

    }


    void getAlarm() {
        System.out.println("TIME" + "  |  " + "Action" + "  |  " + "Monday   |  Tuesday  |  Wednesday  |  Thursday  |  Friday  |  Saturday  |  Sunday");
       Cursor cursor = getContext().getContentResolver().query(AlamEntry.CONTENT_URI, null, null, null, null);


        Log.i("Frag1", String.valueOf(cursor.getCount()));
        if (cursor != null && cursor.getCount() != 0) {
            try {
                while (cursor.moveToNext()) {
                    int time = cursor.getColumnIndex(AlamEntry.TIME);
                    int act = cursor.getColumnIndex(AlamEntry.ACTION);

                    int mond = cursor.getColumnIndex(AlamEntry.MONDAY);
                    int tues = cursor.getColumnIndex(AlamEntry.TUESDAY);
                    int wedn = cursor.getColumnIndex(AlamEntry.WEDNESDAY);
                    int thur = cursor.getColumnIndex(AlamEntry.THURSDAY);
                    int frid = cursor.getColumnIndex(AlamEntry.FRIDAY);
                    int satd = cursor.getColumnIndex(AlamEntry.SATURDAY);
                    int sund = cursor.getColumnIndex(AlamEntry.SUNDAY);

                    String mon = cursor.getString(mond);
                    String tue = cursor.getString(tues);
                    String wed = cursor.getString(wedn);
                    String thu = cursor.getString(thur);
                    String fri = cursor.getString(frid);
                    String sat = cursor.getString(satd);
                    String sun = cursor.getString(sund);
                    String tim = cursor.getString(time);
                    String action = cursor.getString(act);



                    System.out.println(tim + "  " + action + "  " + mon + "  " + tue + "  " + wed + "  " + thu + "  " + fri + "  " + sat + "  " + sun);

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }


    }


}
