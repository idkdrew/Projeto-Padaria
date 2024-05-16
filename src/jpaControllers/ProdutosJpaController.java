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
import entidades.Comandaproduto;
import entidades.Produtos;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaControllers.exceptions.IllegalOrphanException;
import jpaControllers.exceptions.NonexistentEntityException;

/**
 *
 * @author gabal
 */
public class ProdutosJpaController implements Serializable {

    public ProdutosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Produtos produtos) {
        if (produtos.getComandaprodutoCollection() == null) {
            produtos.setComandaprodutoCollection(new ArrayList<Comandaproduto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Comandaproduto> attachedComandaprodutoCollection = new ArrayList<Comandaproduto>();
            for (Comandaproduto comandaprodutoCollectionComandaprodutoToAttach : produtos.getComandaprodutoCollection()) {
                comandaprodutoCollectionComandaprodutoToAttach = em.getReference(comandaprodutoCollectionComandaprodutoToAttach.getClass(), comandaprodutoCollectionComandaprodutoToAttach.getComandaprodutoPK());
                attachedComandaprodutoCollection.add(comandaprodutoCollectionComandaprodutoToAttach);
            }
            produtos.setComandaprodutoCollection(attachedComandaprodutoCollection);
            em.persist(produtos);
            for (Comandaproduto comandaprodutoCollectionComandaproduto : produtos.getComandaprodutoCollection()) {
                Produtos oldIdprodutoOfComandaprodutoCollectionComandaproduto = comandaprodutoCollectionComandaproduto.getIdproduto();
                comandaprodutoCollectionComandaproduto.setIdproduto(produtos);
                comandaprodutoCollectionComandaproduto = em.merge(comandaprodutoCollectionComandaproduto);
                if (oldIdprodutoOfComandaprodutoCollectionComandaproduto != null) {
                    oldIdprodutoOfComandaprodutoCollectionComandaproduto.getComandaprodutoCollection().remove(comandaprodutoCollectionComandaproduto);
                    oldIdprodutoOfComandaprodutoCollectionComandaproduto = em.merge(oldIdprodutoOfComandaprodutoCollectionComandaproduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produtos produtos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produtos persistentProdutos = em.find(Produtos.class, produtos.getIdprod());
            Collection<Comandaproduto> comandaprodutoCollectionOld = persistentProdutos.getComandaprodutoCollection();
            Collection<Comandaproduto> comandaprodutoCollectionNew = produtos.getComandaprodutoCollection();
            List<String> illegalOrphanMessages = null;
            for (Comandaproduto comandaprodutoCollectionOldComandaproduto : comandaprodutoCollectionOld) {
                if (!comandaprodutoCollectionNew.contains(comandaprodutoCollectionOldComandaproduto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comandaproduto " + comandaprodutoCollectionOldComandaproduto + " since its idproduto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Comandaproduto> attachedComandaprodutoCollectionNew = new ArrayList<Comandaproduto>();
            for (Comandaproduto comandaprodutoCollectionNewComandaprodutoToAttach : comandaprodutoCollectionNew) {
                comandaprodutoCollectionNewComandaprodutoToAttach = em.getReference(comandaprodutoCollectionNewComandaprodutoToAttach.getClass(), comandaprodutoCollectionNewComandaprodutoToAttach.getComandaprodutoPK());
                attachedComandaprodutoCollectionNew.add(comandaprodutoCollectionNewComandaprodutoToAttach);
            }
            comandaprodutoCollectionNew = attachedComandaprodutoCollectionNew;
            produtos.setComandaprodutoCollection(comandaprodutoCollectionNew);
            produtos = em.merge(produtos);
            for (Comandaproduto comandaprodutoCollectionNewComandaproduto : comandaprodutoCollectionNew) {
                if (!comandaprodutoCollectionOld.contains(comandaprodutoCollectionNewComandaproduto)) {
                    Produtos oldIdprodutoOfComandaprodutoCollectionNewComandaproduto = comandaprodutoCollectionNewComandaproduto.getIdproduto();
                    comandaprodutoCollectionNewComandaproduto.setIdproduto(produtos);
                    comandaprodutoCollectionNewComandaproduto = em.merge(comandaprodutoCollectionNewComandaproduto);
                    if (oldIdprodutoOfComandaprodutoCollectionNewComandaproduto != null && !oldIdprodutoOfComandaprodutoCollectionNewComandaproduto.equals(produtos)) {
                        oldIdprodutoOfComandaprodutoCollectionNewComandaproduto.getComandaprodutoCollection().remove(comandaprodutoCollectionNewComandaproduto);
                        oldIdprodutoOfComandaprodutoCollectionNewComandaproduto = em.merge(oldIdprodutoOfComandaprodutoCollectionNewComandaproduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = produtos.getIdprod();
                if (findProdutos(id) == null) {
                    throw new NonexistentEntityException("The produtos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produtos produtos;
            try {
                produtos = em.getReference(Produtos.class, id);
                produtos.getIdprod();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produtos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Comandaproduto> comandaprodutoCollectionOrphanCheck = produtos.getComandaprodutoCollection();
            for (Comandaproduto comandaprodutoCollectionOrphanCheckComandaproduto : comandaprodutoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Produtos (" + produtos + ") cannot be destroyed since the Comandaproduto " + comandaprodutoCollectionOrphanCheckComandaproduto + " in its comandaprodutoCollection field has a non-nullable idproduto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(produtos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produtos> findProdutosEntities() {
        return findProdutosEntities(true, -1, -1);
    }

    public List<Produtos> findProdutosEntities(int maxResults, int firstResult) {
        return findProdutosEntities(false, maxResults, firstResult);
    }

    private List<Produtos> findProdutosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produtos.class));
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

    public Produtos findProdutos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produtos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProdutosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produtos> rt = cq.from(Produtos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
