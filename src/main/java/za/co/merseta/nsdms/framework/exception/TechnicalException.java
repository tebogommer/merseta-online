package za.co.merseta.nsdms.framework.exception;

/**
 * An exception indicating that a technical error occurred e.g. a connectivity
 * error
 * 
 * @author Akhil Gupta
 *
 */
public class TechnicalException extends RetriableException {

    private static final long serialVersionUID = 1L;

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, Throwable exception) {
        super(message, exception);
    }
}
