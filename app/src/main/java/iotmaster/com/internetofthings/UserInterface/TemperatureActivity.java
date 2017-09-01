package iotmaster.com.internetofthings.UserInterface;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import iotmaster.com.internetofthings.R;

public class TemperatureActivity extends AppCompatActivity {
    TextView temperature;
    ImageView temperaturImage;
    TextView humidity;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Temperature");
        mContext = TemperatureActivity.this;
        temperature = (TextView) findViewById(R.id.temperature);
        temperaturImage = (ImageView) findViewById(R.id.image_temperature);
        humidity = (TextView) findViewById(R.id.humidity);


    }

    public void getTemperature() {

        String url = "http://www.codeham.com/iot/getTemp.php";
        StringRequest stringRequest = new StringRequest(Method.GET, url, new Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(mContext, response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
               /* map.put("token", token);
                map.put("email", email);*/
                return map;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);

    }
}

