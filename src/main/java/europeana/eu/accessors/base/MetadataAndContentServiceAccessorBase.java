package europeana.eu.accessors.base;

import europeana.eu.accessors.MetadataAndContentServiceAccessor;
import europeana.eu.commons.Tools;
import europeana.eu.exceptions.BadRequest;
import europeana.eu.model.Constants;
import europeana.eu.model.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-30
 */
public class MetadataAndContentServiceAccessorBase implements MetadataAndContentServiceAccessor {
    private static final Logger logger = LogManager.getLogger();
    private Client client = JerseyClientBuilder.newClient();
    URL accessorUrl;

    public MetadataAndContentServiceAccessorBase(String accessUrl, String username, String password) {
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

    @Override
    public String createRepresentationVersion(String cloudId, String representationName, String providerId) throws BadRequest {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName);

        Map<String,String> map = new HashMap<>();
        map.put(Constants.PROVIDERID.getConstant(), providerId);
        String formURLEncoded = Tools.generateFormURLEncoded(map);

        Response response = target.request(MediaType.APPLICATION_FORM_URLENCODED).post(
                Entity.entity(formURLEncoded, MediaType.APPLICATION_FORM_URLENCODED), Response.class);

        short status = (short) response.getStatus();

        if (status == 201) {
            logger.info("createRepresentationVersion: " + target.getUri() + ", response: " + status + ", Representation with name: " + representationName + " created successfully!");
            return response.getHeaderString(Constants.LOCATION_HEADER.getConstant());
        }
        else{
            Result result = response.readEntity(Result.class);
            String errorString = "Response code: " + status + ", ErrorCode=" + result.getErrorCode() + ", Details: " + result.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 400:
                    throw new BadRequest(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
            }
        }
        return null;
    }
}
