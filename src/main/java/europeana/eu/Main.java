package europeana.eu;

import europeana.eu.commons.AccessorsManager;
import europeana.eu.exceptions.AlreadyExistsException;
import europeana.eu.exceptions.BadRequest;
import europeana.eu.exceptions.DoesNotExistException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.NoContentException;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-23
 */
public class Main {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws AlreadyExistsException, BadRequest, DoesNotExistException, NoContentException, JAXBException, FileNotFoundException, ConfigurationException {
        System.out.println("Hello ECloud!");
        logger.info("Started in Main");


//        INITIALIZE START
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
        PropertiesConfigurationLayout configurationPropertiesLayout = new PropertiesConfigurationLayout(propertiesConfiguration);
        configurationPropertiesLayout.load(new FileReader(Main.class.getClassLoader().getResource("credentials.properties").getFile()));

        AccessorsManager accessorsManager = new AccessorsManager();
//        accessorsManager.InitializeAllAccessors("https://test-cloud.europeana.eu/api", "stzanakis", "Pom3ohco");
        accessorsManager.InitializeAllAccessors("https://test-cloud.europeana.eu/api",
                propertiesConfiguration.getProperty("username_admin").toString(), propertiesConfiguration.getProperty("password_admin").toString());
//        INITIALIZE END

//        DATA PROVIDERS START
//        Create Data Provider
//        dataProviderAccessor.createDataProvider("STempProvider11", "So-url-example.com",
//                "Semail@example.com", "Sdl-url-example.com", "SOrganizationName", "SRemarks", "SContactPersonName");

        //Get Data Provider
//        accessorsManager.getUniqueIdentifierServiceAccessorBase().getProvider("STempProvider");
//        DataProvider dataProvider =accessorsManager.getDataProviderAccessorBase().getProvider("STempProvider8");
//        System.out.println(dataProvider.getId());

        //Delete Data Provider
//        accessorsManager.getDataProviderAccessorBase().deleteDataProvider("STempProvider8");
//        DATA PROVIDERS END

//        CLOUD IDS START
//        accessorsManager.getUniqueIdentifierServiceAccessorBase().createNewCloudId("STempProvider5");
//        CloudIdsSlice cloudIdsSlice = accessorsManager.getUniqueIdentifierServiceAccessorBase()
//                .getCloudIdsOfProvider("STempProvider5");
//        System.out.println(accessorsManager.getUniqueIdentifierServiceAccessorBase()
//                .deleteCloudId("JFCBS7OOB3PKQBO7DV7SMMHRJDOJ47JKFVVJIMDQID5VEHRROQPQ"));

//        for (CloudId cloudId : cloudIdsSlice.getCloudIds()) {
//            Tools.marshallAny(cloudId);
//            System.out.println(cloudId.getId());
//            System.out.println(accessorsManager.getUniqueIdentifierServiceAccessorBase()
//                    .deleteCloudId(cloudId.getId()));
//        }
//        System.out.println(cloudIdsSlice.getCloudIds().size());
//        System.out.println(cloudIdsSlice.getNextSlice());
//        CLOUD IDS END

//        RECORDS START
//        String representationVersion = accessorsManager.getMetadataAndContentServiceAccessor().createRepresentationVersion("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA", "TEST", "STempProvider5");
//        RECORDS END

        logger.info("Ended in Main");
    }
}
