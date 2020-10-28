/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginlogoutserver;

import classes.Message;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rubir
 */
public class Server {
    
    private  final String HOST = "localhost";
    private static final int PORT = 5000;
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        ServerSocket serverSocket = null;
        Socket socket = null;
        
        // declaramos Streams 
        ObjectInputStream objectInput;
        ObjectOutputStream objectOutput;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Sevidor iniciado");
            
            while(true) {  // mantenemos a la escucha
                socket = serverSocket.accept(); // devuelve el socket del cliente
                
                System.out.println("Cliente conectado");
                objectInput = new ObjectInputStream(socket.getInputStream()); // para leer lo que nos manda el cliente
                objectOutput = new ObjectOutputStream(socket.getOutputStream());  // para contestar al cliente
                
                 //importante que el primero en ejecutarse en el lado servidor sea el mensaje que se LEE 
                 //(que le llega desde el cliente)
               
                //= objectInput.readUTF(); // lee lo que le manda
                
              //  objectOutput.writeUTF(messageClient);
                
                socket.close();  // cerramos el cliente   
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
