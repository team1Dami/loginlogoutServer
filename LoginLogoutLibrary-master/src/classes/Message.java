package classes;

import java.io.Serializable;

/**
 * Class Message used to communicate with the server
 *
 * @author eneko
 */
public class Message implements Serializable {

    private User user;

    private enum Type {
        logIn,
        logUp,
        logOut
    }
    private Type type;
    private Exception exception;

    /**
     * constructor
     */
    public Message() {
    }

    /**
     * Method to obtain the enum type
     *
     * @return logIn or logUp or logOut
     */
    public String getType() {
        return type.name();
    }

    /**
     * Method to set the enum type in order to ask for a query to the DB
     *
     * @param windowType
     */
    public void setType(int windowType) {
        if (windowType == 1) {
            this.type = type.logIn;
        } else if (windowType == 2) {
            this.type = type.logUp;
        }
    }

    /**
     * Method to obtain the User class with the values of the DB
     *
     * @return User class
     */
    public User getUser() {
        return user;
    }

    /**
     * Method to obtain the exception message
     *
     * @return string message or null
     */
    public String getException() {
        if (exception == null) {
            return null;
        } else {
            return this.exception.getMessage();
        }
    }

    /**
     * Method to set the User class with the values of the client
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Method to set the exception
     *
     * @param exception it can be null
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }
}