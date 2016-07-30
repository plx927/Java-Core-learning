package com.panlingxiao.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by panlingxiao on 2016/7/30.
 * <pre>
 * 1、源码分析为什么会出现ConcurrentModificationException
 * 2、ArrayList在JDK1.7的源码实现上，底层实现了一个大小为0的数组，为什么要这样做?
 *   对比JDK 1.6的源码实现，分析这样设计的目的。
 *
 *  引申:如果在JDK 1.6的情况下，当查询数据，如果查询的结果为null，你需要返回给前端一个非空的List，你会如何处理？
 *
 *  eg:public List<User> listUser(){
 *      List<User> users = userDao.list();
 *      if(users != null){
 *          return users;
 *      }
 *      //可以，性能不好。
 *      return new ArrayList<User>();
 *  }
 *  JDK1.6与1.7在ArrayList实现上的改动:http://www.tuicool.com/articles/Evu2IzF
 * </pre>
 *
 *
 */
public class FastFailTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>(Arrays.asList(new String[]{"hello","word","Java"}));
        for(String str : list){
            /*这种情况下为什么不抛异常
            if("world".equals(str)){
                list.add("JDK");
            }
            */
            list.add("aa");
        }
    }


}
