/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author gabal
 */
@Entity
@Table(name = "comanda")
@NamedQueries({
    @NamedQuery(name = "Comanda.findAll", query = "SELECT c FROM Comanda c"),
    @NamedQuery(name = "Comanda.findByIdcomanda", query = "SELECT c FROM Comanda c WHERE c.idcomanda = :idcomanda"),
    @NamedQuery(name = "Comanda.findByNomecomanda", query = "SELECT c FROM Comanda c WHERE c.nomecomanda = :nomecomanda")})
public class Comanda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcomanda")
    private Integer idcomanda;
    @Column(name = "nomecomanda")
    private String nomecomanda;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcomanda")
    private Collection<Comandaproduto> comandaprodutoCollection;

    public Comanda() {
    }

    public Comanda(Integer idcomanda) {
        this.idcomanda = idcomanda;
    }

    public Integer getIdcomanda() {
        return idcomanda;
    }

    public void setIdcomanda(Integer idcomanda) {
        this.idcomanda = idcomanda;
    }

    public String getNomecomanda() {
        return nomecomanda;
    }

    public void setNomecomanda(String nomecomanda) {
        this.nomecomanda = nomecomanda;
    }

    public Collection<Comandaproduto> getComandaprodutoCollection() {
        return comandaprodutoCollection;
    }

    public void setComandaprodutoCollection(Collection<Comandaproduto> comandaprodutoCollection) {
        this.comandaprodutoCollection = comandaprodutoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomanda != null ? idcomanda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comanda)) {
            return false;
        }
        Comanda other = (Comanda) object;
        if ((this.idcomanda == null && other.idcomanda != null) || (this.idcomanda != null && !this.idcomanda.equals(other.idcomanda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Comanda " + idcomanda;
    }

}
