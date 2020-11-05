package loginlogoutserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import threads.ServerWorker;

/**
 * Server class coints the main method to start the server and the serverSocket
 *
 * @author saray, eneko
 */
public class Server {

    private static final Logger logger = Logger.getLogger("loginlogouServer.Server");
    private static ResourceBundle socketFile = ResourceBundle.getBundle("loginlogoutserver.socketFile");
    private static int PORT = Integer.parseInt(Server.socketFile.getString("PORT"));
    private static int MAX_CONN = Integer.parseInt(Server.socketFile.getString("MAX_CONN"));

    /**
     * Constructor
     */
    private Server() {
        socketFile = ResourceBundle.getBundle("loginlogoutserver.socketFile");
        PORT = Integer.parseInt(this.socketFile.getString("PORT"));
        MAX_CONN = Integer.parseInt(this.socketFile.getString("MAX_CONN"));
    }

    /**
     * Method main to start the infinite loop to wait a connection When a client
     * ask for a connection a new thread is created
     *
     * @param args
     */
    public static void main(String[] args) {

        Socket socket = null;
        ServerSocket server = null;
        ObjectInputStream objectInput = null;

        int numUsers = 0; // contador de clientes conectados

        try {
            // Se crea el serverSocket
            server = new ServerSocket(PORT);

            // Bucle infinito para esperar conexiones
            while (true) {
                logger.info("Servidor a la espera de conexiones.");
                socket = server.accept();
                logger.info("Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado.");

                if (numUsers <= MAX_CONN) {
                    numUsers++;  // sumamos 1 al contador de clientes conectados
                    ServerWorker myClientWorker = new ServerWorker(socket);

                    myClientWorker.start();
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Ha fallado la lectura en el servidor", ex.getMessage());
        } finally {
            try {
                numUsers--; // restamos 1 al contador de clientes conectados
                objectInput.close();
                socket.close();
                server.close();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Ha fallado el cierre del servidor " + ex.getMessage());

            }
        }
    }

}
