package europeana.eu.accessors.base;

import europeana.eu.model.DataProvider;
import europeana.eu.model.DataProviderProperties;
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
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-06-01
 */

public class UniqueIdentifierServiceAccessorBaseTest {
    private final static String accessUrl = "https://test-cloud.europeana.eu/api";
    private final static String credentialsFileName = "credentials.properties";
    private final static String username_key = "username_admin";
    private final static String password_key = "password_admin";
    private static UniqueIdentifierServiceAccessorBase uis;
    private static DataProvider junitTestDataProvider;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
        PropertiesConfigurationLayout configurationPropertiesLayout = new PropertiesConfigurationLayout(propertiesConfiguration);
        configurationPropertiesLayout.load(new FileReader(UniqueIdentifierServiceAccessorBaseTest.class.getClassLoader().getResource(credentialsFileName).getFile()));
        uis = new UniqueIdentifierServiceAccessorBase(accessUrl, propertiesConfiguration.getProperty(username_key).toString(), propertiesConfiguration.getProperty(password_key).toString());

        junitTestDataProvider = new DataProvider("junitTestProvider", -1, new DataProviderProperties("SOrganizationName", "So-website-example.com", "So-url-example.com",
                "Semail@example.com", "Sdl-website-example.com", "Sdl-url-example.com", "SContactPersonName", "SRemarks"));
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        uis.close();
    }

    @Test
    public void testCreateGetDelete() throws Exception {
        testCreateDataProvider();
        testGetDataProvider();
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

    private void testDeleteDataProvider() throws Exception {
        short status = uis.deleteDataProvider(junitTestDataProvider.getId());
        assertEquals(status, 200);
    }





    public void testCreateNewCloudId() throws Exception {

    }

    public void testCreateNewCloudId1() throws Exception {

    }

    public void testGetCloudId() throws Exception {

    }

    public void testGetCloudIdWithRecordIds() throws Exception {

    }

    public void testCreateMappingRecordIdToCloudId() throws Exception {

    }

    public void testCreateMappingRecordIdToCloudId1() throws Exception {

    }

    public void testDeleteCloudId() throws Exception {

    }

    public void testGetCloudIdsOfProvider() throws Exception {

    }

    public void testGetCloudIdsOfProvider1() throws Exception {

    }


    public void testUpdateDataProvider() throws Exception {

    }

    public void testGetDataProviders() throws Exception {

    }

    public void testGetDataProviders1() throws Exception {

    }


    public void testActivateDataProvider() throws Exception {

    }

    public void testDeactivateDataProvider() throws Exception {

    }

    public void testGetLocalIdOfProvider() throws Exception {

    }

    public void testGetLocalIdOfProvider1() throws Exception {

    }

    public void testDeleteMappingLocalIdFromCloudId() throws Exception {

    }
}