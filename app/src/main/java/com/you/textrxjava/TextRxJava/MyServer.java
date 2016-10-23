package com.you.textrxjava.TextRxJava;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lenovo on 2016/10/22.
 */

//新建一个API接口
public interface MyServer {

    //登录时的用户信息
    @GET("/login")
    Observable<String>login(@Query("userName")String userName,
                              @Query("passWord")String passWord);

    //登录获取Token
    @GET("/user")
    Observable<UserInfo> getUser(@Query("token")String token);
}
