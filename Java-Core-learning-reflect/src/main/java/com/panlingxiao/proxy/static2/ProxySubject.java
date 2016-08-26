package com.panlingxiao.proxy.static2;

import com.panlingxiao.proxy.Subject;

/**
 * Created by panlingxiao on 2016/8/26.
 */
public class ProxySubject implements Subject {

    private Subject subject;

    public ProxySubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String sayHello(String name) {
        System.out.println("before invoke");
        String result = subject.sayHello(name);
        System.out.println("after invoke");
        return result;
    }
}
