package za.co.merseta.nsdms.framework.exception;

/**
 * An exception returned on creates when the item being created has already been
 * created e.g.payable invoice with the ID passed in was already created
 * successfully.
 * 
 * @author Akhil Gupta
 *
 */
public class EntityAlreadyExistsException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public EntityAlreadyExistsException(String message, Throwable exception) {
        super(message, exception);
    }
}
