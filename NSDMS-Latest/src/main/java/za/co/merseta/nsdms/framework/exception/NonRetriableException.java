package za.co.merseta.nsdms.framework.exception;

/**
 * A marker exception indicating to a caller that the call it is safe to retry
 * the call i.e.retries would either not succeed or would duplicate the
 * transaction
 * 
 * @author Akhil Gupta
 *
 */
public class NonRetriableException extends NSDMSException {

    private static final long serialVersionUID = 1L;

    public NonRetriableException(String message) {
        super(message);
    }

    public NonRetriableException(String message, Throwable exception) {
        super(message, exception);
    }
}
