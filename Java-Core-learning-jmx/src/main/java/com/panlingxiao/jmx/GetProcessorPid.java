package com.panlingxiao.jmx;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.concurrent.TimeUnit;

/**
 * @Author: panlingxiao
 * @Date: 2016/12/13 0013
 * @Description: 获取一个Java进行的Pid
 * 参考RocketMQ中的UtilAll中的getPid方法。
 */
public class GetProcessorPid {

    public static void main(String[] args) throws Exception{
        int pid = getPid();
        System.out.println(pid);
        TimeUnit.SECONDS.sleep(30);
    }

    public static int getPid() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName(); // format: "pid@hostname"
        try {
            System.out.println(name);
            return Integer.parseInt(name.substring(0, name.indexOf('@')));
        } catch (Exception e) {
            return -1;
        }
    }
}
