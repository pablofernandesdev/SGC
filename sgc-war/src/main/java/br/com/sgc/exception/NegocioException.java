//quando da um throw excepcio, pra forçar que acontece uma excessao.

package br.com.sgc.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 9104109396993245062L;

	private String codigo;
	private String[] args;

	public NegocioException() {
		super();
	}

	public NegocioException(String message) {
		super(message);
		this.codigo = message;
	}

	public NegocioException(String message, String... args) {
		this(message);
		this.args = args;
	}

	public NegocioException(String codigo, String message) {
		super(message);
		this.codigo = codigo;
	}

	public NegocioException(String codigo, String message, Throwable cause) {
		super(message, cause);
	}

	public NegocioException(String message, Throwable cause) {
		super(message, cause);
	}

	public NegocioException(Throwable cause) {
		super(cause);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String[] getArgs() {
		return args;
	}
}
