package com.developinggeek.naggaro.AddPostRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by gargc on 15-09-2018.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("enter remaining url here")
    Call<PostModel> getPostResponse(@Body PostModel postRequest);


}
