package europeana.eu.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-06-08
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DataSet {
    private String id;
    private String description;
    private URI uri;
    private String providerId;

    public DataSet() {
    }

    public DataSet(String id, String description, URI uri, String providerId) {
        this.id = id;
        this.description = description;
        this.uri = uri;
        this.providerId = providerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
