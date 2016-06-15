//quando tenta persistir algo, usa o PersistenciaException

package br.com.sgc.exception;


public class PersistenciaException extends RuntimeException {

	private static final long serialVersionUID = 7803595695812860781L;
	
	public PersistenciaException(String mensagem, Exception e) {
		super(mensagem, e);
	}

}
