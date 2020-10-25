/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author saray
 */
public class SignUpController implements Initializable {
    
    private static final Logger logger = Logger.getLogger("controller.SignUpController");
    
    @FXML
    private Stage signUpstage;
    
    @FXML
    private TextField tfFullName;
     
    @FXML
    private TextField tfUser;
    
    @FXML
    private TextField tfEmail;
    
    @FXML
    private TextField tfPasswd;
    
    @FXML
    private TextField tfPasswd2;
    
    @FXML
    private Button btnCancel;
    
    @FXML
    private Button btnAccept;
    
    @FXML
    private void returnSignIn() {
        // Button was clicked, close the signUp windows...     
        signUpstage.close();
    }
    
    public void setStage(Stage stage) {
        this.signUpstage = stage;
    }
    
    public void initStage(Parent root) {
        
        Scene scene = new Scene(root);
        signUpstage.setScene(scene); 
        signUpstage.setResizable(false);
        signUpstage.show();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
          btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {             
               returnSignIn(); } 
        });
    }
    
    
}
