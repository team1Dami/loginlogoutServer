/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ApplicationClient.Controller;

import classes.User;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
        tfFullName.setPromptText("Introduzca su nombre completo");
        tfUser.textProperty().addListener(this::textChanged);
        tfUser.setPromptText("Introduzca un nombre de usuario");
        tfEmail.textProperty().addListener(this::textChanged);
        tfEmail.setPromptText("Introduzca su Email");
        tfPasswd.textProperty().addListener(this::textChanged);
        tfPasswd.setPromptText("Introduzca una contraseña");
        tfPasswd2.textProperty().addListener(this::textChanged);
        tfPasswd2.setPromptText("Repita su contraseña");
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                signUpStage.close();
            }
        });
        btnAccept.setOnAction(this::handleOnActionEvent);
        signUpStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Beginning initializing controller");
    }

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void textChanged(ObservableValue observable,
            String oldValue,
            String newValue) {
        Alert alert;
        if (!validateEmail(tfEmail.getText().toString().trim())) {
            alert = new Alert(Alert.AlertType.WARNING);
            
        } else {
           // handleTextChange();
        }
        if (!tfPasswd.getText().toString().isEmpty()) {
            if (tfPasswd.getText().length() < MIN_PASS_LENGHT || tfPasswd.getText().length() > MAX_PASS_LENGHT) {
                alert = new Alert(Alert.AlertType.WARNING);
              
            } else {
             //   handleTextChange();
            }
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
        }
        if (!tfPasswd2.getText().toString().isEmpty()) {
            if (tfPasswd2.getText().length() < MIN_PASS_LENGHT || tfPasswd2.getText().length() > MAX_PASS_LENGHT) {
                alert = new Alert(Alert.AlertType.WARNING);
            } else {
                alert = new Alert(Alert.AlertType.WARNING);
            }
        } else {
           // handleTextChange();
        }
        if (tfUser.getText().toString().isEmpty()) {
            alert = new Alert(Alert.AlertType.WARNING);
        } else {
           // handleTextChange();
        }
        if (tfFullName.getText().toString().isEmpty()) {
            alert = new Alert(Alert.AlertType.WARNING);
        } else {
            
        }
        handleTextChange();
    }

    /**
     *
     * @param email
     * @return true if email is correct or false if not
     */
    private boolean validateEmail(String email) {
        // Patron para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);
        return mather.find();
    }

    /**
     *
     * @param event
     */
    private void handleWindowShowing(WindowEvent event) {
        btnAccept.setDisable(true);
    }

    private void handleOnActionEvent(ActionEvent event) {
        User user = new User();
        user.setFullname(tfFullName.getText().toString());
        user.setLogIn(tfUser.getText().toString());
        user.setEmail(tfEmail.getText().toString());
        user.setPasswd(tfPasswd.getText().toString());
    }
    /**
     * *
     *
     */
    private void handleTextChange() {
        Alert alert;
        if (validateEmail(tfEmail.getText().toString())
                && !tfFullName.getText().isEmpty()
                && !tfUser.getText().isEmpty()
                && !tfEmail.getText().isEmpty()
                && !tfPasswd.getText().isEmpty()
                && !tfPasswd2.getText().isEmpty()
                && tfPasswd.getText().equals(tfPasswd2.getText())) {
            btnAccept.setDisable(false);
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
          //  alert.showAndWait();
            btnAccept.setDisable(true);
        }
    }

}
