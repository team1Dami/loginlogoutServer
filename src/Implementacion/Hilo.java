/*
 * 
 *
 */
package Implementacion;

import classes.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rubén Rabadán
 */
public class Hilo extends Thread {

    private static final Logger logger = Logger.getLogger("Implementacion.Hilo");
    private static final ResourceBundle clientFile = ResourceBundle.getBundle("ApplicationClient.Properties/Client");
    private static String HOST;
    private static int PORT;
    private Message message;

    /**
     *
     * @param message
     */
    public void setMessage(Message message) {
        this.message = message;
    }

    /**
     *
     */
    public void run() {
        logger.info("Hilo del cliente recogiendo el mensaje");

        this.HOST = clientFile.getString("HOST");
        this.PORT = Integer.parseInt(this.clientFile.getString("PORT"));

        Socket socket = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            socket = new Socket(HOST, PORT);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
            logger.log(Level.INFO, "Mensaje{0}", message.getUser());

            objectInputStream = new ObjectInputStream(socket.getInputStream());
            message = (Message) objectInputStream.readObject();

        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            message.setException(ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
            message.setException(ex);
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                    objectInputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
                    message.setException(ex);
                }
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
                message.setException(ex);
            }
        }
    }

    /**
     *
     * @return
     */
    public Message getMessage() {
        return message;
    }
}
