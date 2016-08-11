package com.panlingxiao.classloader;

/**
 * Created by panlingxiao on 2016/8/9.
 */
public class TestClassLoad {

    static {
        a = 2;
    }
    static int a = 1;

    static {
        a = 3;
    }

    public static void main(String[] args) {
        System.out.println(a);
    }
}
