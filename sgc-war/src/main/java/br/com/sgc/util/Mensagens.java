package br.com.sgc.util;

import javax.faces.application.FacesMessage;

public final class Mensagens extends MensagensAbstract {

	public static void addError(String key, Object... parameters) {
		addMessage(false, key, FacesMessage.SEVERITY_ERROR, parameters);
	}

	public static void addInfo(String key, Object... parameters) {
		addMessage(false, key, FacesMessage.SEVERITY_INFO, parameters);
	}

	public static void addWarn(String key, Object... parameters) {
		addMessage(false, key, FacesMessage.SEVERITY_WARN, parameters);
	}

	public static void addFatal(String key, Object... parameters) {
		addMessage(false, key, FacesMessage.SEVERITY_FATAL, parameters);
	}

}
