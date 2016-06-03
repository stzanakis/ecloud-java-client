package europeana.eu.accessors.base;


import europeana.eu.model.*;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileReader;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static org.junit.Assert.assertNotNull;

/**
 * Integration tests, run with caution.
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-06-01
 */

public class UniqueIdentifierServiceAccessorBaseTest_Integration {
    private final static String accessUrl = "https://test-cloud.europeana.eu/api/";
    private final static String credentialsFileName = "credentials.properties";
    private final static String username_key = "username_admin";
    private final static String password_key = "password_admin";
    private static UniqueIdentifierServiceAccessorBase uis;
    private static DataProvider junitTestDataProvider;
    private static LocalId junitTestLocalId;
    private static CloudId junitTestCloudId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
        PropertiesConfigurationLayout configurationPropertiesLayout = new PropertiesConfigurationLayout(propertiesConfiguration);
        configurationPropertiesLayout.load(new FileReader(UniqueIdentifierServiceAccessorBaseTest_Integration.class.getClassLoader().getResource(credentialsFileName).getFile()));
        uis = new UniqueIdentifierServiceAccessorBase(accessUrl, propertiesConfiguration.getProperty(username_key).toString(), propertiesConfiguration.getProperty(password_key).toString());

        junitTestDataProvider = new DataProvider("junitTestProvider", -1, new DataProviderProperties("SOrganizationName", "So-website-example.com", "So-url-example.com",
                "Semail@example.com", "Sdl-website-example.com", "Sdl-url-example.com", "SContactPersonName", "SRemarks"));
        junitTestLocalId = new LocalId(junitTestDataProvider.getId(), "junitTestRecordId");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        uis.close();
    }

    @Test
    public void testCreateGetUpdateActivateDeactivateDeleteDataProviders() throws Exception {
        testCreateDataProvider();
        testGetDataProvider();
        testGetDataProviders();
        testUpdateDataProvider();
        testActivateDataProvider();
        testDeactivateDataProvider();
        testDeleteDataProvider();
    }

    private void testCreateDataProvider() throws Exception {
        DataProviderProperties dpp = junitTestDataProvider.getDataProviderProperties();
        String location = uis.createDataProvider(junitTestDataProvider.getId(), dpp.getOrganisationWebsite(), dpp.getOrganisationWebsiteURL(), dpp.getOfficialAddress(),
                dpp.getDigitalLibraryWebsite(), dpp.getDigitalLibraryURL(), dpp.getOrganisationName(), dpp.getRemarks(), dpp.getContactPerson());
        assertNotNull(location);
        assertNotSame(location, "");
    }

    private void testGetDataProvider() throws Exception {
        DataProvider provider = uis.getDataProvider(junitTestDataProvider.getId());
        assertNotNull(provider);
    }

    public void testGetDataProviders() throws Exception {
        ResultsSlice<DataProvider> dataProviders = uis.getDataProviders();
        assertNotNull(dataProviders);
    }

    public void testUpdateDataProvider() throws Exception {
        DataProviderProperties dpp = junitTestDataProvider.getDataProviderProperties();
        short status = uis.updateDataProvider(junitTestDataProvider.getId(), dpp.getOrganisationWebsite(), dpp.getOrganisationWebsiteURL(), dpp.getOfficialAddress(),
                dpp.getDigitalLibraryWebsite(), dpp.getDigitalLibraryURL(), dpp.getOrganisationName(), dpp.getRemarks(), dpp.getContactPerson());
        assertEquals(204, status);
    }

    private void testDeleteDataProvider() throws Exception {
        short status = uis.deleteDataProvider(junitTestDataProvider.getId());
        assertEquals(status, 200);
    }

    public void testActivateDataProvider() throws Exception {
        short status = uis.activateDataProvider(junitTestDataProvider.getId());
        assertEquals(200, status);
    }

    public void testDeactivateDataProvider() throws Exception {
        short status = uis.deactivateDataProvider(junitTestDataProvider.getId());
        assertEquals(200, status);
    }

    @Test
    public void testCreateGetMapDeleteCloudId() throws Exception {
        //We need a Data Provider to create CloudIds
        testCreateDataProvider();

        testCreateNewCloudId();
        testGetCloudId();
        testGetCloudIdsOfProvider();
        testCreateMappingRecordIdToCloudId();
        testGetCloudIdWithRecordIds();
        testGetLocalIdOfProvider();
        testDeleteMappingLocalIdFromCloudId();
        testDeleteCloudId();

        testDeleteDataProvider();

    }

    public void testCreateNewCloudId() throws Exception {
        junitTestCloudId = uis.createNewCloudId(junitTestDataProvider.getId(), junitTestLocalId.getRecordId());
        assertNotNull(junitTestCloudId);
    }

    public void testGetCloudId() throws Exception {
        CloudId cloudId = uis.getCloudId(junitTestDataProvider.getId(), junitTestLocalId.getRecordId());
        assertNotNull(cloudId);
    }

    public void testGetCloudIdsOfProvider() throws Exception {
        ResultsSlice<CloudId> cloudIdsOfProvider = uis.getCloudIdsOfProvider(junitTestDataProvider.getId());
        assertNotNull(cloudIdsOfProvider);
        assertEquals(1, cloudIdsOfProvider.getCloudIds().size());
    }

    public void testCreateMappingRecordIdToCloudId() throws Exception {
        CloudId mappingRecordIdToCloudId = uis.createMappingRecordIdToCloudId(junitTestDataProvider.getId(), junitTestCloudId.getId());
        assertNotNull(mappingRecordIdToCloudId);
    }
    public void testGetCloudIdWithRecordIds() throws Exception {
        ResultsSlice<CloudId> cloudIdWithRecordIds = uis.getCloudIdWithRecordIds(junitTestCloudId.getId());
        assertNotNull(cloudIdWithRecordIds);
        assertEquals(2, cloudIdWithRecordIds.getCloudIds().size());
    }

    public void testGetLocalIdOfProvider() throws Exception {
        ResultsSlice<CloudId> localIdsOfProvider = uis.getLocalIdsOfProvider(junitTestDataProvider.getId());
        assertNotNull(localIdsOfProvider);
        assertEquals(2, localIdsOfProvider.getCloudIds().size());
    }

    public void testDeleteMappingLocalIdFromCloudId() throws Exception {
        short status = uis.deleteMappingLocalIdFromCloudId(junitTestDataProvider.getId(), junitTestLocalId.getRecordId());
        assertEquals(200, status);
    }

    public void testDeleteCloudId() throws Exception {
        short status = uis.deleteCloudId(junitTestCloudId.getId());
        assertEquals(200, status);
    }
}