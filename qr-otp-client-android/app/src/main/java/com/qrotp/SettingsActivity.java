package com.qrotp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.qrotp.datastore.ApplicationKeyDBHelper;
import com.qrotp.datastore.ApplicationSettingsDBHelper;
import com.qrotp.datastore.ApplicationSettingsMater;

public class SettingsActivity extends AppCompatActivity {

    private EditText    serviceURLText;
    private Button      updateServiceURLButton;
    private Button      deleteProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        serviceURLText=(EditText)findViewById(R.id.settings_service_url_txt);
        updateServiceURLButton=(Button)findViewById(R.id.settings_update_service_url_btn);
        deleteProfileButton=(Button)findViewById(R.id.settings_delete_profile_btn);

        ApplicationSettingsDBHelper applicationSettingsDBHelper=new ApplicationSettingsDBHelper(this);
        ApplicationSettingsMater applicationSettingsMater=applicationSettingsDBHelper.getApplicationSettingsMater();
        applicationSettingsDBHelper.close();

        serviceURLText.setText(applicationSettingsMater.getServiceURL());

        final Context context=this;

        updateServiceURLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serviceURL=serviceURLText.getText().toString();
                ApplicationSettingsDBHelper applicationSettingsDBHelper=new ApplicationSettingsDBHelper(context);
                ApplicationSettingsMater rec=applicationSettingsDBHelper.getApplicationSettingsMater();
                rec.setServiceURL(serviceURL);
                applicationSettingsDBHelper.deleteApplicationSettingsMater();
                applicationSettingsDBHelper.saveApplicationSettingsMater(rec);
                Toast.makeText(context,"Service URL updated",Toast.LENGTH_LONG).show();
            }
        });

        deleteProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationKeyDBHelper applicationKeyDBHelper=new ApplicationKeyDBHelper(context);
                applicationKeyDBHelper.deleteApplicationKeyMaster();
                Toast.makeText(context,"Please restart the application",Toast.LENGTH_LONG).show();
            }
        });

    }

}
