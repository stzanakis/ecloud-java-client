package europeana.eu.accessors.base;

import europeana.eu.accessors.MetadataAndContentServiceAccessor;
import europeana.eu.commons.Tools;
import europeana.eu.exceptions.AlreadyExistsException;
import europeana.eu.exceptions.BadRequest;
import europeana.eu.exceptions.DoesNotExistException;
import europeana.eu.exceptions.MethodNotAllowedException;
import europeana.eu.model.Constants;
import europeana.eu.model.RepresentationVersion;
import europeana.eu.model.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NoContentException;
import javax.ws.rs.core.Response;
import java.io.File;
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
            String location = response.getHeaderString(Constants.LOCATION_HEADER.getConstant());
            logger.info("createRepresentationVersion: " + target.getUri() + ", response: " + status + ", Representation version created with URI: " + location);
            return location;
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

    @Override
    public String getRepresentations(String cloudId) throws NoContentException, DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant());
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        short status = (short) response.getStatus();
        System.out.println(response.readEntity(String.class));

        // TODO: 30-5-16 Implement correctly
        if (status == 200) {
//            dataProvider = response.readEntity(DataProvider.class);
            System.out.println(response.readEntity(String.class));
            logger.info("getRepresentations: " + target.getUri() + ", response: " + status + ", Returned a list of results!");
            return null;
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
    public String getRepresentation(String cloudId, String representationName) throws NoContentException, DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName);
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        short status = (short) response.getStatus();
        System.out.println(status);
        System.out.println(response.readEntity(String.class));
        // TODO: 30-5-16 Implement correctly
        if (status == 200) {
            System.out.println(response.readEntity(String.class));
            logger.info("getRepresentation: " + target.getUri() + ", response: " + status + ", Representation with name: " + representationName + " exists!");
            return null;
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
    public RepresentationVersion[] getRepresentationVersions(String cloudId, String representationName) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant());
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        short status = (short) response.getStatus();

        if (status == 200) {
            RepresentationVersion[] representationVersions = response.readEntity(RepresentationVersion[].class);
            logger.info("getRepresentationVersions: " + target.getUri() + ", response: " + status + ", Returned a list of results!");
            return representationVersions;
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
    public RepresentationVersion getRepresentationVersion(String cloudId, String representationName, String version) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant()).path(version);
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        short status = (short) response.getStatus();

        if (status == 200) {
            RepresentationVersion representationVersion = response.readEntity(RepresentationVersion.class);
            logger.info("getRepresentationVersion: " + target.getUri() + ", response: " + status + ", Returned a list of results!");
            return representationVersion;
        }
        else{
            System.out.println(response.readEntity(String.class));
            Result result = response.readEntity(Result.class);
            String errorString = "Response code: " + status + ", ErrorCode=" + result.getErrorCode() + ", Representation version with version: " + version + " exists!";
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
    public short deleteRepresentation(String cloudId, String representationName) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName);
        Response response = target.request(MediaType.APPLICATION_JSON).delete();

        short status = (short) response.getStatus();

        System.out.println(response.readEntity(String.class));
        if (status == 204) {
            logger.info("deleteRepresentation: " + target.getUri() + ", response: " + status + ", representationName: " + representationName + " deleted!");
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

    @Override
    public short deleteRepresentationVersion(String cloudId, String representationName, String version) throws DoesNotExistException {
        WebTarget target = client.target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant()).path(version);
        Response response = target.request(MediaType.APPLICATION_JSON).delete();

        short status = (short) response.getStatus();

        if (status == 204) {
            logger.info("deleteRepresentationVersion: " + target.getUri() + ", response: " + status + ", representationName: " + representationName + ", version: " + version + " deleted!");
        }
        else{
            System.out.println(response.readEntity(String.class));
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

    @Override
    public String addFileToRepresentationVersion(String cloudId, String representationName, String version, File file) throws BadRequest, DoesNotExistException, AlreadyExistsException, MethodNotAllowedException {
        WebTarget target = client.register(MultiPartFeature.class).target(accessorUrl.toString());
        target = target.path(Constants.RECORDS_PATH.getConstant()).path(cloudId).path(Constants.REPRESENTATIONS_PATH.getConstant()).path(representationName)
                .path(Constants.VERSIONS_PATH.getConstant()).path(version).path(Constants.FILES_PATH.getConstant());

        // MediaType of the body part will be derived from the file.
        Map<String,String> map = new HashMap<>();
        map.put("mimeType", "application/png");
        String formURLEncoded = Tools.generateFormURLEncoded(map);

        final FileDataBodyPart filePart =
                new FileDataBodyPart("data", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        FormDataBodyPart formDataBodyPart = new FormDataBodyPart();
        formDataBodyPart.setValue(MediaType.APPLICATION_FORM_URLENCODED_TYPE, formURLEncoded);
        MultiPart multiPartEntity = new MultiPart();
        multiPartEntity.bodyPart(formDataBodyPart);
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
            Result result = response.readEntity(Result.class);
            String errorString = "Response code: " + status + ", ErrorCode=" + result.getErrorCode() + ", Details: " + result.getDetails();
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
            }
        }
        return null;
    }
}
