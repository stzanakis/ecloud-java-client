package europeana.eu.model;

import europeana.eu.commons.Tools;

import javax.xml.bind.JAXBException;
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
    private String organisationWebsite;
    private String organisationWebsiteURL;
    private String officialAddress;
    private String digitalLibraryWebsite;
    private String digitalLibraryURL;
    private String contactPerson;
    private String remarks;

    public DataProviderProperties() {
    }

    public DataProviderProperties(String organisationName, String organisationWebsite, String organisationWebsiteURL, String officialAddress, String digitalLibraryWebsite, String digitalLibraryURL, String contactPerson, String remarks) {
        this.organisationName = organisationName;
        this.organisationWebsite = organisationWebsite;
        this.organisationWebsiteURL = organisationWebsiteURL;
        this.officialAddress = officialAddress;
        this.digitalLibraryWebsite = digitalLibraryWebsite;
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

    public String getOrganisationWebsite() { return organisationWebsite; }

    public void setOrganisationWebsite(String organisationWebsite) { this.organisationWebsite = organisationWebsite; }

    public String getOrganisationWebsiteURL() {
        return organisationWebsiteURL;
    }

    public void setOrganisationWebsiteURL(String organisationWebsiteURL) { this.organisationWebsiteURL = organisationWebsiteURL; }

    public String getOfficialAddress() {
        return officialAddress;
    }

    public void setOfficialAddress(String officialAddress) {
        this.officialAddress = officialAddress;
    }

    public String getDigitalLibraryWebsite() { return digitalLibraryWebsite; }

    public void setDigitalLibraryWebsite(String digitalLibraryWebsite) { this.digitalLibraryWebsite = digitalLibraryWebsite;}

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
