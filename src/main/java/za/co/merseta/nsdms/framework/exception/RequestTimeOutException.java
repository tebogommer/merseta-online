package za.co.merseta.nsdms.framework.exception;

/**
 * An exception indicating that the transaction waited for the configured
 * timeout and no response was received. The reason it is not retriable is
 * because we cannot tell what happened in a timeout, the call could have
 * completed so a retry might cause a duplicate.
 * 
 * @author Akhil Gupta
 *
 */
public class RequestTimeOutException extends NonRetriableException {

    private static final long serialVersionUID = 1L;

    public RequestTimeOutException(String message) {
        super(message);
    }

    public RequestTimeOutException(String message, Throwable exception) {
        super(message, exception);
    }
}
