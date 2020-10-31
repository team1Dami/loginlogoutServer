/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import classes.User;
import interfaces.ClientServer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2dam
 */
public class DAO implements ClientServer {

    private Connection connection = null;
    private PreparedStatement preparedStmt = null;
    private Exception exception = null;

    //PreparedStatment
    private static final String insertUser = "INSERT INTO users VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String selectLogin = "SELECT login FROM users WHERE login = ?";
    private static final String selectPasswd = "SELECT passwd FROM users WHERE login = ?";
    private static final String selectCountId = "SELECT MAX(id) FROM user";

    public void setConnection(Connection connection) {
        this.connection = connection; // instanciamos la conexión que nos da el hilo (que es la que le ha dado el pool de conexiones)
    }

    //method that sees if the login is correct
    @Override
    public User signIn(User user) {
        boolean blnOk = false;

        if (blnExist(user)) {
            if (blnPassExist(user)) {
                //both login and passwd are correct
                blnOk = true;
            } else {
                //login is correct but the passwd is wrong
                user.setPasswd(null);
            }
        } else {
            //login is incorrect
            user.setLogIn(null);
        }
        if (blnOk) {
            //Actualizar la hora de ultimo login
        }
        return user;
    }

    //method that creates a new user
    /**
     *
     * @param user
     */
    @Override
    public User signUp(User user) {

        if (!blnExist(user)) {
            try {
                Long UserId = getUserId();
                preparedStmt = connection.prepareStatement(insertUser);

                preparedStmt.setLong(1, UserId);
                preparedStmt.setString(2, user.getLogIn());
                preparedStmt.setString(3, user.getEmail());
                preparedStmt.setString(4, user.getFullname());
                preparedStmt.setObject(5, user.getStatus());
                preparedStmt.setObject(6, user.getPrivilage());
                preparedStmt.setString(7, user.getPasswd());
                preparedStmt.setDate(8, (Date) user.getLastAccess());
                preparedStmt.setDate(9, (Date) user.getLastPasswdChange());

                preparedStmt.executeUpdate();

            } catch (SQLException sqlE) {
// falla conexión con DB Definir qué excepción ponemos----------------------------------------------------------------------
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, sqlE);
                setException(sqlE);
            } catch (Exception e) {
// definir qué está cascando------------------------------------------------------------------------------------------------
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
                setException(e);
            } finally {
                try {
                    preparedStmt.close();
                    connection.close();  // devolvemos la conexión al Pool
                } catch (SQLException ex) {
// falla cierre de conexión con DB----------------------------------------------------------------------                    
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                    setException(ex);
                }
            }
        } else {
// Put a message telling the id is already used---------------------------------------------------------
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, "---");
        }
        return user;
    }

    //method that sees if the user is already created
    public boolean blnExist(User user) {
        preparedStmt = null;
        ResultSet resultSet = null;
        try {
            preparedStmt = connection.prepareStatement(selectLogin);
            preparedStmt.setString(1, user.getLogIn());

            resultSet = preparedStmt.executeQuery(selectLogin);

            while (resultSet.next()) {
                if (user.getLogIn().equalsIgnoreCase(resultSet.getString("login"))) {
                    //System.out.println("El login introducido ya esta registrado");   //
                    return true;
                }
            }
        } catch (SQLException sqlE) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, sqlE);
            setException(sqlE);
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            setException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                    setException(ex);
                }
            }
            if (preparedStmt != null) {
                try {
                    preparedStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                    setException(ex);
                }
            }
            try {
                connection.close();  // devolvemos conexión al pool
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                setException(ex);
            }
        }
        return false;
    }

    //methos that sees if the passwd is correct
    public boolean blnPassExist(User user) {
        preparedStmt = null;
        //  boolean blnCorrect = false;
        ResultSet resultSet = null;
        try {

            preparedStmt = connection.prepareStatement(selectPasswd);
            preparedStmt.setString(1, user.getPasswd());

            resultSet = preparedStmt.executeQuery(selectPasswd);

            while (resultSet.next()) {
                if (user.getPasswd().equals(resultSet.getString("passwd"))) {
                    System.out.println("Password correct");  // quitar syso
                    return true;
                }
            }
        } catch (SQLException sqlE) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, sqlE);
            setException(sqlE);
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            setException(ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                    setException(ex);
                }
            }
            if (preparedStmt != null) {
                try {
                    preparedStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                    setException(ex);
                }
            }
            try {
                connection.close();  // devolvemos conexión al Pool
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                setException(ex);
            }
        }
        return false;
    }

    private void setException(Exception e) {
        this.exception = e;
    }

    public Exception getException() {
        return this.exception;
    }

    private Long getUserId() {
        Long UserId = null;
        ResultSet resultSet = null;
        try {
            preparedStmt = connection.prepareStatement(selectCountId);
            resultSet = preparedStmt.executeQuery(selectLogin);
            while (resultSet.next()) {
                UserId = resultSet.getLong("Id");
            }
            UserId += UserId; // sumamos 1 al total de Id users
        } catch (SQLException e) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, e);
            setException(e);
        }
        return UserId;
    }
}
