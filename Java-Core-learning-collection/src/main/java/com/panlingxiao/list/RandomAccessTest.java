package com.panlingxiao.list;

import java.util.*;

/**
 * Created by panlingxiao on 2016/7/30.
 * RandomAccess是一个标识接口，当一个List实现该接口时，就说明该List支持快速(常量级别)的随机查找。
 *
 */
public class RandomAccessTest {

    public static final int COUNTER = 100000;
    public static void main(String[] args) {
        /*
         * 对比ArrayList与LinkedList，同样适用for循环造成的性能差异
         * LinkedList测试耗时：11s
         * ArrayList测试耗时：0s
         * 分析LinkedList在底层get的时候速度慢的原因:
         * 因为LinkedList是基于链表实现，无法随机快速定位到一个元素的位置。
         * 必须从头还是进行查找，LinkedList底层虽然对查找做了优化，其使用二分查找的方式
         * 根据指定的索引，判断是从头开始找还是从尾部开始找，但是这样的执行效率依旧很慢。
         *
         * size>>1 > index ? last : start
         *
         */
        iteratorListOptimized(new LinkedList<String>());


    }

    private static void iteratorList(List<String> list) {
        for(int i = 0;i < COUNTER;i++) {
            list.add(String.valueOf(i));
        }
        long start = System.currentTimeMillis();
        for(int i = 0 ;i < list.size();i++){
            String str = list.get(i);
            System.out.println(str);
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000);
    }


    private static void iteratorListOptimized(List<String> list) {
        for(int i = 0;i < COUNTER;i++) {
            list.add(String.valueOf(i));
        }
        long start = System.currentTimeMillis();
        if(list instanceof RandomAccess) {
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i);
                System.out.println(str);
            }
        }else{
            for(Iterator<String> iterator = list.iterator();iterator.hasNext();){
                System.out.println(iterator.next());
            }
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start)/1000);
    }



}
