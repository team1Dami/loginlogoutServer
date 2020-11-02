/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Pool.ConnectionPool;
import static Pool.ConnectionPool.getPoolInstance;

/**
 *
 * @author eneko, saray
 */
public class DAOFactory {

    //   private DAO myDAO = null;
    public synchronized static DAO getDAO() {
        return new DAO();
    }

    public synchronized static ConnectionPool Pool() throws Exception {
        // le pide al Pool una instancia
        return getPoolInstance();
    }
}
