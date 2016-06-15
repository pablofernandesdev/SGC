package br.com.sgc.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public final class SgcUtil {
	
	/**
	 * imprimi o CNPJ com a máscara: 99.999.999.9999-99
	 */
	public static String imprimeCNPJ(String CNPJ) {
		return (CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "."
				+ CNPJ.substring(5, 8) + "." + CNPJ.substring(8, 12) + "-" + CNPJ
					.substring(12, 14));
	}

	/**
	 * imprimi o CPF com a máscara: 999.999.999-99
	 */
	public static String imprimeCPF(String CPF) {
		return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "."
				+ CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
	}
	
	/**
	 * imprimi o telefone com a máscara: 99 9999-9999
	 */
	public static String imprimeTelefone(String Telefone) {
		return (Telefone.substring(0, 2) + " " + Telefone.substring(2, 6) + "-" + Telefone
				.substring(6, 10));
	}
	
	/**
	 * imprimi o Status com a máscara: Administrador ou Vendedor
	 */
	public static String imprimeStatus(String Status) {
		if (Status.equals("A")) {
			return ("Administrador");
		} else {
			return ("Vendedor");
		}
	}

	/**
	 * remove as máscaras
	 */
	public static String removerMascara(String str) {
		return str.replaceAll("\\D", "");
	}

	/**
	 * Converte o valor double para moeda
	 */
	public static String formataMoeda(double vlr) {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(vlr);
	}

	/**
	 * Transforma um String contendo um numero em formato brasileiro (tipo
	 * 123.456,78) para um double O metodo retira todos os pontos do String e
	 * substitui as virgula por um ponto.
	 * 
	 * @param numero
	 *            String
	 * @return double correspondente ao numero passado como parametro
	 */
	public static double stringToDouble(String numero) {
		double retornoMetodo;
		try {
			StringBuffer retorno = new StringBuffer();
			int tamanho = numero.length();
			// -- Se o separador decimal é ponto, ele é transformado em
			// virgula
			for (int i = tamanho - 1; i >= 0; i--) {
				if (numero.charAt(i) == ',') {
					break;
				}
				if (numero.charAt(i) == '.') {
					numero = numero.substring(0, i) + ","
							+ numero.substring(i + 1, tamanho);
					break;
				}
			}
			for (int i = 0; i < numero.length(); i++) {
				if (numero.charAt(i) == '.') {
					continue;
				}
				retorno.append(numero.charAt(i));
			}
			retornoMetodo = Double.parseDouble(retorno.toString().replace(',',
					'.'));
		} catch (Exception e) {
			retornoMetodo = 0;
		}
		return retornoMetodo;
	}

	/**
	 * Formata valore String mascara String #.000,00. o valor de entrada deverá
	 * está no formato double #0.00
	 * 
	 * @param valor
	 *            o valor que se deseja formatar
	 * @return o valor no formato monetário
	 */
	public static String formataDoubleEmMacaraMonetario(double valor) {
		return doubleToString(valor, 2);
	}

	/**
	 * Converte um double para String, com precisao de duas casas. Ideal para
	 * exibir valores monetarios.
	 * 
	 * @param valor
	 *            - Valor ponto-flutuante a ser formatado.
	 * @param decimais
	 *            - quantidade de decimais presentesno resultado
	 * @return String correspondente a 'valor', com duas casas decimais
	 */
	public static String doubleToString(double valor, int decimais) {

		String valorStr = null;
		DecimalFormat instance = null;
		Locale locale = null;

		try {
			locale = new Locale("pt", "BR");
			instance = (DecimalFormat) DecimalFormat.getInstance(locale);
			instance.setDecimalSeparatorAlwaysShown(true);
			instance.setMaximumFractionDigits(decimais);
			instance.setMinimumFractionDigits(decimais);
			valorStr = instance.format(valor);

		} catch (Exception e) {
			// logger.error(e);
		}

		return valorStr;
	}

}
