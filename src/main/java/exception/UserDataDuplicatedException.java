package exception;

@SuppressWarnings("serial")
public class UserDataDuplicatedException extends RuntimeException{
	public UserDataDuplicatedException() {}
	public UserDataDuplicatedException(RuntimeException e){
		super(e);
	}
}
