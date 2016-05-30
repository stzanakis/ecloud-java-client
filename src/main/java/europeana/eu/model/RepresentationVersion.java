package europeana.eu.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;
import java.util.List;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-30
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RepresentationVersion {
    URI allVersionsUri;
    String cloudId;
    String creationDate;
    String dataProvider;
    boolean persistent;
    String representationName;
    URI uri;
    String version;
    @XmlElement(name = "files")
    List<File> files;

    public RepresentationVersion() {
    }

    public RepresentationVersion(URI allVersionsUri, String cloudId, String creationDate, String dataProvider, boolean persistent, String representationName, URI uri, String version, List<File> files) {
        this.allVersionsUri = allVersionsUri;
        this.cloudId = cloudId;
        this.creationDate = creationDate;
        this.dataProvider = dataProvider;
        this.persistent = persistent;
        this.representationName = representationName;
        this.uri = uri;
        this.version = version;
        this.files = files;
    }

    public URI getAllVersionsUri() {
        return allVersionsUri;
    }

    public void setAllVersionsUri(URI allVersionsUri) {
        this.allVersionsUri = allVersionsUri;
    }

    public String getCloudId() {
        return cloudId;
    }

    public void setCloudId(String cloudId) {
        this.cloudId = cloudId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDataProvider() {
        return dataProvider;
    }

    public void setDataProvider(String dataProvider) {
        this.dataProvider = dataProvider;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    public String getRepresentationName() {
        return representationName;
    }

    public void setRepresentationName(String representationName) {
        this.representationName = representationName;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
