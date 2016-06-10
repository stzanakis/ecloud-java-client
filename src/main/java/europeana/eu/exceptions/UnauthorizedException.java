package europeana.eu.exceptions;

import java.io.Serializable;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-25
 */
public class UnauthorizedException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 44L;

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException(String msg, Exception e) {
        super(msg, e);
    }
}
