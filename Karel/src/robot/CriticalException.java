package robot;

/**
 * Kritická výjimka, po které se mmusí ukončit program.
 * @author Michal Petříček
 */
public class CriticalException extends Exception{

    /**
     * Konstruktor.
     * @param message
     */
    public CriticalException(String message) {
        super(message);
    }

}
