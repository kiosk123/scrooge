package exception;

public class IdNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1652724250866635255L;
	public IdNotFoundException(){}
	public IdNotFoundException(RuntimeException e){
		super(e);
	}
}
