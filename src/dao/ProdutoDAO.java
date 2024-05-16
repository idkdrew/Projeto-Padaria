/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author gabal
 */
import entidades.Produtos;
import utility.JPAUtils;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import jpaControllers.ProdutosJpaController;

public class ProdutoDAO extends AbstractDAO<ProdutosJpaController, Produtos> {

    public ProdutoDAO() {
        objetoJPA = new ProdutosJpaController(getEntityManagerFactory());
    }

    @Override
    public void inserir(Produtos objeto) throws Exception {
        objetoJPA.create(objeto);
    }

    @Override
    public void editar(Produtos objeto) throws Exception {
        objetoJPA.edit(objeto);
    }

    @Override
    public void deletar(Produtos objeto) throws Exception {
        objetoJPA.destroy(objeto.getIdprod());
    }

    @Override
    public Produtos get(Produtos objeto) {
        return objetoJPA.findProdutos(objeto.getIdprod());
    }

    @Override
    public List<Produtos> getAll() {
        return objetoJPA.findProdutosEntities();
    }

    public List<Produtos> getUnidade(String unidade) {
        EntityManager banco = objetoJPA.getEntityManager();
        try {
            Query consulta = banco.createNamedQuery("Produto.findByUnidade",
                    Produtos.class);
            consulta.setParameter("unidade", unidade);
            return consulta.getResultList();

        } finally {
            banco.close();
        }

    }

}
