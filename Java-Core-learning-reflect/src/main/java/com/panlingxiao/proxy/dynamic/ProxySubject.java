package com.panlingxiao.proxy.dynamic;

import com.panlingxiao.MyTransational;
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

        //执行真实对象的方法
        Object result = method.invoke(subject, args);

        //当方法上使用了注解，那么就输出日志
        if(null != method.getAnnotation(MyTransational.class)) {
            System.out.println("log something");
        }
        System.out.println("after invoke");
        return result;
    }
}
