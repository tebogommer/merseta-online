package haj.com.service;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

// TODO: Auto-generated Javadoc
/**
 * The Class ProxyAuthenticator.
 */
class ProxyAuthenticator extends Authenticator {

    /** The password. */
    private String user, password;

    /**
     * Instantiates a new proxy authenticator.
     *
     * @param user the user
     * @param password the password
     */
    public ProxyAuthenticator(String user, String password) {
        this.user = user;
        this.password = password;
    }

    /* (non-Javadoc)
     * @see java.net.Authenticator#getPasswordAuthentication()
     */
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password.toCharArray());
    }
}