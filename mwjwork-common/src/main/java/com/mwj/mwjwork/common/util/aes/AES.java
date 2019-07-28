package com.mwj.mwjwork.common.util.aes;

import com.mwj.mwjwork.common.constant.Const;
import com.mwj.mwjwork.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Meng Wei Jin
 */
@Slf4j
public final class AES {

	public static final String AES_STRING = "AES";

	private static final int DEFAULT_BIT = 128;

    private AES() {
    }

	private static final byte[] DEFAULT_SALT_BYTE = {55, 123, 112, 21, -106, -117, 123, -107, 57, 44, -48, 100, -82, -117, 65, -74};

	/**
	 * 加密
	 * @param password
	 * @return
	 */
	public static String encrypt(final String password) {
		return encrypt(password, null);
	}

	/**
	 * 加密
	 * @param password 待加密的明文密码
	 * @param salt 盐
	 * @return
	 */
	public static String encrypt(String password, String salt) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(AES.getSaltByte(salt), AES_STRING);
			// 创建密码器
			Cipher cipher = Cipher.getInstance(AES_STRING);
			// 初始化
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);

			byte[] byteContent = password.getBytes(Const.UTF_8);
			// 加密
			byte[] result = cipher.doFinal(byteContent);

			return Hex.encodeHexString(result);
		} catch (Exception e){
			log.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
	}

	/**
	 * 解密
	 * @param password
	 * @return
	 */
	public static String decrypt(String password) {
		return decrypt(password, null);
	}

	/**
	 * 解密
	 * @param password 待解密的密文密码
	 * @return
	 */
	public static String decrypt(String password, String salt) {
		try{
			byte[] decryptFrom = Hex.decodeHex(password);
			SecretKeySpec keySpec = new SecretKeySpec(AES.getSaltByte(salt), AES_STRING);
			// 创建密码器
			Cipher cipher = Cipher.getInstance(AES_STRING);
			// 初始化
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			// 解密
			byte[] result = cipher.doFinal(decryptFrom);
			return new String(result, Const.UTF_8);
		} catch (Exception e){
			log.error(e.getMessage(), e);
			throw new SystemException(e.getMessage(), e);
		}
	}

	private static byte[] getSaltByte(String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] saltByte;
		if(salt == null || Const.EMPTY.equals(salt)){
			saltByte = DEFAULT_SALT_BYTE;
		} else {
			saltByte = calcKeyByte(salt, DEFAULT_BIT);
		}
		return  saltByte;
	}

	/**
	 * 根据加密参数和加密位数（128或256位加密）计算对应的字节数组类型的加密参数
	 * @param salt 加密盐
	 * @param bit 要加密的位数
	 *            	DES算法必须是56位
	 *				DESede算法可以是112位或168位
	 *				AES算法可以是128、192、256位
	 * @return
	 */
	private static byte[] calcKeyByte(String salt, int bit) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_STRING);
		keyGenerator.init(bit, new SecureRandom(salt.getBytes(Const.UTF_8)));
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}

}
