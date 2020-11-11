package exceptions;

import java.io.Serializable;

/**
 * LoginNoExistException class
 *
 * @author saray
 */
public class LoginNoExistException extends Exception implements Serializable {

    private String message;

    /**
     * Method to set the message
     *
     * @param log
     */
    public LoginNoExistException(String m) {
        this.message = m;
    }

    /**
     * Method to get the message
     *
     * @return a string with the information to the user or null
     */
    public String getMessage() {
        if (message == null) {
            return "El usuario no está registrado";
        } else {
            return null;
        }
    }
}
