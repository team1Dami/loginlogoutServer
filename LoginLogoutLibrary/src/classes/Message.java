/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author eneko
 */
public class Message {
    private User usuario;
    private enum type{
        logIn,
        logUp,
        logOut
    }
    private Exception e;

    public User getUsuario() {
        return usuario;
    }

    public Exception getE() {
        return e;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public void setE(Exception e) {
        this.e = e;
    }
    
}
