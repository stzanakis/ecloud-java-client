package europeana.eu.commons;

import europeana.eu.exceptions.MethodNotAllowedException;
import europeana.eu.exceptions.UnauthorizedException;
import europeana.eu.model.ErrorInfo;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-25
 */
public class Tools {

    public static String marshallAny(Object object) throws JAXBException {

        JAXBContext jaxbContext =   JAXBContextFactory.createContext(new Class[]{object.getClass()}, null);

        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

//        System.out.println(jaxbContext);

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);

//        jaxbMarshaller.marshal(customer, file);
//        jaxbMarshaller.marshal(dataProvider, System.out);

        jaxbMarshaller.setProperty("eclipselink.media-type", "application/json");
        StringWriter stringWriter = new StringWriter();
        jaxbMarshaller.marshal(object,stringWriter);

        return stringWriter.toString();
    }

    public static String generateFormURLEncoded(Map<String, String> map)
    {
        StringBuilder url_encoded = new StringBuilder("");
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<String, String> entry = iterator.next();
            url_encoded.append(entry.getKey() + "=" + entry.getValue());
            if(iterator.hasNext())
                url_encoded.append("&");
        }
        return url_encoded.toString();
    }

    public static String parseResponse(String uri, short status, Response response){

        String errorString;
        MultivaluedMap<String, Object> responseHeaders = response.getHeaders();
        if(responseHeaders.containsKey("Content-Type") && (!responseHeaders.get("Content-Type").get(0).equals("application/xml") && !responseHeaders.get("Content-Type").get(0).equals("application/json")))
            errorString = response.readEntity(String.class);
        else {
            ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
            errorString = "Target URI: " + uri + ", Response code: " + status + ", ErrorCode=" + errorInfo.getErrorCode() + ", Details: " + errorInfo.getDetails();
        }
        return errorString;
    }

    public static void generalExceptionHandler(short status, String errorString) {
        switch (status)
        {
            case 401:
                throw new UnauthorizedException(errorString);
            case 405:
                throw new MethodNotAllowedException(errorString);
            case 500:
                throw new InternalServerErrorException(errorString);
            default:
                throw new UnsupportedOperationException(errorString);
        }
    }
}
