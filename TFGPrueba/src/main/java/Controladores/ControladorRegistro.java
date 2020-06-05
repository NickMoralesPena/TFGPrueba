package Controladores;

import Utils.CRUD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

public class ControladorRegistro implements Initializable {
    @FXML
    Button RAtras, RBRegistro;
    @FXML
    ComboBox REdad,RCarnet, RNacionalidad;
    @FXML
    TextField RNombre, RApellido, RDNI, RPass;

    String nacionalidades[];
    private CRUD conexionCRUD;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conexionCRUD = new CRUD();
        instancias();
        perdonalizarComboBox();
        acciones();


    }

    //Rellenar los combos del registro de usuario
    private void perdonalizarComboBox() {
        REdad.setVisibleRowCount(10);
        RCarnet.setVisibleRowCount(10);
        RNacionalidad.setVisibleRowCount(10);
        ObservableList itemCombo = FXCollections.observableArrayList();
        ObservableList itemCombo2 = FXCollections.observableArrayList();
        ObservableList itemCombo3 = FXCollections.observableArrayList();

        //relleno edadea
        for (int i = 18; i < 66; i++){
            itemCombo.add(i);
        }

        //relleno los años de experiencia con carnet
        for (int j = 1; j <49; j++){
            itemCombo2.add(j);
        }

        //relleno las nacionalidades
        for (String nacionalidad : nacionalidades){
            itemCombo3.add(nacionalidad);
        }

        REdad.setItems(itemCombo);
        RCarnet.setItems(itemCombo2);
        RNacionalidad.setItems(itemCombo3);

    }

    private void acciones() {
        RBRegistro.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Sacamos el valor de los campos introducidos en el registro
                String regNombre = RNombre.getText();
                String regApellido = RApellido.getText();
                String regDNI = RDNI.getText();
                String regPass = RPass.getText();

                if (!regNombre.isEmpty() && !regApellido.isEmpty() && !regDNI.isEmpty() && !regPass.isEmpty() && RNacionalidad.getValue()!= null & RCarnet.getValue()!= null & REdad.getValue()!= null){

                    String regEdad =  REdad.getSelectionModel().getSelectedItem().toString();
                    String regCarnet = RCarnet.getSelectionModel().getSelectedItem().toString();
                    String regNacionalidad = RNacionalidad.getSelectionModel().getSelectedItem().toString();

                    try {
                        Connection conexion = conexionCRUD.realizarConexion();
                        PreparedStatement ps = conexion.prepareStatement("INSERT INTO clientes" +
                                "(DNI,nombre,apellidos,edad,carnet,nacionalidad,password) VALUES (?,?,?,?,?,?,?)");
                        ps.setString(1, regDNI);
                        ps.setString(2, regNombre);
                        ps.setString(3, regApellido);
                        ps.setInt(4, Integer.parseInt(regEdad));
                        ps.setInt(5, Integer.parseInt(regCarnet));
                        ps.setString(6, regNacionalidad);
                        ps.setString(7, regPass);
                        ps.executeUpdate();

                        conexion.close();

                        Alert alertCorreo = new Alert(Alert.AlertType.INFORMATION);
                        alertCorreo.setTitle("¡Enorabuena!");
                        alertCorreo.setHeaderText("Ya eres nuestro nuevo cliente");
                        alertCorreo.showAndWait();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println("introduce todos los campos");
                }
            }
        });
        RAtras.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Scene sceneActual = RAtras.getScene();
                Stage stage= (Stage) sceneActual.getWindow();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass()
                            .getResource("../../resources/Layouts/login_layout.fxml"));
                } catch (IOException e) {
                    System.out.println(e.getMessage());

                }
                Scene scene = new Scene(root, sceneActual.getWidth(), sceneActual.getHeight());
                stage.setScene(scene);
            }
        });

    }
    private void instancias() {
        nacionalidades = new String[]{"Afgana", "Alemana", "Árabe", "Argentina", "Australiana", "Belga", "Boliviana",
                "Brasileña", "Camboyana", "Canadiense", "Chilena", "China", "Colombiana", "Coreana", "Costarricence",
        "Cubana", "Danesa", "Ecuatoriana", "Egipcia", "Salvadoreña", "Escocesa", "Española","Estaunidense", "Estonia",
        "Etiope", "Filipina", "Finlandesa", "Francesa", "Galesa", "Griega", "Guatemalteca", "Haitiana", "Holandesa",
        "Hondureña", "Indonesa", "Inglesa", "Iraquí", "Iraní", "Irlandesa", "Israelí", "Italiana", "Japonesa", "Jordana",
        "Laosiana", "Letona", "Letonesa", "Malaya", "Marroqui", "Mexicana", "Nicaragüense", "Noruega", "Neozelandesa",
        "Panameña", "Paraguaya", "Peruana", "Polaca", "Portuguesa", "Puertorriqueño", "Dominicana", "Rumana", "Rusa",
        "Sueca", "Suiza", "Tailandesa", "Taiwanesa", "Turca", "Ucraniana", "Uruguaya", "Venezolana", "Vietnamita"};
    }
}