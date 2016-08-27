package com.panlingxiao.proxy.dynamic;

import com.panlingxiao.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by panlingxiao on 2016/8/27.
 */
public class UserServiceProxy implements InvocationHandler {


    private UserService userService;

    private UserServiceProxy(UserService userService){
        this.userService = userService;
    }

    public static UserService getProxy(UserService userService) {
        return (UserService) Proxy.newProxyInstance(UserService.class.getClassLoader(), userService.getClass().getInterfaces(), new UserServiceProxy(userService));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Open Connection");
        System.out.println("Open Transaction");
        Object result = null;
        try {
            result = method.invoke(userService,args);
            System.out.println("Transaction Commit");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Transaction Rollback");
        }finally {
            System.out.println("Close Connection");
        }

        return result;
    }
}
