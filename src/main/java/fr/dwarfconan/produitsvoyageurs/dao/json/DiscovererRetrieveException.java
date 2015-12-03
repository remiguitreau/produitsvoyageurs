package fr.dwarfconan.produitsvoyageurs.dao.json;

public class DiscovererRetrieveException extends RuntimeException {

    public DiscovererRetrieveException() {
    }

    public DiscovererRetrieveException(final String message) {
        super(message);
    }

    public DiscovererRetrieveException(final Throwable cause) {
        super(cause);
    }

    public DiscovererRetrieveException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DiscovererRetrieveException(final String message, final Throwable cause,
            final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
