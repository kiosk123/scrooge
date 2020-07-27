package exception;

public class ChangingPasswordIsMeaninglessException extends RuntimeException{

	private static final long serialVersionUID = 2135636665278965669L;
	public ChangingPasswordIsMeaninglessException(){	}
	public ChangingPasswordIsMeaninglessException(RuntimeException e){
		super(e);
	}
}
