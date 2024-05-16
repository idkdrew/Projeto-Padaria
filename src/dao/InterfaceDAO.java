/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author gabal
 */
public interface InterfaceDAO<J, E> {

    public EntityManagerFactory getEntityManagerFactory();

    public void inserir(E objeto) throws Exception;

    public void editar(E objeto) throws Exception;

    public void deletar(E objeto) throws Exception;

    public E get(E objeto);

    public List<E> getAll();
}
