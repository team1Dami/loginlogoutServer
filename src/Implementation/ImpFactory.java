package Implementation;

/**
 * Client implementation factory
 *
 * @author Ruben
 */
public class ImpFactory {

    /**
     * Returns an implementation
     *
     * @return Client server implementation
     */
    public static ClientServerImplementation getImplement() {
        return new ClientServerImplementation();
    }
}
