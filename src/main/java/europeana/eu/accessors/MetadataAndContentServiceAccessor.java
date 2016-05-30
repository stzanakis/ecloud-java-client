package europeana.eu.accessors;

import europeana.eu.exceptions.BadRequest;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-30
 */
public interface MetadataAndContentServiceAccessor {
    String createRepresentationVersion(String cloudId, String representationName, String providerId) throws BadRequest;
}
