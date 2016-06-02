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
    private final static String accessUrl = "https://test-cloud.europeana.eu/api";
    private final static String credentialsFileName = "credentials.properties";
    private final static String username_key = "username_admin";
    private final static String password_key = "password_admin";
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

    public static String getAccessUrl() {
        return accessUrl;
    }

    public static String getCredentialsFileName() {
        return credentialsFileName;
    }

    public static String getUsername_key() {
        return username_key;
    }

    public static String getPassword_key() {
        return password_key;
    }
}
