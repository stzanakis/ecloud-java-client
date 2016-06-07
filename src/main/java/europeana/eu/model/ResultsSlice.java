package europeana.eu.model;

import europeana.eu.commons.Tools;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-06-03
 */
@XmlRootElement(name = "resultSlice")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultsSlice<T> {
        @XmlElement(name = "results")
        private List<T> results;
        private String nextSlice;

        public ResultsSlice() {
        }

        public ResultsSlice(List<T> results) {
            this.results = results;
        }

        public List<T> getCloudIds() {
            return results;
        }

        public void setResults(List<T> results) {
            this.results = results;
        }

        public String getNextSlice() {
            return nextSlice;
        }

        public void setNextSlice(String nextSlice) {
            this.nextSlice = nextSlice;
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
