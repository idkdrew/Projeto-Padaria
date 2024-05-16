/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpaControllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Comanda;
import entidades.Comandaproduto;
import entidades.ComandaprodutoPK;
import entidades.Produtos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaControllers.exceptions.NonexistentEntityException;
import jpaControllers.exceptions.PreexistingEntityException;

/**
 *
 * @author gabal
 */
public class ComandaprodutoJpaController implements Serializable {

    public ComandaprodutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comandaproduto comandaproduto) throws PreexistingEntityException, Exception {
        if (comandaproduto.getComandaprodutoPK() == null) {
            comandaproduto.setComandaprodutoPK(new ComandaprodutoPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comanda idcomanda = comandaproduto.getIdcomanda();
            if (idcomanda != null) {
                idcomanda = em.getReference(idcomanda.getClass(), idcomanda.getIdcomanda());
                comandaproduto.setIdcomanda(idcomanda);
            }
            Produtos idproduto = comandaproduto.getIdproduto();
            if (idproduto != null) {
                idproduto = em.getReference(idproduto.getClass(), idproduto.getIdprod());
                comandaproduto.setIdproduto(idproduto);
            }
            em.persist(comandaproduto);
            if (idcomanda != null) {
                idcomanda.getComandaprodutoCollection().add(comandaproduto);
                idcomanda = em.merge(idcomanda);
            }
            if (idproduto != null) {
                idproduto.getComandaprodutoCollection().add(comandaproduto);
                idproduto = em.merge(idproduto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComandaproduto(comandaproduto.getComandaprodutoPK()) != null) {
                throw new PreexistingEntityException("Comandaproduto " + comandaproduto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comandaproduto comandaproduto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comandaproduto persistentComandaproduto = em.find(Comandaproduto.class, comandaproduto.getComandaprodutoPK());
            Comanda idcomandaOld = persistentComandaproduto.getIdcomanda();
            Comanda idcomandaNew = comandaproduto.getIdcomanda();
            Produtos idprodutoOld = persistentComandaproduto.getIdproduto();
            Produtos idprodutoNew = comandaproduto.getIdproduto();
            if (idcomandaNew != null) {
                idcomandaNew = em.getReference(idcomandaNew.getClass(), idcomandaNew.getIdcomanda());
                comandaproduto.setIdcomanda(idcomandaNew);
            }
            if (idprodutoNew != null) {
                idprodutoNew = em.getReference(idprodutoNew.getClass(), idprodutoNew.getIdprod());
                comandaproduto.setIdproduto(idprodutoNew);
            }
            comandaproduto = em.merge(comandaproduto);
            if (idcomandaOld != null && !idcomandaOld.equals(idcomandaNew)) {
                idcomandaOld.getComandaprodutoCollection().remove(comandaproduto);
                idcomandaOld = em.merge(idcomandaOld);
            }
            if (idcomandaNew != null && !idcomandaNew.equals(idcomandaOld)) {
                idcomandaNew.getComandaprodutoCollection().add(comandaproduto);
                idcomandaNew = em.merge(idcomandaNew);
            }
            if (idprodutoOld != null && !idprodutoOld.equals(idprodutoNew)) {
                idprodutoOld.getComandaprodutoCollection().remove(comandaproduto);
                idprodutoOld = em.merge(idprodutoOld);
            }
            if (idprodutoNew != null && !idprodutoNew.equals(idprodutoOld)) {
                idprodutoNew.getComandaprodutoCollection().add(comandaproduto);
                idprodutoNew = em.merge(idprodutoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ComandaprodutoPK id = comandaproduto.getComandaprodutoPK();
                if (findComandaproduto(id) == null) {
                    throw new NonexistentEntityException("The comandaproduto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ComandaprodutoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comandaproduto comandaproduto;
            try {
                comandaproduto = em.getReference(Comandaproduto.class, id);
                comandaproduto.getComandaprodutoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comandaproduto with id " + id + " no longer exists.", enfe);
            }
            Comanda idcomanda = comandaproduto.getIdcomanda();
            if (idcomanda != null) {
                idcomanda.getComandaprodutoCollection().remove(comandaproduto);
                idcomanda = em.merge(idcomanda);
            }
            Produtos idproduto = comandaproduto.getIdproduto();
            if (idproduto != null) {
                idproduto.getComandaprodutoCollection().remove(comandaproduto);
                idproduto = em.merge(idproduto);
            }
            em.remove(comandaproduto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comandaproduto> findComandaprodutoEntities() {
        return findComandaprodutoEntities(true, -1, -1);
    }

    public List<Comandaproduto> findComandaprodutoEntities(int maxResults, int firstResult) {
        return findComandaprodutoEntities(false, maxResults, firstResult);
    }

    private List<Comandaproduto> findComandaprodutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comandaproduto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Comandaproduto findComandaproduto(ComandaprodutoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comandaproduto.class, id);
        } finally {
            em.close();
        }
    }

    public int getComandaprodutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comandaproduto> rt = cq.from(Comandaproduto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
