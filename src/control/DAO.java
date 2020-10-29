/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import Pool.ConnectionPool;
import classes.User;
import interfaces.ClientServer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2dam
 */
public class DAO implements ClientServer {

    private Connection connection = null;
    private PreparedStatement preparedStmt = null;
    private final ConnectionPool myPool = new ConnectionPool();

    //PreparedStatment
    private static final String insertCustomerAccount = "INSERT INTO customer_account VALUES (?,?,?,?,?,?,?)";
    private static final String selectLogin = "SELECT login FROM user WHERE login = ?";
    private static final String selectPasswd = "SELECT passwd FROM user WHERE login = ?";

    private ResourceBundle configFile;
    private String driverDB;
    private String urlDB;
    private String userDB;
    private String passwordDB;

    //method that creates a new user
    /**
     *
     * @param user
     */
    @Override
    public User signUp(User user) {
        boolean blnEstaDentro;
        blnEstaDentro = isUser(user);
        if (!blnEstaDentro) {
            try {
                // this.openConnection();
                // solicitamos conexión al Pool
                connection = getConnection();
                preparedStmt = connection.prepareStatement(insertCustomerAccount);

                preparedStmt.setString(1, user.getEmail());
                //preparedStmt.setString(1,user.getFullName());             
                preparedStmt.setLong(1, user.getId());
                preparedStmt.setDate(1, (Date) user.getLastAccess());
                preparedStmt.setDate(1, (Date) user.getLastPasswdChange());
                preparedStmt.setString(1, user.getLogIn());
                preparedStmt.setString(1, user.getPasswd());
                preparedStmt.setObject(1, user.getPrivilage());
                preparedStmt.setObject(1, user.getStatus());

                preparedStmt.executeUpdate();

            } catch (SQLException sqlE) {
                System.out.println("insert failed");
            } catch (Exception e) {
                System.out.println("");
            } finally {
                try {
                    preparedStmt.close();
                    connection.close();  // devolvemos la conexión al Pool
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            //Put a message telling the id is already used
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, "---");
        }
        return user;
    }

    //method that sees if the user is already created
    public boolean isUser(User user) {
        preparedStmt = null;
        boolean blnExist = false;
        ResultSet resultSet = null;
        try {
            // pedimos conexión al Pool
            connection = getConnection();
            preparedStmt = connection.prepareStatement(selectLogin);
            preparedStmt.setString(1, user.getLogIn());

            resultSet = preparedStmt.executeQuery(selectLogin);

            while (resultSet.next()) {
                if (user.getLogIn().equals(resultSet.getString("login"))) {
                    System.out.println("El login introducido ya esta registrado");
                    blnExist = true;
                }
            }
        } catch (SQLException sqlE) {
            System.out.println("no exist");
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();

                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (preparedStmt != null) {
                try {
                    preparedStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                connection.close();  // devolvemos conexión al pool
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return blnExist;
    }

    //method that sees if the login is correct
    @Override
    public User signIn(User user) {
        boolean blnTodoCorrecto = false;
        boolean blnEstaDentro = false;
        blnEstaDentro = isUser(user);
        if (blnEstaDentro) {
            blnEstaDentro = false;
            blnEstaDentro = passwdCorrect(user);
            if (blnEstaDentro) {
                //both login and passwd are correct
                blnTodoCorrecto = true;
            } else {
                //login is correct but the passwd is wrong
            }
        } else {
            //login is incorrect
        }
        if (blnTodoCorrecto) {
            //Actualizar la hora de ultimo login

        }
        return user;
    }

    private void lastLogin(Date last) {

    }

    //methos that sees if the passwd is correct
    public boolean passwdCorrect(User user) {
        PreparedStatement preparedStmt = null;
        boolean blnCorrect = false;
        ResultSet resultSet = null;
        try {
            // pedimos conexión al Pool
            connection = getConnection();
            preparedStmt = connection.prepareStatement(selectPasswd);
            preparedStmt.setString(1, user.getPasswd());

            resultSet = preparedStmt.executeQuery(selectPasswd);

            while (resultSet.next()) {
                if (user.getPasswd().equals(resultSet.getString("passwd"))) {
                    System.out.println("Password correct");
                    blnCorrect = true;
                }
            }

        } catch (SQLException sqlE) {
            System.out.println("no exist");
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (preparedStmt != null) {
                try {
                    preparedStmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                connection.close();  // devolvemos conexión al Pool
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return blnCorrect;
    }

    // return conexión del pool
    private Connection getConnection() {
        try {
            return myPool.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //method that makes a connection with the database
    /*  private void openConnection() {

        try {
            if(connection == null){
                connection = myPool.getDataSource().getConnection();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //method that close a connection with the database
    private void closeConnection() {
        try {
            //statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     */
}
