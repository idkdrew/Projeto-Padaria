/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controladores;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utility.DiasDaSemana;

/**
 * FXML Controller class
 *
 * @author gmend
 */
public class FXMLController implements Initializable {

    @FXML
    private Button cadprod;
    @FXML
    private AnchorPane root;
    @FXML
    private Button selectcomanda;
    @FXML
    private Button consultarcomanda;
    @FXML
    private Label diaSemana;

    DiasDaSemana diadasemana;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //GregorianCalendar gc = new GregorianCalendar();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        try {
            switch (Calendar.DATE) {
                case 7:
                    diaSemana.setText(diadasemana.DOM.getDescricao());
                    break;
                case 1:
                    diaSemana.setText(diadasemana.SEG.getDescricao());
                    break;
                case 2:
                    diaSemana.setText(diadasemana.TER.getDescricao());
                    break;
                case 3:
                    diaSemana.setText(diadasemana.QUA.getDescricao());
                    break;
                case 4:
                    diaSemana.setText(diadasemana.QUI.getDescricao());
                    break;
                case 5:
                    diaSemana.setText(diadasemana.SEX.getDescricao());
                    break;
                case 6:
                    diaSemana.setText(diadasemana.SAB.getDescricao());
                    break;
                default:
                    diaSemana.setText("kkk");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO
    }

    @FXML
    private void cadastro(ActionEvent event) throws IOException {

        Parent novaTela = FXMLLoader.load(getClass().getClassLoader().getResource("./views/Cadastro.fxml"));
        Scene newscene = new Scene(novaTela);
        Stage novaJanela = new Stage();
        novaJanela.initOwner((Stage) root.getScene().getWindow());
        novaJanela.initModality(Modality.WINDOW_MODAL);
        novaJanela.setScene(newscene);
        novaJanela.show();

    }

    @FXML
    private void sair(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void selecionar(ActionEvent event) throws IOException {

        Parent novaTela = FXMLLoader.load(getClass().getClassLoader().getResource("./views/LancamentoComandaFXML.fxml"));
        Scene newscene = new Scene(novaTela);
        Stage novaJanela = new Stage();
        novaJanela.initOwner((Stage) root.getScene().getWindow());
        novaJanela.initModality(Modality.WINDOW_MODAL);
        novaJanela.setScene(newscene);
        novaJanela.show();
    }

    @FXML
    private void consultar(ActionEvent event) throws IOException {

        Parent novaTela = FXMLLoader.load(getClass().getClassLoader().getResource("./views/FechamentoComandaFXML.fxml"));
        Scene newscene = new Scene(novaTela);
        Stage novaJanela = new Stage();
        novaJanela.initOwner((Stage) root.getScene().getWindow());
        novaJanela.initModality(Modality.WINDOW_MODAL);
        novaJanela.setScene(newscene);
        novaJanela.show();
    }

}
