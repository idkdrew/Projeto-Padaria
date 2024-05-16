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
@Table(name = "produtos")
@NamedQueries({
    @NamedQuery(name = "Produtos.findAll", query = "SELECT p FROM Produtos p"),
    @NamedQuery(name = "Produtos.findByIdprod", query = "SELECT p FROM Produtos p WHERE p.idprod = :idprod"),
    @NamedQuery(name = "Produtos.findByNomeprod", query = "SELECT p FROM Produtos p WHERE p.nomeprod = :nomeprod"),
    @NamedQuery(name = "Produtos.findByPrecoprod", query = "SELECT p FROM Produtos p WHERE p.precoprod = :precoprod"),
    @NamedQuery(name = "Produtos.findByQtdeprod", query = "SELECT p FROM Produtos p WHERE p.qtdeprod = :qtdeprod")})
public class Produtos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprod")
    private Integer idprod;
    @Column(name = "nomeprod")
    private String nomeprod;
    @Basic(optional = false)
    @Column(name = "precoprod")
    private double precoprod;
    @Column(name = "qtdeprod")
    private Integer qtdeprod;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idproduto")
    private Collection<Comandaproduto> comandaprodutoCollection;

    public Produtos() {
    }

    public Produtos(Integer idprod) {
        this.idprod = idprod;
    }

    public Produtos(Integer idprod, double precoprod) {
        this.idprod = idprod;
        this.precoprod = precoprod;
    }

    public Integer getIdprod() {
        return idprod;
    }

    public void setIdprod(Integer idprod) {
        this.idprod = idprod;
    }

    public String getNomeprod() {
        return nomeprod;
    }

    public void setNomeprod(String nomeprod) {
        this.nomeprod = nomeprod;
    }

    public double getPrecoprod() {
        return precoprod;
    }

    public void setPrecoprod(double precoprod) {
        this.precoprod = precoprod;
    }

    public Integer getQtdeprod() {
        return qtdeprod;
    }

    public void setQtdeprod(Integer qtdeprod) {
        this.qtdeprod = qtdeprod;
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
        hash += (idprod != null ? idprod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produtos)) {
            return false;
        }
        Produtos other = (Produtos) object;
        if ((this.idprod == null && other.idprod != null) || (this.idprod != null && !this.idprod.equals(other.idprod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Produtos[ idprod=" + idprod + " ]";
    }

}
