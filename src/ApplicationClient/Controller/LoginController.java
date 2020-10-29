/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationClient.Controller;

import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author rubir
 */
public class LoginController {
    
    @FXML
    private Stage stage;
    
    @FXML
        private Button btnLogin;
    @FXML
        private Button btnRegister;
    @FXML
        private TextField tfLogin;
    @FXML
        private TextField tfPasswd;
    
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
    public void initStage(Parent root) {        
        Scene scene = new Scene(root);
        stage.setScene(scene); 
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.show();
        
        tfLogin.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleTextChange();
            }  
        });
         tfPasswd.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleTextChange();               
            }              
        });
         
    }
    
     /* @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
       //Abrir ventana Registrarse
        LoginController controller = new LoginController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Logout.fxml"));
        Parent root = (Parent) loader.load();
        
        controller = (loader.getController());
        controller.setStage(stage);
        controller.initStage(root);
     }*/
    @FXML
    private void handleButtonLoginAction(ActionEvent event) throws IOException {
        Alert alert;
        if(tfPasswd.getText().toString().length()<6||tfPasswd.getText().toString().length()>12){
                    alert=new Alert(Alert.AlertType.WARNING);
                }
        else{
            alert=new Alert(Alert.AlertType.CONFIRMATION);
        }
    }

    private void handleTextChange() {
        if(tfLogin.getText().toString().isEmpty()){
            btnLogin.setDisable(true);
        }
        else if(tfPasswd.getText().toString().isEmpty()){
            btnLogin.setDisable(true);
        }
        else{
            btnLogin.setDisable(false);
        }
    }
}
