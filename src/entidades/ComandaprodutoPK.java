/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author gabal
 */
@Embeddable
public class ComandaprodutoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idcomandaproduto")
    private int idcomandaproduto;

    public ComandaprodutoPK() {
    }

    public ComandaprodutoPK(int idcomandaproduto) {
        this.idcomandaproduto = idcomandaproduto;
    }

    public int getIdcomandaproduto() {
        return idcomandaproduto;
    }

    public void setIdcomandaproduto(int idcomandaproduto) {
        this.idcomandaproduto = idcomandaproduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idcomandaproduto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComandaprodutoPK)) {
            return false;
        }
        ComandaprodutoPK other = (ComandaprodutoPK) object;
        if (this.idcomandaproduto != other.idcomandaproduto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ComandaprodutoPK[ idcomandaproduto=" + idcomandaproduto + " ]";
    }

}
