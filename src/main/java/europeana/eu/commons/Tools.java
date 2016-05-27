package europeana.eu.commons;

import europeana.eu.model.DataProviderProperties;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-25
 */
public class Tools {

    public static void marshallTest() throws JAXBException {
        DataProviderProperties dataProvider = new DataProviderProperties("SOrganizationName","So-url-example.com","Semail@example.com"
                ,"Sdl-url-example.com","SContactPersonName","SRemarks");

        JAXBContext jaxbContext =   JAXBContextFactory.createContext(new Class[]{DataProviderProperties.class}, null);

        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

//        System.out.println(jaxbContext);

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);

//        jaxbMarshaller.marshal(customer, file);
//        jaxbMarshaller.marshal(dataProvider, System.out);

        jaxbMarshaller.setProperty("eclipselink.media-type", "application/json");
        jaxbMarshaller.marshal(dataProvider, System.out);
    }

    public static void marshallAny(Object object) throws JAXBException {

        JAXBContext jaxbContext =   JAXBContextFactory.createContext(new Class[]{object.getClass()}, null);

        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

//        System.out.println(jaxbContext);

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);

//        jaxbMarshaller.marshal(customer, file);
//        jaxbMarshaller.marshal(dataProvider, System.out);

        jaxbMarshaller.setProperty("eclipselink.media-type", "application/json");
        jaxbMarshaller.marshal(object, System.out);
    }
}
