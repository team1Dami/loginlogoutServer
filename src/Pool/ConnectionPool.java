/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author saray
 */
public class ConnectionPool {

    private static final Logger logger = Logger.getLogger("Pool.ConnectionPool");

    private static ConnectionPool PoolInstance = null; // definimos objeto del mismo tipo que nuestra clase
 //   private static int countOfInstance = 0;
    private static ResourceBundle ConnectionFile;
    private static BasicDataSource basicDataSource = null;
    private static int MIN_POOL_SIZE;
    private static int MAX_IDLE;
    private static int MAX_TIME;
        	//    private static DAO DAOInstance;
//    private static int countOfInstance = 0;

    // constructor
    private ConnectionPool() {}  // así cada vez que cree un
 
    public synchronized static ConnectionPool getPoolInstance() {
	if (PoolInstance == null) {  // preguntamos si la instancia es null
            PoolInstance = new ConnectionPool(); // la primera vez entrará e instanciará la PoolInstance
        }
	return PoolInstance;  // devolvemos la instancia de nuestra clase ConnectionPool
    }
    
    public synchronized BasicDataSource getDataSource() {
        this.ConnectionFile = ResourceBundle.getBundle("control.ConnectionFile");

        if (null == basicDataSource) {
            basicDataSource.setDriverClassName(this.ConnectionFile.getString("Driver"));
            basicDataSource.setUsername(this.ConnectionFile.getString("DBUser"));
            basicDataSource.setPassword(this.ConnectionFile.getString("DBPass"));
            basicDataSource.setUrl(this.ConnectionFile.getString("Conn"));
            // pool size
            basicDataSource.setInitialSize(Integer.parseInt(this.ConnectionFile.getString("MIN_POOL_SIZE")));
            basicDataSource.setMaxIdle(Integer.parseInt(this.ConnectionFile.getString("MAX_IDLE")));
            // Max time wait
            basicDataSource.setMaxWaitMillis(Integer.parseInt(this.ConnectionFile.getString("MAX_TIME")));
        }
        return basicDataSource;
    }

    // devolvemos la conexión
    public synchronized Connection getConnection() {
        
      //  Connection conn = basicDataSource.
        try {
            return getDataSource().getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
