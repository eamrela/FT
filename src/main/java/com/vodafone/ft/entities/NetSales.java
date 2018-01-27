/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.ft.entities;

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
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author eamrela
 */
@Entity
@Table(name = "net_sales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NetSales.findAll", query = "SELECT n FROM NetSales n")
    , @NamedQuery(name = "NetSales.findByNetsalesId", query = "SELECT n FROM NetSales n WHERE n.netsalesId = :netsalesId")
    , @NamedQuery(name = "NetSales.findByDescription", query = "SELECT n FROM NetSales n WHERE n.description = :description")
    , @NamedQuery(name = "NetSales.findByNetsalesAmount", query = "SELECT n FROM NetSales n WHERE n.netsalesAmount = :netsalesAmount")
    , @NamedQuery(name = "NetSales.findByOriginalAmount", query = "SELECT n FROM NetSales n WHERE n.originalAmount = :originalAmount")
    , @NamedQuery(name = "NetSales.findByCategory", query = "SELECT n FROM NetSales n WHERE n.category = :category")})
public class NetSales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "netsales_id")
    private Long netsalesId;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Column(name = "netsales_amount")
    private Double netsalesAmount;
    @Column(name = "original_amount")
    private Double originalAmount;
    @Size(max = 2147483647)
    @Column(name = "category")
    private String category;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "netSales")
    private Collection<NetsalesJCustomerServiceInvoice> netsalesJCustomerServiceInvoiceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "netSales")
    private Collection<NetsalesJCustomerExtraworkInvoice> netsalesJCustomerExtraworkInvoiceCollection;

    @Transient
    private Double invoiceIssued;
    
    public NetSales() {
    }

    public NetSales(Long netsalesId) {
        this.netsalesId = netsalesId;
    }

    public Long getNetsalesId() {
        return netsalesId;
    }

    public void setNetsalesId(Long netsalesId) {
        this.netsalesId = netsalesId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getNetsalesAmount() {
        return netsalesAmount;
    }

    public void setNetsalesAmount(Double netsalesAmount) {
        this.netsalesAmount = netsalesAmount;
    }

    public Double getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @XmlTransient
    public Collection<NetsalesJCustomerServiceInvoice> getNetsalesJCustomerServiceInvoiceCollection() {
        return netsalesJCustomerServiceInvoiceCollection;
    }

    public void setNetsalesJCustomerServiceInvoiceCollection(Collection<NetsalesJCustomerServiceInvoice> netsalesJCustomerServiceInvoiceCollection) {
        this.netsalesJCustomerServiceInvoiceCollection = netsalesJCustomerServiceInvoiceCollection;
    }

    @XmlTransient
    public Collection<NetsalesJCustomerExtraworkInvoice> getNetsalesJCustomerExtraworkInvoiceCollection() {
        return netsalesJCustomerExtraworkInvoiceCollection;
    }

    public void setNetsalesJCustomerExtraworkInvoiceCollection(Collection<NetsalesJCustomerExtraworkInvoice> netsalesJCustomerExtraworkInvoiceCollection) {
        this.netsalesJCustomerExtraworkInvoiceCollection = netsalesJCustomerExtraworkInvoiceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (netsalesId != null ? netsalesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NetSales)) {
            return false;
        }
        NetSales other = (NetSales) object;
        if ((this.netsalesId == null && other.netsalesId != null) || (this.netsalesId != null && !this.netsalesId.equals(other.netsalesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vodafone.ft.entities.NetSales[ netsalesId=" + netsalesId + " ]";
    }
    
    @Transient
    public Double getInvoiceIssued() {
        invoiceIssued = 0.0;
        if(getNetsalesJCustomerServiceInvoiceCollection()!=null){
            Object[] poSr = getNetsalesJCustomerServiceInvoiceCollection().toArray();
                    for (Object poSrMd : poSr) {
                        invoiceIssued += ((NetsalesJCustomerServiceInvoice)poSrMd).getCustomerServiceInvoice().getInvoiceValue()
                                * ((NetsalesJCustomerServiceInvoice)poSrMd).getSettlePercentage();
                    }
        }
        if(getNetsalesJCustomerExtraworkInvoiceCollection()!=null){
            Object[] poEw = getNetsalesJCustomerExtraworkInvoiceCollection().toArray();
                    for (Object poEwMd : poEw) {
                        invoiceIssued += ((NetsalesJCustomerExtraworkInvoice)poEwMd).getCustomerExtraworkInvoice().getInvoiceValue()
                                * ((NetsalesJCustomerExtraworkInvoice)poEwMd).getSettlePercentage();
                    }
        }
        return invoiceIssued;
    }
}
