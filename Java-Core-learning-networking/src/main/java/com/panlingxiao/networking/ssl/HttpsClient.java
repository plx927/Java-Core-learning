package com.panlingxiao.networking.ssl;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by panlingxiao on 2016/7/29.
 * 使用SSLSocket发送Http请求
 * 参考：https://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/samples/sockets/client/SSLSocketClient.java
 *
 * This example demostrates how to use a SSLSocket as client to
 * send a HTTP request and get response from an HTTPS server.
 * It assumes that the client is not behind a firewall
 */
public class HttpsClient {
    static  SSLSocketFactory sslSocketFactory;
    static SSLSocket sslSocket;
    public static void main(String[] args) throws  Exception{
         sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
         sslSocket = (SSLSocket) sslSocketFactory.createSocket("www.verisign.com", 443);

        //客户端获取其所支持的加密套件
        String[] cipherSuites = sslSocket.getSupportedCipherSuites();
        sslSocket.setEnabledCipherSuites(cipherSuites);

        //构造HTTP请求头
        StringBuffer httpRequest = new StringBuffer("GET / HTTP/1.1\n");
        httpRequest.append("Host: www.verisign.com\n");
        httpRequest.append("Connection: keep-alive\n");
        httpRequest.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n");
        httpRequest.append("Upgrade-Insecure-Requests: 1\n");
        httpRequest.append("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36\n");
        httpRequest.append("\r\n");
        System.out.println("Http Request : \r\n"+httpRequest);
        OutputStream outputStream = sslSocket.getOutputStream();
        outputStream.write(httpRequest.toString().getBytes());
        outputStream.flush();

        InputStream inputStream = sslSocket.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = 0;
        byte[] data = new byte[1024];
        while(-1 != (len = inputStream.read(data))){
            bos.write(data,0,len);
        }
        System.out.println("reponse is :");
        System.out.println(new String(bos.toByteArray()));

        inputStream.close();
        outputStream.close();
        sslSocket.close();
    }
}
