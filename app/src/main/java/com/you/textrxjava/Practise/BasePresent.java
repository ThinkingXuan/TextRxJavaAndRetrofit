package com.you.textrxjava.Practise;

/**
 * Created by lenovo on 2016/10/22.
 */

public class BasePresent<T> {
    /*这个基类的Presenter 主要的作用就是将当前Presenter持有的view 在合适的时候给清除掉*/

    public T mView ;
    public void attach(T mView){
        this.mView=mView;
    }

    public void dettach(){
        mView=null;
    }
}
