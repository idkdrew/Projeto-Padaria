/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package padrao;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author gmend
 */
public class padaria extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("./views/Tela1.FXML"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("Padaria Doce Sabor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
