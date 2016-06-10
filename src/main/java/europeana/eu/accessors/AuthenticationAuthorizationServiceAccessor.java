package europeana.eu.accessors;

import europeana.eu.exceptions.DoesNotExistException;

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
     * Deletes a user with the specified username.
     * @param username
     * @return HTTP Response code or throws exception
     */
    short deleteUser(String username) throws DoesNotExistException, Exception;

    /**
     * Updates an ecloud-user with the specified username + password.
     * @param username
     * @param password
     * @return HTTP Response code or throws exception
     * @throws DoesNotExistException
     */
    short updateUser(String username, String password) throws Exception;

    /**
     * Release all resources
     */
    void close();
}
