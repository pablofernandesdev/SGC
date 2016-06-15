package br.com.sgc.util.autenticacao;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

public class SenhaUtils {

	private static final String METODO_ENCRIPTACAO = "AES";
	public static final byte[] CHAVE = { 85, 10, 0, -25, 68, 88, 46, 37, 107,
			48, 10, -1, -37, -90, 70, -36 };;

	public static String gerarSenha(int digitos) {
		return RandomStringUtils.random(digitos, true, true);
	}

	public static String aplicarCriptografia(String senha) {
		return Base64.encodeBase64String(DigestUtils.sha512(senha));
	}

	public static String encriptar(String value)
			throws Exception {

		try {
			SecretKeySpec skeySpec = new SecretKeySpec(CHAVE, METODO_ENCRIPTACAO);

			Cipher cipher = Cipher.getInstance(METODO_ENCRIPTACAO);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(value.getBytes());

			return new String(Base64.encodeBase64URLSafe(encrypted));
		} catch (Exception e) {
			throw new Exception("Erro ao criptografar informações "
					+ e.getMessage());
		}
	}

	public static String decriptar(String encrypted)
			throws Exception {

		byte[] decrypted = null;

		try {
			SecretKeySpec skeySpec = new SecretKeySpec(CHAVE, METODO_ENCRIPTACAO);

			byte[] decoded = Base64.decodeBase64(encrypted);

			Cipher cipher = Cipher.getInstance(METODO_ENCRIPTACAO);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			decrypted = cipher.doFinal(decoded);
		} catch (Exception e) {
			throw new Exception("Erro ao descriptografar informações "
					+ e.getMessage());
		}

		return new String(decrypted);
	}

}
