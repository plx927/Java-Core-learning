package com.panlingxiao.security.acl;

import java.io.FilePermission;
import java.security.AccessController;

/**
 * Created by panlingxiao on 2016/7/29.
 * 使用AccessController检查文件权限
 */
public class AccessControllerTest {

    public static void main(String[] args) {
        FilePermission permission = new FilePermission("d:/lecturer2.jpg","read");
        AccessController.checkPermission(permission);
    }
}
