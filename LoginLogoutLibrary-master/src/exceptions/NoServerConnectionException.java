/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import java.io.Serializable;

/**
 * NoServerConnectionException class
 *
 * @author saray
 */
public class NoServerConnectionException extends Exception implements Serializable {

    private String message;
    //  private Error code;

    /**
     * Method to set the message
     *
     * @param log
     */
    public NoServerConnectionException(String m) {
        this.message = m;
    }

    /**
     * Method to get the message
     *
     * @return a string with the information to the user or null
     */
    public String getMessage() {
        if (message == null) {
            return "Ha ocurrido un error inesperado"
                    + "\n Inténtelo de nuevo en unos minutos";
        } else {
            return null;
        }
    }
}
