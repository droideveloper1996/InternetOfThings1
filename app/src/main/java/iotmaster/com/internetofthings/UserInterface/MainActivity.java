package iotmaster.com.internetofthings.UserInterface;

import android.Manifest;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import iotmaster.com.internetofthings.BackgroundServices.DeviceStatusCheck;
import iotmaster.com.internetofthings.Location.LocationActivity;
import iotmaster.com.internetofthings.Network.NetworkUtils;
import iotmaster.com.internetofthings.R;
import iotmaster.com.internetofthings.data.PrefManager;

import static iotmaster.com.internetofthings.UserInterface.SwitchRegisterActivity.UNIQUE_KEY;

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
    ActionBar actionBar;
    RelativeLayout relativeLayout;
    IntentFilter intentFilter;
    public static final String HIGH_STATE = "1";
    public static final String LOW_STATE = "0";
    DeviceState deviceState;
    public static final String SAVED_STATE_KEY = "saved-state";
    ProgressBar progressBar;
    TextView ProgresText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout=(RelativeLayout)findViewById(R.id.main_relative_layout);
        NotificationUtils.GeoNotification(this);
        //Firebase Job Dispatcher;
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        ProgresText=(TextView)findViewById(R.id.progressText);
        deviceState = new DeviceState();
        intentFilter = new IntentFilter();
        intentFilter.addAction(HIGH_STATE);
        intentFilter.addAction(LOW_STATE);

        DeviceStatusCheck.checkStatus(this);
        Boolean fromSetupActivity = getIntent().getBooleanExtra("fromSetupActivity", false);
        if (fromSetupActivity) {
            dialogueBuilder();
        }
        power_switch = (ImageView) findViewById(R.id.power_switch);
        actionBar = getSupportActionBar();
        if (savedInstanceState != null) {
            Boolean state = savedInstanceState.getBoolean(SAVED_STATE_KEY);
            if (state) {
                power_switch.setImageResource(R.drawable.ic_power_button);
                isSwitched = true;

            } else {
                power_switch.setImageResource(R.drawable.ic_power);
                isSwitched = false;
            }
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //   FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
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
        network = new NetworkUtils(this);

        intentFilter = new IntentFilter();
        intentFilter.addAction(HIGH_STATE);
        intentFilter.addAction(LOW_STATE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

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
    /*    floatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listen();
            }
        });*/

        power_switch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSwitched) {

                    power_switch.setImageResource(R.drawable.ic_power_button);
                    isSwitched = true;
                    NetworkUtils.getdata(MainActivity.this, "1");


                } else {

                    power_switch.setImageResource(R.drawable.ic_power);
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
                    case R.id.action_alarm:
                        startActivity(new Intent(MainActivity.this, AlarmActivity.class));
                        break;
                    case R.id.action_my_device:
                        startActivity(new Intent(mContext, MyDevicesActivity.class));
                        break;

                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        // NotificationUtils.GeoNotification(this);
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
            //  Log.i("ASDFGH", strings.get(0));
            for (String abc : strings) {
                String word = abc.toLowerCase();
                if (word.equals("turn off my lights") ||
                        word.equals("turn off my light") ||
                        word.equals("turn off") ||
                        word.equals("turn off lights") ||
                        word.equals("turn off light") ||
                        word.equals("switch off my lights") ||
                        word.equals("switch off my light") ||
                        word.equals("switch off") ||
                        word.equals("switch off light") ||
                        word.equals("turn off lights") ||
                        word.equals("off")
                        || word.equals("please off")
                        || word.equals("please off light")
                        || word.equals("please off my light")
                        || word.equals("please off lights")
                        || word.equals("please off my lights") ||
                        word.equals("close  my lights") ||
                        word.equals("close  my light") ||
                        word.equals("close lights") ||
                        word.equals("close light") ||
                        word.equals("close switch") ||
                        word.equals("power off") ||
                        word.equals("stop switch")
                        || word.equals("terminate")
                        || word.equals("cut power")
                        || word.equals("stop")
                        ) {
                    power_switch.setImageResource(R.drawable.ic_power);
                    // Toast.makeText(mContext, "Off state", Toast.LENGTH_LONG).show();
                    NetworkUtils.getdata(MainActivity.this, "0");
                    break;
                } else {
                    if (word.equals("turn on my lights")
                            || word.equals("turn on my light")
                            || word.equals("turn on lights")
                            || word.equals("turn on lights")
                            || word.equals("on")
                            || word.equals("turn on")
                            || word.equals("turn on my lights")
                            || word.equals("switch on my light")
                            || word.equals("switch on lights")
                            || word.equals("switch on lights")
                            || word.equals("on")
                            || word.equals("please on")
                            || word.equals("please on light")
                            || word.equals("please on my light")
                            || word.equals("please on lights")
                            || word.equals("please on my lights")


                            || word.equals("switch on")
                            || word.equals("switch on my lights")
                            || word.equals("open  my lights")
                            || word.equals("open  my light")
                            || word.equals("open lights")
                            || word.equals("open light")
                            || word.equals("open switch")
                            || word.equals("power on ")
                            || word.equals("start switch")
                            || word.equals("cut")
                            || word.equals("start")
                            || word.equals("power on")
                            || word.equals("on on on on on on on on on")

                            )

                    {
                        power_switch.setImageResource(R.drawable.ic_power_button);
                        //  Toast.makeText(mContext, "On State", Toast.LENGTH_SHORT).show();
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
                // Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_STATE_KEY, isSwitched);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String Key = new PrefManager(MainActivity.this).getKey();
        checkDeviceState(Key);

    }

    public void getState(Boolean check) {
        if (check) {
            power_switch.setImageResource(R.drawable.ic_power_button);
            isSwitched = true;
        } else {
            power_switch.setImageResource(R.drawable.ic_power);
            isSwitched = false;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(deviceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(deviceState, intentFilter);


    }

    void dialogueBuilder() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Device Reset was Successful...");
        alertDialog.setMessage("Please restart the device...Thank You for Choosing Our Product");
        alertDialog.setIcon(R.drawable.ic_check_circle_black_24px);
        alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        alertDialog.show();
    }


    public class DeviceState extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {

        }


    }


    public void checkDeviceState(final String key) {

        if (key != null) {
            String finalResult;

            String url = "http://iotsswitch.atwebpages.com/switchit.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String state = jsonObject.optString("status");
                                Log.i("NetworkUtils GetState", state);

                                if (state.equals("1")) {
                                    Toast.makeText(MainActivity.this, "HiGH Response", Toast.LENGTH_LONG).show();
                                    getState(true);
                                } else if (state.equals("0")) {
                                    Toast.makeText(MainActivity.this, "LOW Response", Toast.LENGTH_LONG).show();
                                    getState(false);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            finally {
                                progressBar.setVisibility(View.GONE);
                                ProgresText.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.VISIBLE);
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {


                    Map<String, String> params = new HashMap<String, String>();
                    params.put(UNIQUE_KEY, key);

                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);
        }

    }
}

