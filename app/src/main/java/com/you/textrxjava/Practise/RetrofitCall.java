package com.you.textrxjava.Practise;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by youxuan on 2016/10/22.
 */

public interface RetrofitCall {

    // http://192.168.1:8080/app/index/type?version=query
    @GET("app/index/type")
    Call<VersonBean> getVersionCall(@Query("version")String version);

    @GET("app/index/type")
    Observable<VersonBean> getVersionCallByRxJava(@Query("version")String verson);
}
