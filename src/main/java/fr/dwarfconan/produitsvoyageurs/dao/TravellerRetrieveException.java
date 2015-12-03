package fr.dwarfconan.produitsvoyageurs.dao;

public class TravellerRetrieveException extends RuntimeException {

    public TravellerRetrieveException() {
    }

    public TravellerRetrieveException(final String message) {
        super(message);
    }

    public TravellerRetrieveException(final Throwable cause) {
        super(cause);
    }

    public TravellerRetrieveException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TravellerRetrieveException(final String message, final Throwable cause,
            final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
