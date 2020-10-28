/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationClient.Controller;

import java.awt.event.InputEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author saray
 */
public class SignUpController implements Initializable {
    
    private static final Logger logger = Logger.getLogger("ApplicationClient.Controller.SignUpController");
    
    private static final int MAX_PASS_LENGHT = 12;
    private static final int MIN_PASS_LENGHT = 6;
    
    @FXML
    private Stage signUpStage;
    
    @FXML
    private TextField tfFullName;
     
    @FXML
    private TextField tfUser;
    
    @FXML
    private TextField tfEmail;
    
    @FXML
    private TextField tfPasswd;  //min 6 max 12
    
    @FXML
    private TextField tfPasswd2;
    
    @FXML
    private Button btnCancel;
    
    @FXML
    private Button btnAccept;
      
    public void setStage(Stage stage) {
        this.signUpStage = stage;
    }
    
    public void initStage(Parent root) {
        logger.info("Initializing signUp stage");
        
        Scene scene = new Scene(root);
//        signUpStage.initModality(Modality.APPLICATION_MODAL);
        signUpStage.setScene(scene);
        signUpStage.setTitle("Formulario de registro");
        signUpStage.setResizable(false);
        signUpStage.setOnShowing(this::handleWindowShowing);
        tfFullName.textProperty().addListener(this::textChanged);
      //  tfFullName.setPromptText("Nombre completo de usuario");
        tfUser.textProperty().addListener(this::textChanged);
        tfEmail.textProperty().addListener(this::textEmailChanged);
        tfPasswd.textProperty().addListener(this::textChanged);
        tfPasswd2.textProperty().addListener(this::textChanged);
        btnCancel.setOnAction(this::handleOnActionEvent);
        signUpStage.show();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Beginning initializing controller");    
    }
    private void textChanged(ObservableValue observable,
            String oldValue,
            String newValue){
        if(observable.getClass().equals(tfPasswd)) {
            if(!tfPasswd.getText().isEmpty()){
                if(tfPasswd.getText().length()<6 || tfPasswd.getText().length() > 12){
                    
                }
            }
        }
    }
    private void textEmailChanged(ObservableValue observable,
            String oldValue,
            String newValue){
        
    }
    private void handleWindowShowing(WindowEvent event) {
        btnAccept.setDisable(true);
    }    
    private void handleOnActionEvent(ActionEvent event) {
        signUpStage.close();
    }
            
}
