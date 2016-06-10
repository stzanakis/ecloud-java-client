package europeana.eu.accessors;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-06-09
 */
public interface AuthenticationAuthorizationServiceAccessor {

    short createUser(String username, String password);

    /**
     * Release all resources
     */
    void close();
}
