package com.panlingxiao.proxy;

import com.panlingxiao.MyTransational;

/**
 * Created by panlingxiao on 2016/8/26.
 */
public interface Subject {

    String sayHello(String name);
    @MyTransational
    void go();
}
