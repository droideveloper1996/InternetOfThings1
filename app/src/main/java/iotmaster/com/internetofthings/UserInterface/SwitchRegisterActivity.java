package iotmaster.com.internetofthings.UserInterface;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import iotmaster.com.internetofthings.Network.NetworkUtils;
import iotmaster.com.internetofthings.R;
import iotmaster.com.internetofthings.data.DeviceContract.DeviceEntry;

public class SwitchRegisterActivity extends AppCompatActivity {

    private EditText mKey;
    private Button registerProduct;
    private String email;
    private String password;
    private EditText productName;
    private String number;

    public static final String UNIQUE_KEY = "unique_key";
    public static final String MOBILE = "mobile";
    public static final String PASSWORD = "password";
    public static final String USERNAME = "username";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_register);

        mKey = (EditText) findViewById(R.id.key);
        registerProduct = (Button) findViewById(R.id.registerProduct);
        productName = (EditText) findViewById(R.id.productName);


        Intent intent = getIntent();
        Bundle varBundle = intent.getExtras();
        if (varBundle != null) {
            email = varBundle.getString(RegisterActivity.EMAIL);
            password = varBundle.getString(RegisterActivity.PASSWORD);
            number = varBundle.getString(RegisterActivity.NUMBER);

            Log.i("Bundle Extras", email + '\n' + password + '\n' + number);
        }
        final Boolean fromMain = getIntent().getBooleanExtra("FromMainActivity", false);
        Log.i("fromMain", fromMain.toString());
        registerProduct.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String product_key = mKey.getText().toString();
                if (!fromMain) {
                    setRegisterProduct(product_key);
                } else {
                    String deviceName = productName.getText().toString();
                    String key = mKey.getText().toString();
                    if (TextUtils.isEmpty(key) || TextUtils.isEmpty(deviceName)) {
                        Toast.makeText(SwitchRegisterActivity.this, "Please Enter Details", Toast.LENGTH_SHORT).show();
                    } else {
                        //TODO: Implement When new Switch added from mainActivity.

                        //Added to my sqlite database key& device name;
                        addNewDevice(deviceName, key);

                        Toast.makeText(SwitchRegisterActivity.this, "Clicked Switch Activity", Toast.LENGTH_SHORT).show();
                            finish();
                    }
                }
            }
        });
    }

    public void setRegisterProduct(String productKey) {

        Map<String, String> stringStringMap = new HashMap<String, String>();
        stringStringMap.put(UNIQUE_KEY, productKey);
        stringStringMap.put(PASSWORD, password);
        stringStringMap.put(USERNAME, email);
        stringStringMap.put(MOBILE, number);


        addNewDevice(productName.getText().toString().trim(), mKey.getText().toString().trim());


        NetworkUtils.registerProduct(SwitchRegisterActivity.this, stringStringMap);

    }

    public void addNewDevice(String name, String k) {
        //TODO Add new device details to Website Database.
        //TODO Add to SqLite Database
        Map<String, String> devicemap = new HashMap<String, String>();
        Uri insertedUri = null;
        ContentResolver contentResolver = getContentResolver();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DeviceEntry.DEVICE_NAME, name);
        contentValues.put(DeviceEntry.UNIQUE_KEY, k);
        contentValues.put(DeviceEntry.RELAY1, 0);
        contentValues.put(DeviceEntry.RELAY2, 0);
        contentValues.put(DeviceEntry.RELAY3, 0);
        contentValues.put(DeviceEntry.RELAY4, 0);
        contentValues.put(DeviceEntry.RELAY5, 0);


        insertedUri = contentResolver.insert(DeviceEntry.CONTENT_URI, contentValues);
        Toast.makeText(SwitchRegisterActivity.this, "Added Device " + insertedUri.toString(), Toast.LENGTH_SHORT).show();

        if (insertedUri != null) {
            Toast.makeText(SwitchRegisterActivity.this, "Added Device", Toast.LENGTH_SHORT);
            Toast.makeText(SwitchRegisterActivity.this, "Added Device " + insertedUri.toString(), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(SwitchRegisterActivity.this, "Some Problem adding new Device", Toast.LENGTH_SHORT).show();
            Toast.makeText(SwitchRegisterActivity.this, "Added Device " + insertedUri.toString(), Toast.LENGTH_SHORT).show();

        }

        NetworkUtils.RegisterDeviceFromMainActivity(SwitchRegisterActivity.this, devicemap);


    }

}
