/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import java.io.Serializable;

/**
 * PasswordErrorException class
 *
 * @author saray
 */
public class PasswordErrorException extends Exception implements Serializable {

    private String password;

    /**
     * Method to set the message
     *
     * @param passwd
     */
    public PasswordErrorException(String passwd) {
        this.password = passwd;
    }

    /**
     * Method to get the message
     *
     * @return a string with the information to the user or null
     */
    public String getMessage() {
        if (password == null) {
            return "Usuario o contraseña incorrectos";
        } else {
            return null;
        }
    }
}
