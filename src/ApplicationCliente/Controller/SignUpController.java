package ApplicationCliente.Controller;

import Implementation.ClientServerImplementation;
import classes.User;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

/**
 * Class to controll the elements of the signUp UI
 *
 * @author saray
 */
public class SignUpController implements Initializable {

    private static final Logger logger = Logger.getLogger("ApplicationClient.Controller.SignUpController");

    private static final int MAX_PASS_LENGHT = 12;
    private static final int MIN_PASS_LENGHT = 6;

    @FXML
    private Stage stage;
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

    /**
     * Method to set the stage
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method to initialice the stage and add the events to the elements of the
     * UI
     *
     * @param root
     */
    public void initStage(Parent root) {
        logger.info("Initializing signUp stage");

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Formulario de registro");
        stage.setResizable(false);
        stage.setOnShowing(this::handleWindowShowing);
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
        btnCancel.setOnAction(this::handleButtonCancelarAction);
        btnAccept.setDisable(true);
        btnAccept.setOnAction(this::handleButtonAceptarAction);
        stage.show();
    }

    /**
     * Method to initialize the view
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Beginning initializing controller");
    }

    /**
     * Method to control the changes on the text fields when the focus change If
     * an error ocurr an alert advice the user of the error
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void textChanged(ObservableValue observable,
            String oldValue,
            String newValue) {
        logger.info("Checking changes in text fields");
        Alert alert;
        if (validateEmail(tfEmail.getText().toString())
                && !tfFullName.getText().isEmpty()
                && !tfUser.getText().isEmpty()
                && !tfEmail.getText().isEmpty()
                && !tfPasswd.getText().isEmpty()
                && !tfPasswd2.getText().isEmpty()
                && tfPasswd.getText().equals(tfPasswd2.getText())
                && tfPasswd.getText().length() >= MIN_PASS_LENGHT
                && tfPasswd.getText().length() <= MAX_PASS_LENGHT) {
            btnAccept.setDisable(false);
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
            //  alert.showAndWait();
            btnAccept.setDisable(true);
        }
    }

    /**
     * Method to manage the button Accept At the begining the buton is disabled
     *
     * @param event
     */
    private void handleWindowShowing(WindowEvent event) {
        btnAccept.setDisable(true);
    }

    /**
     * Method to manage the action on the button Accept First of all: get the
     * values of the text fields and set to the User class After sends the
     * values to the ClientServerImplementation class If the register is
     * succesfull the login view is opened
     *
     * @param event
     */
    @FXML
    private void handleButtonAceptarAction(ActionEvent event) {
        User myUser;
        try {
            myUser = new User();
            myUser.setFullname(tfFullName.getText().toString());
            myUser.setLogIn(tfUser.getText().toString());
            myUser.setEmail(tfEmail.getText().toString());
            myUser.setPasswd(tfPasswd.getText().toString());
            ClientServerImplementation imp = new ClientServerImplementation();
            User serverUser = imp.signUp(myUser);

            if (null != serverUser) {
                FXMLLoader loader
                        = new FXMLLoader(getClass().getResource("Login.fxml"));
                Parent root = (Parent) loader.load();
                LoginController controller = ((LoginController) loader.getController());
                controller = (loader.getController());
                controller.setStage(stage);
                controller.initStage(root);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "User can not set", e.getMessage());
        }
    }

    /**
     * Method to manage the action on the button Cancel if is clicked the logIn
     * view is opened, if it can't be opened an alert message is showing
     *
     * @param event
     */
    @FXML
    private void handleButtonCancelarAction(ActionEvent event) {

        try {
            FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = (Parent) loader.load();
            LoginController controller = ((LoginController) loader.getController());
            controller = (loader.getController());
            controller.setStage(stage);
            controller.initStage(root);

        } catch (Exception ex) {
            logger.log(Level.SEVERE,
                    "UI LoginController: Error opening users managing window: {0}",
                    ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.WARNING, "No se ha podido cargar la ventana", ButtonType.OK);
        }
    }

    /**
     * Method to validate the email format
     *
     * @param email
     * @return true or false
     */
    private boolean validateEmail(String email) {
        // Patron para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);
        if (!mather.find()) {
            return false;
        } else {
            return true;
        }
    }
}
