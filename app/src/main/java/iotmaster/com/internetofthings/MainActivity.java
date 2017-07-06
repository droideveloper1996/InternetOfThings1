package iotmaster.com.internetofthings;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private WifiManager mWifiManager;
    private Context mContext;
    List<ScanResult> scanResults;
    private IntentFilter mIntentFilter;
    ImageView power_switch;
    Boolean isSwitched = false;
    LinearLayout linearLayout;
    NetworkUtils network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        setSupportActionBar(toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mContext = MainActivity.this;
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        final TextView scanResult = (TextView) findViewById(R.id.ScanResult);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.True, R.string.False);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        power_switch = (ImageView) findViewById(R.id.power_switch);
        network = new NetworkUtils(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
        }

        findViewById(R.id.btn_play_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
                mWifiManager.startScan();
                scanResults = mWifiManager.getScanResults();
                String wifi = "";
                for (ScanResult scanResult : scanResults) {

                    wifi += scanResult.SSID.toString() + '\n';

                }
                scanResult.setText(wifi);

                NotificationUtils.installNotification(mContext);
                //startActivity(new Intent(MainActivity.this, LoginActivity.class));

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        floatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listen();
            }
        });

        power_switch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSwitched) {

                    power_switch.setImageResource(R.drawable.ic_power_button);

                    Toast.makeText(mContext, "Switch On", Toast.LENGTH_LONG).show();
                    isSwitched = true;
                    NetworkUtils.getdata(MainActivity.this, "1");

                } else {

                    power_switch.setImageResource(R.drawable.ic_power);
                    Toast.makeText(mContext, "Switch Off", Toast.LENGTH_LONG).show();
                    isSwitched = false;
                    NetworkUtils.getdata(MainActivity.this, "0");
                }

            }
        });
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(mWifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        if (getIntent().getAction() != null && getIntent().getAction().equals("com.google.android.gms.actions.SEARCH_ACTION")) {
            String query = getIntent().getStringExtra(SearchManager.QUERY);
            Log.e("Query:", query);   //query is the search word
        }

        mNavigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_location:
                        startActivity(new Intent(MainActivity.this, LocationActivity.class));
                        break;
                    case R.id.action_addProduct:
                        Intent i = new Intent(mContext, SwitchRegisterActivity.class);
                        i.putExtra("FromMainActivity", true);
                        startActivity(i);
                        break;
                    case R.id.action_reset:
                        startActivity(new Intent(mContext, SetupActivity.class));
                        Log.i("Action", "Reset");
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        switch (id) {
            case R.id.action_setting:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void checkConnection() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.startScan();
            String ssid = mWifiManager.getConnectionInfo().getSSID();
            System.out.println(mWifiManager.getConnectionInfo());
            System.out.println(ssid);
            if (ssid.equals("\"Fibre\"")) {
                Toast.makeText(mContext, "Connected to Preferred Network", Toast.LENGTH_LONG).show();


            } else {
                Toast.makeText(mContext, "Please Connect to Fibre Network", Toast.LENGTH_SHORT).show();

            }

        } else {
            mWifiManager.setWifiEnabled(true);

        }
    }

    public void listen() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening....");

        try {
            startActivityForResult(intent, 100);
        } catch (Exception e) {
            Toast.makeText(mContext, "Device not Supported", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            ArrayList<String> strings = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Log.i("ASDFGH", strings.get(0));
            for (String abc : strings) {

                if (abc.toLowerCase().equals("turn off my lights")) {
                    power_switch.setImageResource(R.drawable.ic_power);
                    Toast.makeText(mContext, "Off state", Toast.LENGTH_LONG).show();
                    NetworkUtils.getdata(MainActivity.this, "0");
                    break;
                } else {
                    if (abc.toLowerCase().equals("turn on my lights")) {
                        power_switch.setImageResource(R.drawable.ic_power_button);
                        Toast.makeText(mContext, "On State", Toast.LENGTH_SHORT).show();
                        NetworkUtils.getdata(MainActivity.this, "1");
                        break;

                    }
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RESULT_OK && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
            }
        }
    }
}

