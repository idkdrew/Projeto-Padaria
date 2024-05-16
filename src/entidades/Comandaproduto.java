/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gabal
 */
@Entity
@Table(name = "comandaproduto")
@NamedQueries({
    @NamedQuery(name = "Comandaproduto.findAll", query = "SELECT c FROM Comandaproduto c"),
    @NamedQuery(name = "Comandaproduto.findByIdcomandaproduto", query = "SELECT c FROM Comandaproduto c WHERE c.comandaprodutoPK.idcomandaproduto = :idcomandaproduto"),
    @NamedQuery(name = "Comandaproduto.findByIdcomanda", query = "SELECT c FROM Comandaproduto c WHERE c.idcomanda = :idcomanda")})
public class Comandaproduto implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComandaprodutoPK comandaprodutoPK;
    @JoinColumn(name = "idcomanda", referencedColumnName = "idcomanda")
    @ManyToOne(optional = false)
    private Comanda idcomanda;
    @JoinColumn(name = "idproduto", referencedColumnName = "idprod")
    @ManyToOne(optional = false)
    private Produtos idproduto;

    public Comandaproduto() {
    }

    public Comandaproduto(ComandaprodutoPK comandaprodutoPK) {
        this.comandaprodutoPK = comandaprodutoPK;
    }

    public Comandaproduto(int idcomandaproduto) {
        this.comandaprodutoPK = new ComandaprodutoPK(idcomandaproduto);
    }

    public ComandaprodutoPK getComandaprodutoPK() {
        return comandaprodutoPK;
    }

    public void setComandaprodutoPK(ComandaprodutoPK comandaprodutoPK) {
        this.comandaprodutoPK = comandaprodutoPK;
    }

    public Comanda getIdcomanda() {
        return idcomanda;
    }

    public void setIdcomanda(Comanda idcomanda) {
        this.idcomanda = idcomanda;
    }

    public Produtos getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(Produtos idproduto) {
        this.idproduto = idproduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comandaprodutoPK != null ? comandaprodutoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comandaproduto)) {
            return false;
        }
        Comandaproduto other = (Comandaproduto) object;
        if ((this.comandaprodutoPK == null && other.comandaprodutoPK != null) || (this.comandaprodutoPK != null && !this.comandaprodutoPK.equals(other.comandaprodutoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Comandaproduto[ comandaprodutoPK=" + comandaprodutoPK + " ]";
    }

}
