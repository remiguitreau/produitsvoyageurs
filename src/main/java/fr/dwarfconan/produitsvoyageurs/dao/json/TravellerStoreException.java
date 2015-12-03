package fr.dwarfconan.produitsvoyageurs.dao.json;

public class TravellerStoreException extends RuntimeException {

    public TravellerStoreException() {
    }

    public TravellerStoreException(final String message) {
        super(message);
    }

    public TravellerStoreException(final Throwable cause) {
        super(cause);
    }

    public TravellerStoreException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TravellerStoreException(final String message, final Throwable cause,
            final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
