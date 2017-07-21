package iotmaster.com.internetofthings.UserInterface;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import iotmaster.com.internetofthings.Adapters.DeviceAdapter;
import iotmaster.com.internetofthings.R;
import iotmaster.com.internetofthings.data.DeviceContract.DeviceEntry;

public class MyDevicesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    Toolbar toolbar;
    public static final String TAG = "MyDeviceActivity.class";
    ListView listView;
    DeviceAdapter deviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_devices);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Manage Devices");

        GridView gridView = (GridView) findViewById(R.id.grid_layout);
      //  gridView.setAdapter(new GridAdapter(this));

        getDeviceData();
        listView = (ListView) findViewById(R.id.list);
        deviceAdapter = new DeviceAdapter(this, null);
        listView.setAdapter(deviceAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent i=new  Intent(MyDevicesActivity.this,DeepManageActivity.class);
                Uri uri = ContentUris.withAppendedId(DeviceEntry.CONTENT_URI, id);
                i.setData(uri);
                startActivity(i);
            }
        });
        getSupportLoaderManager().initLoader(10, null, this);
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
        try {
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
            cursor = contentResolver.query(DeviceEntry.CONTENT_URI, null, null, null, null, null);
        } else {
            Toast.makeText(MyDevicesActivity.this, "Operation Not Supported", Toast.LENGTH_SHORT).show();
            return;

        }
        if (cursor != null&& cursor.getCount()!=0) {

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
            }} catch (Exception e) {
                e.printStackTrace();
            } finally {
            if(cursor!=null) {
                cursor.close();
            }
        }

    }

    public void getDevice() {
        Cursor c = null;
        ContentResolver contentResolver = getContentResolver();


        c = contentResolver.query(DeviceEntry.CONTENT_URI, null, null, null, null, null);
        System.out.println("help -1");

        if (c.getCount() != 0 && c != null) {
            System.out.println("help0");
            while (c.moveToNext()) {
                int deviceName = c.getColumnIndex(DeviceEntry.DEVICE_NAME);

                int relay1 = c.getColumnIndex(DeviceEntry.RELAY1);


                String name = c.getString(deviceName);

                int relay11 = c.getInt(relay1);


                Log.i(TAG, name + "            " + relay11 + "         ");

            }
        } else

        {
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, DeviceEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        deviceAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        deviceAdapter.swapCursor(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(10,null,this);
    }
}