package za.co.merseta.nsdms.framework.exception;

/**
 * A marker exception indicating to a caller that the call is safe to retry the
 * call i.e.that on retry the call could succeed.
 * 
 * @author Akhil Gupta
 *
 */
public class RetriableException extends NSDMSException {

    private static final long serialVersionUID = 1L;

    public RetriableException(String message) {
        super(message);
    }

    public RetriableException(String message, Throwable exception) {
        super(message, exception);
    }
    
}
