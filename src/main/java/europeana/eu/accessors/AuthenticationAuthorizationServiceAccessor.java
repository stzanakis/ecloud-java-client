package europeana.eu.accessors;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-06-09
 */
public interface AuthenticationAuthorizationServiceAccessor {

    /**
     * Creates a new ecloud-user with the specified username + password.
     * @param username
     * @param password
     * @return HTTP Response code or throws exception
     */
    short createUser(String username, String password);

    /**
     * Release all resources
     */
    void close();
}
