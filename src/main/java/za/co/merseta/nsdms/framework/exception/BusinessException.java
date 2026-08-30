package za.co.merseta.nsdms.framework.exception;

/**
 * An exception indicating that the call was processed correctly and
 * failed due to valid business reasons e.g. validation was done and some field
 * is missing
 * 
 * @author Akhil Gupta
 *
 */
public class BusinessException extends NonRetriableException {

    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable exception) {
        super(message, exception);
    }
}
