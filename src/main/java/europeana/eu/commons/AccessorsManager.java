package europeana.eu.commons;

import europeana.eu.accessors.MetadataAndContentServiceAccessor;
import europeana.eu.accessors.UniqueIdentifierServiceAccessor;
import europeana.eu.accessors.base.MetadataAndContentServiceAccessorBase;
import europeana.eu.accessors.base.UniqueIdentifierServiceAccessorBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-25
 */
public class AccessorsManager {
    private static final Logger logger = LogManager.getLogger();
    private UniqueIdentifierServiceAccessor uniqueIdentifierServiceAccessor;
    private MetadataAndContentServiceAccessor metadataAndContentServiceAccessor;

    public void InitializeAllAccessors(String accessUrl, String username, String password)
    {
        logger.info("Initializing all http clients");
        uniqueIdentifierServiceAccessor = new UniqueIdentifierServiceAccessorBase(accessUrl, username, password);
        metadataAndContentServiceAccessor = new MetadataAndContentServiceAccessorBase(accessUrl, username, password);
        logger.info("Initialized all http clients");
    }

    public void closeAllAccessors()
    {
        uniqueIdentifierServiceAccessor.close();
        metadataAndContentServiceAccessor.close();
    }

    public UniqueIdentifierServiceAccessor getUniqueIdentifierServiceAccessor() {
        return uniqueIdentifierServiceAccessor;
    }

    public void setUniqueIdentifierServiceAccessor(UniqueIdentifierServiceAccessorBase uniqueIdentifierServiceAccessorBase) {
        this.uniqueIdentifierServiceAccessor = uniqueIdentifierServiceAccessorBase;
    }

    public MetadataAndContentServiceAccessor getMetadataAndContentServiceAccessor() {
        return metadataAndContentServiceAccessor;
    }

    public void setMetadataAndContentServiceAccessor(MetadataAndContentServiceAccessor metadataAndContentServiceAccessor) {
        this.metadataAndContentServiceAccessor = metadataAndContentServiceAccessor;
    }
}
