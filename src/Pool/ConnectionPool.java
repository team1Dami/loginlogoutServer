/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pool;

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
    private final ResourceBundle ConnectionFile;
   
    private static BasicDataSource basicDataSource=null;
    
    /**
     * Constructor
     */
    public ConnectionPool(){
        
        this.ConnectionFile = ResourceBundle.getBundle("control.ConnectionFile");
        
        if (null==basicDataSource){         
            basicDataSource.setDriverClassName(this.ConnectionFile.getString("Driver"));
            basicDataSource.setUsername(this.ConnectionFile.getString("DBUser"));
            basicDataSource.setPassword(this.ConnectionFile.getString("DBPass"));
            basicDataSource.setUrl(this.ConnectionFile.getString("Conn"));
            basicDataSource.setMinIdle(0);
            basicDataSource.setMaxIdle(10);
        }
   }
    
    public BasicDataSource getDataSource(){
        return basicDataSource;
    }
    
}
