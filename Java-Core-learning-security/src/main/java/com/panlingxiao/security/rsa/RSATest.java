package com.panlingxiao.security.rsa;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * @Author: panlingxiao
 * @Date: 2016/12/20 0020
 * @Description: RSA加密与解密
 * 先使用Openssl生成公私钥，RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024
 * 参考:https://doc.open.alipay.com/doc2/detail?treeId=44&articleId=103242&docType=1
 */
public class RSATest {

    public static void main(String[] args) throws Exception {
        //加载公钥
        InputStream publicKeyResource = RSATest.class.getClassLoader().getResourceAsStream("rsa/rsa_public_key.pem");
        RSAPublicKey rsaPublicKey = loadPublicKey(publicKeyResource);

        //加载私钥
        InputStream privateKeyResource = RSATest.class.getClassLoader().getResourceAsStream("rsa/rsa_private_key_pkcs8.pem");
        RSAPrivateKey privateKey = loadPrivateKey(privateKeyResource);

        //原始数据
        String content = "test";

        //加密数据
        byte[] bytes = encryptByPublicKey(content.getBytes(), rsaPublicKey);
        //对加密的数据进行Base64编码
        String cipherData = new String(Base64.encode(bytes));

        //将数据进行Base64解码
        byte[] data = decryptByPrivateKey(Base64.decode(cipherData), privateKey);
        String decipherData = new String(data);
        System.out.println("解密后的数据:"+decipherData);

        byte[] encode = Base64.encode(content.getBytes());
        System.out.println("Base64数据编码:"+Arrays.toString(encode)+",");


    }

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;


    //RSA/ECB/PKCS1Padding
    static String CIPHER_ALGORITHM = "RSA/NONE/PKCS1Padding";


    /**
     * 私钥解密
     * @param content     密文
     * @param private_key 私钥
     * @return 解密后的字符串
     */
    public static String decryptByPrivateKey(String content, String private_key) throws Exception {
        PrivateKey privateKey = loadPrivateKey(private_key);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        InputStream ins = new ByteArrayInputStream(Base64.decode(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        // rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;
        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;
            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }
            writer.write(cipher.doFinal(block));
        }
        return new String(writer.toByteArray(), "UTF-8");
    }

    /**
     * 公钥加密
     *
     * @param source    源数据
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String source, String publicKey) throws Exception {
        byte[] cipher = encryptByPublicKey(source.getBytes("UTF-8"), loadPublicKey(publicKey));//"UTF-8"
        return new String(Base64.encode(cipher));
    }




    /**
     * 私钥解密
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, new BouncyCastleProvider());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 使用RSA公钥对数据加密
     * 由OpenSSL根据私钥生成得到，PEM公钥格式(开头为-----BEGIN PUBLIC KEY-----)、长度为1024位，填充方式为PKCS#1，无加密。
     * 注: 如果待加密串长度大于117字节，需要分段加密(每117字节分为一段，加密后长为128字节)，再按顺序拼接成密串(长度为128的整数倍字节)。
     *
     * @param data      待加密的数据
     * @param publicKey RSA公钥
     * @return 加密后的数据
     * @throws Exception
     */
    static byte[] encryptByPublicKey(byte[] data, RSAPublicKey publicKey) throws Exception {
        // 对数据加密
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, new BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }


    //--------------------------------------------------加载过程--------------------------------------------------------------------------------------

    /**
     * 从字符串中加载公钥
     * @param publicKeyStr 公钥数据字符串
     * @return 公钥
     * @throws Exception 加载公钥时产生的异常
     */
    public static RSAPublicKey loadPublicKey(String publicKeyStr) {
        try {
            byte[] buffer = Base64.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("公钥非法");
        } catch (NullPointerException e) {
            throw new RuntimeException("公钥数据为空");
        }
    }


    /**
     * 根据私钥字符串生成RSAPrivateKey
     * @param privateKeyStr 私钥字符串
     * @return 私钥
     * @throws Exception
     */
    public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }


    /**
     * 从文件中输入流中加载公钥
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public static RSAPublicKey loadPublicKey(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                //读取的PEM格式的公私钥,格式以-----BEGIN PRIVATE KEY-----开头,所以要去除头和尾
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                }
            }
            return loadPublicKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }


    /**
     * 从文件中加载私钥
     *
     * @param in 私钥文件名
     * @throws Exception
     */
    public static RSAPrivateKey loadPrivateKey(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                //因为私钥
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                }
            }
            return loadPrivateKey(sb.toString());
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }


    /**
     * 从文件中加载私钥
     *
     * @param in 私钥文件名
     * @throws Exception
     */
    public static String loadPrivateKeyString(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                //因为私钥
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                }
            }
            return sb.toString();
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     * 从文件中输入流中加载公钥
     * @param in 公钥输入流
     * @throws Exception 加载公钥时产生的异常
     */
    public static String loadPublicKeyStr(InputStream in) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                //读取的PEM格式的公私钥,格式以-----BEGIN PRIVATE KEY-----开头,所以要去除头和尾
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                }
            }
            return sb.toString();
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }
}
