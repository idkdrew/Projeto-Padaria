    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controladores;

import dao.ComandaDAO;
import dao.ComandaprodutoDAO;
import dao.ProdutoDAO;
import entidades.Comanda;
import entidades.Comandaproduto;
import entidades.Produtos;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.CaixaDialogo;

/**
 * FXML Controller class
 *
 * @author gabal
 */
public class TelaProdutoController implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private HBox pDados;
    @FXML
    private TextField txCodigo;
    @FXML
    private TextField txNome;
    @FXML
    private TextField txPreco;
    @FXML
    private TextField txUnidade;
    @FXML
    private HBox pBotoes;
    @FXML
    private HBox pTabela;
    @FXML
    private TableView<Produtos> tabela;
    @FXML
    private TableColumn<Produtos, Integer> colCodigo;
    @FXML
    private TableColumn<Produtos, String> colNome;
    @FXML
    private TableColumn<Produtos, Double> colPreco;
    @FXML
    private TableColumn<Produtos, Integer> colUnidade;
    @FXML
    private VBox pBotoes2;
    @FXML
    private ToggleButton btNovo;
    @FXML
    private ToggleButton btSair;

    ObservableList<Produtos> lista;
    Produtos regAtual;
    ProdutoDAO banco;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        regAtual = new Produtos();
        try {
            banco = new ProdutoDAO();
            lista = FXCollections.observableArrayList(banco.getAll());
            montaTabela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gravar(ActionEvent event) throws Exception {
        montarRegistro();
        if (btNovo.isSelected()) {
            banco.inserir(regAtual);
            lista.add(regAtual);
        } else {
            banco.editar(regAtual);
            lista.remove(regAtual);
            lista.add(regAtual);
            lista = null;
            banco = new ProdutoDAO();
            lista = FXCollections.observableArrayList(banco.getAll());
            montaTabela();
        }
        limparTela();
    }

    @FXML
    private void excluir(ActionEvent event) throws Exception {
        if (CaixaDialogo.displayConfirmacao("CONFIRMATION", "Confirma a exclusão de " + regAtual, "Exclusão")
                == ButtonType.OK) {
            banco.deletar(regAtual);
            lista.remove(regAtual);
            CaixaDialogo.displayMensagem("INFORMATION", "Exclusão realizada com sucesso", "Operação com sucesso");
        }
        limparTela();
    }

    @FXML
    private void inserir(ActionEvent event) {
    }

    @FXML
    private void sair(ActionEvent event) {
        Node no = (Node) event.getSource();
        Stage janela = (Stage) no.getScene().getWindow();
        janela.close();
    }

    private void montaTabela() {
        try {
            tabela.setItems(lista);
            colCodigo.setCellValueFactory(new PropertyValueFactory<>(""
                    + "idprod"));
            colNome.setCellValueFactory(new PropertyValueFactory<>("nomeprod"));
            colPreco.setCellValueFactory(new PropertyValueFactory<>("precoprod"));
            colUnidade.setCellValueFactory(new PropertyValueFactory<>("qtdeprod"));
            //mostraProdutos(tabela.getSelectionModel().getSelectedItem().getIdprod());
            tabela.getSelectionModel().selectedIndexProperty().addListener(
                    evt -> {
                        try {
                            mostraProdutos(tabela.getSelectionModel().getSelectedItem().getIdprod());

                        } catch (Exception e) {
                        }
                    }
            );
        } catch (Exception e) {

        }

    }

    private void mostraProdutos(Integer selectedItem) throws Exception {
        Produtos prodTest = new Produtos(selectedItem);
        regAtual = banco.get(prodTest);
        this.txCodigo.setText(String.valueOf(regAtual.getIdprod()));
        this.txNome.setText(regAtual.getNomeprod());
        this.txPreco.setText(String.valueOf(regAtual.getPrecoprod()));
        this.txUnidade.setText(String.valueOf(regAtual.getQtdeprod()));
        this.txCodigo.requestFocus();
    }

    private void montarRegistro() {
        regAtual.setIdprod(Integer.parseInt(txCodigo.getText()));
        regAtual.setNomeprod(txNome.getText());
        regAtual.setPrecoprod(Double.parseDouble(txPreco.getText()));
        regAtual.setQtdeprod(Integer.parseInt(txUnidade.getText()));
    }

    private void limparTela() {
        txCodigo.clear();
        txNome.clear();
        txPreco.clear();
        txUnidade.clear();

    }
}
