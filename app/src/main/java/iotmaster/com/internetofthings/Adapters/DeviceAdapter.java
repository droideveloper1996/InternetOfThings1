package iotmaster.com.internetofthings.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import iotmaster.com.internetofthings.R;
import iotmaster.com.internetofthings.data.DeviceContract.DeviceEntry;

/**
 * Created by Abhishek on 17/07/2017.
 */

public class DeviceAdapter extends CursorAdapter {
    public DeviceAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View v=layoutInflater.inflate(R.layout.list_item,parent,false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView imageView=(ImageView)view.findViewById(R.id.imageView);
        TextView switchName=(TextView)view.findViewById(R.id.switchName);
        TextView switchStatus=(TextView)view.findViewById(R.id.switchStatus);

        int deviceName = cursor.getColumnIndex(DeviceEntry.DEVICE_NAME);
        int deviceKey = cursor.getColumnIndex(DeviceEntry.UNIQUE_KEY);
        int relay1 = cursor.getColumnIndex(DeviceEntry.RELAY1);
        int relay2 = cursor.getColumnIndex(DeviceEntry.RELAY2);
        int relay3 = cursor.getColumnIndex(DeviceEntry.RELAY3);
        int relay4 = cursor.getColumnIndex(DeviceEntry.RELAY4);
        int relay5 = cursor.getColumnIndex(DeviceEntry.RELAY5);

        String name = cursor.getString(deviceName);
        String key = cursor.getString(deviceKey);
        int relay11 = cursor.getInt(relay1);
        int relay22 = cursor.getInt(relay2);

        int relay33 = cursor.getInt(relay3);

        int relay44 = cursor.getInt(relay4);

        int relay55 = cursor.getInt(relay5);

        imageView.setImageResource(R.drawable.plug_socket);
        switchName.setText(name);
        switchStatus.setText(key);


    }
}
