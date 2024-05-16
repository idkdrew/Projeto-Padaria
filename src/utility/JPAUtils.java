/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author gabal
 */
public class JPAUtils {

    private static EntityManagerFactory EMF = null;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (EMF == null) {
            EMF = Persistence.createEntityManagerFactory("PadocasPU");
        }
        return EMF;
    }
}
