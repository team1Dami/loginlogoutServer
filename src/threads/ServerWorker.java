/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;


import classes.Message;
import classes.User;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saray
 */
public class ServerWorker extends Thread{
    private static final Logger logger = Logger.getLogger("threads.ServerWorker");
    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    


    
    @Override
    public void run() {
        ObjectOutputStream escritura;
        ObjectInputStream salida;
        Message message;
        User usuario;
        
        try {
            salida = new ObjectInputStream(socket.getInputStream());
            message=(Message) salida.readObject();
            usuario=message.getUser();
            System.out.println(message.getUser().getLogIn());
            System.out.println(message.getUser().getPasswd());
            usuario.setEmail("ruben@funciona.com");
            message.setUser(usuario);
            
            escritura = new ObjectOutputStream(socket.getOutputStream());
            escritura.writeObject(message);
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
