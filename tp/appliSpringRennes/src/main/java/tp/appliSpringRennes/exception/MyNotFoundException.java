package tp.appliSpringRennes.exception;

public class MyNotFoundException extends RuntimeException{
    public MyNotFoundException() {
    }

    public MyNotFoundException(String message) {
        super(message);
    }

    public MyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
