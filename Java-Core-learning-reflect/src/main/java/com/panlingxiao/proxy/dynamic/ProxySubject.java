package com.panlingxiao.proxy.dynamic;

import com.panlingxiao.proxy.Subject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by panlingxiao on 2016/8/26.
 */
public class ProxySubject implements InvocationHandler {

    private Subject subject;

    public ProxySubject(Subject subject) {
        this.subject = subject;
    }



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke");
        Object result = method.invoke(subject, args);
        System.out.println("after invoke");
        return result;
    }
}
