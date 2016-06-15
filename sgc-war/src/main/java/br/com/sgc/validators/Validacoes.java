package br.com.sgc.validators;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacoes {

	/**
	 * verifica se o email é valido.
	 */
	public static boolean validEmail(String email) {

		Pattern p = Pattern
				.compile("[a-zA-Z0-9]+[a-zA-Z0-9_.-]+@{1}[a-zA-Z0-9_.-]*\\.+[a-z]{2,4}");
		Matcher m = p.matcher(email);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * verifica se o nome é valido.
	 */	
	public static boolean validarNome(String nome) {

		Pattern p = Pattern.compile("[a-zA-Z ]{2,}");
		Matcher m = p.matcher(nome);
		if (m.find()) {
			return true;
		}

		else {
			return false;
		}
	}

	/**
	 * verifica se o CEP é valido.
	 */
	public static boolean validarCep(String cep) {

		cep = cep.replaceAll("\\D", ""); // o "\D" é uma expressão regular que
											// casa com qualquer coisa que não
											// seja um número [0-9], portanto
											// ele vai varrer sua String, e tudo
											// o que não for um número, ele vai
											// substituir por vazio.

		Pattern p = Pattern.compile("^\\d{5,5}-?\\d{3,3}$");
		Matcher m = p.matcher(cep);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * verifica se o telefone é valido.
	 */
	public static boolean validarTelefone(String telefone) {

		telefone = telefone.replaceAll("\\D", "");
		return telefone.matches("[0-9]{1,2}[0-9]{4,5}[0-9]{4,5}");
	}

	/**
	 * verifica se o CPF é válido
	 */
	public static boolean validarCPF(String cpf) {

		cpf = cpf.replaceAll("\\D", "");
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (cpf == null
				|| cpf.length() != 11
				|| cpf.matches("^(0{11}|1{11}|2{11}|3{11}|4{11}|5{11}|6{11}|7{11}|8{11}|9{11})$"))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;
		// "try" - protege o codigo para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero: por
				// exemplo, transforma o caractere '0' no inteiro 0 (48 eh a
				// posicao de '0' na tabela ASCII)
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48);
			// converte no respectivo caractere numerico Calculo do 2o. Digito
			// Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);
			// Verifica se os digitos calculados conferem com os digitos
			// informados.
			if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	/**
	 * verifica se o CNPJ é válido
	 */
	public static boolean validarCNPJ(String cnpj) {
		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais

		cnpj = cnpj.replaceAll("\\D", "");

		if (cnpj == null
				|| cnpj.length() != 14
				|| cnpj.matches("^(0{14}|1{14}|2{14}|3{14}|4{14}|5{14}|6{14}|7{14}|8{14}|9{14})$"))
			return (false);
		char dig13, dig14;
		int sm, i, r, num, peso;
		// "try" - protege o código para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				// converte o i-ésimo caractere do CNPJ em um número:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posição de '0' na tabela ASCII)
				num = (int) (cnpj.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}
			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);
			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (cnpj.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}
			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);
			// Verifica se os dígitos calculados conferem com os dígitos
			// informados.
			if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13)))

				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

}
