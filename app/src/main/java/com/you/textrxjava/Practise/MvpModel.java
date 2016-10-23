package com.you.textrxjava.Practise;

/**
 * Created by lenovo on 2016/10/22.
 */

public class MvpModel {
    /**从服务器获取的apk版本*/
    private String newApkVersion;

    /**GET 请求*/
    public static final String GET_NEW_VER_URL = "http://192.168.1:8080/app/index/type?version=query";

    public String getNewApkVersion() {
        return newApkVersion;
    }

    public void setNewApkVersion(String newApkVersion) {
        this.newApkVersion = newApkVersion;
    }
}
