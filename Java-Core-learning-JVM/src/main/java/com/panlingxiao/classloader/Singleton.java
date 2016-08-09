package com.panlingxiao.classloader;

/**
 * Created by panlingxiao on 2016/8/9.
 *
 * 世界上99%的Java程序猿都犯得错误
 */
public class Singleton {
    private static  Singleton singleton = new Singleton();
    private static int counter1;
    private static int counter2 = 2;

    private Singleton(){
        counter1++;
        counter2++;
    }

    public static void main(String[] args) {
        System.out.println("counter1:"+counter1); // 1
        System.out.println("counter2:"+counter2); // 2
    }
}
