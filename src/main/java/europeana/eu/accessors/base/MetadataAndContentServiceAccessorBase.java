package europeana.eu.accessors.base;

import europeana.eu.accessors.MetadataAndContentServiceAccessor;
import europeana.eu.commons.Tools;
import europeana.eu.exceptions.*;
import europeana.eu.model.CloudRecord;
import europeana.eu.model.Constants;
import europeana.eu.model.ErrorInfo;
import europeana.eu.model.RepresentationVersion;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpHeaders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NoContentException;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
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

    public void close()
    {
        client.close();
    }

    @Override
    public CloudRecord getCloudRecordWithSimplifiedUrl(String providerId, String recordId) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).path(providerId).path(Constants.RECORDS_PATH.getConstant()).path(recordId);
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        short status = (short) response.getStatus();

        if (status == 200) {
            CloudRecord cloudRecord = response.readEntity(CloudRecord.class);
            logger.info("getCloudRecordWithSimplifiedUrl: " + target.getUri() + ", response: " + status + ", Returned a list of results!");
            return cloudRecord;
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
    public RepresentationVersion getRepresentationWithSimplifiedUrl(String providerId, String recordId, String representationName) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).path(providerId).path(Constants.RECORDS_PATH.getConstant()).path(recordId)
                .path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName);
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        short status = (short) response.getStatus();

        if (status == 200) {
            RepresentationVersion representationVersion = response.readEntity(RepresentationVersion.class);
            logger.info("getRepresentation: " + target.getUri() + ", response: " + status + ", ProviderId: " + providerId + ", RecordId: " + recordId +
                    ", Representation: " + representationName + " exists!");
            logger.info("getRepresentationWithSimplifiedUrl: " + target.getUri() + ", response: " + status + ", Returned a list of results!");
            return representationVersion;
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
    public String getFileFromLatestPersistentWithSimplifiedUrl(String providerId, String recordId, String representationName, String fileName, String downloadDirectory) throws IOException, DoesNotExistException {
        WebTarget target = client.register(MultiPartFeature.class).target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).path(providerId).path(Constants.RECORDS_PATH.getConstant()).path(recordId)
                .path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName).path(fileName);
        File downloadFile = Paths.get(downloadDirectory, fileName).toFile();
        downloadFile.getParentFile().mkdirs();
        downloadFile.createNewFile(); //Check if it can be created before making any calls.
        Response response = target.request().get();

        short status = (short) response.getStatus();

        if (status == 200) {
            byte[] bytes = response.readEntity(byte[].class);
            FileUtils.writeByteArrayToFile(downloadFile, bytes);
            logger.info("getFileFromLatestPersistentWithSimplifiedUrl: " + target.getUri() + ", response: " + status + ", ProviderId: " + providerId + ", RecordId: " + recordId +
                    ", Representation: " + representationName + ", fileName: " + fileName + " stored at: " + downloadFile.toString());
            return downloadFile.toString();
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
    public MultivaluedMap<String, Object> getHeadersFromFileFromLatestPersistentWithSimplifiedUrl(String providerId, String recordId, String representationName, String fileName) throws IOException, DoesNotExistException {
        WebTarget target = client.register(MultiPartFeature.class).target(accessorUrl.toString());
        target = target.path(Constants.DATAPROVIDERS_PATH.getConstant()).path(providerId).path(Constants.RECORDS_PATH.getConstant()).path(recordId)
                .path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName).path(fileName);
        Response response = target.request().head();

        short status = (short) response.getStatus();
        System.out.println(response.readEntity(String.class));

        if (status == 200) {
            logger.info("getFileFromLatestPersistentWithSimplifiedUrl: " + target.getUri() + ", response: " + status + ", ProviderId: " + providerId + ", RecordId: " + recordId +
                    ", Representation: " + representationName + ", fileName: " + fileName + " exists!");
            return response.getHeaders();
        }
        else{
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status;
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
    public String createRepresentationVersion(String cloudId, String representationName, String providerId) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName);

        Map<String,String> map = new HashMap<>();
        map.put(Constants.PROVIDERID.getConstant(), providerId);
        String formURLEncoded = Tools.generateFormURLEncoded(map);

        Response response = target.request().post(
                Entity.entity(formURLEncoded, MediaType.APPLICATION_FORM_URLENCODED), Response.class);

        short status = (short) response.getStatus();

        if (status == 201) {
            String location = response.getHeaderString(Constants.LOCATION_HEADER.getConstant());
            logger.info("createRepresentationVersion: " + target.getUri() + ", response: " + status + ", Representation version created with URI: " + location);
            return location;
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
    public CloudRecord getCloudRecordRepresentations(String cloudId) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId);
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        short status = (short) response.getStatus();

        if (status == 200) {
            CloudRecord cloudRecord = response.readEntity(CloudRecord.class);
            logger.info("getCloudRecordRepresentations: " + target.getUri() + ", response: " + status + ", Returned a list of results!");
            return cloudRecord;
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
    public short deleteCloudRecordRepresentationsAndVersions(String cloudId) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId);
        Response response = target.request(MediaType.APPLICATION_JSON).delete();

        short status = (short) response.getStatus();

//        System.out.println(response.readEntity(String.class));
        if (status == 204) {
            logger.info("deleteCloudRecordRepresentationsAndVersions: " + target.getUri() + ", response: " + status + ", CloudId: " + cloudId + ", all representations and versions deleted!");
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
    public RepresentationVersion[] getRepresentations(String cloudId) throws NoContentException, DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant());
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        short status = (short) response.getStatus();

        if (status == 200) {
            RepresentationVersion[] representationVersions = response.readEntity(RepresentationVersion[].class);
            logger.info("getRepresentations: " + target.getUri() + ", response: " + status + ", Returned a list of results!");
            return representationVersions;
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
    public RepresentationVersion getRepresentation(String cloudId, String representationName) throws NoContentException, DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName);
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        short status = (short) response.getStatus();

        if (status == 200) {
            RepresentationVersion representationVersion = response.readEntity(RepresentationVersion.class);
            logger.info("getRepresentation: " + target.getUri() + ", response: " + status + ", Representation with name: " + representationName + " exists!");
            return representationVersion;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 204:
                    throw new NoContentException(errorString);
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
    public RepresentationVersion[] getRepresentationVersions(String cloudId, String representationName) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant());
        Response response = target.request(MediaType.APPLICATION_XML).get();

        short status = (short) response.getStatus();

        if (status == 200) {
            RepresentationVersion[] representationVersions = response.readEntity(RepresentationVersion[].class);
            logger.info("getRepresentationVersions: " + target.getUri() + ", response: " + status + ", Returned a list of results!");
            return representationVersions;
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
    public RepresentationVersion getRepresentationVersion(String cloudId, String representationName, String version) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant()).path(version);
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        short status = (short) response.getStatus();
//        System.out.println(response.readEntity(String.class));
//        System.out.println(response.getHeaders());

        if (status == 200) {
            RepresentationVersion representationVersion = response.readEntity(RepresentationVersion.class);
            logger.info("getRepresentationVersion: " + target.getUri() + ", response: " + status + ", CloudId: " + cloudId + ", and Representation name: " + representationName + ", and version: " + version + " exists!");
            return representationVersion;
        }
        else{
//            System.out.println(response.readEntity(String.class));
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
    public short deleteRepresentation(String cloudId, String representationName) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName);
        Response response = target.request(MediaType.APPLICATION_JSON).delete();

        short status = (short) response.getStatus();

//        System.out.println(response.readEntity(String.class));
        if (status == 204) {
            logger.info("deleteRepresentation: " + target.getUri() + ", response: " + status + ", CloudId: " + cloudId + ", RepresentationName: " + representationName + " deleted!");
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
    public short deleteRepresentationVersion(String cloudId, String representationName, String version) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant()).path(version);
        Response response = target.request().delete();

        short status = (short) response.getStatus();

        if (status == 204) {
            logger.info("deleteRepresentationVersion: " + target.getUri() + ", response: " + status + ", CloudId: " + cloudId + ", RepresentationName: " + representationName + ", Version: " + version + " deleted!");
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
    public String copyRepresentationVersionWithContents(String cloudId, String representationName, String version) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant()).path(version).path(Constants.COPY_PATH.getConstant());

        Response response = target.request().post(Entity.entity(null, MediaType.APPLICATION_JSON), Response.class);

        short status = (short) response.getStatus();

        if (status == 201) {
            String location = response.getHeaderString(Constants.LOCATION_HEADER.getConstant());
            logger.info("copyRepresentationVersionWithContents: " + target.getUri() + ", response: " + status + ", Representation version copied with URI: " + location);
            return location;
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
    public short updatePermissionsForRepresentationVersion(String cloudId, String representationName, String version, String permission, String toUsername) {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant()).path(version).path(Constants.PERMISSIONS_PATH.getConstant()).path(permission)
                .path(Constants.USERS_PATH.getConstant()).path(toUsername);

        Response response = target.request().post(null, Response.class);

        short status = (short) response.getStatus();

        if (status == 200) {
            logger.info("updatePermissionsForRepresentationVersion: " + target.getUri() + ", response: " + status + ", Representation version has permissions: " + permission + ", for user: " + toUsername);
            return status;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public short deletePermissionsForRepresentationVersion(String cloudId, String representationName, String version, String permission, String toUsername) {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant()).path(version).path(Constants.PERMISSIONS_PATH.getConstant()).path(permission)
                .path(Constants.USERS_PATH.getConstant()).path(toUsername);

        Response response = target.request().delete();

        short status = (short) response.getStatus();

        if (status == 204) {
            logger.info("deletePermissionsForRepresentationVersion: " + target.getUri() + ", response: " + status + ", Representation version deleted permissions: " + permission + ", for user: " + toUsername);
            return status;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public short permitRepresentationVersion(String cloudId, String representationName, String version) {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant()).path(version).path(Constants.PERMIT_PATH.getConstant());

        Response response = target.request().post(null, Response.class);

        short status = (short) response.getStatus();
//        System.out.println(response.readEntity(String.class));

        if (status == 200) {
            logger.info("permitRepresentationVersion: " + target.getUri() + ", response: " + status + ", Representation version is permitted for public access");
            return status;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public String persistRepresentationVersion(String cloudId, String representationName, String version) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant()).path(version).path(Constants.PERSIST_PATH.getConstant());

        Response response = target.request().post(Entity.entity(null, MediaType.APPLICATION_JSON), Response.class);

        short status = (short) response.getStatus();

        if (status == 201) {
            String location = response.getHeaderString(Constants.LOCATION_HEADER.getConstant());
            logger.info("copyRepresentationVersionWithContents: " + target.getUri() + ", response: " + status + ", Representation version persisted with URI: " + location);
            return location;
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
    public String addFileToRepresentationVersion(String cloudId, String representationName, String version, File file, String mimeType, String fileName) throws BadRequest, DoesNotExistException, AlreadyExistsException, MethodNotAllowedException {
        return uploadFileToRepresentationVersion(cloudId, representationName, version, file, mimeType, fileName);
    }

    @Override
    public String addFileToRepresentationVersion(String cloudId, String representationName, String version, File file) throws BadRequest, DoesNotExistException, AlreadyExistsException, MethodNotAllowedException {
        return uploadFileToRepresentationVersion(cloudId, representationName, version, file, null, null);
    }

    @Override
    public String updateFileToRepresentationVersion(String cloudId, String representationName, String version, String fileName, File file, String mimeType) throws DoesNotExistException, MethodNotAllowedException {
        WebTarget target = client.register(MultiPartFeature.class).target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant()).path(version).path(Constants.FILES_PATH.getConstant()).path(fileName);

        final FileDataBodyPart filePart =
                new FileDataBodyPart("data", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);

        FormDataMultiPart multiPartEntity = new FormDataMultiPart();
        if (mimeType != null && !mimeType.equals(""))
            multiPartEntity.field(Constants.MIMETYPE_FIELD.getConstant(), mimeType);
        multiPartEntity.bodyPart(filePart);

        Response response =
                target.request().put(Entity.entity(multiPartEntity, MediaType.MULTIPART_FORM_DATA),
                        Response.class);

        short status = (short) response.getStatus();

        if (status == 204) {
            String location = response.getHeaderString(Constants.LOCATION_HEADER.getConstant());
            logger.info("updateFileToRepresentationVersion: " + target.getUri() + ", response: " + status +
                    ", Updated file with URI: " + location);
            return location;
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 405:
                    throw new MethodNotAllowedException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    @Override
    public String updateFileToRepresentationVersion(String cloudId, String representationName, String version, String fileName, File file) throws DoesNotExistException, MethodNotAllowedException {
        return updateFileToRepresentationVersion(cloudId, representationName, version, fileName, file, null);
    }

    @Override
    public String getFileFromRepresentationVersion(String cloudId, String representationName, String version, String fileName, String downloadDirectory) throws DoesNotExistException, RangeHeaderInvalidException, IOException {
        return downloadFileFromRepresentationVersion(cloudId, representationName, version, fileName, downloadDirectory, -1, -1);
    }

    @Override
    public String getPartialFileFromRepresentationVersion(String cloudId, String representationName, String version, String fileName, String downloadDirectory, long rangeFrom, long rangeTo) throws DoesNotExistException, RangeHeaderInvalidException, IOException {
        return downloadFileFromRepresentationVersion(cloudId, representationName, version, fileName, downloadDirectory, rangeFrom, rangeTo);
    }

    @Override
    public MultivaluedMap<String, Object> getHeadersForFileFromRepresentationVersion(String cloudId, String representationName, String version, String fileName) throws DoesNotExistException {
        WebTarget target = client.register(MultiPartFeature.class).target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant()).path(version).path(Constants.FILES_PATH.getConstant()).path(fileName);
        Response response = target.request().head();

        short status = (short) response.getStatus();

        if (status == 200) {
            logger.info("getHeadersForFileFromRepresentationVersion: " + target.getUri() + ", response: " + status + ", representationName: " + representationName + ", version: " + version + " exists!");
            return response.getHeaders();
        }
        else{
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status;
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
     * Download a file of a specific Representation Version FileName.
     * It is a wrap up of the public calls for downloading a file of a specific Representation Version FileName combined.
     * @param cloudId
     * @param representationName
     * @param version
     * @param fileName
     * @param downloadDirectory
     * @param rangeFrom
     * @param rangeTo
     * @return The location of the new file in the file system
     * @throws DoesNotExistException
     * @throws RangeHeaderInvalidException
     * @throws IOException
     */
    private String downloadFileFromRepresentationVersion(String cloudId, String representationName, String version, String fileName, String downloadDirectory, long rangeFrom, long rangeTo) throws DoesNotExistException, RangeHeaderInvalidException, IOException {
        WebTarget target = client.register(MultiPartFeature.class).target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant()).path(version).path(Constants.FILES_PATH.getConstant()).path(fileName);
        File downloadFile = Paths.get(downloadDirectory, fileName).toFile();
        downloadFile.getParentFile().mkdirs();
        downloadFile.createNewFile(); //Check if it can be created before making any calls.
        Response response;
        if(rangeFrom == -1)
             response = target.request().get();
        else
            response = target.request().header(HttpHeaders.RANGE, "bytes=" + rangeFrom + "-" + (rangeTo==-1?"":rangeTo)).get();

        short status = (short) response.getStatus();

        if (status == 200) {
            byte[] bytes = response.readEntity(byte[].class);
            FileUtils.writeByteArrayToFile(downloadFile, bytes);
            logger.info("getFileFromRepresentationVersion: " + target.getUri() + ", response: " + status + ", representationName: " + representationName + ", version: " + version + ", fileName: " + fileName + " stored at: " + downloadFile.toString());
            return downloadFile.toString();
        }
        else if(status == 206)
        {
            byte[] bytes = response.readEntity(byte[].class);
            try (FileOutputStream output = new FileOutputStream(downloadFile, true)) {
                output.write(bytes);
            }
            logger.info("getPartialFileFromRepresentationVersion: " + target.getUri() + ", response: " + status + ", representationName: " + representationName + ", version: " + version + ", fileName: " + fileName + " appended and stored at: " + downloadFile.toString());
            return downloadFile.toString();
        }
        else{
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            String errorString = "Target URI: " + target.getUri() + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
            logger.error(errorString);
            switch (status)
            {
                case 404:
                    throw new DoesNotExistException(errorString);
                case 416:
                    throw new RangeHeaderInvalidException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }

    /**
     * Upload a file to a specific Representation Version.
     * It is a wrap up of the public calls of uploading a file to a specific Representation Version combined.
     * @param cloudId
     * @param representationName
     * @param version
     * @param file
     * @param mimeType
     * @param fileName
     * @return The location URI of the new Representation Version
     * @throws BadRequest
     * @throws DoesNotExistException
     * @throws AlreadyExistsException
     * @throws MethodNotAllowedException
     */
    private String uploadFileToRepresentationVersion(String cloudId, String representationName, String version, File file, String mimeType, String fileName) throws BadRequest, DoesNotExistException, AlreadyExistsException, MethodNotAllowedException {
        WebTarget target = client.register(MultiPartFeature.class).target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant()).path(version).path(Constants.FILES_PATH.getConstant());

        final FileDataBodyPart filePart =
                new FileDataBodyPart("data", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);

        FormDataMultiPart multiPartEntity = new FormDataMultiPart();
        if (mimeType != null && !mimeType.equals(""))
            multiPartEntity.field(Constants.MIMETYPE_FIELD.getConstant(), mimeType);
        if (fileName != null && !fileName.equals(""))
            multiPartEntity.field(Constants.FILENAME_FIELD.getConstant(), "testPNG");
        multiPartEntity.bodyPart(filePart);

        Response response =
                target.request().post(Entity.entity(multiPartEntity, MediaType.MULTIPART_FORM_DATA),
                        Response.class);

        short status = (short) response.getStatus();

        if (status == 201) {
            String location = response.getHeaderString(Constants.LOCATION_HEADER.getConstant());
            logger.info("addFileToRepresentationVersion: " + target.getUri() + ", response: " + status +
                    ", Added file with URI: " + location);
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
                case 404:
                    throw new DoesNotExistException(errorString);
                case 405:
                    throw new MethodNotAllowedException(errorString);
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
    public short deleteFileFromRepresentationVersion(String cloudId, String representationName, String version, String fileName) throws DoesNotExistException, MethodNotAllowedException {
        WebTarget target = client.register(MultiPartFeature.class).target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant()).path(version).path(Constants.FILES_PATH.getConstant()).path(fileName);

        Response response = target.request().delete();

        short status = (short) response.getStatus();

        if (status == 204) {
            logger.info("deleteFileFromRepresentationVersion: " + target.getUri() + ", response: " + status + ", representationName: " + representationName + ", version: " + version + ", fileName: " + fileName + " deleted!");
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
                case 405:
                    throw new MethodNotAllowedException(errorString);
                case 500:
                    throw new InternalServerErrorException(errorString);
                default:
                    throw new UnsupportedOperationException(errorString);
            }
        }
    }
}
