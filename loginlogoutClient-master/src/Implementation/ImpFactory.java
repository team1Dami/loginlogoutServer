package Implementation;

import interfaces.ClientServer;

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
    public static ClientServer getImplement() {
        return new ClientServerImplementation();
    }
}
