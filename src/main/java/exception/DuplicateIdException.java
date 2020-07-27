package exception;

public class DuplicateIdException extends RuntimeException{
	
	private static final long serialVersionUID = -2737303107273154630L;
	public DuplicateIdException(){}
	public DuplicateIdException(RuntimeException e){
		super(e);
	}
}
