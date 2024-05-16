/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entidades.Produtos;
import java.util.List;
import utility.JPAUtils;
import entidades.Comanda;
import jpaControllers.ComandaJpaController;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author gabal
 */
public class ComandaDAO extends AbstractDAO<ComandaJpaController, Comanda> {

    private ComandaprodutoDAO comandaprodutoDAO;

    private EntityManager entityManager;

    public ComandaDAO() {
        objetoJPA = new ComandaJpaController(getEntityManagerFactory());
        this.comandaprodutoDAO = new ComandaprodutoDAO();
    }

    @Override
    public void inserir(Comanda objeto) throws Exception {
        objetoJPA.create(objeto);
    }

    @Override
    public void editar(Comanda objeto) throws Exception {
        objetoJPA.edit(objeto);
    }

    @Override
    public void deletar(Comanda objeto) throws Exception {
        objetoJPA.destroy(objeto.getIdcomanda());
    }

    @Override
    public Comanda get(Comanda objeto) {
        return objetoJPA.findComanda(objeto.getIdcomanda());
    }

    @Override
    public List<Comanda> getAll() {
        return objetoJPA.findComandaEntities();
    }

    public List<Comanda> getUnidade(String unidade) {
        EntityManager banco = objetoJPA.getEntityManager();
        try {
            Query consulta = banco.createNamedQuery("Comanda.findByUnidade",
                    Comanda.class);
            consulta.setParameter("unidade", unidade);
            return consulta.getResultList();

        } finally {
            banco.close();
        }

    }

    public List<Produtos> buscarProdutosPorComanda(int idComanda) {
        String jpql = "SELECT cp.idproduto FROM Comandaproduto cp WHERE cp.idcomanda.idcomanda = :idComanda";
        TypedQuery<Produtos> query = this.objetoJPA.getEntityManager().createQuery(jpql, Produtos.class); //entityManager.createQuery(jpql, Produtos.class);
        query.setParameter("idComanda", idComanda);
        return query.getResultList();
    }

    public List<Comanda> obterTodasComandas() {
        EntityManager entityManager = JPAUtils.getEntityManagerFactory().createEntityManager();
        try {
            String jpql = "SELECT c FROM Comanda c";
            TypedQuery<Comanda> query = entityManager.createQuery(jpql, Comanda.class);
            List<Comanda> comandas = query.getResultList();
            System.out.println("Número de comandas recuperadas: " + comandas.size());
            return comandas;
        } finally {
            entityManager.close();
        }

    }

}
