package exception;

public class EmailAlreadyExistException extends RuntimeException {
	private static final long serialVersionUID = -2035687824196755628L;
	public EmailAlreadyExistException() {}
	public EmailAlreadyExistException(RuntimeException e) {
		super(e);
	}
}
