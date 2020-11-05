package control;

import classes.User;
import exceptions.LoginNoExistException;
import exceptions.NoConnectionDBException;
import exceptions.PasswordErrorException;
import interfaces.ClientServer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO is the one how can made queries (select and insert ) with the Data Base
 * (db)
 *
 * @author Gonzalo, Rubén, Saray
 */
public class DAO implements ClientServer {

    private Connection connection;
    private PreparedStatement preparedStmt;
    private Exception exception = null;

    private static final String insertUser = "INSERT INTO users VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String selectLogin = "SELECT login FROM users";
    private static final String selectPasswd = "SELECT password FROM users WHERE login = ?";
    private static final String selectMaxId = "SELECT MAX(id) FROM users";

    /**
     * Method to take the connection throws the thread serverWorker
     *
     * @param connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection; // instanciamos la conexión que nos da el hilo (que es la que le ha dado el pool de conexiones)
    }

    /**
     *
     * @param user
     * @return the values of login and password if the user doesn't exist into
     * the db returns login = null if the password doesn't match with that is
     * registered into the db of that login return password = null
     */
    @Override
    public User signIn(User user) {
        try {
            if (blnExist(user)) {
                if (blnPassExist(user)) {

                } else {
                    user.setPasswd(null);
                    throw new PasswordErrorException(null);
                }
            } else {
                user.setLogIn(null);
                throw new LoginNoExistException(null);
            }
        } catch (LoginNoExistException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            setException(ex);
        } catch (PasswordErrorException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            setException(ex);
        }
        return user;
    }

    /**
     * method to register a new user into DB
     *
     * @param user
     * @return the values of the new user
     *
     */
    @Override
    public User signUp(User user) {
        try {
            if (!blnExist(user)) {
                try {
                    Integer UserId = getUserId();
                    preparedStmt = connection.prepareStatement(insertUser);
                    preparedStmt.setInt(1, UserId);
                    preparedStmt.setString(2, user.getLogIn());
                    preparedStmt.setString(3, user.getEmail());
                    preparedStmt.setString(4, user.getFullname());
                    preparedStmt.setObject(5, 1);
                    preparedStmt.setObject(6, 1);
                    preparedStmt.setString(7, user.getPasswd());
                    preparedStmt.setDate(8, Date.valueOf(LocalDate.now()));
                    preparedStmt.setDate(9, Date.valueOf(LocalDate.now()));

                    preparedStmt.executeUpdate();

                } catch (SQLException sqlE) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, sqlE);
                    NoConnectionDBException noCon = new NoConnectionDBException(null);
                    setException(noCon);
                } catch (Exception e) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
                    setException(e);
                } finally {
                    try {
                        preparedStmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                        NoConnectionDBException noCon = new NoConnectionDBException(null);
                        setException(noCon);
                    }
                }
            } else {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, "");
                throw new LoginNoExistException(null);
            }
        } catch (LoginNoExistException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    /**
     * Method that sees if the user is already created
     *
     * @param user
     * @return true if the login exist into the db or false if its not
     * @throws UserExistException
     */
    public boolean blnExist(User user) {
        preparedStmt = null;
        ResultSet resultSet = null;
        try {
            preparedStmt = connection.prepareStatement(selectLogin);
            //  preparedStmt.setString(1, user.getLogIn());
            resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                if (user.getLogIn().equalsIgnoreCase(resultSet.getString("login"))) {
                    return true;
                }
            }
        } catch (SQLException sqlE) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, sqlE);
            NoConnectionDBException noCon = new NoConnectionDBException(null);
            setException(noCon);

        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            setException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                    NoConnectionDBException noCon = new NoConnectionDBException(null);
                    setException(noCon);
                }
            }
            if (preparedStmt != null) {
                try {
                    preparedStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                    NoConnectionDBException noCon = new NoConnectionDBException(null);
                    setException(noCon);
                }
            }
        }
        return false;
    }

    /**
     * method that verify if the passwd is correct
     *
     * @param user
     * @return true if the password is correct or false if its not
     * @throws PasswordErrorException
     */
    public boolean blnPassExist(User user) throws PasswordErrorException {
        preparedStmt = null;
        ResultSet resultSet = null;
        try {
            preparedStmt = connection.prepareStatement(selectPasswd);
            preparedStmt.setString(1, user.getLogIn());
            resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {
                if (user.getPasswd().equals(resultSet.getString("password"))) {
                    return true;
                }
            }
        } catch (SQLException sqlE) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, sqlE);
            NoConnectionDBException noCon = new NoConnectionDBException(null);
            setException(noCon);
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            setException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                    NoConnectionDBException noCon = new NoConnectionDBException(null);
                    setException(noCon);
                }
            }
            if (preparedStmt != null) {
                try {
                    preparedStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                    NoConnectionDBException noCon = new NoConnectionDBException(null);
                    setException(noCon);
                }
            }
        }
        return false;
    }

    /**
     * Method that save if an Exception has ocurred
     *
     * @param e
     */
    private void setException(Exception e) {
        this.exception = e;
    }

    /**
     * Method that get the exception saved previously
     *
     * @return the exception or null if any exception has ocurred
     */
    public Exception getException() {
        return this.exception;
    }

    /**
     * Method to increment automathicaly the user id
     *
     * @return max id is saved in the DB +1
     */
    private Integer getUserId() {
        Integer UserId = 0;
        ResultSet resultSet = null;
        try {
            preparedStmt = connection.prepareStatement(selectMaxId);
            resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                UserId = resultSet.getInt("MAX(id)");
            }
            UserId = UserId + 1; // sumamos 1 al total de Id users
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
            NoConnectionDBException noCon = new NoConnectionDBException(null);
            setException(noCon);
        }
        return UserId;
    }

    /**
     * Method that return the connection to the Pool
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            NoConnectionDBException noCon = new NoConnectionDBException(null);
            setException(noCon);
        }
    }
}
