package exception;

public class IdAlreadyExistException extends RuntimeException{

	private static final long serialVersionUID = -4908982118262000985L;
	public IdAlreadyExistException(){}
	public IdAlreadyExistException(RuntimeException e){
		super(e);
	}
}
