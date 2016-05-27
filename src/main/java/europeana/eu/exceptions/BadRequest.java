package europeana.eu.exceptions;

import java.io.Serializable;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-25
 */
public class BadRequest extends Exception implements Serializable {
    private static final long serialVersionUID = 44L;

    public BadRequest() {
        super();
    }

    public BadRequest(String msg) {
        super(msg);
    }

    public BadRequest(String msg, Exception e) {
        super(msg, e);
    }
}
