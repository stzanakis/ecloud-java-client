package europeana.eu.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-25
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CloudIdsSlice {
    @XmlElement(name = "results")
    private List<CloudId> cloudIds;
    private String nextSlice;

    public CloudIdsSlice() {
    }

    public CloudIdsSlice(List<CloudId> cloudIds) {
        this.cloudIds = cloudIds;
    }

    public List<CloudId> getCloudIds() {
        return cloudIds;
    }

    public void setCloudIds(List<CloudId> cloudIds) {
        this.cloudIds = cloudIds;
    }

    public String getNextSlice() {
        return nextSlice;
    }

    public void setNextSlice(String nextSlice) {
        this.nextSlice = nextSlice;
    }
}
