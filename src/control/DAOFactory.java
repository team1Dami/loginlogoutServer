package control;

import Pool.ConnectionPool;
import static Pool.ConnectionPool.getPoolInstance;

/**
 * DAOFactory class used to return DAO class and PoolInstance
 *
 * @author eneko, saray
 */
public class DAOFactory {

    /**
     * This method return a DAO class to the thread serverWorker
     *
     * @return a new DAO that implements interface ClientServer
     */
    public synchronized static DAO getDAO() {
        return new DAO();
    }

    /**
     *
     * @return @throws Exception
     */
    public synchronized static ConnectionPool Pool() throws Exception {
        // le pide al Pool una instancia
        return getPoolInstance();
    }
}
