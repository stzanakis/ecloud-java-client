package europeana.eu.accessors.base;

import europeana.eu.accessors.UniqueIdentifierServiceAccessor;
import europeana.eu.exceptions.AlreadyExistsException;
import europeana.eu.exceptions.BadRequest;
import europeana.eu.exceptions.DoesNotExistException;
import europeana.eu.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NoContentException;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-25
 */
public class UniqueIdentifierServiceAccessorBase implements UniqueIdentifierServiceAccessor {
    private static final Logger logger = LogManager.getLogger();
    private Client client = JerseyClientBuilder.newClient();
    URL accessorUrl;

    public UniqueIdentifierServiceAccessorBase(String accessUrl, String username, String password) {
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
    public CloudId createNewCloudId(String providerId) throws DoesNotExistException, AlreadyExistsException {
        return generateCloudId(providerId, null);
    }

    @Override
    public CloudId createNewCloudId(String providerId, String recordId) throws DoesNotExistException, AlreadyExistsException {
        return generateCloudId(providerId, recordId);
    }

    @Override
    public short deleteCloudId(String cloudId) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.CLOUDIDS_PATH.getConstant()).path(cloudId);
        Response response = target.request(MediaType.APPLICATION_JSON).delete();

        short status = (short) response.getStatus();

        if (status == 200) {
            logger.info("deleteCloudId: " + target.getUri() + ", response: " + status + ", CloudId: " + cloudId + " deleted!");
        }
        else{
            Result result = response.readEntity(Result.class);
            String errorString = "Response code: " + status + ", ErrorCode=" + result.getErrorCode() + ", Details: " + result.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
            }
        }
        return status;
    }

    private CloudId generateCloudId(String providerId, String recordId) throws DoesNotExistException, AlreadyExistsException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.CLOUDIDS_PATH.getConstant()).queryParam(Constants.PROVIDERID.getConstant(), providerId)
                .queryParam(Constants.RECORDID.getConstant(), recordId);

        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(null, MediaType.APPLICATION_JSON), Response.class);

        short status = (short) response.getStatus();

        if (status == 200) {
            CloudId cloudId = response.readEntity(CloudId.class);
            logger.info("generateCloudId: " + target.getUri() + ", response: " + status + ", Cloud Id with id: " + cloudId.getId() +" created successfully");
            return cloudId;
        }
        else{
            Result result = response.readEntity(Result.class);
            String errorString = "Response code: " + status + ", ErrorCode=" + result.getErrorCode() + ", Details: " + result.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 409:
                    throw new AlreadyExistsException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
            }
        }
        return null;
    }

    @Override
    public CloudIdsSlice getCloudIdsOfProvider(String providerId) throws DoesNotExistException {
        return retrieveCloudIdsOfProvider(providerId, null, 0);
    }

    @Override
    public CloudIdsSlice getCloudIdsOfProvider(String providerId, String from, int to) throws DoesNotExistException {
        return retrieveCloudIdsOfProvider(providerId, from, to);
    }

    private CloudIdsSlice retrieveCloudIdsOfProvider(String providerId, String from, int to) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).path(providerId).path(Constants.CLOUDIDS_PATH.getConstant());
        if (from != null)
            target = target.queryParam(Constants.FROM.getConstant(), from);
        if (to > 0)
            target = target.queryParam(Constants.TO.getConstant(), to);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

//        System.out.println(target.getUri());
//        System.out.println(response.readEntity(String.class));
        short status = (short) response.getStatus();

        if (status == 200) {
            CloudIdsSlice cloudIdsSlice = response.readEntity(CloudIdsSlice.class);
            logger.info("getCloudIdsOfProvider: " + target.getUri() + ", response: " + status + ", Returned a list of results!");
            return cloudIdsSlice;
        }
        else{
            Result result = response.readEntity(Result.class);
            String errorString = "Response code: " + status + ", ErrorCode=" + result.getErrorCode() + ", Details: " + result.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
            }
        }
        return null;
    }

    @Override
    public short createDataProvider(String providerId, String organizationUrl, String email, String digitalLibraryUrl, String organizationName, String remarks, String contactPersonName) throws AlreadyExistsException, BadRequest {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).queryParam(Constants.PROVIDERID.getConstant(), providerId);

        DataProviderProperties dataProvider = new DataProviderProperties("SOrganizationName","So-url-example.com","Semail@example.com"
                ,"Sdl-url-example.com","SContactPersonName","SRemarks");

        Response response = target.request(MediaType.APPLICATION_JSON).post(
                Entity.entity(dataProvider, MediaType.APPLICATION_JSON), Response.class);

        short status = (short) response.getStatus();

        if (status == 201) {
            logger.info("createDataProvider: " + target.getUri() + ", response: " + status + ", Provider with providerId: " + providerId + " created successfully!");
        }
        else{
            Result result = response.readEntity(Result.class);
            String errorString = "Response code: " + status + ", ErrorCode=" + result.getErrorCode() + ", Details: " + result.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 400:
                    throw new BadRequest(errorString);
                case 409:
                    throw new AlreadyExistsException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
            }
        }
        return status;
    }

    @Override
    public DataProvider getProvider(String providerId) throws BadRequest, DoesNotExistException, NoContentException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).path(providerId);
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        short status = (short) response.getStatus();

        DataProvider dataProvider;
        if (status == 200) {
            dataProvider = response.readEntity(DataProvider.class);
            logger.info("getProvider: " + target.getUri() + ", response: " + status + ", Provider with providerId: " + providerId + " exists!");
            return dataProvider;
        }
        else{
            Result result = response.readEntity(Result.class);
            String errorString = "Response code: " + status + ", ErrorCode=" + result.getErrorCode() + ", Details: " + result.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 204:
                    throw new NoContentException(errorString);
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
            }
        }
        return null;
    }

    @Override
    public short deleteDataProvider(String providerId) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).path(providerId);
        Response response = target.request(MediaType.APPLICATION_JSON).delete();

        short status = (short) response.getStatus();

        if (status == 200) {
            logger.info("deleteDataProvider: " + target.getUri() + ", response: " + status + ", Provider with providerId: " + providerId + " deleted!");
        }
        else{
            Result result = response.readEntity(Result.class);
            String errorString = "Response code: " + status + ", ErrorCode=" + result.getErrorCode() + ", Details: " + result.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
            }
        }
        return status;
    }
}
