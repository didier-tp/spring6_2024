package tp.appliSpring.generic.exception;

public class ConflictException extends RuntimeException {

	public ConflictException() {
	}

	public ConflictException(String message) {
		super(message);
	}

	public ConflictException(Throwable cause) {
		super(cause);
	}

	public ConflictException(String message, Throwable cause) {
		super(message, cause);
	}

}
