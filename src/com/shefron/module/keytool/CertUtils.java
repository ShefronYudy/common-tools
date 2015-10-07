package com.shefron.module.keytool;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 数字证书与 密钥库使用
 */
public class CertUtils {

	/**
	 * 由密钥库获得私钥
	 *
	 * @param keyStorePath
	 *            密钥库路径
	 * @param passwd
	 * @param alias
	 * @return 16进制封装的私钥
	 * @throws KeyStoreException
	 * @throws IOException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws UnrecoverableKeyException
	 */
	public static PrivateKey getPrikeyByKeyStore(String keyStorePath,
												 String passwd, String alias) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableKeyException {
		// 实例化密钥库
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());//JKS

		FileInputStream is = new FileInputStream(keyStorePath);
		// 加载密钥库
		ks.load(is, passwd.toCharArray());
		is.close();
		PrivateKey privateKey = (PrivateKey) ks.getKey(alias, passwd.toCharArray());

		// 封装成16进制
		// return Hex.encodeHexString(privateKey.getEncoded());
		return privateKey;
	}

	/**
	 * 由证书获得公钥
	 *
	 * @param cerPath
	 *            证书路径
	 * @return 16进制封装的公钥
	 * @throws CertificateException
	 * @throws IOException
	 */

	public static PublicKey getPubKeyByCer(String cerPath)
			throws CertificateException, IOException {
		// 实例化证书工厂
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		FileInputStream in = new FileInputStream(cerPath);
		Certificate certificate = certificateFactory.generateCertificate(in);
		in.close();
		PublicKey publicKey = certificate.getPublicKey();
		// 封装成16进制
		// return Hex.encodeHexString(publicKey.getEncoded());
		return publicKey;
	}

	public static String getPubKeyStr(String cerPath) throws CertificateException,IOException{
		PublicKey publicKey = getPubKeyByCer(cerPath);
		return Coder.encodeByBase64(publicKey.getEncoded());
	}

	public static PublicKey getPublicKeyByStr(String pubKeyStr) throws IOException,NoSuchAlgorithmException,InvalidKeySpecException {
		byte[] bytes = Coder.decodeByBase64(pubKeyStr);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		return factory.generatePublic(keySpec);
	}

	public static PrivateKey getPrivateKeyByStr(String priKeyStr) throws Exception{
		byte[] bytes = Coder.decodeByBase64(priKeyStr);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");

		return factory.generatePrivate(keySpec);
	}

	/**
	 * 私钥签名
	 *
	 * @param data
	 * @param keyStorePath
	 * @param passwd
	 * @param alias
	 * @param cerPath
	 * @return
	 * @throws CertificateException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws SignatureException
	 * @throws InvalidKeyException
	 */
	public static byte[] sign(byte[] data, String keyStorePath, String passwd,
							  String alias, String cerPath) throws CertificateException,
			IOException, NoSuchAlgorithmException, UnrecoverableKeyException,
			KeyStoreException, SignatureException, InvalidKeyException {
		// 获得证书
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		FileInputStream in = new FileInputStream(cerPath);
		Certificate certificate = certificateFactory.generateCertificate(in);
		in.close();
		X509Certificate x509certificate = (X509Certificate) certificate;

		System.out.println(x509certificate.getSigAlgName());

		// 构建签名
		Signature signature = Signature.getInstance(x509certificate.getSigAlgName());
		PrivateKey privateKey = getPrikeyByKeyStore(keyStorePath, passwd, alias);
		// 初始化签名
		signature.initSign(privateKey);
		signature.update(data);
		return signature.sign();
	}

	/**
	 * 验证签名
	 *
	 * @param data
	 * @param sign
	 * @param cerPath
	 * @return
	 * @throws CertificateException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static boolean verify(byte[] data, byte[] sign, String cerPath)
			throws CertificateException, IOException, NoSuchAlgorithmException,
			InvalidKeyException, SignatureException {
		CertificateFactory certificateFactory = CertificateFactory
				.getInstance("X.509");
		FileInputStream in = new FileInputStream(cerPath);
		Certificate certificate = certificateFactory.generateCertificate(in);
		in.close();
		X509Certificate x509certificate = (X509Certificate) certificate;
		// 构建签名
		Signature signature = Signature.getInstance(x509certificate.getSigAlgName());
		signature.initVerify(x509certificate);
		signature.update(data);
		return signature.verify(sign);
	}

	public static void main(String[] args) throws Exception {
		String keyStorePath = "e:/temp/yushengqiang.keystore";
		String cerPath = "e:/temp/yushengqiang.cer";
		String passwd = "shefron4ym";
		String alias = "yushengqiang";

		PrivateKey prikey = getPrikeyByKeyStore(keyStorePath, passwd, alias);
		System.err.println("私钥：\n" + prikey);
		PrivateKey priKey2 = getPrivateKeyByStr(Coder.encodeByBase64(prikey.getEncoded()));
		System.err.println("私钥：\n"+ priKey2);

		PublicKey pubkey = getPubKeyByCer(cerPath);
		System.err.println("公钥：\n" + pubkey);
		PublicKey pubKey2 = getPublicKeyByStr(Coder.encodeByBase64(pubkey.getEncoded()));
		System.err.println("公钥：\n"+pubKey2);

		byte[] sign = sign("待签名的".getBytes(), keyStorePath, passwd, alias,
				cerPath);

		// 传来的数据最好加密
		System.out.println(verify("待签名的".getBytes(), sign, cerPath));
	}

}