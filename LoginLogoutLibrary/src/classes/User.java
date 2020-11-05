/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author 2dam
 */
public class User implements Serializable{
    
    enum Userstatus{
        ENABLE,
        DISABLE
        
    }
    enum UserPrivilage{
        USER,
        ADMIN
    }
    private Integer id;
    private String logIn;
    private String email;
    private String fullname;
    private Userstatus status;
    private UserPrivilage privilage;
    private String passwd;
    private java.sql.Date lastAccess;
    private java.sql.Date lastPasswdChange;

    public UserPrivilage getPrivilage() {
        return privilage;
    }

    public void setPrivilage(UserPrivilage privilage) {
        this.privilage = privilage;
    }
    
    
    public Userstatus getStatus() {
        return status;
    }

    public void setStatus(Userstatus status) {
        this.status = status;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogIn() {
        return logIn;
    }

    public void setLogIn(String logIn) {
        this.logIn = logIn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = (java.sql.Date) lastAccess;
    }

    public Date getLastPasswdChange() {
        return lastPasswdChange;
    }

    public void setLastPasswdChange(Date lastPasswdChange) {
        this.lastPasswdChange = (java.sql.Date) lastPasswdChange;
    }
  
}
