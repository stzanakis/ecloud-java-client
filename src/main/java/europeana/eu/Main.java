package europeana.eu;

import europeana.eu.accessors.UniqueIdentifierServiceAccessor;
import europeana.eu.commons.AccessorsManager;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-23
 */
public class Main {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws Exception {
        System.out.println("Hello ECloud!");
        logger.info("Started in Main");

//        INITIALIZE START
        PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
        PropertiesConfigurationLayout configurationPropertiesLayout = new PropertiesConfigurationLayout(propertiesConfiguration);
        File credentialsFile = new File(AccessorsManager.getDefaultCredentialsPath() + "/" + AccessorsManager.getCredentialsFileName());
        if(credentialsFile.exists())
            configurationPropertiesLayout.load(new FileReader(credentialsFile));
        else
            configurationPropertiesLayout.load(new FileReader(Main.class.getClassLoader().getResource(AccessorsManager.getCredentialsFileName()).getFile()));

        System.out.println(propertiesConfiguration.getProperty(AccessorsManager.getUsername_key()).toString());

        AccessorsManager accessorsManager = new AccessorsManager();
        accessorsManager.InitializeAllAccessors(AccessorsManager.getAccessUrl(),
                propertiesConfiguration.getProperty(AccessorsManager.getUsername_key()).toString(), propertiesConfiguration.getProperty(AccessorsManager.getPassword_key()).toString());
//        accessorsManager.InitializeAllAccessors("https://test-cloud.europeana.eu/api",
//                propertiesConfiguration.getProperty("username").toString(), propertiesConfiguration.getProperty("password").toString());
        UniqueIdentifierServiceAccessor uis = accessorsManager.getUniqueIdentifierServiceAccessor();
//        INITIALIZE END

//        uis.createDataProvider("STempProvider10", "So-website-example.com", "So-url-example.com",
//                "Semail@example.com", "Sdl-website-example.com", "Sdl-url-example.com", "SOrganizationName", "SRemarks", "SContactPersonName");

//        uis.createDataProvider("STempProvider17", "", "", "", "", "", "", "", null);
//

//        uis.updateDataProvider("STempProvider5", "So-website-example.com", "So-url-example.com",
//                "Semail@example.com", "Sdl-website-example.com", "Sdl-url-example.com", "SOrganizationName", "SRemarks", "SContactPersonName");
//

//        uis.activateDataProvider("STempProvider10");
//        uis.deactivateDataProvider("STempProvider10");

//                uis.deleteDataProvider("STempProvider");
//
//        DataProvider provider = uis.getDataProvider("STempProvider10");
//        System.out.println(Tools.marshallAny(provider));

//        DataProviderSlice dataProviders = accessorsManager.getUniqueIdentifierServiceAccessor().getDataProviders();
//        for (DataProvider dp :
//                dataProviders.getDataProviders()) {
////            System.out.println(dp.getId());
//            System.out.println(Tools.marshallAny(dp));
//        }
//        System.out.println(dataProviders.getDataProviders().size());
//        System.out.println(dataProviders.getNextSlice());



//        CLOUD IDS START
//        CloudId cloudId = uis.getCloudId("junitTestProvider", "junitTestRecordId");
//        System.out.println(Tools.marshallAny(cloudId));


//        accessorsManager.getUniqueIdentifierServiceAccessor().createNewCloudId("STempProvider10", "123");
//        ResultsSlice resultsSlice = accessorsManager.getUniqueIdentifierServiceAccessor()
//                .getCloudIdsOfProvider("STempProvider10");
//        System.out.println(Tools.marshallAny(resultsSlice));


//        for (int i = 0; i < resultsSlice.getCloudIds().size(); i++)
//        {
//             accessorsManager.getUniqueIdentifierServiceAccessor().deleteCloudId(((LinkedHashMap)resultsSlice.getCloudIds().get(i)).get("id").toString());
//        }

