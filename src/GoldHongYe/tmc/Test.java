package GoldHongYe.tmc;

import java.io.InputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Test {

	public static void main(String[] args) throws Exception {
		String s = "";
		byte abyte0[] = initSecretKey(s);
		Key key = toKey(abyte0);
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(1, key);
		//System.out.println(cipher.g);
		InputStream inputstream = null;
		CipherInputStream cipherinputstream = new CipherInputStream(inputstream, cipher);
	}
	public static byte[] initSecretKey(String s)
	{
		KeyGenerator keygenerator = null;
		try
		{
			keygenerator = KeyGenerator.getInstance("AES");
			SecureRandom securerandom = SecureRandom.getInstance("SHA1PRNG");
			securerandom.setSeed(s.getBytes());
			keygenerator.init(128, securerandom);
		}
		catch (NoSuchAlgorithmException nosuchalgorithmexception)
		{
			nosuchalgorithmexception.printStackTrace();
			return new byte[0];
		}
		SecretKey secretkey = keygenerator.generateKey();
		return secretkey.getEncoded();
	}
	
	private static Key toKey(byte abyte0[])	{
		return new SecretKeySpec(abyte0, "AES");
	}
}
