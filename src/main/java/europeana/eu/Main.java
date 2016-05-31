package europeana.eu;

import europeana.eu.commons.AccessorsManager;
import europeana.eu.exceptions.AlreadyExistsException;
import europeana.eu.exceptions.BadRequest;
import europeana.eu.exceptions.DoesNotExistException;
import europeana.eu.exceptions.MethodNotAllowedException;
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
    public static void main(String[] args) throws AlreadyExistsException, BadRequest, DoesNotExistException, NoContentException, JAXBException, FileNotFoundException, ConfigurationException, MethodNotAllowedException {
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

//        CloudIdsSlice cloudIdsSlice = accessorsManager.getUniqueIdentifierServiceAccessorBase()
//                .getCloudIdsOfProvider("STempProvider5");
//        for (CloudId cloudId : cloudIdsSlice.getCloudIds()) {
//            System.out.println(cloudId.getId());
//            System.out.println(Tools.marshallAny(cloudId));
//        }

//        CloudIdsSlice cloudIdsSlice = accessorsManager.getUniqueIdentifierServiceAccessorBase().getCloudIdWithRecordIds("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA");
//        System.out.println(Tools.marshallAny(cloudIdsSlice));
//
//        accessorsManager.getUniqueIdentifierServiceAccessorBase().createMappingRecordIdToCloudId("STempProvider5", "ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA");
//
//        cloudIdsSlice = accessorsManager.getUniqueIdentifierServiceAccessorBase().getCloudIdWithRecordIds("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA");
//        System.out.println(Tools.marshallAny(cloudIdsSlice));

//        accessorsManager.getUniqueIdentifierServiceAccessorBase().getCloudIdWithRecordIds("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA");

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
//        System.out.println(representationVersion);

//          accessorsManager.getMetadataAndContentServiceAccessor().getRepresentations("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA");
//        accessorsManager.getMetadataAndContentServiceAccessor().getRepresentation("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA", "TEST");
//
//
//        RepresentationVersion[] representationVersions = accessorsManager.getMetadataAndContentServiceAccessor().getRepresentationVersions("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA", "TEST");
//        for (RepresentationVersion representationVersion: representationVersions
//             ) {
//            System.out.println(representationVersion.getVersion());
//        }
//
//        RepresentationVersion representationVersion = accessorsManager.getMetadataAndContentServiceAccessor()
//                .getRepresentationVersion("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA", "TEST", "476feab0-2668-11e6-8cf5-fa163e8d4ae3");
//        System.out.println(representationVersion);

//        accessorsManager.getMetadataAndContentServiceAccessor()
//                .deleteRepresentationVersion("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA", "TEST", "1b12a390-2659-11e6-9e71-fa163e64bb83");

//        accessorsManager.getMetadataAndContentServiceAccessor().deleteRepresentation("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA", "TEST");

//        File file = new File("/tmp/test.png");
//        accessorsManager.getMetadataAndContentServiceAccessor()
//                .addFileToRepresentationVersion("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA", "TEST", "476feab0-2668-11e6-8cf5-fa163e8d4ae3", file, "wtf/png");


//        accessorsManager.getMetadataAndContentServiceAccessor()
//                .deleteFileFromRepresentationVersion("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA", "TEST",
//                        "476feab0-2668-11e6-8cf5-fa163e8d4ae3", "6f06b354-3c5a-4fb8-be6a-5401497ad3d1");

//        for (FileMetadata fm :
//                representationVersion.getFilesMetadata()) {
//            accessorsManager.getMetadataAndContentServiceAccessor()
//                    .deleteFileFromRepresentationVersion("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA", "TEST",
//                            "476feab0-2668-11e6-8cf5-fa163e8d4ae3", fm.getFileName());
//        }

//        RECORDS END

        logger.info("Ended in Main");
    }
}
