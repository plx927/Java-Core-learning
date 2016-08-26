package com.panlingxiao.proxy.dynamic;

import com.panlingxiao.proxy.RealSubject;
import com.panlingxiao.proxy.Subject;

import java.lang.reflect.Proxy;

/**
 * Created by panlingxiao on 2016/8/26.
 */
public class Client {
    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        /*
         * 这是有JDK自动生成的类
         *  需要3个参数:
         *  1.类加载器,使用系统类加载器即可
         *  2.真实对象所实现的接口的Class(因为JDK的动态代理必须要求真实对象实现接口,否则无法生成代理,另外的一个框架可以要求真实对象不实现接口也能生成代理(CGLIB))
         *  3.InvocationHandler的实现类
         *
         */
        Subject proxy = (Subject) Proxy.newProxyInstance(Client.class.getClassLoader(), realSubject.getClass().getInterfaces(), new ProxySubject(realSubject));
        System.out.println(proxy.getClass());
        String result = proxy.sayHello("吴大神");
        System.out.println(result);
    }
}
