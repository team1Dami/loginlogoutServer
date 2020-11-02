/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pool;

import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author saray
 */
public class ConnectionPool {

    private static final Logger logger = Logger.getLogger("Pool.ConnectionPool");
    private static ConnectionPool PoolInstance = null; // definimos objeto del mismo tipo que nuestra clase
    //   private static int countOfInstance = 0;
    private static ResourceBundle ConnectionFile = ResourceBundle.getBundle("control.ConnectionFile");
    private BasicDataSource ds;

    // constructor
    private ConnectionPool() {
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

    public synchronized static ConnectionPool getPoolInstance() throws Exception {
        if (null == PoolInstance) {  // preguntamos si la instancia es null
            PoolInstance = new ConnectionPool(); // la primera vez entrará e instanciará la PoolInstance        
        }
        return PoolInstance;  // devolvemos la instancia de nuestra clase ConnectionPool
    }

    public BasicDataSource getDataSource() {
        return ds;
    }

    // devolvemos la conexión
    public synchronized Connection getConnection() throws SQLException {
        return ds.getConnection();
        //return null;
    }

}
