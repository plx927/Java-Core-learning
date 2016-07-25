package com.panlingxiao.security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 * Created by panlingxiao on 2016/7/26.
 * 使用Java生成证书
 * 参考:
 * http://docs.oracle.com/javase/6/docs/technotes/guides/security/StandardNames.html#CertificateFactory
 * http://www.java2s.com/Tutorial/Java/0490__Security/UsingCertificatesinJava.htm
 *
 * 证书的生成方式：
 * 1.证书通过keytool先生成密钥对
 * keytool -genkey -alias broker -keyalg RSA -keystore broker.ks
 * 2.然后导出证书
 * keytool -export -alias broker -keystore broker.ks -file broker_cert
 *
 */
public class CertificateTest {

    public static void main(String[] args) throws CertificateException, IOException {
        //返回特定的证书工厂，该证书工厂实现了特定类型的证书
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        InputStream inputStream = CertificateTest.class.getClassLoader().getResourceAsStream("test_cert");
        //读取证书文件生成证书
        Certificate certificate = certificateFactory.generateCertificate(inputStream);
        inputStream.close();
        System.out.println(certificate);

    }
}
