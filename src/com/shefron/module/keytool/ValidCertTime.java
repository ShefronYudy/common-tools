package com.shefron.module.keytool;

import java.io.FileInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
/**
 * 校验证书有效期
 * @author Administrator
 *
 */
public class ValidCertTime {
	public static void main(String args[]) throws Exception {
		// 获取X509Certificate的对象
		CertificateFactory of = CertificateFactory.getInstance("x.509");
		FileInputStream in = new FileInputStream("e:\\temp\\yushengqiang.cer");
		Certificate ceof = of.generateCertificate(in);
		System.out.println(ceof.getPublicKey().getEncoded() + "\t" + ceof.getEncoded());
		in.close();
		X509Certificate t = (X509Certificate) ceof;

		// 获取日期
		Calendar cld = Calendar.getInstance();
		int year = Integer.parseInt("2016");
		int month = Integer.parseInt("9");
		int day = Integer.parseInt("24");
		cld.set(year, month, day);
		Date d = cld.getTime();
		System.out.println(d);
		try {
			// 检验证书
			t.checkValidity(d);

			System.out.print("OK");
		}

		// 处理CertificateExpiredException异常
		catch (CertificateExpiredException e) {
			System.out.println("expired");
			System.out.println(e.getMessage());
		}
		// 处理CertificateNorYetValidException异常
		catch (CertificateNotYetValidException e) {
			System.out.println("Too early");
			System.out.println(e.getMessage());
		}
	}
}