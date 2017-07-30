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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import iotmaster.com.internetofthings.R;
import iotmaster.com.internetofthings.data.DeviceContract.DeviceEntry;
import iotmaster.com.internetofthings.data.PrefManager;

public class DeepManageActivity extends AppCompatActivity {

    private Uri mUri = null;
    String key;

    Switch aSwitch1;
    Switch aSwitch2;
    Switch aSwitch3;
    Switch aSwitch4;
    Switch aSwitch5;

    ImageView view1;
    ImageView view2;
    ImageView view3;
    ImageView view4;
    ImageView view5;

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

        aSwitch1 = (Switch) findViewById(R.id.toggle1);
        aSwitch2 = (Switch) findViewById(R.id.toggle2);
        aSwitch3 = (Switch) findViewById(R.id.toggle3);
        aSwitch4 = (Switch) findViewById(R.id.toggle4);
        aSwitch5 = (Switch) findViewById(R.id.toggle5);

        view1 = (ImageView) findViewById(R.id.bulb1);
        view2 = (ImageView) findViewById(R.id.bulb2);
        view3 = (ImageView) findViewById(R.id.bulb3);
        view4 = (ImageView) findViewById(R.id.bulb4);
        view5 = (ImageView) findViewById(R.id.bulb5);

        swit(aSwitch1, view1);
        swit(aSwitch2, view2);
        swit(aSwitch3, view3);
        swit(aSwitch4, view4);
        swit(aSwitch5, view5);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_customise_device:
                String Key = new PrefManager(DeepManageActivity.this).getKey();
                Log.i("KEYKEYK", Key);
                break;
            case R.id.action_remove_device:
                dialogueBuilder();
                break;
            case R.id.action_make_active:
                String b = getData();
                if (b != null) {
                    Log.i("DeepManageActivity", b);
                    new PrefManager(this).setUniqueKey(b);
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
        String selection="_id=?";
        String id=mUri.getPathSegments().get(1);
       // System.out.println("fdsgfdhsh"+id);
        String args[]=new String[]{id};

        cursor = contentResolver.query(mUri, null, selection, args, null,null);
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                int key_index = cursor.getColumnIndex(DeviceEntry.UNIQUE_KEY);

                key = cursor.getString(key_index);
                System.out.println(key);
            }
        }
        return key;
    }

    public void swit(final Switch s, final ImageView imageView) {


        s.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int tag = Integer.parseInt((String) (s.getTag()));
                //  Log.i("hello check",Integer.toString(tag));

                if (isChecked) {
                    switch (tag) {
                        case 1:

                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                    }
                    imageView.setImageResource(R.drawable.yellow);
                } else {
                    switch (tag) {
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                    }
                    imageView.setImageResource(R.drawable.bulb);

                }
            }
        });
    }
}
