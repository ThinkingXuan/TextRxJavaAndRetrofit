package com.you.textrxjava.TextRxBus;

import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by youxuan on 2016/10/23.
 * 借用RxBus实现EventBus功能
 */

public class RxBus {
    private static volatile RxBus mInstance;
    private SerializedSubject<Object,Object> mSubject;
    private HashMap<String,CompositeSubscription> mSubscriptionHashMap;

    //私有化构造器，保证类不能被正常实例化
    private RxBus() {
        mSubject = new SerializedSubject<>(PublishSubject.create());

    }

    //通过一个单例，来获取自身的实例
    public static RxBus getInstance(){

        if(mInstance == null){
            synchronized (RxBus.class){
                if (mInstance == null){
                    mInstance = new RxBus();
                }
            }
        }
        return mInstance;
    }

    /**
     * 发送事件
     */

    public void post(Object o){
        mSubject.onNext(o);
    }

    /**
     * 返回指定类型的Observable实例
     */

    public <T>Observable<T> toObservable(final Class<T> type){

        return mSubject.ofType(type);
    }

    /**
     * 是否已有被观察者订阅
     */

    public boolean hasObservers(){
        return mSubject.hasObservers();
    }

    /**
     * 一个默认的订阅方法
     */
    public <T> Subscription doSubsrible(Class <T> type,
                                        Action1<T> next,Action1<Throwable> error){
        return toObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next,error);
    }

    /**
     * 保存订阅后的subscription
     */

    public void addSubscription(Object o,Subscription subscription){

        if(mSubscriptionHashMap == null){
            mSubscriptionHashMap = new HashMap<>();
        }

        String key = o.getClass().getName();
        if(mSubscriptionHashMap.get(key)!=null){
            mSubscriptionHashMap.get(key).add(subscription);
        }else {

            CompositeSubscription compositeSubscription = new CompositeSubscription();
            compositeSubscription.add(subscription);
            mSubscriptionHashMap.put(key,compositeSubscription);
        }
    }

    /**
     * 取消订阅
     */
    public void unSubscribe(Object o){
        if(mSubscriptionHashMap == null){
            return;
        }
        String key = o.getClass().getName();
        if(!mSubscriptionHashMap.containsKey(key)){
            return;
        }
        if(mSubscriptionHashMap.get(key)!=null){
            mSubscriptionHashMap.get(key).unsubscribe();
        }

        mSubscriptionHashMap.remove(key);
    }
}
