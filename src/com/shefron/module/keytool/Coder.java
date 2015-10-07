package com.shefron.module.keytool;

import java.io.IOException;
import java.security.MessageDigest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密工具类
 *
 * @author Administrator
 */
public abstract class Coder {
    private static BASE64Decoder decoder = new BASE64Decoder();
    private static BASE64Encoder encoder = new BASE64Encoder();

    /**
     * MAC算法可选以下多种算法
     * *
     * * <pre>
     *     * HmacMD5
     *     * HmacSHA1
     *     * HmacSHA256
     *     * HmacSHA384
     *     * HmacSHA512
     *     *
     * </pre>
     */
    public static final String KEY_MAC = "HmacMD5";

    /**
     * DES加密键值
     */
    private static String encryptKey = "Shefron@802!W&P$321";

    /**
     * base64编码
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] decodeByBase64(String data) throws IOException {
        return decoder.decodeBuffer(data);
    }

    /**
     * base64解码
     *
     * @param bytes
     * @return
     */
    public static String encodeByBase64(byte[] bytes) {
        return encoder.encode(bytes);
    }

    /**
     * 单向(不对称)加密算法MD5
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptByMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }

    /**
     * 单向（不对称）加密SHA
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptBySHA(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA");
        sha.update(data);
        return sha.digest();
    }

    /**
     * 初始化HMAC密钥
     * @return
     * @throws Exception
     */
    public static String initMacKey() throws Exception{
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return encodeByBase64(secretKey.getEncoded());
    }

    /**
     * HMAC加密
     * @param data
     * @param macKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByHMac(byte[] data, String macKey) throws Exception{
        SecretKey secretKey = new SecretKeySpec(decodeByBase64(macKey),KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    public static void main(String[] args) throws Exception {
        String msg = "加密";
        String data = Coder.encodeByBase64(msg.getBytes("UTF-8"));
        System.out.println(data);
        System.out.println(new String(Coder.decodeByBase64(data), "UTF-8"));
    }

}
