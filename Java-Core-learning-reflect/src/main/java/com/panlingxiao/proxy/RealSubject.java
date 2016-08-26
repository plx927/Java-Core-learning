package com.panlingxiao.proxy;

import com.panlingxiao.proxy.Subject;

/**
 * Created by panlingxiao on 2016/8/26.
 */
public class RealSubject implements Subject {
    @Override
    public String sayHello(String name) {
        return "hello:"+name;
    }
}
