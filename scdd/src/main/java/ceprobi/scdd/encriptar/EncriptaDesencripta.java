package ceprobi.scdd.encriptar;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class EncriptaDesencripta {

	private SecretKeySpec crearLlave(String clave) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] claveEncriptar = clave.getBytes("UTF-8");
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		claveEncriptar = sha.digest(claveEncriptar);
		claveEncriptar = Arrays.copyOf(claveEncriptar, 16);
		SecretKeySpec secretKey = new SecretKeySpec(claveEncriptar, "AES");
		return secretKey;
	}
	public String encripta(String dato, String claveSecreta) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec llaveSecreta = this.crearLlave(claveSecreta);
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");        
        cipher.init(Cipher.ENCRYPT_MODE, llaveSecreta);
        byte[] datosEncriptar = dato.getBytes("UTF-8");
        byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
        String encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);
		return encriptado;
	}
	public String desencripta(String datoEncriptado, String claveSecreta) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secretKey = this.crearLlave(claveSecreta);
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] bytesEncriptados = Base64.getDecoder().decode(datoEncriptado);
        byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
        String datos = new String(datosDesencriptados);
		return datos;
	}
}
