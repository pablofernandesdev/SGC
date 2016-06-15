package br.com.sgc.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

public abstract class MensagensAbstract {

	private static final Logger LOGGER = Logger.getLogger(MensagensAbstract.class);

	private static ResourceBundle bundle;

	public static void addMessage(boolean modal, String key, Severity severity, Object... parameters) {
		FacesContext fc = FacesContext.getCurrentInstance();
		String message = "";
		if (key == null || key.trim().isEmpty()) {
			LOGGER.error("Erro ao configurar mensagens. A chave é nula ou VAZIA");
			message = "O sistema apresentou um problema interno, tente novamente.";
		} else {
			try {
				message = setMessageParameters(getBundle(fc).getString(key), parameters);
			} catch (RuntimeException e) {
				LOGGER.error("Erro ao configurar mensagens. Key: '" + key + "', Message: '" + message + "', Parameters: '" + parameters + "'");
				message = "O sistema apresentou um problema interno, tente novamente.";
			}
		}
		FacesMessage fm = new FacesMessage(severity, message, message);
		fc.addMessage(null, fm);
	}

	public static String setMessageParameters(String message, Object... parameters) {
		if (parameters != null && parameters.length > 0 && message.indexOf('{') != -1) {
			message = MessageFormat.format(message, parameters);
		}
		return message;
	}

	public static String getValueProperties(String property) {
		bundle = getBundle(FacesContext.getCurrentInstance());
		return bundle.getString(property);
	}

	private static ResourceBundle getBundle(FacesContext fc) {
		if (bundle == null) {
			bundle = ResourceBundle.getBundle("br.com.sgc.resource.messages");
			// Verificação temporária
			if (bundle == null) bundle = ResourceBundle.getBundle("br.com.sgc.resource.Messages_pt_BR");
		}
		return bundle;
	}

}
