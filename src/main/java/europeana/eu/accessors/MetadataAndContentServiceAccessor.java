package europeana.eu.accessors;

import europeana.eu.exceptions.BadRequest;
import europeana.eu.exceptions.DoesNotExistException;
import europeana.eu.model.RepresentationVersion;

import javax.ws.rs.core.NoContentException;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-30
 */
public interface MetadataAndContentServiceAccessor {
    String createRepresentationVersion(String cloudId, String representationName, String providerId) throws BadRequest;
    String getRepresentations(String cloudId) throws NoContentException, DoesNotExistException;
    String getRepresentation(String cloudId, String representationName) throws NoContentException, DoesNotExistException;
    RepresentationVersion[] getRepresentationVersions(String cloudId, String representationName) throws DoesNotExistException;
    RepresentationVersion getRepresentationVersion(String cloudId, String representationName, String version) throws DoesNotExistException;
    short deleteRepresentation(String cloudId, String representationName) throws DoesNotExistException;
    short deleteRepresentationVersion(String cloudId, String representationName, String version) throws DoesNotExistException;
}
