package iotmaster.com.internetofthings.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.Switch;
import android.widget.TextView;

import iotmaster.com.internetofthings.R;
import iotmaster.com.internetofthings.data.AlamContract.AlamEntry;

/**
 * Created by Abhishek on 02/08/2017.
 */

public class AlarmAdapter extends CursorAdapter {
    public AlarmAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.alarmlist, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textView = (TextView) view.findViewById(R.id.time);
        Switch swit = (Switch) view.findViewById(R.id.timeSwitch);
        TextView meridian = (TextView) view.findViewById(R.id.meridian);
        System.out.println(cursor.getCount());
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
                            textView.setText(tim);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }}

    }
}
