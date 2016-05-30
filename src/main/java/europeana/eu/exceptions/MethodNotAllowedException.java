package europeana.eu.exceptions;

import java.io.Serializable;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since 2016-05-30
 */
public class MethodNotAllowedException extends Exception implements Serializable {
        private static final long serialVersionUID = 44L;

        public MethodNotAllowedException() {
            super();
        }

        public MethodNotAllowedException(String msg) {
            super(msg);
        }

        public MethodNotAllowedException(String msg, Exception e) {
            super(msg, e);
        }
    }
