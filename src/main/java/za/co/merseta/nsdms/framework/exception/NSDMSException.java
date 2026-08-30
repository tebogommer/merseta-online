package za.co.merseta.nsdms.framework.exception;

/**
 * An exception indicating that the error was created or handled within NSDMS 
 * 
 * @author Akhil Gupta
 *
 */
public class NSDMSException extends Exception {

    private static final long serialVersionUID = 1L;

    public NSDMSException(String message) {
        super(message);
    }

    public NSDMSException(String message, Throwable exception) {
        super(message, exception);
    }
}
