package test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.zip.CRC32;

import encode.BASE64Encoder;

public class Test {

	public static void main(String[] args) throws Exception {
		BitSet users = BitSet.valueOf(new byte[] { 1, 1, 0, 1, 1 });
		System.out.println(users.cardinality());
		String ip = "4294967295";

		CRC32 crc32 = new CRC32();
		crc32.update(1);
		System.out.println(crc32.getValue());

		 System.out.println(0xffffffffL);
		 
		 System.out.println(((long)1<<32) - 1);
		 
		 System.out.println(0xfffff);
		 System.out.println((1<<20) - 1);

		System.out.println(generateGUID(ip, ip));
		
		
//		CRC20 crc20 = new CRC20();
//		crc20.update(1);
//		System.out.println(crc20.getValue());
		
	}

	private static CRC32 localCRC32Generator = new CRC32();

	public static String generateGUID(String anApplication, String aVersion) {
		localCRC32Generator.update(anApplication.getBytes());
		Long tempApplicationChecksum = localCRC32Generator.getValue();
		localCRC32Generator.update(aVersion.getBytes());
		Long tempVersionChecksum = localCRC32Generator.getValue();
		localCRC32Generator.reset();

		return new UUID(tempApplicationChecksum, tempVersionChecksum).toString();
	}

	public static String md5(String messages) {
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] md5 = md.digest();

			// 转化为明文
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(md5);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
