package com.panlingxiao.proxy.dynamic;

import com.panlingxiao.UserService;
import com.panlingxiao.UserServiceImpl;

/**
 * Created by panlingxiao on 2016/8/27.
 */
public class MockSpringTransation {


    public static void main(String[] args) {
        //打开连接
        //开启事务

        //执行方法
        UserService userService = UserServiceProxy.getProxy(new UserServiceImpl());
        userService.addUser("吴大神");

        //commit/rollback
        //关闭连接


    }
}
