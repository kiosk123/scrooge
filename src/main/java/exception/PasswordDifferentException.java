package exception;

public class PasswordDifferentException extends RuntimeException{
	private static final long serialVersionUID = -2951276820062134669L;
	public PasswordDifferentException(){}
	public PasswordDifferentException(RuntimeException e){
		super(e);
	}
}
