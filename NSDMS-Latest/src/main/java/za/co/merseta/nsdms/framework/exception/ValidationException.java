package za.co.merseta.nsdms.framework.exception;

public class ValidationException extends BusinessException{

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable exception) {
        super(message, exception);
    }    
}
