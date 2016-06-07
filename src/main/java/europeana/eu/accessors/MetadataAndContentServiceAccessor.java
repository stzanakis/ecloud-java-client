package europeana.eu.accessors;

import europeana.eu.exceptions.AlreadyExistsException;
import europeana.eu.exceptions.BadRequest;
import europeana.eu.exceptions.DoesNotExistException;
import europeana.eu.exceptions.MethodNotAllowedException;
import europeana.eu.model.RepresentationVersion;

import javax.ws.rs.core.NoContentException;
import java.io.File;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-30
 */
public interface MetadataAndContentServiceAccessor {
    /**
     * Create a new Representation Version
     * Analogous URL: POST base-url/records/CLOUDID/representations/REPRESENTATIONNAME/
     * Requires a form parameter to be send in the body in the form providerId=exampleProvider
     * @param cloudId
     * @param representationName
     * @param providerId
     * @return The location URI of the new Representation Version
     * @throws DoesNotExistException
     */
    String createRepresentationVersion(String cloudId, String representationName, String providerId) throws DoesNotExistException;


    /**Returns record with all its latest persistent representations.
     * Analogous URL: GET base-url/records/CLOUDID/
     * todo Fix when its fixed internally in
     * @param cloudId
     * @return
     * @throws DoesNotExistException
     */
    String getRecordRepresentations(String cloudId) throws DoesNotExistException;

    /**
     * Returns a list of all the latest persistent versions of a record representation.
     * Analogous URL: GET base-url/records/CLOUDID/representations/
     * todo Fix when its fixed internally in ECloud
     * @param cloudId
     * @return
     * @throws NoContentException
     * @throws DoesNotExistException
     */
    String getRepresentations(String cloudId) throws NoContentException, DoesNotExistException;

    /**
     * Returns the latest persistent version of a given representation.
     * Analogous URL: GET base-url/records/CLOUDID/representations/REPRESENTATIONNAME/
     * todo Fix when its fixed internally in ECloud
     * @param cloudId
     * @param representationName
     * @return
     * @throws NoContentException
     * @throws DoesNotExistException
     */
    String getRepresentation(String cloudId, String representationName) throws NoContentException, DoesNotExistException;

    /**
     * Lists all versions of a record representation. Temporary versions will be included in the returned list.
     * Analogous URL: GET base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/
     * @param cloudId
     * @param representationName
     * @return Array of {@link europeana.eu.model.RepresentationVersion}
     * @throws DoesNotExistException
     */
    RepresentationVersion[] getRepresentationVersions(String cloudId, String representationName) throws DoesNotExistException;

    /**
     * Returns representation in a specified version.
     * Analogous URL: GET base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/VERSION/
     * @param cloudId
     * @param representationName
     * @param version
     * @return {@link europeana.eu.model.RepresentationVersion}
     * @throws DoesNotExistException
     */
    RepresentationVersion getRepresentationVersion(String cloudId, String representationName, String version) throws DoesNotExistException;

    /**
     * Deletes representation with all of its versions for a given cloudId.
     * Analogous URL: GET base-url/records/CLOUDID/representations/REPRESENTATIONNAME/
     * @param cloudId
     * @param representationName
     * @return HTTP Response code or throws exception
     * @throws DoesNotExistException
     */
    short deleteRepresentation(String cloudId, String representationName) throws DoesNotExistException;

    /**
     * Deletes representation version.
     * Analogous URL: GET base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/VERSION/
     * @param cloudId
     * @param representationName
     * @param version
     * @return HTTP Response code or throws exception
     * @throws DoesNotExistException
     */
    short deleteRepresentationVersion(String cloudId, String representationName, String version) throws DoesNotExistException;

    /**
     * @param cloudId
     * @param representationName
     * @param version
     * @param file
     * @param mimeType
     * @return
     * @throws BadRequest
     * @throws DoesNotExistException
     * @throws AlreadyExistsException
     * @throws MethodNotAllowedException
     */
    String addFileToRepresentationVersion(String cloudId, String representationName, String version, File file, String mimeType) throws BadRequest, DoesNotExistException, AlreadyExistsException, MethodNotAllowedException;

    /**
     * @param cloudId
     * @param representationName
     * @param version
     * @param fileName
     * @return
     * @throws DoesNotExistException
     * @throws MethodNotAllowedException
     */
    short deleteFileFromRepresentationVersion(String cloudId, String representationName, String version, String fileName) throws DoesNotExistException, MethodNotAllowedException;

    /**
     * Release all resources
     */
    void close();
}
