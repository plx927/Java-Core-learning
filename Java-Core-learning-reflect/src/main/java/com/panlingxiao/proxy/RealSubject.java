package com.panlingxiao.proxy;

/**
 * Created by panlingxiao on 2016/8/26.
 */
public class RealSubject implements Subject {
    @Override
    public String sayHello(String name) {
        return "hello:"+name;
    }


    @Override
    public void go() {
        System.out.println("I'm going !");
    }
}
