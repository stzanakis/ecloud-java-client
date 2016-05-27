package europeana.eu.commons;

import europeana.eu.accessors.base.UniqueIdentifierServiceAccessorBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-25
 */
public class AccessorsManager {
    private static final Logger logger = LogManager.getLogger();
    private UniqueIdentifierServiceAccessorBase uniqueIdentifierServiceAccessorBase;

    public void InitializeAllAccessors(String accessUrl, String username, String password)
    {
        logger.info("Initializing all http clients");
        uniqueIdentifierServiceAccessorBase = new UniqueIdentifierServiceAccessorBase(accessUrl, username, password);
        logger.info("Initialized all http clients");
    }

    public UniqueIdentifierServiceAccessorBase getUniqueIdentifierServiceAccessorBase() {
        return uniqueIdentifierServiceAccessorBase;
    }

    public void setUniqueIdentifierServiceAccessorBase(UniqueIdentifierServiceAccessorBase uniqueIdentifierServiceAccessorBase) {
        this.uniqueIdentifierServiceAccessorBase = uniqueIdentifierServiceAccessorBase;
    }
}
