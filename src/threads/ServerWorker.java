/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import Pool.ConnectionPool;
import classes.Message;
import classes.User;
import control.DAO;
import control.DAOFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saray
 */
public class ServerWorker extends Thread {

    private static final Logger logger = Logger.getLogger("threads.ServerWorker");

    
    private Connection connection = null;
    private Message myMessage;
    private Socket mySocket;
    // private FactoryDao myDAO = new FactoryDAO(); 

    public Socket getSocket() {
        return mySocket;
    }

    public void setSocket(Socket socket) {
        this.mySocket = socket;
    }
    public ServerWorker(Socket socket) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        // pedir clase DAO a la factory
        ObjectInputStream objectInput = null;
        ObjectOutputStream objectOutput = null;
       
        Message message;
        User myUser;

        try {
            // instanciamos bufferes de lectura y escritura
            objectInput = new ObjectInputStream(mySocket.getInputStream()); // para leer lo que nos manda el cliente          
            objectOutput = new ObjectOutputStream(mySocket.getOutputStream());  // para contestar al cliente
            
            // leemos el mensaje y recogemos los datos
            message=(Message) objectInput.readObject();
            myUser=message.getUser();   
            message.setUser(myUser);       
            
            DAO myDAO;
            myDAO = DAOFactory.getDAO();
            
            // cambiar y pedir conexión a la clase DAO que nos devuelve el factory
          //  connection = myPool.getDataSource().getConnection();

            // if obtains connection sends data to DAO class
            
            
            // escribimos los datos en el buffer
            objectOutput = new ObjectOutputStream(mySocket.getOutputStream());
            objectOutput.writeObject(message);
            
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        } // trabajar con la conexión
        finally {
            try {
                connection.close(); // devuelve la conexión a su pool
            } catch (SQLException ex) {
                Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
