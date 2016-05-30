package europeana.eu.model;

import europeana.eu.commons.Tools;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-25
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DataProvider {
    private String id;
    private boolean active;
    private long partitionKey;
    @XmlElement(name = "properties")
    private DataProviderProperties dataProviderProperties;

    public DataProvider() {
    }

    public DataProvider(String id, long partitionKey, DataProviderProperties dataProviderProperties) {
        this.id = id;
        this.partitionKey = partitionKey;
        this.dataProviderProperties = dataProviderProperties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(long partitionKey) {
        this.partitionKey = partitionKey;
    }

    public DataProviderProperties getDataProviderProperties() {
        return dataProviderProperties;
    }

    public void setDataProviderProperties(DataProviderProperties dataProviderProperties) {
        this.dataProviderProperties = dataProviderProperties;
    }

    @Override
    public String toString() {
        try {
            return Tools.marshallAny(this);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return "";
    }
}
