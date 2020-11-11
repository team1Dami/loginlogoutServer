/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author USUARIO
 */
public class GmailNoCorrectException {
    
    private String gmail;

    /**
     * Method to set the message
     *
     * @param passwd
     */
    public GmailNoCorrectException(String passwd) {
        this.gmail = passwd;
    }

    /**
     * Method to get the message
     *
     * @return a string with the information to the user or null
     */
    public String getMessage() {
        if (gmail == null) {
            return "Gmail no acecuado";
        } else {
            return null;
        }
    }
}
