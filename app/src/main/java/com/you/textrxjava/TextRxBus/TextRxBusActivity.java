package com.you.textrxjava.TextRxBus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.you.textrxjava.R;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2016/10/23.
 */

public class TextRxBusActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button  bt1,bt2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxjava_layout);
        mTextView = (TextView) findViewById(R.id.tv_rxbus);
        bt1 = (Button) findViewById(R.id.button1);
        bt2 = (Button) findViewById(R.id.button2);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getInstance().post("尤旋是傻逼");
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RxBus.getInstance().post(234324);
                    }
                }).start();
            }
        });

        doSubscibe();  //订阅

    }

    private void doSubscibe(){

        Subscription subscription1 = RxBus.getInstance()
                .toObservable(String.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mTextView.setText("事件内容：" + s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        RxBus.getInstance().addSubscription(this,subscription1);

        Subscription subscription2 = RxBus.getInstance()
                .doSubsrible(Integer.class, new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mTextView.setText("事件内容：" + integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

        RxBus.getInstance().addSubscription(this,subscription2);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unSubscribe(this);
    }
}
