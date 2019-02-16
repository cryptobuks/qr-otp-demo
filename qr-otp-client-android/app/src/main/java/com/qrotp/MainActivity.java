package com.qrotp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.qrotp.datastore.ApplicationKeyDBHelper;

public class MainActivity extends AppCompatActivity {

    private Button  scanButton;
    private TextView    otpDisplay;
    private Button  settingsButton;

    private static final int RC_BARCODE_CAPTURE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanButton=(Button)findViewById(R.id.scan_btn);

        otpDisplay=(TextView)findViewById(R.id.otp_lbl);

        final MainActivity mainActivity=this;
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity, BarcodeCaptureActivity.class);
                intent.putExtra(BarcodeCaptureActivity.AutoFocus, true);
                intent.putExtra(BarcodeCaptureActivity.UseFlash, false);
                intent.putExtra(BarcodeCaptureActivity.AutoCapture, true);
                startActivityForResult(intent, RC_BARCODE_CAPTURE);
            }
        });


        settingsButton=(Button)findViewById(R.id.main_menu_settings_btn);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mainActivity,SettingsActivity.class);
                startActivity(intent);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        otpDisplay.setText("");

        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                //    statusMessage.setText(R.string.barcode_success);
                //    barcodeValue.setText(barcode.displayValue);
                //    Log.d(TAG, "Barcode read: " + barcode.displayValue);

                    try {
                        String encryptedData = barcode.displayValue;

                        ApplicationKeyDBHelper applicationKeyDBHelper = new ApplicationKeyDBHelper(this);
                        String privateKey = applicationKeyDBHelper.getApplicationKeyMaster().getPrivateKey();
                        String otp=CryptoUtil.decryptText(encryptedData, privateKey);
                        otpDisplay.setText(otp);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(this,"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                    }


                 //   otpDisplay.setText(barcode.displayValue);
                 //   Toast.makeText(this,"Barcode value: "+barcode.displayValue,Toast.LENGTH_LONG).show();

                } else {
                //    statusMessage.setText(R.string.barcode_failure);
                 //   Log.d(TAG, "No barcode captured, intent data is null");

                    Toast.makeText(this,"No barcode captured, intent data is null",Toast.LENGTH_LONG).show();
                }
            } else {
            //    statusMessage.setText(String.format(getString(R.string.barcode_error),
            //            CommonStatusCodes.getStatusCodeString(resultCode)));

                Toast.makeText(this,
                        String.format(getString(R.string.barcode_error),CommonStatusCodes.getStatusCodeString(resultCode)),
                        Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
