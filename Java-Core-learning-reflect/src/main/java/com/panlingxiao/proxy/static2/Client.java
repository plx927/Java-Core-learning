package com.panlingxiao.proxy.static2;

import com.panlingxiao.proxy.RealSubject;
import com.panlingxiao.proxy.Subject;

/**
 * Created by panlingxiao on 2016/8/26.
 */
public class Client {
    public static void main(String[] args) {
        Subject subject = new ProxySubject(new RealSubject());
        String result = subject.sayHello("吴大神");
        System.out.println(result);
    }
}
