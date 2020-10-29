/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author eneko
 */
public class DAOFactory {
    
    private DAO myDAO= null;
   
    public static DAO getDAO(){
        
      /*  if(type.equals("MySql")) {
            return new DAO();
        }*/
        
        return new DAO();
    }
}
