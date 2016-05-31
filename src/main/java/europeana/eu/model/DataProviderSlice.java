package europeana.eu.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-31
 */
@XmlRootElement(name = "resultSlice")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataProviderSlice {
        @XmlElement(name = "results")
        private List<DataProvider> dataProviders;
        private String nextSlice;

    public DataProviderSlice() {
    }

    public DataProviderSlice(List<DataProvider> dataProviders, String nextSlice) {
        this.dataProviders = dataProviders;
        this.nextSlice = nextSlice;
    }

    public List<DataProvider> getDataProviders() {
        return dataProviders;
    }

    public void setDataProviders(List<DataProvider> dataProviders) {
        this.dataProviders = dataProviders;
    }

    public String getNextSlice() {
        return nextSlice;
    }

    public void setNextSlice(String nextSlice) {
        this.nextSlice = nextSlice;
    }
}
