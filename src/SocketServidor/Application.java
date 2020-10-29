/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocketServidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import threads.ServerWorker;



/**
 *
 * @author eneko
 */
public class Application {
    private static final int PORT = 5000;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket socket = null;
        
        
        
        while(true){
            ServerWorker hilo = new ServerWorker();
            System.out.println("Escuchando...");
            socket=serverSocket.accept(); //Proceso detenido hasta aceptar conexion
            System.out.println("Recibido, pasandoselo a el hilo");
            hilo.setSocket(socket);
            hilo.start();
        }
    }
}
