package com.you.textrxjava;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by youxuan on 2016/10/22.
 * Retrofit简单使用
 * 1.新建API接口
 * 2.新建Retrofit实例
 * 3.调用API接口
 */

// https://api.github.com/repos/square/retrofit/contributors



public class TextSimpleGetRequst extends AppCompatActivity {


    private static final String TAG="TextSimpleGetRequst";

    private String mUserName = "square";
    private String mrepo = "retrofit";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //新建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build();

        //调取API
        MyGithubApi myGithubApi = retrofit.create(MyGithubApi.class);
        Call<ResponseBody> call = myGithubApi.MyRetrofitSimpleRequest(mUserName,mrepo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "onResponse: "+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });

    }
}

//新建API接口
interface MyGithubApi{

    @GET("repos/{owner}/{repo}/contributors")
    Call<ResponseBody> MyRetrofitSimpleRequest(@Path("owner")String owner,@Path("repo")String repo);
}


