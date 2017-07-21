package iotmaster.com.internetofthings.UserInterface;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import iotmaster.com.internetofthings.Network.NetworkUtils;
import iotmaster.com.internetofthings.R;


public class SetupActivity extends AppCompatActivity {

    private Button setUp;
    private EditText name;
    private EditText password;
    public ProgressDialog progressDialog;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        name = (EditText) findViewById(R.id.networkSSID);
        password = (EditText) findViewById(R.id.networkPassword);
        setUp = (Button) findViewById(R.id.setupProduct);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait while we Initialize Product...");

        // final ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressBar);
        // progressBar.setVisibility(View.VISIBLE);

       /* WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://192.168.4.1");*/

        setUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String deviceName = name.getText().toString().trim();
                String devicePassword = password.getText().toString().trim();


                initializeProduct(deviceName, devicePassword);


            }
        });
    }

    private void initializeProduct(String trim, String trim1) {

        NetworkUtils.initializeDevice(SetupActivity.this, trim, trim1);
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Intent i=new Intent(SetupActivity.this,MainActivity.class);
                i.putExtra("fromSetupActivity",true);
                startActivity(i);
            }
        }, 2000);

    }

}
