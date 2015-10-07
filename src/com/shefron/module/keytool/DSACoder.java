package com.shefron.module.keytool;

import sun.security.provider.*;

import java.security.*;
import java.security.SecureRandom;
import java.security.interfaces.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * DSA
   DSA-Digital Signature Algorithm 是Schnorr和ElGamal签名算法的变种，
   被美国NIST作为DSS(DigitalSignature Standard)。简单的说，
   这是一种更高级的验证方式，用作数字签名。不单单只有公钥、私钥，
   还有数字签名。私钥加密生成数字签名，公钥验证数据及签 名。
   如果数据和签名不匹配则认为验证失败！
   数字签名的作用就是校验数据在传输过程中不被修改。
   数字签名，是单向加密的升级！
 */
public abstract class DSACoder extends Coder {
    private static final String ALGORITHM = "DSA";
    /**
     * 默认密钥字节数
     *
     * <pre>
     * DSA
     * Default Keysize 1024
     * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive).
     * </pre>
     */
    private static final int KEY_SIZE = 1024;
    /**
     * 默认种子
     */
    private static final String DEFAULT_SEED = "0f22507a10bbddd07d8a3082122966e3";

    private static final String PUBLIC_KEY = "DSAPublicKey";
    private static final String PRIVATE_KEY = "DSAPrivateKey";

    /**
     * 用私钥对信息生成数字签名
     * @param data 加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data,String privateKey) throws Exception{
        //解密由base64编码的私钥
        byte[] keyBytes = decodeByBase64(privateKey);
        //构造私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        //KeyFactory
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        //取得私钥对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
        signature.initSign(priKey);
        signature.update(data);

        return encodeByBase64(signature.sign());
    }

    /**
     * 校验数字签名
     * @param data 加密数据
     * @param publicKey 公钥
     * @param sign 数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data,String publicKey,String sign) throws Exception{
        //解码公钥
        byte[] keyBytes = decodeByBase64(publicKey);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

        PublicKey pubKey = keyFactory.generatePublic(x509EncodedKeySpec);

        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
        signature.initVerify(pubKey);
        signature.update(data);

        return signature.verify(decodeByBase64(sign));
    }

    /**
     * 生成密钥
     * @param seed 种子
     * @return 密钥对象
     * @throws Exception
     */
    public static Map<String,Object> initKey(String seed) throws Exception{
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        //初始化随机产生器
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed.getBytes());
        keyGen.initialize(KEY_SIZE,secureRandom);

        KeyPair keyPair = keyGen.genKeyPair();

        DSAPublicKey publicKey = (DSAPublicKey) keyPair.getPublic();
        DSAPrivateKey privateKey = (DSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put(PUBLIC_KEY,publicKey);
        map.put(PRIVATE_KEY,privateKey);

        return map;
    }

    /**
     * 默认生成密钥
     * @return 密钥对象
     * @throws Exception
     */
    public static Map<String ,Object> initKey() throws Exception{
        return initKey(DEFAULT_SEED);
    }

    /**
     * 取得私钥
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String,Object> keyMap) throws Exception{
        Key key = (Key)keyMap.get(PRIVATE_KEY);
        return encodeByBase64(key.getEncoded());
    }

    /**
     * 取得公钥
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String,Object> keyMap) throws Exception{
        Key key = (Key)keyMap.get(PRIVATE_KEY);
        return encodeByBase64(key.getEncoded());
    }
}
