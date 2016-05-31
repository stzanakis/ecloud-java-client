package europeana.eu.accessors;

import europeana.eu.exceptions.AlreadyExistsException;
import europeana.eu.exceptions.BadRequest;
import europeana.eu.exceptions.DoesNotExistException;
import europeana.eu.model.CloudId;
import europeana.eu.model.CloudIdsSlice;
import europeana.eu.model.DataProvider;

import javax.ws.rs.core.NoContentException;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-25
 */
public interface UniqueIdentifierServiceAccessor {

    /**
     * Create new Cloud Id using only a providerId.
     * Analogous URL: POST base-url/cloudIds?providerId=exampleProvider
     *
     * @param providerId
     * @return {@link europeana.eu.model.CloudId}
     * @throws DoesNotExistException
     * @throws AlreadyExistsException
     */
    CloudId createNewCloudId(String providerId) throws DoesNotExistException, AlreadyExistsException;

    /**
     * Create new Cloud Id using providerId and local identifier recordId.
     * Analogous URL: POST base-url/cloudIds?providerId=exampleProvider&recordId=exampleLocalRecord
     * @param providerId
     * @param recordId
     * @return {@link europeana.eu.model.CloudId}
     * @throws DoesNotExistException
     * @throws AlreadyExistsException
     */
    CloudId createNewCloudId(String providerId, String recordId) throws DoesNotExistException, AlreadyExistsException;

    /**
     * Retrieve the Cloud Id of the specific combination of providerId and recordId.
     * Analogous URL: GET base-url/cloudIds?providerId=exampleProvider&recordId=exampleLocalRecord
     * @param providerId
     * @param recordId
     * @return {@link europeana.eu.model.CloudId}
     * @throws DoesNotExistException
     */
    CloudId getCloudId(String providerId, String recordId) throws DoesNotExistException;

    /**
     * Returns a sliced list of the currently Record Ids that are mapped to a specific Cloud Id.
     * @param cloudId
     * @return {@link europeana.eu.model.CloudIdsSlice}
     * @throws DoesNotExistException
     */
    CloudIdsSlice getCloudIdWithRecordIds(String cloudId) throws DoesNotExistException;

    /**
     * Create a new mapping for a Record Id to a Cloud Id.
     * @param providerId
     * @param cloudId
     * @param recordId
     * @return The new created mapping {@link europeana.eu.model.CloudId}
     * @throws DoesNotExistException
     * @throws AlreadyExistsException
     */
    CloudId createMappingRecordIdToCloudId(String providerId, String cloudId, String recordId) throws DoesNotExistException, AlreadyExistsException;

    /**
     * Create a new mapping with an auto generated Record Id to a Cloud Id.
     * @param providerId
     * @param cloudId
     * @return The new created mapping {@link europeana.eu.model.CloudId}
     * @throws DoesNotExistException
     * @throws AlreadyExistsException
     */
    CloudId createMappingRecordIdToCloudId(String providerId, String cloudId) throws DoesNotExistException, AlreadyExistsException;

    /**
     * Delete a previously created Cloud Id.
     * Analogous URL: DELETE base-url/cloudIds/CLOUDID/
     * @param cloudId
     * @return HTTP Response code or throws exception
     * @throws DoesNotExistException
     */
    short deleteCloudId(String cloudId) throws DoesNotExistException;

    /**
     * Get all Cloud Ids, in slices, of a specific Data Provider.
     * Analogous URL: GET base-url/data-providers/DATAPROVIDER/cloudIds/
     * @param providerId
     * @return {@link europeana.eu.model.CloudIdsSlice}
     * @throws DoesNotExistException
     */
    CloudIdsSlice getCloudIdsOfProvider(String providerId) throws DoesNotExistException;

    /**
     * Get a set of Cloud Ids, in slices, of a specific Data Provider.
     * Analogous URL: GET base-url/data-providers/DATAPROVIDER/cloudIds?from=CLOUDID&to=number
     * @param providerId
     * @param from
     * @param to
     * @return {@link europeana.eu.model.CloudIdsSlice}
     * @throws DoesNotExistException
     */
    CloudIdsSlice getCloudIdsOfProvider(String providerId, String from, int to) throws DoesNotExistException;

    /**
     * Create a new Data Provider.
     * Analogous URL: POST base-url/data-providers?providerId=exampleProvider
     * @param providerId
     * @param organizationUrl
     * @param email
     * @param digitalLibraryUrl
     * @param organizationName
     * @param remarks
     * @param contactPersonName
     * @return HTTP Response code or throws exception
     * @throws AlreadyExistsException
     * @throws BadRequest
     */
    short createDataProvider(String providerId, String organizationUrl, String email, String digitalLibraryUrl, String organizationName, String remarks, String contactPersonName) throws AlreadyExistsException, BadRequest;

    /**
     * Retrieve a Data Provider.
     * Analogous URL: GET base-url/data-providers/DATAPROVIDER/
     * @param providerId
     * @return {@link europeana.eu.model.DataProvider}
     * @throws BadRequest
     * @throws DoesNotExistException
     * @throws NoContentException
     */
    DataProvider getProvider(String providerId) throws BadRequest, DoesNotExistException, NoContentException;

    /**
     * Delete a Data Provider.
     * Analogous URL: DELETE base-url/data-providers/DATAPROVIDER/
     * @param providerId
     * @return HTTP Response code or throws exception
     * @throws DoesNotExistException
     */
    short deleteDataProvider(String providerId) throws DoesNotExistException;
}
