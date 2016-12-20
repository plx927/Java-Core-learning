package com.panlingxiao.security.rsa;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: panlingxiao
 * @Date: 2016/12/20 0020
 * @Description:
 * KeyFactory通过算法获取实例，然后根据密钥规范生成公私钥
 */
public class RSATest2 {

    public static final String KEY_ALGORITHM = "RSA";

    /**
     * RSA/NONE/PKCS1Padding Java并没有提供,需要使用第三方的框架作为SPI。
     */
    //public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String CIPHER_ALGORITHM = "RSA/NONE/PKCS1Padding";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";


    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";

    /**
     * RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024
     */
    public static final int KEY_SIZE = 2048;

    public static final String PLAIN_TEXT = "MANUTD is the greatest club in the world";

    /**
     * 生成密钥对。注意这里是生成密钥对KeyPair，再由密钥对获取公私钥
     * 这个步骤使用openssl也可以完成。
     *
     * @return
     */
    public static Map<String, byte[]> generateKeyBytes() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM,new BouncyCastleProvider());
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, byte[]> keyMap = new HashMap<>();
            keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
            keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data 已加密数据
     * @param privateKey 私钥(BASE64编码)
     *
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return new String(Base64.encode(signature.sign()));
    }


    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     *
     * @return
     * @throws Exception
     *
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = Base64.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.decode(sign));
    }



    /**
     * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     *
     * @param keyBytes
     * @return
     */
    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
     *
     * @param keyBytes
     * @return
     */
    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = factory.generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密，三步走。
     *
     * @param key
     * @param plainText
     * @return
     */
    public static byte[] RSAEncode(PublicKey key, byte[] plainText) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密，三步走。
     *
     * @param key
     * @param encodedText
     * @return
     */
    public static String RSADecode(PrivateKey key, byte[] encodedText) {

        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM,new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(encodedText));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }


    public static void main(String[] args) throws IOException {
        Map<String, byte[]> keyMap = generateKeyBytes();
        // 加密
        PublicKey publicKey = restorePublicKey(keyMap.get(PUBLIC_KEY));

        byte[] encodedText = RSAEncode(publicKey, PLAIN_TEXT.getBytes());
        String str = new BASE64Encoder().encode(encodedText);
        System.out.println("RSA encoded: " + str);

        // 解密
        PrivateKey privateKey = restorePrivateKey(keyMap.get(PRIVATE_KEY));
        System.out.println("RSA decoded: " + RSADecode(privateKey, new BASE64Decoder().decodeBuffer(str)));





    }


}
