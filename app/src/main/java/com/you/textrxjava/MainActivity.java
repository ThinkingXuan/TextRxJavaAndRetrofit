package com.you.textrxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Action3;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        //用rxjava打印字符串数组
//        String[] s = new String[]{"哈哈", "傻瓜", "真棒"};
//
//        Observable.from(s)
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.d(TAG, s);
//                    }
//                });
//
//
//        final int drawableRes = R.mipmap.ic_launcher;
//        mImageView = (ImageView) findViewById(R.id.imageView);

//        Observable.create(new Observable.OnSubscribe<Drawable>() {
//            @Override
//            public void call(Subscriber<? super Drawable> subscriber) {
//                Drawable drawable =getResources().getDrawable(drawableRes);
//                subscriber.onNext(drawable);
//                subscriber.onCompleted();
//            }
//        }).subscribe(new Observer<Drawable>() {
//            @Override
//            public void onCompleted() {
//                Log.d(TAG, "onCompleted() called");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "onError() called with: e = [" + e + "]");
//            }
//
//            @Override
//            public void onNext(Drawable drawable) {
//                mImageView.setImageDrawable(drawable);
//            }
//        });


//
//        //创建Observable对象。
//        Observable.create(new Observable.OnSubscribe<Drawable>() {
//            @Override
//            public void call(Subscriber<? super Drawable> subscriber) {
//                Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
//                subscriber.onNext(drawable);
//                subscriber.onCompleted();
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Drawable>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.d(TAG, "onCompleted() called");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: ");
//                    }
//
//                    @Override
//                    public void onNext(Drawable drawable) {
//                        mImageView.setImageDrawable(drawable);
//
//                    }
//                });
//
//
//        Observable.create(new Observable.OnSubscribe<Drawable>() {
//            @Override
//            public void call(Subscriber<? super Drawable> subscriber) {
//                Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
//                subscriber.onNext(drawable);
//                subscriber.onCompleted();
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Drawable>() {
//                    @Override
//                    public void call(Drawable drawable) {
//                        mImageView.setImageDrawable(drawable);
//                    }
//                });
//
//
//        String[] s1 = new String[]{"File", "Edit", "View", "Navigate", "Code", "Analyze", "Refactor", "Build", "Run", "Tools"};
//
//
//        Observable.from(s1)
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.d(TAG, s);
//                    }
//                });
//
//
//        //指定线程，通过Observable.just
//
//        Observable.just(1, 2, 3, 4, 5)
//
//                .subscribeOn(Schedulers.io()) //指定Subscrib发生在I/O线程
//                .observeOn(AndroidSchedulers.mainThread())  //指定Observer发生在主线程中
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        Log.d(TAG, integer + ",");
//                    }
//                });
//
//        //变换 (String-> Bitmap)
//        Observable.just("image/logo.png")
//                .map(new Func1<String, Bitmap>() {
//                    @Override
//                    public Bitmap call(String s) {
//                        return BitmapFactory.decodeFile(s);
//                    }
//                })
//                .subscribe(new Action1<Bitmap>() {
//                    @Override
//                    public void call(Bitmap bitmap) {
//                        mImageView.setImageBitmap(bitmap);
//                    }
//                });
//
//
//        Student student1 = new Student();
//        student1.setName("张三");
//        Student student2 = new Student();
//        student2.setName("李四");
//        Student student3 = new Student();
//        student3.setName("尤旋");
//
//
//        final Student[] students = {student1, student2, student3};
//
//        //通过map,来验证rxjava的变换
//        Subscriber<String> subscriber = new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.d(TAG, s);
//            }
//        };
//        Observable.from(students)
//                .map(new Func1<Student, String>() {
//                    @Override
//                    public String call(Student student) {
//                        return student.getName();
//                    }
//                })
//                .subscribe(subscriber);
//
//        Observable.from(students)
//                .flatMap(new Func1<Student, Observable<?>>() {
//                    @Override
//                    public Observable<?> call(Student student) {
//                        return Observable.from(student.getCouser());
//                    }
//                });

        //Observable 作为一个被观察者,可以通过Observable的create()方法创建 在里传入一个OnSubscible抽象类，通过Call 方法响应事件
        // onNext产生一个事件,OnCompleted代表事件产生完成
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("哈哈");
                subscriber.onNext("尤旋");
                subscriber.onCompleted();
            }
        });

        //创建一个Observer,作为一个观察者，用于监听Observable的事件。
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted() called");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError() called with: e = [" + e + "]");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext() called with: s = [" + s + "]");
            }
        };

        //订阅事件 通过subscribe
        observable.subscribe(observer);

        //也可以这样使用，just里面传入一个参数列表
        Observable.just("Hello RxJava","Welocome RxJava")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: e = [" + e + "]");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext() called with: s = [" + s + "]");
                    }
                });

        Observable.just("尤旋")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s+"要成为架构师";
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext() called with: s = [" + s + "]");
                    }
                });
    }


    public Observable query(String str) {
        List<String> s = Arrays.asList("hah", "iosdj", "sdjfoi");
        return Observable.from(s);
    }

}
