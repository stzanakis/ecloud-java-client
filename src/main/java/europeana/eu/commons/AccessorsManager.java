package europeana.eu.commons;

import europeana.eu.accessors.AuthenticationAuthorizationServiceAccessor;
import europeana.eu.accessors.MetadataAndContentServiceAccessor;
import europeana.eu.accessors.UniqueIdentifierServiceAccessor;
import europeana.eu.accessors.base.AuthenticationAuthorizationServiceAccessorBase;
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
    private final static String defaultCredentialsPath = "/data/credentials/ecloud-java-client";
    private final static String credentialsFileName = "credentials.properties";
    private final static String username_key = "username_admin";
    private final static String password_key = "password_admin";
    private UniqueIdentifierServiceAccessor uniqueIdentifierServiceAccessor;
    private MetadataAndContentServiceAccessor metadataAndContentServiceAccessor;

    private AuthenticationAuthorizationServiceAccessor authenticationAuthorizationServiceAccessor;

    public void InitializeAllAccessors(String accessUrl, String username, String password)
    {
        logger.info("Initializing all http clients");
        uniqueIdentifierServiceAccessor = new UniqueIdentifierServiceAccessorBase(accessUrl, username, password);
        metadataAndContentServiceAccessor = new MetadataAndContentServiceAccessorBase(accessUrl, username, password);
        authenticationAuthorizationServiceAccessor = new AuthenticationAuthorizationServiceAccessorBase(accessUrl, username, password);
        logger.info("Initialized all http clients");
    }

    public void closeAllAccessors()
    {
        uniqueIdentifierServiceAccessor.close();
        metadataAndContentServiceAccessor.close();
        authenticationAuthorizationServiceAccessor.close();
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

    public AuthenticationAuthorizationServiceAccessor getAuthenticationAuthorizationServiceAccessor() {
        return authenticationAuthorizationServiceAccessor;
    }

    public void setAuthenticationAuthorizationServiceAccessor(AuthenticationAuthorizationServiceAccessor authenticationAuthorizationServiceAccessor) {
        this.authenticationAuthorizationServiceAccessor = authenticationAuthorizationServiceAccessor;
    }

    public static String getAccessUrl() {
        return accessUrl;
    }

    public static String getDefaultCredentialsPath() {
        return defaultCredentialsPath;
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
