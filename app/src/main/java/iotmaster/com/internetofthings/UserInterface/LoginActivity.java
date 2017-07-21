package iotmaster.com.internetofthings.UserInterface;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import iotmaster.com.internetofthings.Network.NetworkUtils;
import iotmaster.com.internetofthings.R;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mLoginBtn;
    private String email;
    private String password;
    private TextView notRegistered;

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    ImageView bulb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmail = (EditText) findViewById(R.id.email);
        bulb = (ImageView) findViewById(R.id.bulb_image);
        mPassword = (EditText) findViewById(R.id.password);
        mLoginBtn = (Button) findViewById(R.id.login);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bulb.setY(1000f);
                bulb.animate().alpha(1f).setDuration(1500).translationY(0f).scaleYBy(0).scaleXBy(0).start();
            }
        },1000);


        notRegistered = (TextView) findViewById(R.id.notRegistered);

        notRegistered.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                makeIntent();
            }
        });


        mLoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();
                login(email, password);

            }
        });
    }

    public void makeIntent() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public void login(String username, String pass) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(USERNAME, username);
        map.put(PASSWORD, pass);

        NetworkUtils.logMeIn(LoginActivity.this, map);

    }
}
