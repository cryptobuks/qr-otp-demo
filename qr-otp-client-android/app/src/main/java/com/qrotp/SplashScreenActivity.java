package com.qrotp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.qrotp.datastore.ApplicationKeyDBHelper;
import com.qrotp.datastore.ApplicationSettingsDBHelper;
import com.qrotp.datastore.ApplicationSettingsMater;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new SplashScreenActivityTask(this).execute();
    }


    class SplashScreenActivityTask  extends AsyncTask{

        private Context context;

        public SplashScreenActivityTask(Context context){
            this.context=context;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try{
                Thread.sleep(1000 * 3);
            }
            catch (Exception e){

            }

            ApplicationSettingsDBHelper applicationSettingsDBHelper=new ApplicationSettingsDBHelper(context);
            if(applicationSettingsDBHelper.getApplicationSettingsMater()==null){
                ApplicationSettingsMater applicationSettingsMater=new ApplicationSettingsMater();
                applicationSettingsMater.setEncryptedPassword("");
                applicationSettingsMater.setServiceURL("https://enigmatic-springs-80955.herokuapp.com");
                applicationSettingsDBHelper.saveApplicationSettingsMater(applicationSettingsMater);
            }
            applicationSettingsDBHelper.close();


            ApplicationKeyDBHelper applicationKeyDBHelper=new ApplicationKeyDBHelper(context);
            Intent targetIntent=null;
            if(applicationKeyDBHelper.getApplicationKeyMaster()!=null){
                targetIntent=new Intent(context,MainActivity.class);
            }
            else{
                targetIntent=new Intent(context,RegistrationActivity.class);
            }
            applicationKeyDBHelper.close();

            startActivity(targetIntent);
            return null;
        }
    }



}
