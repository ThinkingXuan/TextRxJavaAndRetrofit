package com.you.textrxjava.Practise;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.BaseAdapter;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo on 2016/10/22.
 */

public class MvpPresenter extends BasePresent<MypView> {

    private MvpModel mMvpModel;
    private Context mContext;

    public MvpPresenter(Context context) {
        mContext = context;
        //对业务层初始化为后面获取的apk版本进行存放
        mMvpModel = new MvpModel();
    }

    //由于逻辑处理放到我们的Presenter中了，因此我们需要将在Activity的onResume 时候进行版本的检查
    public void onResume(){
        getVerFromServer();
    }

    private void getVerFromServer(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        RetrofitCall IRetrofit = retrofit.create(RetrofitCall.class);
        Call<VersonBean> call = IRetrofit.getVersionCall("1");
        call.enqueue(new Callback<VersonBean>() {
            @Override
            public void onResponse(Call<VersonBean> call, Response<VersonBean> response) {
                if(response.body().getVerson().equals(getCurrentVer())){

                    mView.showMessage();
                }
            }

            @Override
            public void onFailure(Call<VersonBean> call, Throwable t) {

            }
        });

    }

    //采用RxJava和Retrofit模式

    private void getVerFromServer1(){
        Retrofit mRxJavaRetrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1:8080/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitCall IRetrofitCallByRxJava = mRxJavaRetrofit.create(RetrofitCall.class);
        IRetrofitCallByRxJava.getVersionCallByRxJava("1")
                //消费事件的线程
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VersonBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VersonBean versonBean) {
                        if(versonBean.getVerson().equals(getCurrentVer())){
                            mView.showMessage();
                        }
                    }
                });
    }

    private String getCurrentVer(){

        String verName ="";
        try {
            verName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Log.e("apkVer",verName);
        return verName;
    }

}
