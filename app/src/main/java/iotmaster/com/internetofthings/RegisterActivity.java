package iotmaster.com.internetofthings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmailAddress;
    private EditText Password;
    private EditText mConf_password;
    private EditText mMobile;
    private Button mSignUp;
    private CheckBox mCheckBox;
    private TextView register;
    String pass;
    String conf_password;
    String number;
    String email;

    public static final String PASSWORD = "pswrd";
    public static final String NUMBER = "mob";
    public static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmailAddress = (EditText) findViewById(R.id.email_form);
        mConf_password = (EditText) findViewById(R.id.confirm_password);
        mMobile = (EditText) findViewById(R.id.mobile_form);
        Password = (EditText) findViewById(R.id.password_form);
        mSignUp = (Button) findViewById(R.id.signup);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox);


        register = (TextView) findViewById(R.id.registered);

        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fireIntent();
            }
        });

        mSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = Password.getText().toString();
                conf_password = mConf_password.getText().toString();
                number = mMobile.getText().toString();
                email = mEmailAddress.getText().toString();

                Log.i("pass",pass);
                Log.i("Confirm",conf_password);
                Log.i("Number",number);
                Log.i("email",email);
                // Do something here
                signup();

            }
        });


    }

    public void fireIntent() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    public void signup() {
        if (TextUtils.isEmpty(pass) ||
                TextUtils.isEmpty(conf_password) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(number)) {
            Toast.makeText(RegisterActivity.this, "Some Fields are missing", Toast.LENGTH_SHORT).show();

        } else {
            if (!conf_password.equals(pass)) {
                Toast.makeText(RegisterActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();
            }
            else if(!mCheckBox.isChecked()){
                Toast.makeText(RegisterActivity.this, "Please check Terms and Conditions", Toast.LENGTH_SHORT).show();

            }
            else {

                Bundle bundle = new Bundle();
                bundle.putString(PASSWORD, pass);
                bundle.putString(EMAIL, email);
                bundle.putString(NUMBER, number);

                Intent intent = new Intent(RegisterActivity.this, SwitchRegisterActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
