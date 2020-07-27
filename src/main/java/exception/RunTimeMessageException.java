package exception;

public class RunTimeMessageException extends RuntimeException{
	private static final long serialVersionUID = 1713445092372341182L;
	public RunTimeMessageException() {}
	public RunTimeMessageException(RuntimeException e){
		super(e);
	}
}
