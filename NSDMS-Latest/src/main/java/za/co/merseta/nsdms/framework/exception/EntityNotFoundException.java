package za.co.merseta.nsdms.framework.exception;

/**
 * An exception returned on queries when the item being queried does not exist
 * e.g. the company being queried does not exist
 * 
 * @author Akhil Gupta
 *
 */
public class EntityNotFoundException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable exception) {
        super(message, exception);
    }
}
