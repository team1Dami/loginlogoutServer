/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationClient;

import ApplicationClient.Controller.LoginController;
import ApplicationClient.Controller.SignUpController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author saray
 */
public class ApplicationSignUp extends Application {
    
  /*  @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Fxml/Login.fxml"));
        Parent root = (Parent) loader.load();
        
        LoginController controller =((LoginController) loader.getController());
        controller.setStage(stage);
        controller.initStage(root);
    }*/
    
    @Override
    public void start(Stage signUpStage) throws Exception {
        // cargamos controlador de la ventana SignUp
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("Fxml/SignUp.fxml"));
        Parent root = (Parent) loader.load();
        // instanciamos controlador
        SignUpController controller = ((SignUpController) loader.getController());
       // signUpStage.setTitle("Sign Up");
        controller.setStage(signUpStage);
        controller.initStage(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
