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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qrotp.datastore.ApplicationKeyMaster;
import com.qrotp.datastore.ApplicationKeyDBHelper;
import com.qrotp.datastore.ApplicationSettingsDBHelper;
import com.qrotp.rest.PhotoOTPService;
import com.qrotp.rest.UploadCertificateAcknowledgement;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTPConfirmationActivity extends AppCompatActivity {

    Button confirmButton;
    TextView    emailLabel;

    private String  emailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpconfirmation);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        emailId = intent.getStringExtra(RegistrationActivity.EMAILID_MESSAGE);

        confirmButton=(Button)findViewById(R.id.otp_confirm_btn);
        emailLabel=(TextView)findViewById(R.id.otp_confirm_email_lbl);

        emailLabel.setText(emailId);

        final Context context=this;

        ApplicationSettingsDBHelper applicationSettingsDBHelper=new ApplicationSettingsDBHelper(this);
        final String serviceURL=applicationSettingsDBHelper.getApplicationSettingsMater().getServiceURL();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OTPConfirmationTask task=new OTPConfirmationTask(context,serviceURL);
                task.execute();
            }
        });

    }


    class OTPConfirmationTask extends AsyncTask{
        private Context context;
        private String  serviceURL;

        public OTPConfirmationTask(Context context,String serviceURL){
            this.context=context;
            this.serviceURL=serviceURL;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                KeyPairStore pair=CryptoUtil.generateKeyPair(emailId);

                Retrofit retrofit = NetworkHandler.getRetrofit(serviceURL);

                PhotoOTPService photoOTPService = retrofit.create(PhotoOTPService.class);

                Call<UploadCertificateAcknowledgement> call=photoOTPService.uploadCertificate(emailId,pair.getPublicKeyBytes());

                Response<UploadCertificateAcknowledgement> response=call.execute();

                if(response.isSuccessful() && response.body().getErrorCode()==0){
                    ApplicationKeyDBHelper helper=new ApplicationKeyDBHelper(context);
                    ApplicationKeyMaster rec=new ApplicationKeyMaster();
                    rec.setEmailId(emailId);
                    rec.setPublicKey(pair.getPublicKey());
                    rec.setPrivateKey(pair.getPrivateKey());
                    helper.saveApplicationKeyMaster(rec);


                    Intent intent=new Intent(context,MainActivity.class);
                    startActivity(intent);


                //    Toast.makeText(context,"Done",Toast.LENGTH_LONG).show();

                }
                else{
                //    Toast.makeText(context,"Error!! While connecting server.",Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            //    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            }

            return null;
        }
    }

}
