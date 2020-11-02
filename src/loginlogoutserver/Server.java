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
    private static int MAX_CONN =Integer.parseInt(Server.socketFile.getString("MAX_CONN"));
    
    
    // constructor
    private Server() {
        socketFile = ResourceBundle.getBundle("loginlogoutserver.socketFile");
        PORT = Integer.parseInt(this.socketFile.getString("PORT"));
        MAX_CONN = Integer.parseInt(this.socketFile.getString("MAX_CONN"));
    }

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {

        Socket socket = null;
        ServerSocket server = null;
        ObjectInputStream objectInput = null;
        
    //    System.out.println(HOST);
        System.out.println(PORT);
        System.out.println(MAX_CONN);

        int numUsers = 0; // contador de clientes conectados

        try {
            // Se crea el serverSocket
            server = new ServerSocket(PORT);

            // Bucle infinito para esperar conexiones
            while (true) {
                logger.info("Servidor a la espera de conexiones.");
                socket = server.accept();
                logger.info("Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado.");

    //            objectInput = new ObjectInputStream(socket.getInputStream());

                if (numUsers <= MAX_CONN) {
                    numUsers++;  // sumamos 1 al contador de clientes conectados
                    ServerWorker myClientWorker = new ServerWorker(socket);

                 //   myClientWorker.setSocket(socket);
                    myClientWorker.start();
                    if (!myClientWorker.isAlive()) {
                        numUsers--;
                    }
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Ha fallado la lectura en el servidor", ex.getMessage());
        } finally {
            try {
            //    numUsers--; // restamos 1 al contador de clientes conectados
                objectInput.close();
                socket.close();
                server.close();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, "Ha fallado el cierre del servidor " + ex.getMessage());
            }
        }
    }

}
