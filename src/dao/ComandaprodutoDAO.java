/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entidades.Comanda;
import entidades.Comandaproduto;
import entidades.ComandaprodutoPK;
import entidades.Produtos;
import utility.JPAUtils;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import jpaControllers.ComandaprodutoJpaController;

/**
 *
 * @author gabal
 */
public class ComandaprodutoDAO extends AbstractDAO<ComandaprodutoJpaController, Comandaproduto> {

    private static ComandaprodutoDAO comandaprodutoDAO = new ComandaprodutoDAO();

    public ComandaprodutoDAO() {
        objetoJPA = new ComandaprodutoJpaController(getEntityManagerFactory());
    }

    public static ComandaprodutoDAO getInstance() {
        return comandaprodutoDAO;
    }

    @Override
    public void inserir(Comandaproduto objeto) throws Exception {
        objetoJPA.create(objeto);
    }

    @Override
    public void editar(Comandaproduto objeto) throws Exception {
        objetoJPA.edit(objeto);
    }

    @Override
    public void deletar(Comandaproduto objeto) throws Exception {
        objetoJPA.destroy(objeto.getComandaprodutoPK());
    }

    @Override
    public Comandaproduto get(Comandaproduto objeto) {
        return objetoJPA.findComandaproduto(objeto.getComandaprodutoPK());
    }

    @Override
    public List<Comandaproduto> getAll() {
        return objetoJPA.findComandaprodutoEntities();
    }

    public List<Comandaproduto> getUnidade(Comanda unidade) {
        EntityManager banco = objetoJPA.getEntityManager();
        try {
            Query consulta = banco.createNamedQuery("Comandaproduto.findByIdcomanda",
                    Comandaproduto.class);
            consulta.setParameter("idcomanda", unidade);
            return consulta.getResultList();

        } finally {
            banco.close();
        }

    }

    public boolean lancarProdutoNaComanda(Comanda comanda, Produtos produto) {
        ComandaprodutoPK cmdprodpk;
        int totalComandaproduto = 0;
        ProdutoDAO produtodao = new ProdutoDAO();
        try {
            // Cria uma nova entrada na tabela associativa
            Comandaproduto comandaproduto = new Comandaproduto();
            comandaproduto.setIdcomanda(comanda);
            if (produto.getQtdeprod() > 0) {
                produto.setQtdeprod(produto.getQtdeprod() - 1);
                produtodao.editar(produto);
            }

            comandaproduto.setIdproduto(produto);
            for (Comandaproduto f : getAll()) {
                if (f != null) {
                    totalComandaproduto = f.getComandaprodutoPK().getIdcomandaproduto();
                }
            }
            cmdprodpk = new ComandaprodutoPK(totalComandaproduto + 1);
            comandaproduto.setComandaprodutoPK(cmdprodpk);
            comandaprodutoDAO.inserir(comandaproduto);

            comanda.getComandaprodutoCollection().add(comandaproduto);

            return true;
        } catch (Exception e) {
            // Trate a exceção conforme a necessidade do seu projeto
            e.printStackTrace();
            return false;
        }
    }

}
