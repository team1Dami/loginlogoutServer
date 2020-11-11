package exceptions;

import java.io.Serializable;

/**
 * UserExistException class
 *
 * @author saray
 */
public class UserExistException extends Exception implements Serializable {

    private String Login;

    /**
     * Method to set the message
     *
     * @param log
     */
    public UserExistException(String log) {
        this.Login = log;
    }

    /**
     * Method to get the message
     *
     * @return a string with the information to the user or null
     */
    public String getMessage() {
        if (Login == null) {
            return "El login ya está registrado";
        } else {
            return null;
        }
    }
}
