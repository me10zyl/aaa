package net.xicp.zyl_me.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class EncyptUtil {
	public static String encyptMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		byte[] btInput = str.getBytes();
		// 获得MD5摘要算法的 MessageDigest 对象
		MessageDigest mdInst = MessageDigest.getInstance("MD5");
		// 使用指定的字节更新摘要
		mdInst.update(btInput);
		// 获得密文
		byte[] md = mdInst.digest();
		// 把密文转换成十六进制的字符串形式
		int j = md.length;
		char strs[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			strs[k++] = hexDigits[byte0 >>> 4 & 0xf];
			strs[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(strs);
	}

	public static String encyptPassword(String userID, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		String encyptPassword = null;
		String userIDMD5 = encyptMD5(userID);
		String key = userIDMD5.substring(0, 8);
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
		AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(key.getBytes());
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(1, secretKey, algorithmParameterSpec);
		byte[] bytes = cipher.doFinal(password.getBytes("UTF-8"));
		encyptPassword = BASE64.encode(bytes);
		return encyptPassword;
	}

//	public static String decyptPassword(String userID, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
//		String decyptPassword = null;
//		String userIDMD5 = encyptMD5(userID);
//		String key = userIDMD5.substring(0, 8);
//		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
//		AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(key.getBytes());
//		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
//		SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
//		Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//		deCipher.init(2, secretKey, algorithmParameterSpec);
//		byte[] pasByte = deCipher.doFinal(BASE64.decode(password.getBytes()).getBytes());
//		return new String(pasByte);
//	}
	
	public static String encyptOrDecyptPassword(String password)
	{
		char[] charArray = password.toCharArray();
		for(int i =0 ;i < charArray.length;i ++)
		{
			charArray[i] = (char) (charArray[i] ^ 4);
		}
		return new String(charArray);
	}
}
