package Controladores;

import Ventanas.VentanaLogin;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.ResourceBundle;

public class ControladorSubirParte implements Initializable {
    @FXML
    Button bLPartes, bAVehiculo, bCSesion, bExaminar;
    @FXML
    ImageView imgParte;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        bExaminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("pulsado");
                FileChooser fileChooser = new FileChooser();
                Stage stage = (Stage) bExaminar.getScene().getWindow();

                File ao = fileChooser.showOpenDialog(stage);
                if (ao != null){
                    //Desktop desktop = Desktop.getDesktop();

                    try {
                        //desktop.open(ao);
                        Image image = new Image(ao.toURI().toString());
                        imgParte.setImage(image);
                        //convertir a base 64
                        //String encoderImage = ao.toURI().toString();
                        String encoderImage = "C:/Users/nicol/OneDrive/Imágenes/PartePrueba.jpg";
                        String base64ImageString = encoder(encoderImage);

                        System.out.println(base64ImageString);

                        //tratamos el filepath
                        String filePath = encoderImage;
                        //tratamos el printStream
                        PrintStream out =  new PrintStream(System.out);

                        ControladorImagen.detectDocumentText(filePath, out);
                        try{//cogemos los valores que hemos almacendado de la imagen en un archivo txt
                            String vaciar ="";
                            String nombre = Files.readAllLines(Paths.get("C:/Users/nicol/IdeaProjects/TFGPrueba/DatosParte.txt")).get(0);
                            String fecha = Files.readAllLines(Paths.get("C:/Users/nicol/IdeaProjects/TFGPrueba/DatosParte.txt")).get(1);
                            String matricula = Files.readAllLines(Paths.get("C:/Users/nicol/IdeaProjects/TFGPrueba/DatosParte.txt")).get(2);
                            String DNI = Files.readAllLines(Paths.get("C:/Users/nicol/IdeaProjects/TFGPrueba/DatosParte.txt")).get(3);

                            nombre = nombre.replace(" ", "").replace("Nombre:", vaciar);
                            fecha = fecha.replace(" ", "").replace("fecha:", vaciar);
                            matricula = matricula.replace(" ", "").replace("Matricula:", vaciar);
                            DNI = DNI.replace(" ", "").replace("DNI:", vaciar);
                            System.out.println(nombre);
                            System.out.println(fecha);
                            System.out.println(matricula);
                            System.out.println(DNI);
                        }
                        catch (NoSuchFileException e){

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
    public static String encoder(String encoderImage) {
        String base64Image = "";
        File file = new File(encoderImage);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }

}
