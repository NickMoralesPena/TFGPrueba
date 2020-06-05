package Controladores;

import Utils.CRUD;
import Ventanas.VentanaPrincipal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControladorLogin implements Initializable {
    @FXML
    Button IBRegistro, IBInicio;
    @FXML
    TextField IDNI, IPass;

    private CRUD conexionCRUD;
    ResultSet resultSet= null;
    public static String iniDNI= "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IBRegistro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Scene sceneActual = IBRegistro.getScene();
                Stage stage= (Stage) sceneActual.getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass()
                            .getResource("../../resources/Layouts/registro_layout.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();

                }
                Scene scene = new Scene(root, sceneActual.getWidth(), sceneActual.getHeight());
                stage.setScene(scene);
            }
        });
        IBInicio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                 iniDNI = IDNI.getText().trim();
                String iniPass= IPass.getText();
                if (!iniDNI.isEmpty() && !iniPass.isEmpty()){

                    conexionCRUD = new CRUD();
                    Connection conexion = null;

                    try {
                        conexion = conexionCRUD.realizarConexion();
                        PreparedStatement ps = conexion.prepareStatement("SELECT * FROM clientes Where DNI = ? and password = ?");
                        ps.setString(1, iniDNI);
                        ps.setString(2, iniPass);
                        resultSet = ps.executeQuery();

                        if(!resultSet.next()){
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setHeaderText("Login erroneo");
                            alert.setContentText("DNI y contraseña no coinciden");
                            alert.showAndWait();
                        }
                        else {
                            VentanaPrincipal ventanaMain = new VentanaPrincipal();
                            Stage stage = (Stage) IBRegistro.getScene().getWindow();
                            stage.hide();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else {
                    Alert dialogoWarning = new Alert(Alert.AlertType.WARNING);
                    dialogoWarning.setTitle("Warning");
                    dialogoWarning.setHeaderText("Login erroneo");
                    dialogoWarning.setContentText("Faltan datos");
                    dialogoWarning.showAndWait();
                }
            }
        });
    }
}
