package europeana.eu.model;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-25
 */
public enum Constants {
    DATAPROVIDERS_PATH("data-providers"),
    DATASETS_PATH("data-sets"),
    CLOUDIDS_PATH("cloudIds"),
    RECORDS_PATH("records"),
    REPRESENTATIONS_PATH("representations"),
    VERSIONS_PATH("versions"),
    FILES_PATH("files"),
    LOCALIDS_PATH("localIds"),
    ACTIVE_PATH("active"),
    COPY_PATH("copy"),
    PERSIST_PATH("persist"),
    PERMIT_PATH("permit"),
    PERMISSIONS_PATH("permissions"),
    USERS_PATH("users"),
    ASSIGNMENTS_PATH("assignments"),
    CREATEUSER_PATH("create-user"),
    DELETEUSER_PATH("delete-user"),
    UPDATEUSER_PATH("update-user"),
    CLOUDID("cloudId"),
    PROVIDERID("providerId"),
    DATASETID("dataSetId"),
    DESCRIPTION("description"),
    RECORDID("recordId"),
    REPRESENTATIONNAME("representationName"),
    VERSION("version"),
    LOCATION_HEADER("Location"),
    FROM("from"),
    TO("to"),
    USERNAME_QP("username"),
    PASSWORD_QP("password"),
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
