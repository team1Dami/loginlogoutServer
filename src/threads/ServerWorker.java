/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import Pool.ConnectionPool;
import classes.Message;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saray
 */
public class ServerWorker extends Thread{
    
    private static final Logger logger = Logger.getLogger("threads.ServerWorker");
    
    private final ConnectionPool myPool = new ConnectionPool(); 
    private Connection connection = null;
    private Message message;
    
    @Override
    public void run() {
        try {
            connection = myPool.getDataSource().getConnection();
            // if obtains connection sends data to DAO class
        } catch (SQLException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        // trabajar con la conexión
        finally {
            try {
                connection.close(); // devuelve la conexión a su pool
            } catch (SQLException ex) {
                Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
