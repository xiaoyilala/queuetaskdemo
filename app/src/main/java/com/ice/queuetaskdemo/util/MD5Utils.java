package com.ice.queuetaskdemo.util;


import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	private final static String HEX = "0123456789abcdef";

	public static String md5File(File file) {
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			byte[] buffer = new byte[1024 * 8];
			int length = 0;
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			while ((length = in.read(buffer)) != -1) {
				md5.update(buffer, 0, length);
			}
			value = toHex(md5.digest());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	private static byte[] md5Str(String key) {
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(key.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new byte[16];
		}

		return md5.digest();
	}

	public static String md5String(String key) {
		return new String(toHex(md5Str(key)));
	}

	public static String toHex(String txt) {
		if (TextUtils.isEmpty(txt)) {
			return null;
		} else {
			return toHex(txt.getBytes());
		}
	}

	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}

		return result.toString();
	}

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}
}
