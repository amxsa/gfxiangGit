/************************************************
 MD5 算法的Java Bean
 @author:Topcat Tuppin
 Last Modified:10,Mar,2001
 *************************************************/
package cn.cellcom.common.encrypt;

/*******************************************************************************
 * md5 类实现了RSA Data Security, Inc.在提交给IETF 的RFC1321中的MD5 message-digest 算法。
 ******************************************************************************/

public class MD5 {
	private static char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
	'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	public static String getMD5(byte[] source) {
		String s = null;

		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	public static void main(String[] args){
		System.out.println(MD5.getMD5("0000011409".getBytes()));
		System.out.println(MD5.getMD5("O10h0000011409".getBytes()));
		System.out.println(MD5.getMD5("测试订单".getBytes()));
	}

}
