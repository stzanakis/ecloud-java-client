package europeana.eu.accessors;

import europeana.eu.exceptions.*;
import europeana.eu.model.CloudRecord;
import europeana.eu.model.RepresentationVersion;
import europeana.eu.model.ResultsSlice;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NoContentException;
import java.io.File;
import java.io.IOException;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-30
 */
public interface MetadataAndContentServiceAccessor {

    /**
     * Creates a new data set.
     * Analogous URL: POST base-url/data-providers/DATAPROVIDER/data-sets/
     * @param providerId
     * @param dataSetId
     * @param description
     * @return The location URI of the new Data Set
     * @throws DoesNotExistException
     * @throws AlreadyExistsException
     */
    String createDataSet(String providerId, String dataSetId, String description) throws DoesNotExistException, AlreadyExistsException;

    /**
     * Lists representation versions from data set. Result is returned in slices.
     * Analogous URL: GET base-url/data-providers/DATAPROVIDER/data-sets/DATASET/
     * @param providerId
     * @param dataSetId
     * @return {@link europeana.eu.model.ResultsSlice}
     * @throws DoesNotExistException
     */
    ResultsSlice<RepresentationVersion> getDataSet(String providerId, String dataSetId) throws DoesNotExistException;

    /**
     * Updates description of a data set.
     * Analogous URL: PUT base-url/data-providers/DATAPROVIDER/data-sets/DATASET/
     * @param providerId
     * @param dataSetId
     * @param description
     * @return HTTP Response code or throws exception
     * @throws DoesNotExistException
     */
    short updateDataSetDescription(String providerId, String dataSetId, String description) throws DoesNotExistException;

    /**
     * Deletes data set.
     * Analogous URL: DELETE base-url/data-providers/DATAPROVIDER/data-sets/DATASET/
     * @param providerId
     * @param dataSetId
     * @return HTTP Response code or throws exception
     * @throws DoesNotExistException
     */
    short deleteDataSet(String providerId, String dataSetId) throws DoesNotExistException;

    /**
     * Returns record with all representations with file metadata.
     * Analogous URL: GET base-url/data-providers/DATAPROVIDER/records/LOCALID/
     * @param providerId
     * @param recordId
     * @return {@link europeana.eu.model.CloudRecord}
     * @throws DoesNotExistException
     */
    CloudRecord getCloudRecordWithSimplifiedUrl(String providerId, String recordId) throws DoesNotExistException;

    /**
     * Returns the latest persistent version of a given representation.
     * Analogous URL: GET base-url/data-providers/DATAPROVIDER/records/LOCALID/representations/REPRESENTATIONNAME/
     * @param providerId
     * @param recordId
     * @param representationName
     * @return {@link europeana.eu.model.RepresentationVersion}
     * @throws DoesNotExistException
     */
    RepresentationVersion getRepresentationWithSimplifiedUrl(String providerId, String recordId, String representationName) throws DoesNotExistException;

    /**
     * Retrieve a file and store it in the specified directory.
     * Analogous URL: GET base-url/data-providers/DATAPROVIDER/records/LOCALID/representations/REPRESENTATIONNAME/FILENAME/
     * @param providerId
     * @param recordId
     * @param representationName
     * @param fileName
     * @param dowloadDirectory
     * @return The location of the new file in the file system
     * @throws IOException
     * @throws DoesNotExistException
     */
    String getFileFromLatestPersistentWithSimplifiedUrl(String providerId, String recordId, String representationName, String fileName, String dowloadDirectory) throws IOException, DoesNotExistException;

    /**
     * Retrieve the HTTP Headers of a GET File from Representation Version
     * @param providerId
     * @param recordId
     * @param representationName
     * @param fileName
     * @return {@link javax.ws.rs.core.MultivaluedMap} containing the HTTP Headers of the response
     * @throws IOException
     * @throws DoesNotExistException
     */
    MultivaluedMap<String, Object> getHeadersFromFileFromLatestPersistentWithSimplifiedUrl(String providerId, String recordId, String representationName, String fileName) throws IOException, DoesNotExistException;

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


