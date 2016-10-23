package com.you.textrxjava.TextRxJava;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2016/10/22.
 */


//登录
public class MyLogin {

    public String URL_LOGIN = "";
    public String userName = "";
    public String passWord = "";

    public void login(){

        //创建Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_LOGIN)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        //获取接口实例
        final MyServer server = retrofit.create(MyServer.class);
        server.login(userName,passWord)
                .flatMap(new Func1<String, Observable<UserInfo>>(){
                    @Override
                    public Observable<UserInfo> call(String token) {
                        return server.getUser(token);          //获取Token,然后验证
                    }
                })

                .subscribeOn(Schedulers.newThread())        //事件产出在一个新线程中
                .observeOn(Schedulers.io())                 //在io线程保存用户信息
                .doOnNext(new Action1<UserInfo>() {
                    @Override
                    public void call(UserInfo userInfo) {
                       // SaveUserInfo(userInfo);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())   //事件消费在主线程中
                .subscribe(new Observer<UserInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        //userView.setUser(userInfo);
                    }
                });



    }
}
