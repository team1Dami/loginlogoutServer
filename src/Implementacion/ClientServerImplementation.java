/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementacion;

import classes.Message;
import classes.User;
import interfaces.ClientServer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author rubir
 */
public class ClientServerImplementation implements ClientServer {

    private User userPrueba = new User();
    private Message message = new Message();
    private String login;

    /**
     *
     * @param user
     * @return
     */
    @Override
    public User signIn(User user) {
        userPrueba = user;
        message.setUser(user);

        message.setType(1);
        Hilo hilo = new Hilo();
        hilo.setMessage(message);
        hilo.start();
        try {
            hilo.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        message = hilo.getMessage();

        user = message.getUser();
        this.login = user.getLogIn();
        if (message.getException() != null) {
            user = null;
            String error = exceptions();
            Alert alert = new Alert(Alert.AlertType.ERROR, error, ButtonType.OK);
            alert.showAndWait();
            message.setException(null);
        }
        return user;
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public User signUp(User user) {

        message.setUser(user);
        message.setType(2);
        Hilo hilo = new Hilo();
        hilo.setMessage(message);
        hilo.start();
        try {
            hilo.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        message = hilo.getMessage();
        if (message.getException() != null) {
        } else {

        }
        return user;
    }

    /**
     *
     * @return
     */
    public String exceptions() {
        String error = null;
        switch (message.getException().toString()) {
            case "UserExistException":
                error = "Usuario ya registrado";
                break;
            case "EmailExistException":
                error = "El email ya está registrado";
                break;
            case "EmailFormatException":
                error = "Email con formato incorrecto"
                        + "\n Por favor introduzca un email válido";
                break;
            case "LoginNoExistException":
                error = "El login ya está registrado";
                break;
            case "NoConnectionDBException":
                error = "Ha ocurrido un error inesperado"
                        + "\n Inténtelo de nuevo en unos minutos";
                break;
            case "PasswordErrorException":
                error = "Usuario o contraseña incorrectos";
                break;
            case "NoServerConnectionException":
                break;
            default:
                error = "Ha ocurrido un error inesperado"
                        + "\n Inténtelo de nuevo en unos minutos";
        }
        return error;
    }

    /**
     *
     * @return
     */
    public String getLogin() {

        return login;
    }
}
