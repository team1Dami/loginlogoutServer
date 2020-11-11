package classes;

import java.io.Serializable;
import java.util.Date;

/**
 * Class User used to set the values of the client
 * This class is common to the both projects
 * @author eneko, ruben, saray
 */
public class User implements Serializable {

    enum UserStatus {
        ENABLE,
        DISABLE
    }

    enum UserPrivilege {
        USER,
        ADMIN
    }
    private Integer id;
    private String logIn;
    private String email;
    private String fullname;
    private UserStatus status;
    private UserPrivilege privilege;
    private String passwd;
    private java.sql.Date lastAccess;
    private java.sql.Date lastPasswdChange;

    /**
     * Method to obtain the enum UserPrivilege
     *
     * @return user or admin
     */
    public UserPrivilege getPrivilege() {
        return privilege;
    }

    /**
     * Method to set the privilege to the user
     *
     * @param privilege
     */
    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }

    /**
     * Method to obtain the enum UserStatus
     *
     * @return enable or disable
     */
    public String getStatus() {
        return status.name();
    }

    /**
     * Method to set the enum UserStatus
     *
     * @param status
     */
    public void setStatus(int status) {
        if (status == 1) {
            this.status = UserStatus.ENABLE;
        } else {
            this.status = UserStatus.DISABLE;
        }
    }

    /**
     * Method to get the id of the user
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Method to set the id of the user
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Method to get the string login
     *
     * @return string login
     */
    public String getLogIn() {
        return logIn;
    }

    /**
     * Method to set the string login
     *
     * @param logIn
     */
    public void setLogIn(String logIn) {
        this.logIn = logIn;
    }

    /**
     * Method to get the email of the user
     *
     * @return string email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to set the string email to the user
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method to get the full name of the user
     *
     * @return string fullName
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Method to set the full name to the client
     *
     * @param fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Method to get the password of the user
     *
     * @return string password
     */
    public String getPasswd() {
        return passwd;
    }

    /**
     * Method to set the password to the user
     *
     * @param passwd
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * Method to get the date of the last acces of the client
     *
     * @return Date lastAccess
     */
    public Date getLastAccess() {
        return lastAccess;
    }

    /**
     * Method to set the Date of the last access to the client
     *
     * @param lastAccess
     */
    public void setLastAccess(Date lastAccess) {
        this.lastAccess = (java.sql.Date) lastAccess;
    }

    /**
     * Method to get the Date of the last password change
     *
     * @return Date lastPasswdChange
     */
    public Date getLastPasswdChange() {
        return lastPasswdChange;
    }

    /**
     * Method to set the Date of the lastPasswdChange of the user
     *
     * @param lastPasswdChange
     */
    public void setLastPasswdChange(Date lastPasswdChange) {
        this.lastPasswdChange = (java.sql.Date) lastPasswdChange;
    }

}