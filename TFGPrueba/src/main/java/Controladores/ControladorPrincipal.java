package Controladores;

import Utils.CRUD;
import Utils.Parte;
import Ventanas.VentanaLogin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorPrincipal implements Initializable {
    @FXML
    Button bAParte, bAVehiculo, bCSesion, nombreUs;
    @FXML
    TableView<Parte> tablaDatos;
    @FXML
    TableColumn<Parte, String> columIdParte;
    @FXML
    TableColumn<Parte, String> columFecha;
    @FXML
    TableColumn<Parte, String> columDNICliente;
    @FXML
    TableColumn<Parte, Boolean> columEstado;

    ObservableList<Parte> observableList = FXCollections.observableArrayList();

    private CRUD conexionCRUD = new CRUD();;
    ResultSet resultSet = null;
    String numDNI = ControladorLogin.iniDNI;
    String nombreUsuario;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarPartes();
        personalizarTextField();
        acciones();


    }

    private void cargarPartes() {
        Connection conexion2 = null;
        try {
             conexion2 = conexionCRUD.realizarConexion();
            ResultSet resultSet = conexion2.createStatement().executeQuery("SELECT partes.ID_parte, partes.fecha, partes.DNI_cliete, partes.procesado FROM partes");

            while(resultSet.next()){
                observableList.add(new Parte(resultSet.getString("Identificacion"), resultSet.getString("Fecha"),resultSet.getString("DNI del Cliente"), resultSet.getBoolean("Estado")));

            }
        } catch (SQLException u) {
            Logger.getLogger(CRUD.class.getName()).log(Level.SEVERE, null, u);
        }

        columIdParte.setCellValueFactory(new PropertyValueFactory<>("propIdParte"));
        columFecha.setCellValueFactory(new PropertyValueFactory<>("propFecha"));
        columDNICliente.setCellValueFactory(new PropertyValueFactory<>("propDNICliente"));
        columEstado.setCellValueFactory(new PropertyValueFactory<>("propProcesado"));
        tablaDatos.setItems(observableList);

    }

    private void personalizarTextField() {

        Connection conexion = null;
        try {
            conexion = conexionCRUD.realizarConexion();
            PreparedStatement ps = conexion.prepareStatement("SELECT nombre FROM clientes Where DNI = ?");
            ps.setString(1, numDNI);
            resultSet = ps.executeQuery();

            if(resultSet.next()){
                nombreUsuario = resultSet.getString("nombre");
                nombreUs.setText("Sesión de " + nombreUsuario);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void acciones() {
        bAParte.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Scene sceneActual4 = bAParte.getScene();
                Stage stage= (Stage) sceneActual4.getWindow();
                Parent root = null;

                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/Layouts/subir_parte_layout.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scene scene = new Scene(root, sceneActual4.getWidth(), sceneActual4.getHeight());
                stage.setScene(scene);
            }
        });
        bAVehiculo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Scene sceneActual4 = bAVehiculo.getScene();
                Stage stage= (Stage) sceneActual4.getWindow();
                Parent root = null;

                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/Layouts/add_vehiculo_layout.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scene scene = new Scene(root, sceneActual4.getWidth(), sceneActual4.getHeight());
                stage.setScene(scene);
            }
        });
        bCSesion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VentanaLogin ventanaLogin = new VentanaLogin();
                Stage stage = (Stage) bCSesion.getScene().getWindow();
                stage.hide();
            }
        });
    }
}
