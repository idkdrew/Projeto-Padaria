/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpaControllers;

import entidades.Comanda;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Comandaproduto;
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
public class ComandaJpaController implements Serializable {

    public ComandaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comanda comanda) {
        if (comanda.getComandaprodutoCollection() == null) {
            comanda.setComandaprodutoCollection(new ArrayList<Comandaproduto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Comandaproduto> attachedComandaprodutoCollection = new ArrayList<Comandaproduto>();
            for (Comandaproduto comandaprodutoCollectionComandaprodutoToAttach : comanda.getComandaprodutoCollection()) {
                comandaprodutoCollectionComandaprodutoToAttach = em.getReference(comandaprodutoCollectionComandaprodutoToAttach.getClass(), comandaprodutoCollectionComandaprodutoToAttach.getComandaprodutoPK());
                attachedComandaprodutoCollection.add(comandaprodutoCollectionComandaprodutoToAttach);
            }
            comanda.setComandaprodutoCollection(attachedComandaprodutoCollection);
            em.persist(comanda);
            for (Comandaproduto comandaprodutoCollectionComandaproduto : comanda.getComandaprodutoCollection()) {
                Comanda oldIdcomandaOfComandaprodutoCollectionComandaproduto = comandaprodutoCollectionComandaproduto.getIdcomanda();
                comandaprodutoCollectionComandaproduto.setIdcomanda(comanda);
                comandaprodutoCollectionComandaproduto = em.merge(comandaprodutoCollectionComandaproduto);
                if (oldIdcomandaOfComandaprodutoCollectionComandaproduto != null) {
                    oldIdcomandaOfComandaprodutoCollectionComandaproduto.getComandaprodutoCollection().remove(comandaprodutoCollectionComandaproduto);
                    oldIdcomandaOfComandaprodutoCollectionComandaproduto = em.merge(oldIdcomandaOfComandaprodutoCollectionComandaproduto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comanda comanda) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comanda persistentComanda = em.find(Comanda.class, comanda.getIdcomanda());
            Collection<Comandaproduto> comandaprodutoCollectionOld = persistentComanda.getComandaprodutoCollection();
            Collection<Comandaproduto> comandaprodutoCollectionNew = comanda.getComandaprodutoCollection();
            List<String> illegalOrphanMessages = null;
            for (Comandaproduto comandaprodutoCollectionOldComandaproduto : comandaprodutoCollectionOld) {
                if (!comandaprodutoCollectionNew.contains(comandaprodutoCollectionOldComandaproduto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comandaproduto " + comandaprodutoCollectionOldComandaproduto + " since its idcomanda field is not nullable.");
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
            comanda.setComandaprodutoCollection(comandaprodutoCollectionNew);
            comanda = em.merge(comanda);
            for (Comandaproduto comandaprodutoCollectionNewComandaproduto : comandaprodutoCollectionNew) {
                if (!comandaprodutoCollectionOld.contains(comandaprodutoCollectionNewComandaproduto)) {
                    Comanda oldIdcomandaOfComandaprodutoCollectionNewComandaproduto = comandaprodutoCollectionNewComandaproduto.getIdcomanda();
                    comandaprodutoCollectionNewComandaproduto.setIdcomanda(comanda);
                    comandaprodutoCollectionNewComandaproduto = em.merge(comandaprodutoCollectionNewComandaproduto);
                    if (oldIdcomandaOfComandaprodutoCollectionNewComandaproduto != null && !oldIdcomandaOfComandaprodutoCollectionNewComandaproduto.equals(comanda)) {
                        oldIdcomandaOfComandaprodutoCollectionNewComandaproduto.getComandaprodutoCollection().remove(comandaprodutoCollectionNewComandaproduto);
                        oldIdcomandaOfComandaprodutoCollectionNewComandaproduto = em.merge(oldIdcomandaOfComandaprodutoCollectionNewComandaproduto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comanda.getIdcomanda();
                if (findComanda(id) == null) {
                    throw new NonexistentEntityException("The comanda with id " + id + " no longer exists.");
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
            Comanda comanda;
            try {
                comanda = em.getReference(Comanda.class, id);
                comanda.getIdcomanda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comanda with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Comandaproduto> comandaprodutoCollectionOrphanCheck = comanda.getComandaprodutoCollection();
            for (Comandaproduto comandaprodutoCollectionOrphanCheckComandaproduto : comandaprodutoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Comanda (" + comanda + ") cannot be destroyed since the Comandaproduto " + comandaprodutoCollectionOrphanCheckComandaproduto + " in its comandaprodutoCollection field has a non-nullable idcomanda field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(comanda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comanda> findComandaEntities() {
        return findComandaEntities(true, -1, -1);
    }

    public List<Comanda> findComandaEntities(int maxResults, int firstResult) {
        return findComandaEntities(false, maxResults, firstResult);
    }

    private List<Comanda> findComandaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comanda.class));
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

    public Comanda findComanda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comanda.class, id);
        } finally {
            em.close();
        }
    }

    public int getComandaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comanda> rt = cq.from(Comanda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
