package com.panlingxiao;

/**
 * Created by panlingxiao on 2016/8/27.
 */
public class UserServiceImpl implements UserService{


    @Override
    public void addUser(String name) {
        System.out.println("添加用户:"+name);
        System.out.println("记录日志...");
        System.out.println("更新状态...");
    }
}
