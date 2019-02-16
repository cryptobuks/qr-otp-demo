package com.qrotp.rest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PhotoOTPService {
    @POST("/uploadcertificate")
    @FormUrlEncoded
    Call<UploadCertificateAcknowledgement>    uploadCertificate(@Field("email") String emailId, @Field("publickey") byte[] publicKey);
}
