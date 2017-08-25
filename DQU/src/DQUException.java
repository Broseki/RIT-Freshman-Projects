/**
 * A class used to communicate errors with operations involving the
 * order of QTree operations.
 *
 * @author Sean Strout, James Heliotis
 */
public class DQUException extends Exception {
    /**
     * Create a new DQUException
     * @param msg the reason this exception object is being thrown
     */
    public DQUException( String msg) {
        super(msg);
    }
}
