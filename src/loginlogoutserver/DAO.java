/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginlogoutserver;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalo
 */
public class DAO {
    
    private Connection connection=null;  
    private PreparedStatement preparedStmt=null;
    
    //method that makes a connection with the database
    private void openConnection() throws Exception {
	
        //Class.forName("com.mysql.cj.jdbc.Driver");
        String path = "jdbc:mysql://localhost:3306/LoginLogout";
	      connection = DriverManager.getConnection(path, "root", "");
      
    }
 
    //method that close a connection with the database
   private void closeConnection() throws SQLException {
	//statement.close();
	    connection.close();
   }
   
   //method that creates a new user
    public void signUp(User user){
        boolean blnEstaDentro;
        blnEstaDentro= isUser(user);
        if(!blnEstaDentro){
             try{
                this.openConnection(); 

                preparedStmt = connection.prepareStatement("INSERT INTO customer_account VALUES (?,?,?,?,?,?,?)");
                
                preparedStmt.setString(1,user.getEmail());
                preparedStmt.setString(1,user.getFullName());             
                preparedStmt.setLong(1, user.getId());
                preparedStmt.setDate(1, (Date) user.getLastAccess());
                preparedStmt.setDate(1, (Date) user.getLastPasswdChange());
                preparedStmt.setString(1,user.getLogIn());
                preparedStmt.setString(1,user.getPasswd());
                preparedStmt.setObject(1, user.getPrivilage());
                preparedStmt.setObject(1, user.getStatus());
                
                preparedStmt.executeUpdate();

                preparedStmt.close();
                this.closeConnection();

            } catch (SQLException sqlE) {
                 System.out.println("insert failed");
             } catch (Exception e){
                 System.out.println("");
             }
        }else{
            //Put a message telling the id is already used
            //Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
    
    //method that sees if the user is already created
    public boolean isUser(User user){
       PreparedStatement preparedStmt = null;
       boolean blnExist = false;
       
       try{
           this.openConnection();         
            String select = "select login from user where login = "+user.getLogIn()+";";
            preparedStmt = connection.prepareStatement(select);
         
            ResultSet resultSet = preparedStmt.executeQuery(select);
           
            while (resultSet.next()){              
                if(user.getLogIn().equals(resultSet.getString("login"))){
                    System.out.println("El login introducido ya esta registrado");
                    blnExist=true;
                }
            }
         
         resultSet.close();
         preparedStmt.close();
         
         this.closeConnection();
        }catch (SQLException sqlE) {
            System.out.println("no exist");
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return blnExist;
    }
    
    //method that sees if the login is correct
    public boolean logIn(User user){
        boolean blnTodoCorrecto=false;
        boolean blnEstaDentro=false;
        blnEstaDentro= isUser(user);
        if(blnEstaDentro){
            blnEstaDentro=false;
            blnEstaDentro = passwdCorrect(user);
            if(blnEstaDentro){
                //both login and passwd are correct
                blnTodoCorrecto=true;
            }else{
                //login is correct but the passwd is wrong
            }
        }else{
            //login is incorrect
        }
        return blnTodoCorrecto;
    }
    
    //methos that sees if the passwd is correct
    public boolean passwdCorrect(User user){
        PreparedStatement preparedStmt = null;
       boolean blnCorrect = false;
       
       try{
           this.openConnection();         
            String select = "select passwd from user where login = "+user.getLogIn()+";";
            preparedStmt = connection.prepareStatement(select);
         
            ResultSet resultSet = preparedStmt.executeQuery(select);
           
            while (resultSet.next()){              
                if(user.getPasswd().equals(resultSet.getString("passwd"))){
                    System.out.println("Password correct");
                    blnCorrect=true;
                }
            }
         
         resultSet.close();
         preparedStmt.close();
         
         this.closeConnection();
        }catch (SQLException sqlE) {
            System.out.println("no exist");
        } catch (Exception ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return blnCorrect;
    }

}
