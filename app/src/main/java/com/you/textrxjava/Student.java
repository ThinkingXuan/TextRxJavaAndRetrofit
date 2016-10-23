package com.you.textrxjava;

import java.util.List;

/**
 * Created by lenovo on 2016/10/19.
 */

public class Student {

    private String name;
    private List<String> couser;

    public List<String> getCouser() {
        return couser;
    }

    public void setCouser(List<String> couser) {
        this.couser = couser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
