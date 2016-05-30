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

    CloudId createNewCloudId(String providerId) throws DoesNotExistException, AlreadyExistsException;
    CloudId createNewCloudId(String providerId, String recordId) throws DoesNotExistException, AlreadyExistsException;

    short deleteCloudId(String cloudId) throws DoesNotExistException;

    CloudIdsSlice getCloudIdsOfProvider(String providerId) throws DoesNotExistException;
    CloudIdsSlice getCloudIdsOfProvider(String providerId, String from, int to) throws DoesNotExistException;

    short createDataProvider(String providerId, String organizationUrl, String email, String digitalLibraryUrl, String organizationName, String remarks, String contactPersonName) throws AlreadyExistsException, BadRequest;

    DataProvider getProvider(String organizationName) throws BadRequest, DoesNotExistException, NoContentException;

    short deleteDataProvider(String providerId) throws DoesNotExistException;
}
