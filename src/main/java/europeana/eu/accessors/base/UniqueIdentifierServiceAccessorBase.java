package europeana.eu.accessors.base;

import europeana.eu.accessors.UniqueIdentifierServiceAccessor;
import europeana.eu.exceptions.AlreadyExistsException;
import europeana.eu.exceptions.BadRequest;
import europeana.eu.exceptions.DoesNotExistException;
import europeana.eu.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientProperties;
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
 * Implementation of the Unique Identifier Service Accessor using Jersey.
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
    public void close() {
        client.close();
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
    public CloudId getCloudId(String providerId, String recordId) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.CLOUDIDS_PATH.getConstant())
                .queryParam(Constants.PROVIDERID.getConstant(), providerId).queryParam(Constants.RECORDID.getConstant(), recordId);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        short status = (short) response.getStatus();

        if (status == 200) {
            CloudId cloudId = response.readEntity(CloudId.class);
            logger.info("getCloudId: " + target.getUri() + ", response: " + status + ", Cloud Id with id: " + cloudId.getId() + " exists!");
            return cloudId;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public ResultsSlice<CloudId> getCloudIdWithRecordIds(String cloudId) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.CLOUDIDS_PATH.getConstant()).path(cloudId);

        Response response = target.request(MediaType.APPLICATION_JSON).get();
        short status = (short) response.getStatus();

        if (status == 200) {
            ResultsSlice ResultsSlice = response.readEntity(ResultsSlice.class);
            logger.info("getCloudIdWithRecordIds: " + target.getUri() + ", response: " + status + ", Returned a list of results!");
            return ResultsSlice;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public CloudId createMappingRecordIdToCloudId(String providerId, String cloudId, String recordId) throws DoesNotExistException, AlreadyExistsException {
        return mapRecordIdToCloudId(providerId, cloudId, recordId);
    }

    @Override
    public CloudId createMappingRecordIdToCloudId(String providerId, String cloudId) throws DoesNotExistException, AlreadyExistsException {
        return mapRecordIdToCloudId(providerId, cloudId, null);
    }

    /**
     * Maps a providerId and recordId to the Cloud Id.
     * It is a wrap up of the public calls of mapping a Cloud Id combined.
     * @param providerId
     * @param cloudId
     * @param recordId
     * @return
     * @throws DoesNotExistException
     * @throws AlreadyExistsException
     */
    private CloudId mapRecordIdToCloudId(String providerId, String cloudId, String recordId) throws DoesNotExistException, AlreadyExistsException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant())
                .path(providerId).path(Constants.CLOUDIDS_PATH.getConstant()).path(cloudId).queryParam(Constants.RECORDID.getConstant(), recordId);

        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(null, MediaType.APPLICATION_JSON), Response.class);

        short status = (short) response.getStatus();

        if (status == 200) {
            CloudId cloudIdRepresentation = response.readEntity(CloudId.class);
            logger.info("mapRecordIdToCloudId: " + target.getUri() + ", response: " + status + ", Cloud Id with id: " + cloudIdRepresentation.getId() + " and Record Id: " + recordId + " mapped successfully!");
            return cloudIdRepresentation;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 409:
                    throw new AlreadyExistsException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public short deleteCloudId(String cloudId) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.CLOUDIDS_PATH.getConstant()).path(cloudId);
        Response response = target.request(MediaType.APPLICATION_JSON).delete();

        short status = (short) response.getStatus();

        if (status == 200) {
            logger.info("deleteCloudId: " + target.getUri() + ", response: " + status + ", CloudId: " + cloudId + " deleted!");
            return status;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    /**
     * Generates the Cloud Id.
     * It is a wrap up of the public calls of creating a Cloud Id combined.
     * @param providerId
     * @param recordId
     * @return {@link europeana.eu.model.CloudId}
     * @throws DoesNotExistException
     * @throws AlreadyExistsException
     */
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
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 409:
                    throw new AlreadyExistsException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public ResultsSlice<CloudId> getCloudIdsOfProvider(String providerId) throws DoesNotExistException {
        return retrieveCloudIdsOfProvider(providerId, null, 0);
    }

    @Override
    public ResultsSlice<CloudId> getCloudIdsOfProvider(String providerId, String from, int to) throws DoesNotExistException {
        return retrieveCloudIdsOfProvider(providerId, from, to);
    }

    /**
     * Get a set of Cloud Ids, in slices, of a specific Data Provider.
     * It is a wrap up of the public calls of getting Cloud Ids per Data Provider combined.
     * @param providerId
     * @param from
     * @param to
     * @return {@link europeana.eu.model.ResultsSlice}
     * @throws DoesNotExistException
     */
    private ResultsSlice<CloudId> retrieveCloudIdsOfProvider(String providerId, String from, int to) throws DoesNotExistException {
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
            ResultsSlice<CloudId> ResultsSlice = response.readEntity(ResultsSlice.class);
            logger.info("getCloudIdsOfProvider: " + target.getUri() + ", response: " + status + ", Returned a list of results!");
            return ResultsSlice;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public String createDataProvider(String providerId, String organizationWebsite, String organizationUrl, String email, String digitalLibraryWebsite, String digitalLibraryUrl, String organizationName, String remarks, String contactPersonName) throws AlreadyExistsException, BadRequest {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).queryParam(Constants.PROVIDERID.getConstant(), providerId);

        DataProviderProperties dataProvider = new DataProviderProperties(organizationName,organizationWebsite, organizationUrl,email
                ,digitalLibraryWebsite, digitalLibraryUrl,contactPersonName,remarks);

        Response response = target.request(MediaType.APPLICATION_JSON).post(
                Entity.entity(dataProvider, MediaType.APPLICATION_JSON), Response.class);

        short status = (short) response.getStatus();

        if (status == 201) {
            String location = response.getHeaderString(Constants.LOCATION_HEADER.getConstant());
            logger.info("createDataProvider: " + target.getUri() + ", response: " + status + ", Provider with providerId: " + providerId + " created successfully!");
            return location;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 400:
                    throw new BadRequest(errorString);
                case 409:
                    throw new AlreadyExistsException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public short updateDataProvider(String providerId, String organizationWebsite, String organizationUrl, String email, String digitalLibraryWebsite, String digitalLibraryUrl, String organizationName, String remarks, String contactPersonName) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).path(providerId);

        DataProviderProperties dataProvider = new DataProviderProperties(organizationName,organizationWebsite, organizationUrl,email
                ,digitalLibraryWebsite, digitalLibraryUrl,contactPersonName,remarks);

        Response response = target.request(MediaType.APPLICATION_JSON).put(
                Entity.entity(dataProvider, MediaType.APPLICATION_JSON), Response.class);

        short status = (short) response.getStatus();

        if (status == 204) {
            logger.info("updateDataProvider: " + target.getUri() + ", response: " + status + ", Provider with providerId: " + providerId + " updated successfully!");
            return status;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public ResultsSlice<DataProvider> getDataProviders() throws DoesNotExistException {
        return retrieveProviders(null);
    }

    @Override
    public ResultsSlice<DataProvider> getDataProviders(String from) throws DoesNotExistException {
        return retrieveProviders(from);
    }

    /**
     * Get a set of Data Providers, in slices.
     * It is a wrap up of the public calls of getting Data Providers combined.
     * @param from
     * @return {@link europeana.eu.model.DataProviderSlice}
     * @throws DoesNotExistException
     */
    private ResultsSlice<DataProvider> retrieveProviders(String from) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant());

        if (from != null)
            target = target.queryParam(Constants.FROM.getConstant(), from);

        Response response = target.request(MediaType.APPLICATION_XML).get();

        short status = (short) response.getStatus();

        if (status == 200) {
            ResultsSlice<DataProvider> dataProviderSlice = response.readEntity(ResultsSlice.class);
            logger.info("getDataProviders: " + target.getUri() + ", response: " + status + ", Returned a list of results!");
            return dataProviderSlice;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public DataProvider getDataProvider(String providerId) throws BadRequest, DoesNotExistException, NoContentException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).path(providerId);
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        short status = (short) response.getStatus();

        if (status == 200) {
            DataProvider dataProvider = response.readEntity(DataProvider.class);
            logger.info("getDataProvider: " + target.getUri() + ", response: " + status + ", Provider with providerId: " + providerId + " exists!");
            return dataProvider;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public short deleteDataProvider(String providerId) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).path(providerId);
        Response response = target.request(MediaType.APPLICATION_JSON).delete();

        short status = (short) response.getStatus();

        if (status == 200) {
            logger.info("deleteDataProvider: " + target.getUri() + ", response: " + status + ", Provider with providerId: " + providerId + " deleted!");
            return status;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public short activateDataProvider(String providerId) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).path(providerId).path(Constants.ACTIVE_PATH.getConstant());
        target.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true); //Jersey client does not permit empty PUT method so we need to suppress

        Response response = target.request(MediaType.APPLICATION_JSON).put(Entity.entity(null, MediaType.APPLICATION_JSON), Response.class);

        short status = (short) response.getStatus();

        if (status == 200) {
            logger.info("activateDataProvider: " + target.getUri() + ", response: " + status + ", Provider with providerId: " + providerId + " activated successfully!");
            return status;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public short deactivateDataProvider(String providerId) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).path(providerId).path(Constants.ACTIVE_PATH.getConstant());

        Response response = target.request(MediaType.APPLICATION_JSON).delete();

        short status = (short) response.getStatus();

        if (status == 200) {
            logger.info("deactivateDataProvider: " + target.getUri() + ", response: " + status + ", Provider with providerId: " + providerId + " deactivated successfully!");
            return status;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public ResultsSlice<CloudId> getLocalIdsOfProvider(String providerId) throws DoesNotExistException {
        return retrieveLocalIdsOfProvider(providerId, null, 0);
    }

    @Override
    public ResultsSlice<CloudId> getLocalIdsOfProvider(String providerId, String from, int to) throws DoesNotExistException {
        return retrieveLocalIdsOfProvider(providerId, from, to);
    }

    @Override
    public short deleteMappingLocalIdFromCloudId(String providerId, String localId) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).path(providerId).path(Constants.LOCALIDS_PATH.getConstant()).path(localId);
        Response response = target.request(MediaType.APPLICATION_JSON).delete();

        short status = (short) response.getStatus();

        if (status == 200) {
            logger.info("deleteMappingLocalIdFromCloudId: " + target.getUri() + ", response: " + status + ", Provider with providerId: " + providerId + ", LocalId: " + localId + " deleted!");
            return status;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    /**
     * Get a set of Local Ids, in slices, of a specific Data Provider.
     * It is a wrap up of the public calls of getting Local Ids per Data Provider combined.
     * @param providerId
     * @param from
     * @param to
     * @return {@link europeana.eu.model.ResultsSlice}
     * @throws DoesNotExistException
     */
    private ResultsSlice<CloudId> retrieveLocalIdsOfProvider(String providerId, String from, int to) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).path(providerId).path(Constants.LOCALIDS_PATH.getConstant());
        if (from != null)
            target = target.queryParam(Constants.FROM.getConstant(), from);
        if (to > 0)
            target = target.queryParam(Constants.TO.getConstant(), to);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        short status = (short) response.getStatus();

        if (status == 200) {
            ResultsSlice<CloudId> ResultsSlice = response.readEntity(ResultsSlice.class);
            logger.info("getLocalIdsOfProvider: " + target.getUri() + ", response: " + status + ", Returned a list of results!");
            return ResultsSlice;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }
}
