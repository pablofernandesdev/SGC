package br.com.sgc.util;

import javax.faces.application.FacesMessage;

public final class MensagensModal extends MensagensAbstract {

	public static void addError(String key, Object... parameters) {
		addMessage(true, key, FacesMessage.SEVERITY_ERROR, parameters);
	}

	public static void addInfo(String key, Object... parameters) {
		addMessage(true, key, FacesMessage.SEVERITY_INFO, parameters);
	}

	public static void addWarn(String key, Object... parameters) {
		addMessage(true, key, FacesMessage.SEVERITY_WARN, parameters);
	}

	public static void addFatal(String key, Object... parameters) {
		addMessage(true, key, FacesMessage.SEVERITY_FATAL, parameters);
	}

	public static void addError(String key) {
		addMessage(true, key, FacesMessage.SEVERITY_ERROR);
	}

	public static void addInfo(String key) {
		addMessage(true, key, FacesMessage.SEVERITY_INFO);
	}

	public static void addWarn(String key) {
		addMessage(true, key, FacesMessage.SEVERITY_WARN);
	}

	public static void addFatal(String key) {
		addMessage(true, key, FacesMessage.SEVERITY_FATAL);
	}

}
