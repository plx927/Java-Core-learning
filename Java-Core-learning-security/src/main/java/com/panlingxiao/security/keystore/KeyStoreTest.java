package com.panlingxiao.security.keystore;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

/**
 * Created by panlingxiao on 2016/7/26.
 * 通过解析KeyStore获取到证书的信息,通过SUN公司所使用KeyTool生成的KeyStore(密钥库文件)包含了证书的信息，
 */
public class KeyStoreTest {
    public static void main(String[] args) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableEntryException {
        String keystoreFilename = "broker.ks";

        char[] password = "123456".toCharArray();
        String alias = "broker";

        InputStream fIn = KeyStoreTest.class.getClassLoader().getResourceAsStream(keystoreFilename);

        //JKS是SUN公司所支持的KeyStore类型
        KeyStore keystore = KeyStore.getInstance("JKS");
        keystore.load(fIn, password);

        //通过密钥库获取解析出证书的信息
        Certificate cert = keystore.getCertificate(alias);
        System.out.println(cert);

        System.out.println("--------------------------------");

        //通过keyStore获取到私钥信息
        Key key = keystore.getKey(alias, password);
        //获取加密算法
        System.out.println(key.getAlgorithm());
        System.out.println(key.getFormat());
        System.out.println(key.getEncoded().length);


        System.out.println("--------------------------------");
        KeyStore.Entry entry = keystore.getEntry(alias, new KeyStore.PasswordProtection(password));
        if(entry instanceof KeyStore.PrivateKeyEntry){
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) entry;
            PrivateKey privateKey = privateKeyEntry.getPrivateKey();
            System.out.println(privateKey.getAlgorithm());
            System.out.println(privateKey.getFormat());
            System.out.println(privateKey.getEncoded().length);
        }

    }
}
