package europeana.eu.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-06-07
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CloudRecord {
    private String cloudId;
    private List<RepresentationVersion> representationVersions;

    public CloudRecord() {
    }

    public CloudRecord(String cloudId, List<RepresentationVersion> representationVersions) {
        this.cloudId = cloudId;
        this.representationVersions = representationVersions;
    }

    public String getCloudId() {
        return cloudId;
    }

    public void setCloudId(String cloudId) {
        this.cloudId = cloudId;
    }

    public List<RepresentationVersion> getRepresentationVersions() {
        return representationVersions;
    }

    public void setRepresentationVersions(List<RepresentationVersion> representationVersions) {
        this.representationVersions = representationVersions;
    }
}
