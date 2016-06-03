package europeana.eu.accessors;

import europeana.eu.exceptions.AlreadyExistsException;
import europeana.eu.exceptions.BadRequest;
import europeana.eu.exceptions.DoesNotExistException;
import europeana.eu.model.CloudId;
import europeana.eu.model.DataProvider;
import europeana.eu.model.ResultsSlice;

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
     * Analogous URL: GET base-url/cloudIds/cloudId/
     * @param cloudId
     * @return {@link europeana.eu.model.ResultsSlice}
     * @throws DoesNotExistException
     */
    ResultsSlice<CloudId> getCloudIdWithRecordIds(String cloudId) throws DoesNotExistException;

    /**
     * Create a new mapping for a Record Id to a Cloud Id.
     * Analogous URL: POST base-url/data-providers/DATAPROVIDER/cloudIds/CLOUDID
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
     * Analogous URL: POST base-url/data-providers/DATAPROVIDER/cloudIds/CLOUDID
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
     * @return {@link europeana.eu.model.ResultsSlice}
     * @throws DoesNotExistException
     */
    ResultsSlice<CloudId> getCloudIdsOfProvider(String providerId) throws DoesNotExistException;

    /**
     * Get a set of Cloud Ids, in slices, of a specific Data Provider.
     * Analogous URL: GET base-url/data-providers/DATAPROVIDER/cloudIds?from=CLOUDID&to=number
     * @param providerId
     * @param from
     * @param to
     * @return {@link europeana.eu.model.ResultsSlice}
     * @throws DoesNotExistException
     */
    ResultsSlice<CloudId> getCloudIdsOfProvider(String providerId, String from, int to) throws DoesNotExistException;

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
     * @return The location URI of the newly created provider
     * @throws AlreadyExistsException
     * @throws BadRequest
     */
    String createDataProvider(String providerId, String organizationWebsite, String organizationUrl, String email, String digitalLibraryWebsite, String digitalLibraryUrl, String organizationName, String remarks, String contactPersonName) throws AlreadyExistsException, BadRequest;

    /**
     * Update a previously created Data Provider.
     * Analogous URL: POST base-url/data-providers/DATAPROVIDER/
     * @param providerId
     * @param organizationUrl
     * @param email
     * @param digitalLibraryUrl
     * @param organizationName
     * @param remarks
     * @param contactPersonName
     * @return HTTP Response code or throws exception
     * @throws DoesNotExistException
     */
    short updateDataProvider(String providerId, String organizationWebsite, String organizationUrl, String email, String digitalLibraryWebsite, String digitalLibraryUrl, String organizationName, String remarks, String contactPersonName) throws DoesNotExistException;

    /**
     * Get all existent Providers in slices.
     * Analogous URL: GET base-url/data-providers/
     * @return {@link europeana.eu.model.DataProviderSlice}
     * @throws DoesNotExistException
     */
    ResultsSlice<DataProvider> getDataProviders() throws DoesNotExistException;

    /**
     * Get all existent Providers in slices from a specific Provider Id.
     * Analogous URL: GET base-url/data-providers/
     * @param from
     * @return {@link europeana.eu.model.DataProviderSlice}
     * @throws DoesNotExistException
     */
    ResultsSlice<DataProvider> getDataProviders(String from) throws DoesNotExistException;

    /**
     * Retrieve a Data Provider.
     * Analogous URL: GET base-url/data-providers/DATAPROVIDER/
     * @param providerId
     * @return {@link europeana.eu.model.DataProvider}
     * @throws BadRequest
     * @throws DoesNotExistException
     * @throws NoContentException
     */
    DataProvider getDataProvider(String providerId) throws BadRequest, DoesNotExistException, NoContentException;

    /**
     * Delete a Data Provider.
     * Analogous URL: DELETE base-url/data-providers/DATAPROVIDER/
     * @param providerId
     * @return HTTP Response code or throws exception
     * @throws DoesNotExistException
     */
    short deleteDataProvider(String providerId) throws DoesNotExistException;

    /**
     * Set the activate field in the Data Provider model as "true"
     * Analogous URL: PUT base-url/data-providers/DATAPROVIDER/active
     * @param providerId
     * @return HTTP Response code or throws exception
     * @throws DoesNotExistException
     */
    short activateDataProvider(String providerId) throws DoesNotExistException;

    /**
     * Set the activate field in the Data Provider model as "false"
     * Analogous URL: DELETE base-url/data-providers/DATAPROVIDER/active
     * @param providerId
     * @return HTTP Response code or throws exception
     * @throws DoesNotExistException
     */
    short deactivateDataProvider(String providerId) throws DoesNotExistException;

    /**
     * Get all Local Ids, in slices, of a specific Data Provider.
     * Analogous URL: GET base-url/data-providers/DATAPROVIDER/localIds/
     * @param providerId
     * @return {@link europeana.eu.model.ResultsSlice}
     * @throws DoesNotExistException
     */
    ResultsSlice<CloudId> getLocalIdsOfProvider(String providerId) throws DoesNotExistException;

    /**
     * Get a set of Local Ids, in slices, of a specific Data Provider.
     * Analogous URL: GET base-url/data-providers/DATAPROVIDER/localIds?from=LOCALID&to=number
     * @param providerId
     * @param from
     * @param to
     * @return {@link europeana.eu.model.ResultsSlice}
     * @throws DoesNotExistException
     */
    ResultsSlice<CloudId> getLocalIdsOfProvider(String providerId, String from, int to) throws DoesNotExistException;

    /**
     * Delete a Local Id to Cloud Id mapping.
     * Analogous URL: GET base-url/data-providers/DATAPROVIDER/localIds/LOCALID
     * @param providerId
     * @param localId
     * @return HTTP Response code or throws exception
     * @throws DoesNotExistException
     */
    short deleteMappingLocalIdFromCloudId(String providerId, String localId) throws DoesNotExistException;

    /**
     * Release all resources
     */
    void close();
}
