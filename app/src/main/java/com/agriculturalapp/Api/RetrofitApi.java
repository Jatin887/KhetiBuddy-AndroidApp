package com.agriculturalapp.Api;

import com.agriculturalapp.modals.DataClass;
import com.agriculturalapp.modals.PredictedResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitApi {
    @POST("crop-predict")
    @FormUrlEncoded

    Call<PredictedResult> createPost(@Field("nitrogen") int nitrogen,
                                             @Field("phosphorous") int phosphorous,
                                             @Field("pottasium") int potassium,
                                             @Field("ph") double ph,
                                             @Field("rainfall") double rainfall,
                                             @Field("city") String city

                                             );
}
