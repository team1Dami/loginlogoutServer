/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import classes.Message;
import classes.User;
import control.DAO;
import control.DAOFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
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

    @Override
    public void run() {
        // pedir clase DAO a la factory
        ObjectInputStream objectInput = null;
        ObjectOutputStream objectOutput = null;

        Message message;
        User myUser;

        try {
            logger.info("voy a abrir el buffer");
            // instanciamos bufferes de lectura y escritura
            objectInput = new ObjectInputStream(mySocket.getInputStream()); // para leer lo que nos manda el cliente          

            logger.info("voy a leer del buffer");
            // leemos el mensaje y recogemos los datos
            message = (Message) objectInput.readObject();
            myUser = message.getUser();
            myUser.setLogIn("ServerWorker");
            message.setUser(myUser);

            DAO myDAO;
            myDAO = DAOFactory.getDAO();
            Connection conn = DAOFactory.Pool().getConnection(); // recogemos la conexión de nuestra instancia del Pool (recuerda que el método Pool pide una instancia del ConnectionPool!!!!
            myDAO.setConnection(conn);  // enviamos conexión a la DB

            if (message.getType().equals("logIn")) {
                message.setUser(myDAO.signIn(myUser));  // recogemos el user tras la consulta a la DB
                message.setException(myDAO.getException());  // recogemos la excepción que nos viene de la DB

            } else if (message.getType().equals("logUp")) {
                message.setUser(myDAO.signUp(myUser));  // recogemos el user tras la consulta a la DB
                message.setException(myDAO.getException()); // recogemos la excepción que nos viene de la DB
            }
            message.setException(myDAO.getException());
            // escribimos los datos en el buffer
            objectOutput = new ObjectOutputStream(mySocket.getOutputStream());
            objectOutput.writeObject(message);

        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                //  connection.close(); // devuelve la conexión a su pool
                objectOutput.close();
                objectInput.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Socket getSocket() {
        return mySocket;
    }

    public void setSocket(Socket socket) {
        this.mySocket = socket;
    }

    public ServerWorker(Socket socket) {

    }

    public void setMessage(Message message) {
        this.myMessage = message;
    }
}
