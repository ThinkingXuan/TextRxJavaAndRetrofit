package com.you.textrxjava;

import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lenovo on 2016/10/21.
 */

public class TextRetrofit extends AppCompatActivity {

    private static final String TAG="TextRetrofitActivity";
    private String mUserName = "square";
    private String mRepo = "retrofit";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        // https://api.github.com/repos/square/retrofit/contributors
//
//        //增加日志信息
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(httpLoggingInterceptor)
//                .build();
//
//        //创建retrofit
//        final Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        GitHubApi gitHubApi = retrofit.create(GitHubApi.class);
//        Call<List<Contributor>> call = gitHubApi.ByAddConverGetCall(mUserName,mRepo);
//
//        call.enqueue(new Callback<List<Contributor>>() {
//            @Override
//            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
//                List<Contributor> list = response.body();
//                for (Contributor u:list) {
//                    Log.d(TAG, "onResponse():"+u.getLogin());
//                    Log.d(TAG, "onResponse():"+u.getContributions());
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Contributor>> call, Throwable t) {
//
//            }
//        });
        // https://api.github.com/search/repositories?q=retrofit&since=2016-03-29&page=1&per_page=3
//       Retrofit retrofit1 = new Retrofit.Builder()
//               .addConverterFactory(GsonConverterFactory.create())
//               .baseUrl("https://api.github.com/")
//               .build();
//
//        GitHubApi gitHubApi1 = retrofit1.create(GitHubApi.class);
//        Call<RetrofitBean> call1 = gitHubApi1.queryRetrofitGetCall("retrofit","2016-03-29",1,3);
//
//        call1.enqueue(new Callback<RetrofitBean>() {
//            @Override
//            public void onResponse(Call<RetrofitBean> call, Response<RetrofitBean> response) {
//                RetrofitBean retrofitBean = response.body();
//                List<Item> list = retrofitBean.getItems();
//                if (list == null)
//                    return;
//                Log.d(TAG, "total:" + retrofitBean.getTotalCount());
//                Log.d(TAG, "incompleteResults:" + retrofitBean.getIncompleteResults());
//                Log.d(TAG, "----------------------");
//                for (Item item : list) {
//                    Log.d(TAG, "name:" + item.getName());
//                    Log.d(TAG, "full_name:" + item.getFull_name());
//                    Log.d(TAG, "description:" + item.getDescription());
//                    Owner owner = item.getOwner();
//                    Log.d(TAG, "login:" + owner.getLogin());
//                    Log.d(TAG, "type:" + owner.getType());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<RetrofitBean> call, Throwable t) {
//                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
//            }
//        });

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build();
        GitHubApi gitHubApi = retrofit.create(GitHubApi.class);

        CompositeSubscription mSubscriptions = new CompositeSubscription();
        mSubscriptions = RxUtils.getNewCompositeSubIfUnsubscribed(mSubscriptions);

        mSubscriptions.add(
                gitHubApi.contributorByRxJava(mUserName,mRepo)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<Contributor>>() {

                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted() called");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: ");
                        }

                        @Override
                        public void onNext(List<Contributor> contributors) {
                            for (Contributor c : contributors) {
                                Log.d("TAG", "login:" + c.getLogin() + "  contributions:" + c.getContributions());
                            }
                        }
                    }));
                
    }
}

 //创建API接口
interface GitHubApi{
     @GET("repos/{owner}/{repo}/contributors")
     Call<ResponseBody> contributorsBySimapleGetCall(
             @Path("owner")String owner,@Path("repo")String repo);


     @Headers({
             "Accept: application/vnd.github.v3.full+json",
             "User-Agent: RetrofitBean-Sample-App",
             "name:ljd"
     })
     @GET("repos/{owner}/{repo}/contributors")
     Call<List<Contributor>> ByAddConverGetCall(
             @Path("owner")String owner,@Path("repo")String repo);

     @GET("search/repositories")
     Call<RetrofitBean> queryRetrofitGetCall(@Query("q")String owner,
                                             @Query("since")String time,
                                             @Query("page")int page,
                                             @Query("per_page")int per_page);

     @GET("repos/{owner}/{repo}/contributors")
     Observable<List<Contributor>> contributorByRxJava(
             @Path("owner")String owner,
             @Path("repo")String repo);

}

