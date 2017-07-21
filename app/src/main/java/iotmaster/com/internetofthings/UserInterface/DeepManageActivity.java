package iotmaster.com.internetofthings.UserInterface;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import iotmaster.com.internetofthings.R;
import iotmaster.com.internetofthings.data.DeviceContract.DeviceEntry;
import iotmaster.com.internetofthings.data.PrefManager;

public class DeepManageActivity extends AppCompatActivity {

    private Uri mUri = null;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_manage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Device");
        key = "";
        mUri = getIntent().getData();

        Log.i("mUri", mUri.toString());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_customise_device:
                break;
            case R.id.action_remove_device:
                dialogueBuilder();
                break;
            case R.id.action_make_active:
                String b = getData();
                if (b != null) {
                    Log.i("DeepManageActivity", b);
                    new PrefManager(this).makeActive(b);
                } else {
                    Log.i("DeepManageActivity", "Error getting key");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_device, menu);
        return super.onCreateOptionsMenu(menu);

    }

    public void discard() {
        ContentResolver contentResolver = getContentResolver();
        long row = contentResolver.delete(mUri, null, null);
        if (row > 0) {
            Toast.makeText(DeepManageActivity.this, "Deleted Device", Toast.LENGTH_SHORT).show();
        }
    }

    void dialogueBuilder() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DeepManageActivity.this);
        alertDialog.setTitle("Confirm Delete...");
        alertDialog.setMessage("Are you sure you want delete this?");
        alertDialog.setIcon(R.drawable.delete_icon);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                discard();
                finish();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public String getData() {
        Cursor cursor = null;
        ContentResolver contentResolver = getContentResolver();
        String projection[] = new String[]{DeviceEntry.UNIQUE_KEY};
        cursor = contentResolver.query(mUri, projection, null, null, null);
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                int key_index = cursor.getColumnIndex(DeviceEntry.UNIQUE_KEY);

                key = cursor.getString(key_index);
            }
        }
        return key;
    }
}