    /**
     * Returns record with all its latest persistent representations.
     * Analogous URL: GET base-url/records/CLOUDID/
     * @param cloudId
     * @return {@link europeana.eu.model.CloudRecord}
     * @throws DoesNotExistException
     */
    CloudRecord getCloudRecordRepresentations(String cloudId) throws DoesNotExistException;

    /**
     * Deletes all the representations and versions of a record with a specific cloudId. Does not remove any Cloud Id mappings created previously
     * in the Unique Identifier Service.
     * Analogous URL: DELETE base-url/records/CLOUDID/
     * @param clooudId
     * @return HTTP Response code or throws exception
     * @throws DoesNotExistException
     */
    short deleteCloudRecordRepresentationsAndVersions(String clooudId) throws DoesNotExistException;

    /**
     * Returns a list of all the latest persistent versions of a record representation without files details content.
     * Analogous URL: GET base-url/records/CLOUDID/representations/
     * @param cloudId
     * @return Array of {@link europeana.eu.model.RepresentationVersion}
     * @throws NoContentException
     * @throws DoesNotExistException
     */
    RepresentationVersion[] getRepresentations(String cloudId) throws NoContentException, DoesNotExistException;

    /**
     * Returns the latest persistent version of a given representation with files details content.
     * Analogous URL: GET base-url/records/CLOUDID/representations/REPRESENTATIONNAME/
     * @param cloudId
     * @param representationName
     * @return
     * @throws NoContentException
     * @throws DoesNotExistException
     */
    RepresentationVersion getRepresentation(String cloudId, String representationName) throws NoContentException, DoesNotExistException;

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
     * Copies all information with all files and their contents from one representation version to a new non-persistent temporary Representation Version.
     * Analogous URL: POST base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/VERSION/copy/
     * @param cloudId
     * @param representationName
     * @param version
     * @return The location URI of the new Representation Version
     * @throws DoesNotExistException
     */
    String copyRepresentationVersionWithContents(String cloudId, String representationName, String version) throws DoesNotExistException;

    /**
     * Modify authorization of versions operation. Updates authorization for a specific representation version.
     * Analogous URL: POST base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/VERSION/permissions/permission/users/username/
     * @param cloudId
     * @param representationName
     * @param version
     * @param permission
     * @param toUsername
     * @return HTTP Response code or throws exception
     */
    short updatePermissionsForRepresentationVersion(String cloudId, String representationName, String version, String permission, String toUsername);

    /**
     * Removes permissions for selected user to selected representation version.
     * Analogous URL: POST base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/VERSION/permissions/permission/users/username/
     * @param cloudId
     * @param representationName
     * @param version
     * @param permission
     * @param toUsername
     * @return HTTP Response code or throws exception
     */
    short deletePermissionsForRepresentationVersion(String cloudId, String representationName, String version, String permission, String toUsername);

    /**
     * Makes a Representation Version publicly available.
     * Analogous URL: POST base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/VERSION/permit/
     * @param cloudId
     * @param representationName
     * @param version
     * @return HTTP Response code or throws exception
     */
    short permitRepresentationVersion(String cloudId, String representationName, String version);

    /**
     * Persists temporary(non-persistent) representation.
     * Analogous URL: POST base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/VERSION/persist/
     * @param cloudId
     * @param representationName
     * @param version
     * @return The location URI of the Representation Version, same as before
     * @throws DoesNotExistException
     */
    String persistRepresentationVersion(String cloudId, String representationName, String version) throws DoesNotExistException;

