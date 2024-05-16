/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controladores;

import java.util.List;
import entidades.Produtos;
import entidades.Comanda;
import dao.ComandaDAO;
import dao.ComandaprodutoDAO;
import entidades.Comandaproduto;
import utility.JPAUtils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gabal
 */
public class FechamentoComandaFXMLController implements Initializable {

    private ComandaDAO comandaDAO;

    private double valor;
    @FXML
    private BorderPane root;
    @FXML
    private Label vlrFinal;
    @FXML
    private TableView<Produtos> tabela;
    @FXML
    private TableColumn<Produtos, String> tbNome;
    @FXML
    private TableColumn<Produtos, Double> tbPreco;
    @FXML
    private ChoiceBox<Comanda> slcComanda;
    @FXML
    private Button confirmBt;

    ObservableList<Comandaproduto> lista;
    ObservableList<Produtos> lista2;
    ComandaprodutoDAO banco;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ComandaDAO comandaDAO = new ComandaDAO();
        try {
            banco = new ComandaprodutoDAO();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //vlrFinal.setText(string);
        carregarComandas(comandaDAO);
    }

    @FXML
    private void pagar(ActionEvent event) {
        lista2.removeAll(tabela.getItems());
        try {
            for (Comandaproduto comandaproduto : banco.getUnidade(slcComanda.getValue())) {
                banco.deletar(comandaproduto);
                vlrFinal.setText("Conta paga!");
            }
        } catch (Exception e) {
        }

    }

    @FXML
    private void voltar(ActionEvent event) {
        Node no = (Node) event.getSource();
        Stage janela = (Stage) no.getScene().getWindow();
        janela.close();

    }

    @FXML
    private void confirma(ActionEvent event) {
        valor = 0;
        lista2 = FXCollections.observableArrayList();

        lista = FXCollections.observableArrayList(banco.getUnidade(slcComanda.getValue()));
        for (Comandaproduto comandaproduto : lista) {
            lista2.add(comandaproduto.getIdproduto());
        }
        System.out.println(slcComanda.getValue());
        carregarProdutosDaComanda();

        for (Produtos produto : lista2) {
            valor += produto.getPrecoprod();

        }
        vlrFinal.setText(String.valueOf("R$" + valor));
    }

    private void carregarComandas(ComandaDAO comandaDAO) {
        System.out.println("Carregando comandas...");

        if (comandaDAO != null) {
            List<Comanda> comandas = comandaDAO.obterTodasComandas();

            System.out.println("Número de comandas carregadas: " + comandas.size());

            // Configure o ChoiceBox com as comandas
            slcComanda.setItems(FXCollections.observableList(comandas));
        } else {
            System.out.println("ERRO: ComandaDAO não inicializado!");
        }
        /* List<Comanda> comandas = comandaDAO.obterTodasComandas();

        // Configure o ChoiceBox com as comandas
        slcComanda.setItems(FXCollections.observableList(comandas));*/
    }

    private void carregarProdutosDaComanda() {
        // Obtenha a comanda selecionada no ChoiceBox
        ComandaDAO comandaDAO = new ComandaDAO();
        Comanda comandaSelecionada = slcComanda.getValue();

        // Supondo que você tenha a informação da comanda disponível
        int idComanda = comandaSelecionada.getIdcomanda(); // Obtenha o ID da comanda, pode vir de algum lugar, como um campo de texto, etc.

        // Obtenha a lista de produtos associados à comanda
        List<Produtos> produtos = comandaDAO.buscarProdutosPorComanda(idComanda);

        // Configure sua tabela com a lista de produtos
        tabela.setItems(lista2);
        tbNome.setCellValueFactory(new PropertyValueFactory<>("nomeprod"));
        tbPreco.setCellValueFactory(new PropertyValueFactory<>("precoprod"));
    }

}
