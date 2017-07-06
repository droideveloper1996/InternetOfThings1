package iotmaster.com.internetofthings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

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
                final String ProductName = productName.getText().toString();
                if (!fromMain) {
                    setRegisterProduct(product_key);
                } else {
                        //TODO: Implement When new Switch added from mainActivity.
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
        NetworkUtils.registerProduct(SwitchRegisterActivity.this, stringStringMap);
    }

}