    /**
     * Adds a new file to representation version.
     * Analogous URL: POST base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/VERSION/files/
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
    String addFileToRepresentationVersion(String cloudId, String representationName, String version, File file, String mimeType, String fileName) throws BadRequest, DoesNotExistException, AlreadyExistsException, MethodNotAllowedException;

    /**
     * Adds a new file to representation version.
     * Mime type will be empty and fileName will be generated from the system.
     * Analogous URL: POST base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/VERSION/files/
     * @param cloudId
     * @param representationName
     * @param version
     * @param file
     * @return The location URI of the new Representation Version
     * @throws BadRequest
     * @throws DoesNotExistException
     * @throws AlreadyExistsException
     * @throws MethodNotAllowedException
     */
    String addFileToRepresentationVersion(String cloudId, String representationName, String version, File file) throws BadRequest, DoesNotExistException, AlreadyExistsException, MethodNotAllowedException;

    /**
     * Updates the file contents of a representation version fileName.
     * Analogous URL: PUT base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/VERSION/files/FILENAME/
     * @param cloudId
     * @param representationName
     * @param version
     * @param fileName
     * @param file
     * @param mimeType
     * @return The location URI of the new Representation Version
     * @throws DoesNotExistException
     * @throws MethodNotAllowedException
     */
    String updateFileToRepresentationVersion(String cloudId, String representationName, String version, String fileName, File file, String mimeType) throws DoesNotExistException, MethodNotAllowedException;

    /**
     * Updates the file contents of a representation version fileName.
     * Analogous URL: PUT base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/VERSION/files/FILENAME/
     * @param cloudId
     * @param representationName
     * @param version
     * @param fileName
     * @param file
     * @return The location URI of the new Representation Version
     * @throws DoesNotExistException
     * @throws MethodNotAllowedException
     */
    String updateFileToRepresentationVersion(String cloudId, String representationName, String version, String fileName, File file) throws DoesNotExistException, MethodNotAllowedException;

    /**
     * Retrieve a file and store it in the specified directory.
     * Analogous URL: GET base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/VERSION/files/FILENAME/
     * @param cloudId
     * @param representationName
     * @param version
     * @param fileName
     * @param downloadDirectory
     * @return The location of the new file in the file system
     * @throws DoesNotExistException
     * @throws RangeHeaderInvalidException
     * @throws IOException
     */
    String getFileFromRepresentationVersion(String cloudId, String representationName, String version, String fileName, String downloadDirectory) throws DoesNotExistException, RangeHeaderInvalidException, IOException;

    /**
     * Retrieve partial content of a file and store it in the specified directory.
     * The content retrieved is written to the newly created file or appended to an already existent file.
     * Uses the Range HTTP Header.
     * Analogous URL: GET base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/VERSION/files/FILENAME/
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
    String getPartialFileFromRepresentationVersion(String cloudId, String representationName, String version, String fileName, String downloadDirectory, long rangeFrom, long rangeTo) throws DoesNotExistException, RangeHeaderInvalidException, IOException;

    /**
     * Retrieve the HTTP Headers of a potential GET File from Representation Version
     * Analogous URL: HEAD base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/VERSION/files/FILENAME/
     * @param cloudId
     * @param representationName
     * @param version
     * @param fileName
     * @return {@link javax.ws.rs.core.MultivaluedMap} containing the HTTP Headers of the response
     * @throws DoesNotExistException
     */
    MultivaluedMap<String, Object> getHeadersForFileFromRepresentationVersion(String cloudId, String representationName, String version, String fileName) throws DoesNotExistException;

    /**
     * Deletes file from representation version.
     * Analogous URL: DELETE base-url/records/CLOUDID/representations/REPRESENTATIONNAME/versions/VERSION/files/FILENAME:(.+)?/
     * @param cloudId
     * @param representationName
     * @param version
     * @param fileName
     * @return HTTP Response code or throws exception
     * @throws DoesNotExistException
     * @throws MethodNotAllowedException
     */
    short deleteFileFromRepresentationVersion(String cloudId, String representationName, String version, String fileName) throws DoesNotExistException, MethodNotAllowedException;

    /**
     * Release all resources
     */
    void close();
}
