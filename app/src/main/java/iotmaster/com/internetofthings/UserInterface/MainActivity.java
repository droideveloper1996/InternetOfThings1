package iotmaster.com.internetofthings.UserInterface;

import android.Manifest;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
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
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import iotmaster.com.internetofthings.Adapters.StyleAdapter;
import iotmaster.com.internetofthings.Adapters.StyleAdapter.ListItemClickListener;
import iotmaster.com.internetofthings.BackgroundServices.ReminderHelper;
import iotmaster.com.internetofthings.FireBaseCloudMessaging.Config;
import iotmaster.com.internetofthings.Location.LocationActivity;
import iotmaster.com.internetofthings.Network.NetworkUtils;
import iotmaster.com.internetofthings.R;
import iotmaster.com.internetofthings.data.PrefManager;

import static iotmaster.com.internetofthings.UserInterface.SwitchRegisterActivity.UNIQUE_KEY;

public class MainActivity extends AppCompatActivity implements ListItemClickListener {

    private static final String TAG = "MainActivity.this";
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Context mContext;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    static public ImageView imageView;
    static ImageView power_switch;
    static Boolean isSwitched = false;


    NetworkUtils network;
    ActionBar actionBar;
    RelativeLayout relativeLayout;
    String Key;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    StyleAdapter styleAdapter;
    ArrayList<String> mList;

    DeviceState deviceState;
    public static final String SAVED_STATE_KEY = "saved-state";
    ProgressBar progressBar;
    TextView ProgresText;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativeLayout = (RelativeLayout) findViewById(R.id.main_relative_layout);
        //  NotificationUtils.GeoNotification(this);
        //Firebase Job Dispatcher;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ProgresText = (TextView) findViewById(R.id.progressText);
        deviceState = new DeviceState();
        imageView = (ImageView) findViewById(R.id.device_status);
        // DeviceStatusCheck.checkStatus(this);
        getOnlineOffline();
        getDeviceOnlineOffline();


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
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        mContext = MainActivity.this;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.True, R.string.False);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        network = new NetworkUtils(this);
        Key = new PrefManager(MainActivity.this).getKey();
        Log.i("KEYKEY", Key);
        checkDeviceState(Key);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /**
         *
         * RECYCLER VIEW AND STYLE ADAPTER
         */
       /* mList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mList.add("This is Bulb  " + i);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        styleAdapter = new StyleAdapter(this, mList, this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(styleAdapter);*/


        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        power_switch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSwitched) {

                    power_switch.setImageResource(R.drawable.ic_power_button);
                    isSwitched = true;

                    vibrator.vibrate(50);
                    NetworkUtils.getdata(MainActivity.this, "1");


                } else {

                    power_switch.setImageResource(R.drawable.ic_power);
                    isSwitched = false;
                    vibrator.vibrate(50);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            vibrator.vibrate(60);

                        }
                    }, 100);


                    NetworkUtils.getdata(MainActivity.this, "0");
                }
            }

        });

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
                    case R.id.action_stat:
                        startActivity(new Intent(mContext,StatisticActivity.class));

                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        // NotificationUtils.GeoNotification(this);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    // txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();
        FirebaseMessaging.getInstance().subscribeToTopic("news");
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e(TAG, "Firebase reg id: " + regId);

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
            case R.id.action_voice:
                listen();
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


    }

    public static void getState(Boolean check) {
        if (check) {
            try {
                power_switch.setImageResource(R.drawable.ic_power_button);
                isSwitched = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                power_switch.setImageResource(R.drawable.ic_power);
                isSwitched = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));


        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));


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

    @Override
    public void onClick(int index) {
        Toast.makeText(mContext, "Clicked Position " + index, Toast.LENGTH_SHORT).show();
    }


    public static class DeviceState extends BroadcastReceiver {

        public DeviceState() {
            //super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (action.equals(ReminderHelper.INTENT_ACTION_POSITIVE)) {
                getState(true);
            } else if (action.equals(ReminderHelper.INTENT_ACTION_NEGATIVE)) {
                getState(false);
            } else if (action.equals(NetworkUtils.DEVICE_IS_ONLINE)) {

                imageView.setImageResource(R.drawable.conne);


            } else if (action.equals(NetworkUtils.DEVICE_IS_NOT_ONLINE)) {
                imageView.setImageResource(R.drawable.disc);

            }

        }


    }


    public void checkDeviceState(final String key) {
        if (key != null) {
            String url = "http://www.codeham.com/iot/switchit.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String state = jsonObject.optString("status");
                                Log.i("DeviceState", state);

                                if (state.equals("1")) {
                                    power_switch.setImageResource(R.drawable.ic_power_button);

                                } else if (state.equals("0")) {
                                    power_switch.setImageResource(R.drawable.ic_power);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } finally {
                                progressBar.setVisibility(View.GONE);
                                ProgresText.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.VISIBLE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.GONE);
                            ProgresText.setText("Please Check Internet Connection");
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Exit");
        alertDialog.setMessage("Are you sure want to exit");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                finish();
                moveTaskToBack(true);
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }
    //cs56zlTPOnY:APA91bHCi4mauCKVU0cfu0gpXxRXnHAOalKNiNz0mBaP8NeUNsrUibS829VwCJ18y0vomMom5ySssKlFAU5zSZCB_ONbAE_YRsGo5eu8AACVsjNZSXaIeYU1K6c1MYBtkSUqK24Pc5wO

    void getDeviceOnlineOffline() {
        int delay = 1000; // delay for 1 sec.
        int period = 20000; // repeat every 20 sec.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                NetworkUtils.checkDeviceStatus(mContext);
            }
        }, delay, period);
    }

    void getOnlineOffline() {

        int delay = 1000; // delay for 1 sec.
        int period = 10000; // repeat every 10 sec.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                NetworkUtils.getState(Key, mContext);
            }
        }, delay, period);
    }
}

