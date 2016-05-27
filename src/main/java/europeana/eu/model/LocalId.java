package europeana.eu.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-25
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LocalId {
    private String providerId;
    private String recordId;

    public LocalId() {
    }

    public LocalId(String providerId, String recordId) {
        this.providerId = providerId;
        this.recordId = recordId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
