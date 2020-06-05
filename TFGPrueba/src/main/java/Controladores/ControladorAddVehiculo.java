package Controladores;

import Utils.CRUD;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControladorAddVehiculo implements Initializable {
    @FXML
    Button bLPartes, bAParte, bCSesion, bAñadirVehiculo;
    @FXML
    ComboBox cBMarca, cBYear;
    @FXML
    TextField tFModelo, tFCV, tFKilometros, tFMatricula, tFConductor;

    String marcas[];
    private CRUD conexionCRUD;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conexionCRUD = new CRUD();
        instancias();
        personalizarCombo();
        acciones();
    }

    private void acciones() {
        bLPartes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Scene sceneActual4 = bLPartes.getScene();
                Stage stage= (Stage) sceneActual4.getWindow();
                Parent root = null;

                try {
                    root = FXMLLoader.load(getClass().getResource("../../resources/Layouts/principal_layout.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scene scene = new Scene(root, sceneActual4.getWidth(), sceneActual4.getHeight());
                stage.setScene(scene);
            }
        });
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
        bCSesion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VentanaLogin ventanaLogin = new VentanaLogin();
                Stage stage = (Stage) bCSesion.getScene().getWindow();
                stage.hide();
            }
        });
        bAñadirVehiculo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String regModelo = tFModelo.getText();
                String regCV = tFCV.getText();
                String regKilometros = tFKilometros.getText();
                String regMatricula = tFMatricula.getText();
                String regConductor = tFConductor.getText();
                if (!regModelo.isEmpty() && !regCV.isEmpty() && !regKilometros.isEmpty() && !regMatricula.isEmpty() && !regConductor.isEmpty() && cBMarca!=null && cBYear!=null){

                    String regMarca = cBMarca.getSelectionModel().getSelectedItem().toString();
                    String regYear = cBYear.getSelectionModel().getSelectedItem().toString();

                    try {
                        Connection conexion = conexionCRUD.realizarConexion();
                        PreparedStatement ps = conexion.prepareStatement("INSERT INTO vehiculos" +
                                "(marca,modelo,año,cv,kilimetros,matricula,DNI_contuctor) VALUES (?,?,?,?,?,?,?)");
                        ps.setString(1, regMarca);
                        ps.setString(2, regModelo);
                        ps.setInt(3, Integer.parseInt(regYear));
                        ps.setInt(4, Integer.parseInt(regCV));
                        ps.setString(5, regKilometros);
                        ps.setString(6, regMatricula);
                        ps.setString(7, regConductor);
                        ps.executeUpdate();

                        conexion.close();


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("introduce todos los campos");
                }
            }
        });
    }

    private void personalizarCombo() {
        cBMarca.setVisibleRowCount(10);
        cBYear.setVisibleRowCount(10);
        ObservableList itemCombo = FXCollections.observableArrayList();
        ObservableList itemCombo1 = FXCollections.observableArrayList();

        for (String marca : marcas){
            itemCombo.add(marca);
        }

        for (int i = 2020; i > 1989; i--){
            itemCombo1.add(i);
        }

        cBMarca.setItems(itemCombo);
        cBYear.setItems(itemCombo1);

    }

    private void instancias() {
        marcas = new String [] {
                "Abarth", "Alfa Romeo",	"Aston Martin",	"Audi",	"Bentley", "BMW", "Cadillac", "Caterham",	"Chevrolet",
                "Citroen", "Dacia",	"Ferrari", "Fiat", "Ford", "Honda", "Infiniti", "Isuzu", "Iveco", "Jaguar",	"Jeep",
                "Kia", "KTM", "Lada", "Lamborghini", "Lancia", "Land Rover", "Lexus", "Lotus", "Maserati", "Mazda", "Mercedes-Benz",
                "Mini",	"Mitsubishi", "Morgan", "Nissan", "Opel", "Peugeot", "Piaggio",	"Porsche", "Renault", "Rolls-Royce",
                "Seat", "Skoda", "Smart", "SsangYong", "Subaru", "Suzuki",	"Tata",	"Tesla", "Toyota", "Volkswagen", "Volvo"};
    }
}
