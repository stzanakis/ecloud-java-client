package europeana.eu.exceptions;

import java.io.Serializable;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-06-07
 */
public class RangeHeaderInvalidException extends Exception implements Serializable {
    private static final long serialVersionUID = 44L;

    public RangeHeaderInvalidException() {
        super();
    }

    public RangeHeaderInvalidException(String msg) {
        super(msg);
    }

    public RangeHeaderInvalidException(String msg, Exception e) {
        super(msg, e);
    }
}
