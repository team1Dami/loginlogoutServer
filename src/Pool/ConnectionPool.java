/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author saray
 */
public class ConnectionPool {

    private static final Logger logger = Logger.getLogger("Pool.ConnectionPool");

    // private Properties properties = new Properties();
    private static ResourceBundle ConnectionFile;
    private static BasicDataSource basicDataSource = null;
    private static int MIN_POOL_SIZE = 1;
    private static int MAX_IDLE = 10;
    private static int MAX_TIME = 5000;

    public BasicDataSource getDataSource() {
        this.ConnectionFile = ResourceBundle.getBundle("control.ConnectionFile");

        if (null == basicDataSource) {
            basicDataSource.setDriverClassName(this.ConnectionFile.getString("Driver"));
            basicDataSource.setUsername(this.ConnectionFile.getString("DBUser"));
            basicDataSource.setPassword(this.ConnectionFile.getString("DBPass"));
            basicDataSource.setUrl(this.ConnectionFile.getString("Conn"));

            //tamaño del pool
            basicDataSource.setInitialSize(MIN_POOL_SIZE);
            basicDataSource.setMaxIdle(MAX_IDLE);

            // tiempo máximo de espera
            basicDataSource.setMaxWaitMillis(MAX_TIME);
        }
        return basicDataSource;
    }

    // devolvemos la conexión
    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public static int getMIN_POOL_SIZE() {
        return MIN_POOL_SIZE;
    }

    public static void setMIN_POOL_SIZE(int MIN_POOL_SIZE) {
        ConnectionPool.MIN_POOL_SIZE = MIN_POOL_SIZE;
    }

    public static int getMAX_POOL_SIZE() {
        return MAX_IDLE;
    }

    public static void setMAX_POOL_SIZE(int MAX_POOL_SIZE) {
        ConnectionPool.MAX_IDLE = MAX_POOL_SIZE;
    }
}
