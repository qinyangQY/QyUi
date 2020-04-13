package com.ccys.qyuilib.verify;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author qyl
 * MD5加密工具
 */
public class MD5Utils {

	/**
	 * MD5加密
	 * @param sourceStr
	 * @return
	 */
	public static String MD5(String sourceStr) {
		try {
			// 获得MD5摘要算法的 MessageDigest对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(sourceStr.getBytes());
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int tmp = md[i];
				if (tmp < 0)
					tmp += 256;
				if (tmp < 16)
					buf.append("0");
				buf.append(Integer.toHexString(tmp));
			}
			// return buf.toString().substring(8, 24);// 16位加密
			return buf.toString();// 32位加密
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 将 字符 进行MD5加密
	 * 
	 * @param str str
	 * @return byte[]
	 */
	public static byte[] Encrypt5ToByte(String str) {
		byte[] digest = null;
		try {
			// 将字符转换
			MessageDigest digester = MessageDigest.getInstance("MD5");
			byte[] bytes = str.getBytes("UTF-8");
			digester.update(bytes);
			// 得到的是10进制的byte数组
			digest = digester.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5出错", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("String 转换成 Byte 编码出错", e);
		}

		return digest;
	}

	public static byte[] Encrypt5ToByte(byte[] str) {
		byte[] digest = null;
		try {
			// 将字符转换
			MessageDigest digester = MessageDigest.getInstance("MD5");
			digester.update(str);
			// 得到的是10进制的byte数组
			digest = digester.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5出错", e);
		}
		return digest;
	}
	
	
}
