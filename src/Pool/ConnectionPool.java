/*
 * Package that contains the ConnectionPool class
 * and the methods to 
 * 
 */
package Pool;

import exceptions.NoConnectionDBException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This is ConnectionPool class Contains the methods to set the properties into
 * BasicDataSource object And the synchronized methods: get a PoolInstance get a
 * connection
 *
 * @author saray
 */
public class ConnectionPool {

    private static final Logger logger = Logger.getLogger("Pool.ConnectionPool");
    private static ConnectionPool PoolInstance = null; // definimos objeto del mismo tipo que nuestra clase
    private static ResourceBundle ConnectionFile;
    private BasicDataSource ds;

    /**
     * Constructor method to set the properties to BasicDataSource object
     */
    private ConnectionPool() {
        
        ConnectionFile = ResourceBundle.getBundle("control.ConnectionFile");
        
        ds = new BasicDataSource();
        ds.setDriverClassName(ConnectionPool.ConnectionFile.getString("Driver"));
        ds.setUsername(ConnectionPool.ConnectionFile.getString("DBUser"));
        ds.setPassword(ConnectionPool.ConnectionFile.getString("DBPass"));
        ds.setUrl(ConnectionPool.ConnectionFile.getString("URL"));
        // pool size
        ds.setInitialSize(Integer.parseInt(ConnectionPool.ConnectionFile.getString("MIN_POOL_SIZE")));
        ds.setMaxIdle(Integer.parseInt(ConnectionPool.ConnectionFile.getString("MAX_IDLE")));
        // Max time wait
        ds.setMaxWaitMillis(Integer.parseInt(ConnectionPool.ConnectionFile.getString("MAX_TIME")));
    }

    /**
     * Synchronized method to get an instance of ConnectionPool
     *
     * @return PoolInstance
     * @throws Exception
     */
    public synchronized static ConnectionPool getPoolInstance() throws Exception {
        if (null == PoolInstance) {  // preguntamos si la instancia es null
            PoolInstance = new ConnectionPool(); // la primera vez entrará e instanciará la PoolInstance        
        }
        return PoolInstance;  // devolvemos la instancia de nuestra clase ConnectionPool
    }

    /**
     * Method to get a connection
     *
     * @return the connection to the thread
     * @throws SQLException
     * @throws NoConnectionDBException
     */
    public synchronized Connection getConnection() throws SQLException, NoConnectionDBException {
        return ds.getConnection();
        //return null;
    }

}
