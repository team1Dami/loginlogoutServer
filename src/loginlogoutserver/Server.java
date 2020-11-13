package loginlogoutserver;

import exceptions.NoServerConnectionException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import threads.ServerWorker;

/**
 *
 * @author saray, eneko
 */
public class Server {

    private static final Logger logger = Logger.getLogger("loginlogouServer.Server");
    private static ResourceBundle socketFile = ResourceBundle.getBundle("loginlogoutserver.socketFile");
    private static int PORT = Integer.parseInt(Server.socketFile.getString("PORT"));
    private static int MAX_CONN = Integer.parseInt(Server.socketFile.getString("MAX_CONN"));
    private static int numUsers = 0; // contador de clientes conectados

    public synchronized static int getNumUsers() {
        return numUsers;
    }

    public synchronized static void setNumUsers(int numUsers) {
        Server.numUsers = numUsers;
    }

    /**
     * Constructor
     */
    private Server() {
        socketFile = ResourceBundle.getBundle("loginlogoutserver.socketFile");
        PORT = Integer.parseInt(this.socketFile.getString("PORT"));
        MAX_CONN = Integer.parseInt(this.socketFile.getString("MAX_CONN"));
    }

    /**
     * Method main to start the infinite loop to wait a connection
     * When a client ask for a connection a new thread is created
     *
     * @param args
     */
    public static void main(String[] args) {

        Socket socket = null;
        ServerSocket server = null;

        try {
            // Se crea el serverSocket
            server = new ServerSocket(PORT);

            // Bucle infinito para esperar conexiones
            while (true) {
                logger.info("Servidor a la espera de conexiones.");
                socket = server.accept();
                logger.info("Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado.");

                if (true) {
                    numUsers++;  // sumamos 1 al contador de clientes conectados
                    ServerWorker myClientWorker = new ServerWorker(socket);

                    myClientWorker.start();
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Ha fallado la lectura en el servidor", ex.getMessage());
        } finally {
            try {
                socket.close();
                server.close();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Ha fallado el cierre del servidor " + ex.getMessage());

            }
        }
    } 
}