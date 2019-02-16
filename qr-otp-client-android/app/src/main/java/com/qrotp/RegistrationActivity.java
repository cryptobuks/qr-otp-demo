package com.qrotp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    EditText    emailIdBox;
    Button  registerButton;
    Button  settingsButton;


    public  static  final   String EMAILID_MESSAGE="com.pradip.photootp.emailid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        emailIdBox=(EditText)findViewById(R.id.email_txt);
        registerButton=(Button)findViewById(R.id.register_btn);
        settingsButton=(Button)findViewById(R.id.register_settings_btn);

        final Context context=this;

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String emailId=emailIdBox.getText().toString();
                Intent intent=new Intent(context,OTPConfirmationActivity.class);
                intent.putExtra(EMAILID_MESSAGE,emailId.toLowerCase().trim());
                startActivity(intent);

            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,SettingsActivity.class);
                startActivity(intent);
            }
        });
    }




}
