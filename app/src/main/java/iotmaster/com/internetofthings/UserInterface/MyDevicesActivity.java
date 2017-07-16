package iotmaster.com.internetofthings.UserInterface;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import iotmaster.com.internetofthings.Adapters.GridAdapter;
import iotmaster.com.internetofthings.R;
import iotmaster.com.internetofthings.data.DeviceContract.DeviceEntry;

public class MyDevicesActivity extends AppCompatActivity {

    Toolbar toolbar;
    public static final String TAG = "MyDeviceActivity.class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_devices);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Manage Devices");

        GridView gridView = (GridView) findViewById(R.id.grid_layout);
        gridView.setAdapter(new GridAdapter(this));

        getDeviceData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_device_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int men = item.getItemId();
        switch (men) {
            case R.id.action_addDevice:
                startActivity(new Intent(MyDevicesActivity.this, SwitchRegisterActivity.class));
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public void getDeviceData() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = null;
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            cursor = contentResolver.query(DeviceEntry.CONTENT_URI, null, null, null, null, null);
        } else {
            Toast.makeText(MyDevicesActivity.this, "Operation Not Supported", Toast.LENGTH_SHORT).show();
            return;

        }
        if (cursor.getCount() != 0 && cursor != null) {
            try {
                while (cursor.moveToNext()) {
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

                    Log.i(TAG, name + "            " + key + "         " + relay11 + "         " + relay22 + "         " + relay33 + "         " + "     " + relay44 + "         " + relay55);

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        } else {
            Toast.makeText(MyDevicesActivity.this, "Failed finding Device", Toast.LENGTH_SHORT).show();
        }

    }
}
