package europeana.eu.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-24
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DataProviderProperties {
    private String organisationName;
    private String organisationWebsiteURL;
    private String officialAddress;
    private String digitalLibraryURL;
    private String contactPerson;
    private String remarks;

    public DataProviderProperties() {
    }

    public DataProviderProperties(String organisationName, String organisationWebsiteURL, String officialAddress, String digitalLibraryURL, String contactPerson, String remarks) {
        this.organisationName = organisationName;
        this.organisationWebsiteURL = organisationWebsiteURL;
        this.officialAddress = officialAddress;
        this.digitalLibraryURL = digitalLibraryURL;
        this.contactPerson = contactPerson;
        this.remarks = remarks;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getOrganisationWebsiteURL() {
        return organisationWebsiteURL;
    }

    public void setOrganisationWebsiteURL(String organisationWebsiteURL) {
        this.organisationWebsiteURL = organisationWebsiteURL;
    }

    public String getOfficialAddress() {
        return officialAddress;
    }

    public void setOfficialAddress(String officialAddress) {
        this.officialAddress = officialAddress;
    }

    public String getDigitalLibraryURL() {
        return digitalLibraryURL;
    }

    public void setDigitalLibraryURL(String digitalLibraryURL) {
        this.digitalLibraryURL = digitalLibraryURL;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
