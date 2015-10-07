package com.shefron.module.codec;

import java.nio.ByteBuffer;
import java.util.Random;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SBase64 {

	private static byte[] key = new byte[] { 'a', 'b', 'c', 'd', 'e', 'f','1', '2', '3', '4', '5', '6','7', '8', '9',
            'g', 'h', 'i', 'j', 'k', 'l','m', 'n', 'o', 'p', 'q', 'r','s', 't', 'u', 'v', 'w', 'x','y', 'z', '@', '!', '$', '#',};

	/**
	 * ����
	 *
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String msg) throws Exception {
		byte[] originBytes = msg.getBytes("UTF-8");
		int length = originBytes.length;

		ByteBuffer bbuf = ByteBuffer.allocate(length * 2 + 8);

		Random random = new Random();
		for (int i = 0, len = key.length; i < length; i++) {
			bbuf.put(new byte[] { originBytes[i], key[random.nextInt(len)] });

		}

		bbuf.flip();

		byte[] newBytes = new byte[length * 2];
		bbuf.get(newBytes);

		byte[] bytes = reverse(newBytes);

		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(bytes);
	}

	/**
	 * ����
	 *
	 * @param encodeStr
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encodeStr) throws Exception {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decodeBytes = decoder.decodeBuffer(encodeStr);

		byte[] bytes = reverse(decodeBytes);

		byte[] readBytes = new byte[bytes.length / 2];
		for (int i = 0, j = 0; i < bytes.length; i++) {
			if (i % 2 == 0) {
				readBytes[j++] = bytes[i];
			}
		}

		return new String(readBytes, "UTF-8");

	}

    public static byte[] reverse(byte[] bytes){
        byte[] _bytes = new byte[bytes.length];
        for (int j = bytes.length - 1, i = 0; j >= 0; j--, i++) {
            _bytes[i] = bytes[j];
        }
        return _bytes;
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		String msg = "于胜强爱王一铭";
		String text = encrypt(msg);
		System.out.println(text);
		System.out.println(decrypt(text));

//        BASE64Decoder decoder = new BASE64Decoder();
//        byte[] decodeBytes = decoder.decodeBuffer("Mq15kyTpZoBwuHLkcYtpjjLnMbF5iHPnaLp0vGjlM5xqg2LodI4zumjk");
//
//        System.out.println(new String(decodeBytes,"UTF-8"));

	}

}
