package europeana.eu.model;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-25
 */
public enum Constants {
    DATAPROVIDERS_PATH("data-providers"),
    CLOUDIDS_PATH("cloudIds"),
    RECORDS_PATH("records"),
    REPRESENTATIONS_PATH("representations"),
    VERSIONS_PATH("versions"),
    FILES_PATH("files"),
    LOCALIDS_PATH("localIds"),
    ACTIVE_PATH("active"),
    PROVIDERID("providerId"),
    RECORDID("recordId"),
    LOCATION_HEADER("Location"),
    FROM("from"),
    TO("to"),
    MIMETYPE_FIELD("mimeType"),
    FILENAME_FIELD("fileName");

    private String constant;

    Constants(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }
}