        //            accessorsManager.getUniqueIdentifierServiceAccessor().deleteCloudId(((CloudId)obj).getId());


//          uis.createMappingRecordIdToCloudId("STempProvider5", "NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "a");

//        ResultsSlice cloudIdWithRecordIds = uis.getCloudIdWithRecordIds("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A");
//        System.out.println(Tools.marshallAny(cloudIdWithRecordIds));


//        accessorsManager.getUniqueIdentifierServiceAccessor().deleteMappingLocalIdFromCloudId("STempProvider10", "123");


//        accessorsManager.getUniqueIdentifierServiceAccessor()
//                .deleteCloudId("X5J3ON5UT7SUALFKO3O52BZOXSUYWLVFKM56YBCL62TVZYKNYCRQ");
//
//        ResultsSlice resultsSlice = accessorsManager.getUniqueIdentifierServiceAccessor()
//                .getCloudIdsOfProvider("STempProvider5");
//        for (CloudId cloudId : resultsSlice.getResults()) {
////            System.out.println(cloudId.getId());
//            System.out.println(Tools.marshallAny(cloudId));
//        }

//        ResultsSlice resultsSlice = accessorsManager.getUniqueIdentifierServiceAccessor().getCloudIdWithRecordIds("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A");
//        System.out.println(Tools.marshallAny(resultsSlice));
//
//        accessorsManager.getUniqueIdentifierServiceAccessorBase().createMappingRecordIdToCloudId("STempProvider5", "ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA");
//
//        resultsSlice = accessorsManager.getUniqueIdentifierServiceAccessorBase().getCloudIdWithRecordIds("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA");
//        System.out.println(Tools.marshallAny(resultsSlice));

//        accessorsManager.getUniqueIdentifierServiceAccessorBase().getCloudIdWithRecordIds("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA");

//        for (CloudId cloudId : resultsSlice.getCloudIds()) {
//            Tools.marshallAny(cloudId);
//            System.out.println(cloudId.getId());
//            System.out.println(accessorsManager.getUniqueIdentifierServiceAccessorBase()
//                    .deleteCloudId(cloudId.getId()));
//        }
//        System.out.println(resultsSlice.getCloudIds().size());
//        System.out.println(resultsSlice.getNextSlice());
//        CLOUD IDS END

//        RECORDS START
//        System.out.println(accessorsManager.getMetadataAndContentServiceAccessor().createDataSet("STempProvider10", "dataSet13", "Test"));
//        System.out.println(accessorsManager.getMetadataAndContentServiceAccessor().updateDataSetDescription("STempProvider10", "dataSet10", "Test2"));

//        ResultsSlice<RepresentationVersion> resultsSlice = accessorsManager.getMetadataAndContentServiceAccessor().getDataSetRepresentationVersions("STempProvider10", "dataSet10");
//        System.out.println(Tools.marshallAny(resultsSlice));

//        ResultsSlice<DataSet> dataSets = accessorsManager.getMetadataAndContentServiceAccessor().getDataSetsOfProvider("STempProvider10", null);
//        System.out.println(dataSets);

//        accessorsManager.getMetadataAndContentServiceAccessor().deleteDataSet("STempProvider10", "dataSet10");

//        accessorsManager.getMetadataAndContentServiceAccessor().assignRepresentationVersionToDataSet("STempProvider10", "dataSet10", "NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST", "b6155eb0-2d56-11e6-9e71-fa163e64bb83");


//        String representationVersion = accessorsManager.getMetadataAndContentServiceAccessor()
//                .createRepresentationVersion("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST", "STempProvider10");
//        System.out.println(representationVersion);

//        CloudRecord cr = accessorsManager.getMetadataAndContentServiceAccessor().getCloudRecordRepresentations("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A");
//        System.out.println(Tools.marshallAny(cr));
//        RepresentationVersion[] representationVersions = accessorsManager.getMetadataAndContentServiceAccessor().getRepresentations("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A");
//        System.out.println(Tools.marshallAny(representationVersions));
//        RepresentationVersion representation = accessorsManager.getMetadataAndContentServiceAccessor().getRepresentation("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST");
//        System.out.println(Tools.marshallAny(representation));

//        accessorsManager.getMetadataAndContentServiceAccessor().deleteCloudRecordRepresentationsAndVersions("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A");
//
//
//        RepresentationVersion[] representationVersions = accessorsManager.getMetadataAndContentServiceAccessor().getRepresentationVersions("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST");
//        for (RepresentationVersion representationVersion: representationVersions
//             ) {
//            System.out.println(representationVersion.getVersion());
//        }
//        System.out.println(representationVersions.length);
//
//        RepresentationVersion representationVersion = accessorsManager.getMetadataAndContentServiceAccessor()
//                .getRepresentationVersion("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST", "b6155eb0-2d56-11e6-9e71-fa163e64bb83");
//        System.out.println(representationVersion);

//        System.out.println(accessorsManager.getMetadataAndContentServiceAccessor().copyRepresentationVersionWithContents("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST", "bd915240-2c8c-11e6-9e71-fa163e64bb83"));

//        System.out.println(accessorsManager.getMetadataAndContentServiceAccessor().persistRepresentationVersion("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST", "909b7930-2cc0-11e6-94ae-fa163e289a71"));

//        accessorsManager.getMetadataAndContentServiceAccessor().permitRepresentationVersion("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST", "909b7930-2cc0-11e6-94ae-fa163e289a71");

//          accessorsManager.getMetadataAndContentServiceAccessor().updatePermissionsForRepresentationVersion("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST", "909b7930-2cc0-11e6-94ae-fa163e289a71", "read", "stzanakis");
//          accessorsManager.getMetadataAndContentServiceAccessor().deletePermissionsForRepresentationVersion("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST", "909b7930-2cc0-11e6-94ae-fa163e289a71", "read", "stzanakis");

//        CloudRecord cloudRecord = accessorsManager.getMetadataAndContentServiceAccessor().getCloudRecordWithSimplifiedUrl("STempProvider10", "123");
//        List<RepresentationVersion> representationVersions = cloudRecord.getRepresentationVersions();
//        for (RepresentationVersion representationVersion: representationVersions
//             ) {
//            System.out.println(representationVersion.getVersion());
//        }
//        System.out.println(representationVersions.size());
//        System.out.println(Tools.marshallAny(cloudRecord));

//        RepresentationVersion representationWithSimplifiedUrl = accessorsManager.getMetadataAndContentServiceAccessor().getRepresentationWithSimplifiedUrl("STempProvider10", "123", "TEST");
//        System.out.println(Tools.marshallAny(representationWithSimplifiedUrl));

//        accessorsManager.getMetadataAndContentServiceAccessor()
//                .deleteRepresentationVersion("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST", "7f192790-2bec-11e6-9e71-fa163e64bb83");

//        accessorsManager.getMetadataAndContentServiceAccessor().deleteRepresentation("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST");

//        File file = new File("/tmp/test.png");
//        accessorsManager.getMetadataAndContentServiceAccessor()
//                .addFileToRepresentationVersion("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST", "b6155eb0-2d56-11e6-9e71-fa163e64bb83", file, "image/png", "newTest");

//        File file = new File("/tmp/test2.png");
//        accessorsManager.getMetadataAndContentServiceAccessor()
//                .updateFileToRepresentationVersion("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST", "bd915240-2c8c-11e6-9e71-fa163e64bb83", "a7bb9ffb-62df-4b8b-9ee2-58d909c6a2cc", file, "image/png");

//        System.out.println(accessorsManager.getMetadataAndContentServiceAccessor().getFileFromRepresentationVersion("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST", "bd915240-2c8c-11e6-9e71-fa163e64bb83", "a7bb9ffb-62df-4b8b-9ee2-58d909c6a2cc", "/tmp/downloads"));
//        System.out.println(accessorsManager.getMetadataAndContentServiceAccessor().getFileFromLatestPersistentWithSimplifiedUrl("STempProvider10", "123", "TEST", "9db1f6ae-6651-4996-9ed8-12f8a3f1c0a2", "/tmp/downloads"));
//        System.out.println(accessorsManager.getMetadataAndContentServiceAccessor().getHeadersFromFileFromLatestPersistentWithSimplifiedUrl("STempProvider10", "123", "TEST", "9db1f6ae-6651-4996-9ed8-12f8a3f1c0a2"));
//        System.out.println(accessorsManager.getMetadataAndContentServiceAccessor().getHeadersForFileFromRepresentationVersion("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST", "bd915240-2c8c-11e6-9e71-fa163e64bb83", "a7bb9ffb-62df-4b8b-9ee2-58d909c6a2cc"));
//        System.out.println(accessorsManager.getMetadataAndContentServiceAccessor().getPartialFileFromRepresentationVersion("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST", "bd915240-2c8c-11e6-9e71-fa163e64bb83", "a7bb9ffb-62df-4b8b-9ee2-58d909c6a2cc", "/tmp/downloads", 0, 9));
//        System.out.println(accessorsManager.getMetadataAndContentServiceAccessor().getPartialFileFromRepresentationVersion("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST", "bd915240-2c8c-11e6-9e71-fa163e64bb83", "a7bb9ffb-62df-4b8b-9ee2-58d909c6a2cc", "/tmp/downloads", 10, -1));

//        accessorsManager.getMetadataAndContentServiceAccessor()
//                .deleteFileFromRepresentationVersion("NON6WPP3AR7SYQBWSWOTX2BXU3KBQQL3X7E5GTK74P72KB5UPW3A", "TEST",
//                        "909b7930-2cc0-11e6-94ae-fa163e289a71", "a7bb9ffb-62df-4b8b-9ee2-58d909c6a2cc");

//        for (FileMetadata fm :
//                representationVersion.getFilesMetadata()) {
//            accessorsManager.getMetadataAndContentServiceAccessor()
//                    .deleteFileFromRepresentationVersion("ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA", "TEST",
//                            "476feab0-2668-11e6-8cf5-fa163e8d4ae3", fm.getFileName());
//        }

//        ResultsSlice resultsSlice = accessorsManager.getUniqueIdentifierServiceAccessor().getLocalIdsOfProvider("STempProvider5", "PWFE4MY56EHKJE3HOAPHBY3ULZJGT7YYBGZ3ITMSHBIA4EGOQXIA", 2);
//        System.out.println(Tools.marshallAny(resultsSlice));
//        ResultsSlice resultsSlice = accessorsManager.getUniqueIdentifierServiceAccessorBase().getLocalIdsOfProvider("STempProvider5", "ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA", 1);
//        ResultsSlice resultsSlice = accessorsManager.getUniqueIdentifierServiceAccessorBase().getLocalIdsOfProvider("STempProvider5", "ARKE2XSKGQF2PRXB5BLRASDOZ3J2UTMRINGLMGVA6BF34VNO5AQA", 1);

//        accessorsManager.getUniqueIdentifierServiceAccessorBase().deleteMappingLocalIdFromCloudId("STempProvider5", "IHAQTCG4RH2XEOTWAPF7CDNIH6QEWVJBMTGPR5ENBO4S2MRCKMGA");
//
//        ResultsSlice resultsSlice = accessorsManager.getUniqueIdentifierServiceAccessorBase().getLocalIdsOfProvider("STempProvider5");
//        System.out.println(Tools.marshallAny(resultsSlice));

//        RECORDS END

        logger.info("Ended in Main");
    }
}
