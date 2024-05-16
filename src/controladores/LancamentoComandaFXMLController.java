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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utility.CaixaDialogo;

/**
 * FXML Controller class
 *
 * @author gabal
 */
public class LancamentoComandaFXMLController implements Initializable {

    @FXML
    private TableView<Produtos> tabela;
    @FXML
    private ChoiceBox<Comanda> slcComanda;
    @FXML
    private TableColumn<Produtos, Integer> fxID;
    @FXML
    private TableColumn<Produtos, String> fxNome;
    @FXML
    private TableColumn<Produtos, Double> fxPreco;
    @FXML
    private TableColumn<Produtos, Integer> fxQtde;

    ObservableList<Produtos> listaProduto;
    ObservableList<Comandaproduto> lista;
    ObservableList<Produtos> lista2;
    ProdutoDAO bancoProd;
    ProdutoDAO bancoProd2;
    ComandaprodutoDAO banco;
    Produtos regAtual;
    Comandaproduto regDel;

    @FXML
    private Label labelComanda;
    @FXML
    private TableView<Produtos> tbComanda;
    @FXML
    private TableColumn<Produtos, String> colNomeComanda;
    @FXML
    private TableColumn<Produtos, Double> colPrecoComanda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ComandaDAO comandaDAO = new ComandaDAO();
        regAtual = new Produtos();
        regDel = new Comandaproduto();

        if (comandaDAO != null) {
            List<Comanda> comandas = comandaDAO.obterTodasComandas();
            System.out.println("Número de comandas carregadas: " + comandas.size());
            slcComanda.setItems(FXCollections.observableList(comandas));
        } else {
            System.out.println("ERRO: ComandaDAO não inicializado!");
        }
        try {
            bancoProd = new ProdutoDAO();
            banco = new ComandaprodutoDAO();
            listaProduto = FXCollections.observableArrayList(bancoProd.getAll());
            montaTabela();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void lancar(ActionEvent event) {
        ComandaprodutoDAO comandaprodutoDAO = new ComandaprodutoDAO();
        // Verifica se um produto e uma comanda foram selecionados
        if (slcComanda.getValue() != null && tabela.getSelectionModel().getSelectedItem() != null) {
            // Obtém a comanda selecionada e o produto selecionado
            Comanda comandaSelecionada = slcComanda.getValue();
            Produtos produtoSelecionado = tabela.getSelectionModel().getSelectedItem();
            if (produtoSelecionado.getQtdeprod() > 0) {
                if (comandaprodutoDAO.lancarProdutoNaComanda(comandaSelecionada, produtoSelecionado)) {
                    // Atualiza a tabela de produtos após o lançamento
                    try {
                        listaProduto = null;
                        bancoProd = new ProdutoDAO();
                        listaProduto = FXCollections.observableArrayList(bancoProd.getAll());
                        lista2 = FXCollections.observableArrayList();

                        for (Comandaproduto comandaproduto : banco.getUnidade(slcComanda.getValue())) {
                            lista2.add(comandaproduto.getIdproduto());
                        }

                        carregarProdutosDaComanda();
                        montaTabela();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CaixaDialogo.displayMensagem("INFORMATION", "Produto lançado na comanda " + slcComanda.getValue().getIdcomanda(), "Operação com sucesso");
                } else {
                    CaixaDialogo.displayMensagem("ERROR", "Erro ao lançar produto na comanda" + slcComanda.getValue().getIdcomanda(), "Operação não executada");
                }

            } else {
                System.out.println("Não há unidades disponíveis do produto selecionado para lançamento");
            }
        }
    }

    @FXML
    private void voltar(ActionEvent event) {
        Node no = (Node) event.getSource();
        Stage janela = (Stage) no.getScene().getWindow();
        janela.close();
    }

    private void montaTabela() {
        tabela.setItems(listaProduto);
        fxID.setCellValueFactory(new PropertyValueFactory<>("idprod"));
        fxNome.setCellValueFactory(new PropertyValueFactory<>("nomeprod"));
        fxPreco.setCellValueFactory(new PropertyValueFactory<>("precoprod"));
        fxQtde.setCellValueFactory(new PropertyValueFactory<>("qtdeprod"));
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
        tbComanda.setItems(lista2);
        colNomeComanda.setCellValueFactory(new PropertyValueFactory<>("nomeprod"));
        colPrecoComanda.setCellValueFactory(new PropertyValueFactory<>("precoprod"));
    }

}
