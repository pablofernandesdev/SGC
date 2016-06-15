package br.com.sgc.exception;


public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 7803595695812860781L;
	
	public ServiceException(String mensagem, Exception e) {
		super(mensagem, e);
	}

}
