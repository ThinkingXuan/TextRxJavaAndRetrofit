package com.you.textrxjava.Practise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.you.textrxjava.R;

/**
 * Created by lenovo on 2016/10/22.
 */

public class MvpActivity extends BaseMvpActivity<MypView,MvpPresenter> implements MypView{

    private TextView tvMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }




    @Override
    public MvpPresenter initPresenter() {
        return new MvpPresenter(getApplicationContext());
    }


    @Override
    public void showMessage() {
        tvMsg.setText("新版本要更新");
        Toast.makeText(getApplicationContext(),"更新版本啦",Toast.LENGTH_LONG).show();

    }
}
