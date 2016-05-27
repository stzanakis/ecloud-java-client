package europeana.eu.exceptions;

import java.io.Serializable;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-25
 */
public class NoContentException extends Exception implements Serializable {
    private static final long serialVersionUID = 44L;

    public NoContentException() {
        super();
    }

    public NoContentException(String msg) {
        super(msg);
    }

    public NoContentException(String msg, Exception e) {
        super(msg, e);
    }
}
