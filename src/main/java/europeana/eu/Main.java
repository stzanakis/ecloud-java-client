package europeana.eu;

import europeana.eu.commons.AccessorsManager;
import europeana.eu.exceptions.AlreadyExistsException;
import europeana.eu.exceptions.BadRequest;
import europeana.eu.exceptions.DoesNotExistException;
import europeana.eu.model.CloudIdsSlice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.NoContentException;
import javax.xml.bind.JAXBException;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-23
 */
public class Main {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws AlreadyExistsException, BadRequest, DoesNotExistException, NoContentException, JAXBException {
        System.out.println("Hello ECloud!");
        logger.info("Started in Main");

        // TODO: 25-5-16 Read credentials from properties file.
        AccessorsManager accessorsManager = new AccessorsManager();
//        accessorsManager.InitializeAllAccessors("https://test-cloud.europeana.eu/api", "stzanakis", "Pom3ohco");
        accessorsManager.InitializeAllAccessors("https://test-cloud.europeana.eu/api", "stzanakis_admin", "keg3zahV");

//        DATA PROVIDERS START
//        Create Data Provider
//        dataProviderAccessor.createDataProvider("STempProvider11", "So-url-example.com",
//                "Semail@example.com", "Sdl-url-example.com", "SOrganizationName", "SRemarks", "SContactPersonName");

        //Get Data Provider
//        accessorsManager.getDataProviderAccessorBase().getProvider("STempProvider");
//        DataProvider dataProvider =accessorsManager.getDataProviderAccessorBase().getProvider("STempProvider8");
//        System.out.println(dataProvider.getId());

        //Delete Data Provider
//        accessorsManager.getDataProviderAccessorBase().deleteDataProvider("STempProvider8");
//        DATA PROVIDERS END

//        CLOUD IDS START
//        CloudId cloudId = accessorsManager.getUniqueIdentifierServiceAccessorBase().createNewCloudId("STempProvider5");
        CloudIdsSlice cloudIdsSlice = accessorsManager.getUniqueIdentifierServiceAccessorBase()
                .getCloudIdsOfProvider("STempProvider5");
//        CloudIdsSlice cloudIdsSlice = accessorsManager.getCloudIdsAccessorBase()
//                .getCloudIdsOfProvider("STempProvider5", "JFCBS7OOB3PKQBO7DV7SMMHRJDOJ47JKFVVJIMDQID5VEHRROQPQ", 6);

//        for (CloudId cloudId : cloudIdsSlice.getCloudIds()) {
//            Tools.marshallAny(cloudId);
//            System.out.println(cloudId.getId());
//        }
//        System.out.println(cloudIdsSlice.getCloudIds().size());
//        System.out.println(cloudIdsSlice.getNextSlice());
//        CLOUD IDS END


        logger.info("Ended in Main");
    }
}
