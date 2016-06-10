package europeana.eu.accessors.base;

import europeana.eu.accessors.AuthenticationAuthorizationServiceAccessor;
import europeana.eu.commons.Tools;
import europeana.eu.exceptions.DoesNotExistException;
import europeana.eu.model.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-06-09
 */
public class AuthenticationAuthorizationServiceAccessorBase implements AuthenticationAuthorizationServiceAccessor {
    private static final Logger logger = LogManager.getLogger();
    private Client client = JerseyClientBuilder.newClient();
    URL accessorUrl;

    public AuthenticationAuthorizationServiceAccessorBase(String accessUrl, String username, String password) {
        logger.info("Initializing http client");
        try {
            this.accessorUrl = new URL(accessUrl);
        } catch (MalformedURLException e) {
            logger.fatal("Url is not valid: " + accessUrl);
            e.printStackTrace();
            return;
        }
        HttpAuthenticationFeature authenticationFeature = HttpAuthenticationFeature.basic(username, password);
        client.register(authenticationFeature);
        logger.info("Initialized http client with target url: {}", this.accessorUrl);
    }

    public void close()
    {
        client.close();
    }

    @Override
    public short createUser(String username, String password) {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.CREATEUSER_PATH.getConstant());

        if (username != null && !username.equals("") && password != null && !password.equals(""))
            target = target.queryParam(Constants.USERNAME_QP.getConstant(), username).queryParam(Constants.PASSWORD_QP.getConstant(), password);
        else
            throw new UnsupportedOperationException("Please fill in all the parameters.");

        Response response = target.request().post(null, Response.class);

        short status = (short) response.getStatus();

        if (status == 200)
            logger.info("createUser: " + target.getUri() + ", response: " + status + ", User created with username: " + username);
        else{
            String errorString = Tools.parseResponse(target.getUri().toString(), status, response);
            logger.error(errorString);
            switch (status)
            {
                default:
                    Tools.generalExceptionHandler(status, errorString);
            }
        }
        return status;
    }

    @Override
    public short deleteUser(String username) throws Exception {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DELETEUSER_PATH.getConstant());

        if (username != null && !username.equals(""))
            target = target.queryParam(Constants.USERNAME_QP.getConstant(), username);
        else
            throw new UnsupportedOperationException("Please fill in all the parameters.");

        Response response = target.request().post(null, Response.class);

        short status = (short) response.getStatus();

        if (status == 200)
            logger.info("deleteUser: " + target.getUri() + ", response: " + status + ", User deleted with username: " + username);
        else{
            String errorString = Tools.parseResponse(target.getUri().toString(), status, response);
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                default:
                    Tools.generalExceptionHandler(status, errorString);
            }
        }
        return status;
    }

    @Override
    public short updateUser(String username, String password) throws Exception {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.UPDATEUSER_PATH.getConstant());

        if (username != null && !username.equals(""))
            target = target.queryParam(Constants.USERNAME_QP.getConstant(), username).queryParam(Constants.PASSWORD_QP.getConstant(), password);
        else
            throw new UnsupportedOperationException("Please fill in all the parameters.");

        Response response = target.request().post(null, Response.class);

        short status = (short) response.getStatus();
        System.out.println(response.getHeaders());

        if (status == 200)
            logger.info("updateUser: " + target.getUri() + ", response: " + status + ", User updated with username: " + username);
        else{
            String errorString = Tools.parseResponse(target.getUri().toString(), status, response);
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                default:
                    Tools.generalExceptionHandler(status, errorString);
            }
        }
        return status;
    }
}
