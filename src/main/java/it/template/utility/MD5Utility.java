package it.template.utility;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.codec.Base64;


public class MD5Utility {
	
    private static final String ALGORITHM = "md5";
    private static final String DIGEST_STRING = "PROIND";
    private static final String CHARSET_UTF_8 = "utf-8";
    private static final String SECRET_KEY_ALGORITHM = "DESede";
    private static final String TRANSFORMATION_PADDING = "DESede/CBC/PKCS5Padding";
    
    private static final String FILE_CER = "";

    /* Encryption Method */
    public static String encrypt(String message) throws Exception
    {
        final MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        final byte[] digestOfPassword = md.digest(DIGEST_STRING.getBytes(CHARSET_UTF_8));
        final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        for (int j = 0, k = 16; j < 8;) {
                keyBytes[k++] = keyBytes[j++];
        }

        final SecretKey key = new SecretKeySpec(keyBytes, SECRET_KEY_ALGORITHM);
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        final Cipher cipher = Cipher.getInstance(TRANSFORMATION_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        final byte[] plainTextBytes = message.getBytes(CHARSET_UTF_8);
        final byte[] cipherText = cipher.doFinal(plainTextBytes);
        String encryptedValue = new String(Base64.encode(cipherText));
        return new String(encryptedValue);
    }
   
   /* Decryption Method */
    public static String decrypt(String message) throws Exception {
        final MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        final byte[] digestOfPassword = md.digest(DIGEST_STRING.getBytes(CHARSET_UTF_8));
        final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
        for (int j = 0, k = 16; j < 8;) {
                keyBytes[k++] = keyBytes[j++];
        }
        byte[] encryptedText = Base64.decode(message.getBytes());
        final SecretKey key = new SecretKeySpec(keyBytes, SECRET_KEY_ALGORITHM);
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        final Cipher decipher = Cipher.getInstance(TRANSFORMATION_PADDING);
        decipher.init(Cipher.DECRYPT_MODE, key, iv);
        final byte[] plainText = decipher.doFinal(encryptedText);

        return new String(plainText, CHARSET_UTF_8);
    }

    public static byte[] encryptWithFile(String message) throws Exception{
    	FileInputStream fis = new FileInputStream(FILE_CER);
    	BufferedInputStream bis = new BufferedInputStream(fis);
    	CertificateFactory cf = CertificateFactory.getInstance("X.509");
    	X509Certificate cert = (X509Certificate)cf.generateCertificate(bis);
    	Cipher cipher = Cipher.getInstance("RSA");
    	// Da ripetere per ogni codice fiscale
    	cipher.init(Cipher.ENCRYPT_MODE, cert);
    	cipher.update(message.getBytes());
    	return  cipher.doFinal();
    }
    
    public static String decryptWithFile(String message) throws Exception {
    	FileInputStream fis = new FileInputStream(FILE_CER);
    	BufferedInputStream bis = new BufferedInputStream(fis);
    	CertificateFactory cf = CertificateFactory.getInstance("X.509");
    	X509Certificate cert = (X509Certificate)cf.generateCertificate(bis);
    	Cipher cipher = Cipher.getInstance("RSA");
    	// Da ripetere per ogni codice fiscale
    	cipher.init(Cipher.DECRYPT_MODE, cert);
    	cipher.update(message.getBytes());
    	return  new String(cipher.doFinal());
    }
}
