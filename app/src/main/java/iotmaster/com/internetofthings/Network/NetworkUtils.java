package iotmaster.com.internetofthings.Network;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import iotmaster.com.internetofthings.UserInterface.MainActivity;
import iotmaster.com.internetofthings.UserInterface.SwitchRegisterActivity;
import iotmaster.com.internetofthings.data.PrefManager;


/**
 * Created by Abhishek on 29/06/2017.
 */

public class NetworkUtils {

    private Context mContext;
    public static final String UNIQUE_KEY = "unique_key";
    public static final String STATUS = "status";

    public static final String TAG = "NetworkUtils.class";

    public NetworkUtils(Context context) {
        mContext = context;
    }

    public static void getdata(final Context context, final String id) {
        final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);


        if (id != null) {
            final PrefManager prefManager = new PrefManager(context);
            String url = "http://iotsswitch.atwebpages.com/changeState.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i(TAG, response.toString());
                            vibrator.vibrate(300);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("NetworkUtils.class", error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    String unique_key = prefManager.getKey();
                    Log.i("GOT UNIQUE KEY", unique_key);
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(UNIQUE_KEY, unique_key);
                    params.put(STATUS, id);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }
    }

    public static void registerProduct(final Context context, final Map<String, String> stringMap) {
        if (stringMap != null) {
            String url = "http://iotsswitch.atwebpages.com/register.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            PrefManager prefManager = new PrefManager(context);
                            prefManager.setUniqueKey(stringMap.get(SwitchRegisterActivity.UNIQUE_KEY));
                            Log.i("STORED UNIQUE KEY", prefManager.getKey());
                            Log.i("response", response.toString());
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String result = jsonObject.optString("status");
                                if (result.equals("success")) {
                                    Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, MainActivity.class);
                                    context.startActivity(intent);
                                } else if (result.equals("User Already registered")) {
                                    Toast.makeText(context, "User Already Exists", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(context, "Failed Registering Product", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //  Toast.makeText(context, response, Toast.LENGTH_LONG).show();


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("Network Utils", error.toString());
                            //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params = stringMap;
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }
    }

    public static void logMeIn(final Context context, final Map<String, String> stringMap) {
        if (stringMap != null) {
            String url = "http://iotsswitch.atwebpages.com/login.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            PrefManager prefManager = new PrefManager(context);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String result = jsonObject.optString("status");
                                String key = jsonObject.optString("unique_key");
                                String username = jsonObject.optString("username");
                                if (result.equals("success")) {
                                    prefManager.setUniqueKey(key);
                                    prefManager.loginSuccessful(username, true);

                                    Log.i("Network Value", Boolean.toString(prefManager.isSuccessfulLogin()));

                                    Log.i("Fresh UNIQUE KEY", prefManager.getKey());
                                    Toast.makeText(context, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, MainActivity.class);
                                    context.startActivity(intent);
                                } else if (result.equals("failed")) {
                                    Toast.makeText(context, "Failed Logging in", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(context, response, Toast.LENGTH_LONG).show();


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params = stringMap;
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }
    }

    public static void initializeDevice(final Context context, String username, String password) {
        if (username != null && username != "" && password != null && password != "") {

            //   String url = "http://192.168.4.1/setting?ssid=" + username + "&pass=" + password;

            String url = makeUrl(username, password).toString();

            Log.i("Url Commited", url.toString());

            RequestQueue queue = Volley.newRequestQueue(context);

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //TODO: Get Device response On Successful Initialization;
                            Log.i("SuccessMessage", response.toString());
                            Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //TODO:// Get Device response on Failure;
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);


        }
    }

    public static void getState(final String key, final Context context) {
        if (key != null) {
            String finalResult;

            String url = "http://iotsswitch.atwebpages.com/switchit.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String state = jsonObject.optString("status");
                                Log.i("NetworkUtils GetState", state);

                                if (state.equals("1")) {
                                    Toast.makeText(context, "HiGH Response", Toast.LENGTH_LONG).show();

                                } else if (state.equals("0")) {
                                    Toast.makeText(context, "LOW Response", Toast.LENGTH_LONG).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {


                    Map<String, String> params = new HashMap<String, String>();
                    params.put(UNIQUE_KEY, key);

                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }

    }

    public static void RegisterDeviceFromMainActivity(Context context, Map<String, String> map) {

    }


    public static void checkDeviceStatus(Context context) {
        String url = "http://iotsswitch.atwebpages.com/update.php";
        final PrefManager prefManager = new PrefManager(context);
        StringRequest stringRequest = new StringRequest(Method.POST, url, new Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put(UNIQUE_KEY, prefManager.getKey());
                map.put("connectivity", "1");
                return map;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public static Uri makeUrl(String ss, String pas) {

        Uri uri = Uri.parse("http://192.168.4.1/setting").buildUpon()
                .appendQueryParameter("ssid", ss).appendQueryParameter("pass", pas).build();
        return uri;

    }
}
