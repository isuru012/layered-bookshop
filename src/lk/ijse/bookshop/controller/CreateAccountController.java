package lk.ijse.bookshop.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bookshop.model.EmployeeModel;
import lk.ijse.bookshop.model.UserCreationModel;
import lk.ijse.bookshop.dto.EmployeeDTO;
import lk.ijse.bookshop.dto.UserDTO;
import lk.ijse.bookshop.util.Notification;
import lk.ijse.bookshop.util.WindowControll;
import tray.notification.NotificationType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.RED;

public class CreateAccountController {

    public AnchorPane pane;
    public Label lblBack;
    public Label lblUsername;
    public Label lblPassword;
    public Label lblName;
    public Label lblAddress;
    public Label lblPhoneNumber;
    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtAddess;

    @FXML
    private JFXTextField txtPhoneNumber;

    public void initialize(){
        Platform.runLater(() -> txtUsername.requestFocus());
    }


    @FXML
    void backOnAction(MouseEvent event) throws IOException {
        Stage stage = (Stage) lblBack.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"));
        stage.setScene(new Scene(root));
    }

    static boolean boolUsername = false;
    static boolean boolPassword = false;
    static boolean boolName = false;
    static boolean boolAddress = false;
    static boolean boolPhonenumber = false;

    @FXML
    void createAccountOnAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        if (!(txtUsername.getText().equals("")) && !(txtPassword.getText().equals("")) &&
                !(txtName.getText().equals("")) && !(txtAddess.getText().equals("")) &&
                !(txtPhoneNumber.getText().equals(""))) {


            if (boolUsername) {
                if (boolPassword) {
                    if (boolName) {
                        if (boolAddress) {
                            if (boolPhonenumber) {
                                UserDTO userDTO = new UserDTO();
                                userDTO.setUserName(txtUsername.getText());
                                userDTO.setPassword(txtPassword.getText());
                                userDTO.setRole("Employee");
                                EmployeeDTO employeeDTO = new EmployeeDTO();
                                employeeDTO.setEmployeeId(generateNextEmployeeId(EmployeeModel.currentEmployeeId()));
                                employeeDTO.setAddress(txtAddess.getText());
                                employeeDTO.setName(txtName.getText());
                                employeeDTO.setSalary(0);
                                employeeDTO.setPhoneNumber(Integer.parseInt(txtPhoneNumber.getText()));
                                employeeDTO.setUserName(txtUsername.getText());

                                try {
                                    boolean isAdded = UserCreationModel.userAllDetailSave(userDTO, employeeDTO);
                                    if (isAdded) {
                                        txtPhoneNumber.setText("");
                                        txtAddess.setText("");
                                        txtName.setText("");
                                        txtPassword.setText("");
                                        txtUsername.setText("");
                                        Notification.notifie("User Creation", "User Added", NotificationType.INFORMATION);
                                    } else {
                                        Notification.notifie("User Creation", "User Not Added", NotificationType.ERROR);
                                    }
                                } catch (Exception e) {
                                    Notification.notifie("User Creation", String.valueOf(e), NotificationType.ERROR);
                                }
                            }

                        }
                    }
                }
            }
        } else {
            Notification.notifie("User Creation", "Enter All details first", NotificationType.ERROR);
        }

    }

    private String generateNextEmployeeId(String currentEmployeeId) {
        if (currentEmployeeId != null) {
            String[] split = currentEmployeeId.split("E");
            int id = Integer.parseInt(split[1]);
            id += 1;
            if (id >= 10) {
                return "E000" + id;
            } else if (id >= 100) {
                return "E00" + id;
            } else if (id >= 1000) {
                return "E0" + id;
            } else if (id >= 10000) {
                return "E" + id;
            } else {

                return "E0000" + id;
            }
        }
        return "E00001";
    }


    public void closeOnAction(ActionEvent actionEvent) {
        WindowControll.window(null, actionEvent);
    }

    public void minimizeOnAction(ActionEvent actionEvent) {
        WindowControll.window(pane, actionEvent);
    }

    public void passwordKeyReleased(KeyEvent keyEvent) {
        Pattern pattern2 = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,10}$");
        Matcher matcher2 = pattern2.matcher(txtPassword.getText());

        boolPassword = matcher2.matches();
        if (boolPassword) {
            txtPassword.setFocusColor(BLUE);
            if (keyEvent.getCode()== KeyCode.ENTER){
                txtName.requestFocus();
            }
        }else{
            txtPassword.setFocusColor(RED);
        }


    }
    public void usernameKeyReleased(KeyEvent keyEvent) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$");
        Matcher matcher = pattern.matcher(txtUsername.getText());


        boolUsername = matcher.matches();
        if (boolUsername) {
            txtUsername.setFocusColor(BLUE);
            if (keyEvent.getCode()== KeyCode.ENTER){
                txtPassword.requestFocus();
            }
        }else{
            txtUsername.setFocusColor(RED);
        }
    }

    public void nameKeyReleased(KeyEvent keyEvent) {
        Pattern pattern3 = Pattern.compile("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        Matcher matcher3 = pattern3.matcher(txtName.getText());

        boolName = matcher3.matches();
        if (boolName) {
            txtName.setFocusColor(BLUE);
            if (keyEvent.getCode()== KeyCode.ENTER){
                txtAddess.requestFocus();
            }
        }else{
            txtName.setFocusColor(RED);
        }
    }

    public void addressKeyReleased(KeyEvent keyEvent) {
        Pattern pattern4 = Pattern.compile("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        Matcher matcher4 = pattern4.matcher(txtAddess.getText());

        boolAddress = matcher4.matches();
        if (boolAddress) {
            txtAddess.setFocusColor(BLUE);
            if (keyEvent.getCode()== KeyCode.ENTER){
                txtPhoneNumber.requestFocus();
            }
        }else{
            txtAddess.setFocusColor(RED);
        }
    }

    public void phoneNumberKeyReleased(KeyEvent keyEvent) {
        Pattern pattern5 = Pattern.compile("^\\d{10}$");
        Matcher matcher5 = pattern5.matcher(txtPhoneNumber.getText());

        boolPhonenumber = matcher5.matches();
        if (boolPhonenumber) {
            txtPhoneNumber.setFocusColor(BLUE);
            if (keyEvent.getCode()== KeyCode.ENTER){

            }
        }else{
            txtPhoneNumber.setFocusColor(RED);
        }

    }
}
