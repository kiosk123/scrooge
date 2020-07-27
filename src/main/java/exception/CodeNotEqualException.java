package exception;


public class CodeNotEqualException extends RuntimeException {
	
	private static final long serialVersionUID = -1761806779112934562L;
	public CodeNotEqualException(){}
	public CodeNotEqualException(RuntimeException e) {
		super(e);
	}

}
