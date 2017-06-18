package cn.cellcom.szba.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Crypter {
	private static Logger logger = LoggerFactory.getLogger(MD5Crypter.class);

	public static String md5Encrypt(String originalString) {

		StringBuffer sb = new StringBuffer();

		if (StringUtils.isNotBlank(originalString)) {
			try {
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				md5.update(originalString.getBytes());
				byte[] b = md5.digest();
				for (int i = 0; i < b.length; i++) {
					int v = b[i];
					v = v < 0 ? 256 + v : v;
					String cc = Integer.toHexString(v);
					if (cc.length() == 1)
						sb.append('0');
					sb.append(cc);
				}
			} catch (NoSuchAlgorithmException e) {
				logger.error("Fail in gettting instance of string:"
						+ originalString, e);
				return null;
			}
		}
		return sb.toString();
	}

}
