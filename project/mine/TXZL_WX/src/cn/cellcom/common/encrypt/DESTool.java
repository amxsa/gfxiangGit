package cn.cellcom.common.encrypt;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class DESTool {

	private static byte[] iv = "cellc0m!".getBytes();
	
	/**
	 *  DES加密 
	 * @param encryptString 加密的字符
	 * @param encryptKey  秘钥，必须是8位
	 * @return
	 * @throws Exception
	 */
	public static String encryptDES(String encryptString, String encryptKey)
			throws Exception {
		// IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
		
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
		return Base64.encode(encryptedData);
	}

	/**
	 * DES解密
	 * @param decryptString 解密的字符
	 * @param decryptKey 秘钥，必须是8位
	 * @return
	 * @throws Exception
	 */
	public static  String decryptDES(String decryptString, String decryptKey)
			throws Exception {
		byte[] byteMi = new Base64().decode(decryptString);
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		// IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte decryptedData[] = cipher.doFinal(byteMi);

		return new String(decryptedData);
	}

	/**
	 * 根据参数生成KEY
	 * 
	 * @param strKey
	 */
	private static Key getKey(String strKey) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			_generator.init(new SecureRandom(strKey.getBytes()));
			return _generator.generateKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 加密String明文输入,String密文输出
	 * 
	 * @param strMing
	 * @return
	 * @throws MyException
	 */
	public static String encode(String strMing, Key key) throws RuntimeException {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = strMing.getBytes("UTF8");
			byteMi = encode(byteMing, key);
			strMi = base64en.encode(byteMi);
		} catch (Exception e) {
			throw new RuntimeException("加密出现异常，请确认密钥正常", e);
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	private static String decode(byte[] bs, Key key) throws RuntimeException {
		return decode(new String(bs), key);
	}

	/**
	 * 解密 以String密文输入,String明文输出
	 * 
	 * @param strMi
	 * @return
	 * @throws RuntimeException
	 */
	public static String decode(String strMi, Key key) throws RuntimeException {
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = base64De.decodeBuffer(strMi);
			byteMing = deCode(byteMi, key);
			strMing = new String(byteMing, "UTF8");
		} catch (Exception e) {
			throw new RuntimeException("解密出现异常，请确认密钥正常", e);
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 * 
	 * @param byteS
	 * @return
	 * @throws RuntimeException
	 */
	private static byte[] encode(byte[] byteS, Key key) throws RuntimeException {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new RuntimeException("加密出现异常，请确认密钥正常", e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 * 
	 * @param byteD
	 * @return
	 * @throws RuntimeException
	 */
	private static byte[] deCode(byte[] byteD, Key key) throws RuntimeException {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException("解密出现异常，请确认密钥正常", e);
		} finally {
			cipher = null;
		}
		return byteFina;

	}
	
	public static void main(String[] args) throws Exception {

		// DES 加密文件
		// des.encryptFile("G:/test.doc", "G:/ 加密 test.doc");
		// DES 解密文件
		// des.decryptFile("G:/ 加密 test.doc", "G:/ 解密 test.doc");
		 String linkmans =
		 "[{\"postcode\":\"510000\",\"address\":\"广州市天河区五山\",\"regNo\":\"18925008300\",\"email\":\"abvaf@163.com\",\"QQ\":\"30389345\",\"name\":\"test\",\"number\":\"18900000000\",\"otherNumbers\":\"18925008304,18925008305,18925008306,18925008307\"},{\"postcode\":\"510000\",\"address\":\"广州市天河区上社\",\"regNo\":\"18925008300\",\"email\":\"lhange@163.com\",\"QQ\":\"30389345\",\"name\":\"张三\",\"number\":\"13600000001\"}]";
		//String linkmans = "E:3a:2fweb:2f:u6570:u636e:u7edf:u8ba1:2f:u5730:u5e02:u7ba1:u7406:u5458";
		// DES 加密字符串
		String str2 = DESTool.encode(linkmans, getKey("cellcom"));
		// DES 解密字符串
		String deStr = DESTool.decode(str2, getKey("cellcom"));
		System.out.println(" 加密前： " + linkmans);
		System.out.println(" 加密后： " + str2.length());
		System.out.println(" 解密后： " + deStr);
		String str3 =encryptDES(linkmans, "cellc0m!");
		System.out.println(" 加密后： " + str3.length());
		String str4= decryptDES(str3, "cellc0m!");
		System.out.println(" 解密后： " + str4);
	}

}
