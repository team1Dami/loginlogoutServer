package threads;

import classes.Message;
import classes.User;
import control.DAO;
import control.DAOFactory;
import exceptions.NoConnectionDBException;
import exceptions.NoServerConnectionException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import loginlogoutserver.Server;

/**
 * ServerWorker class extends Thread
 *
 * @author saray
 */
public class ServerWorker extends Thread {

    private static final Logger logger = Logger.getLogger("threads.ServerWorker");

    private Connection connection = null;
    private Message myMessage;
    private Socket mySocket;

    /**
     * Constructor to set the socket into the thread ServerWorker
     *
     * @param socket
     */
    public ServerWorker(Socket socket) {
        this.mySocket = socket;
    }

    @Override
    /**
     * In the inherit method run we can recover the information that the client
     * sends to the server Provides throws the DAOFactory a DAO class and an
     * Instance of a ConnectionPool class ask for a connection to the
     * PoolInstance and set this one to the individual DAO class in order to
     * make queries Finally returns the answer of the db and send it to the
     * client
     */
    public void run() {

        ObjectInputStream objectInput = null;
        ObjectOutputStream objectOutput = null;

        Message message = null;
        User myUser = null;

        try {
            logger.info("voy a abrir el buffer de lectura");
            // instanciamos bufferes de lectura y escritura
            objectInput = new ObjectInputStream(mySocket.getInputStream()); // para leer lo que nos manda el cliente          

            logger.info("voy a leer del buffer");
            // leemos el mensaje y recogemos los datos
            message = (Message) objectInput.readObject();
            //  setMessage(message);
            myMessage = message;
            myUser = myMessage.getUser();
            //   myUser.setLogIn("ServerWorker");
            myMessage.setUser(myUser);
            // pedimos DAO a la DAOFactory
            DAO myDAO;
            myDAO = DAOFactory.getDAO();

            Connection conn = null;
            try {
                conn = DAOFactory.Pool().getConnection(); // recogemos la conexión de nuestra instancia del Pool (recuerda que el método Pool pide una instancia del ConnectionPool!!!!
            } catch (NoConnectionDBException ex) {
                Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
                message.setException(ex);
            } catch (Exception ex) {
                Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
                NoServerConnectionException noCon = new NoServerConnectionException(null);
                message.setException(noCon);
            }
            myDAO.setConnection(conn);  // enviamos conexión a la DB

            if (myMessage.getType().equals("logIn")) {
                myMessage.setUser(myDAO.signIn(myUser));  // recogemos el user tras la consulta a la DB
                myMessage.setException(myDAO.getException());  // recogemos la excepción que nos viene de la DB

            } else if (myMessage.getType().equals("logUp")) {
                myMessage.setUser(myDAO.signUp(myUser));  // recogemos el user tras la consulta a la DB
                myMessage.setException(myDAO.getException()); // recogemos la excepción que nos viene de la DB
            }
            message.setException(myDAO.getException());
            // escribimos los datos en el buffer
            objectOutput = new ObjectOutputStream(mySocket.getOutputStream());
            objectOutput.writeObject(myMessage);

            //myDAO.closeConnection(); // devolvemos conexión
            if (conn != null) {
                conn.close();
            }

        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
            NoServerConnectionException noCon = new NoServerConnectionException(null);
            message.setException(noCon);

        } catch (SQLException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
            NoConnectionDBException noCon = new NoConnectionDBException(null);
            message.setException(noCon);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
            NoServerConnectionException noCon = new NoServerConnectionException(null);
            message.setException(noCon);
        } finally {
            try {
                Server.setNumUsers(Server.getNumUsers() - 1);
                objectOutput.close();
                objectInput.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
                NoServerConnectionException noCon = new NoServerConnectionException(null);
                message.setException(noCon);
            }
        }
    }

    /**
     * Method to get the socket
     *
     * @return socket
     */
    public Socket getSocket() {
        return mySocket;
    }

    /**
     * Method to set the socket
     *
     * @param socket
     */
    public void setSocket(Socket socket) {
        this.mySocket = socket;
    }

    /**
     * Method to set the message sending by the client
     *
     * @param message
     */
    public void setMessage(Message message) {
        this.myMessage = message;
    }
}
