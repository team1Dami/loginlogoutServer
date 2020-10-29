/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginlogoutserver;

import classes.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import threads.ServerWorker;

/**
 *
 * @author saray, eneko
 */
public class Server {

    private static final Logger logger = Logger.getLogger("loginlogouServer.Server");

    private final String HOST = "localhost";
    private static final int PORT = 5000;
    private static final int MAX_CONN = 10;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Socket socket = null;
        ServerSocket server = null;
        Message message;
        message = new Message();
        ObjectInputStream objectInput = null;

        try {
            // Se crea el serverSocket
            server = new ServerSocket(PORT, MAX_CONN);

            // Bucle infinito para esperar conexiones
            while (true) {
                logger.info("Servidor a la espera de conexiones.");
                socket = server.accept();
                logger.info("Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado.");
                
                objectInput = new ObjectInputStream(socket.getInputStream());
                
                ServerWorker myClientWorker = new ServerWorker(socket);
                myClientWorker.setSocket(socket);
                
                myClientWorker.start();

            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Ha fallado la lectura en el servidor", ex.getMessage());
        } finally {
            try {
                socket.close();
                server.close();
            } catch (IOException ex) {
                logger.log(Level.SEVERE,"Ha fallado el cierre del servidor " + ex.getMessage());
            }
        }
    }
}
