package europeana.eu.exceptions;

import java.io.Serializable;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-25
 */
public class DoesNotExistException extends Exception implements Serializable {
    private static final long serialVersionUID = 44L;

    public DoesNotExistException() {
        super();
    }

    public DoesNotExistException(String msg) {
        super(msg);
    }

    public DoesNotExistException(String msg, Exception e) {
        super(msg, e);
    }
}
