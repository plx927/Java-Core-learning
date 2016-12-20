package com.panlingxiao.security.base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.Arrays;

/**
 * @Author: panlingxiao
 * @Date: 2016/12/20 0020
 * @Description:
 */
public class Base64Test {

    public static void main(String[] args) throws IOException {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        BASE64Decoder base64Decoder = new BASE64Decoder();

        String str = "测试";
        byte[] bytes1 = str.getBytes();
        System.out.println(Arrays.toString(bytes1));
        String encodeStr = base64Encoder.encode(bytes1);
        System.out.println("base64编码结果:"+encodeStr);

        byte[] bytes = base64Decoder.decodeBuffer(encodeStr);
        System.out.println(Arrays.toString(bytes));
        System.out.println(new String(bytes));
    }
}
