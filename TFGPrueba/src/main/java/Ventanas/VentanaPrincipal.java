package Ventanas;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VentanaPrincipal extends Stage {
    public VentanaPrincipal(){
        this.setTitle("Ventana de Credenciales");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../../resources/Layouts/principal_layout.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root, 800.0, 800.0);
        this.setScene(scene);
        this.show();
    }
}
