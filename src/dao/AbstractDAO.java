*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author gabal
 */
import utility.JPAUtils;
import javax.persistence.EntityManagerFactory;

public abstract class AbstractDAO<J, E> implements InterfaceDAO<J, E> {

    J objetoJPA;

    public J getObjetoJPA() {
        return objetoJPA;
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return JPAUtils.getEntityManagerFactory();
    }
}
