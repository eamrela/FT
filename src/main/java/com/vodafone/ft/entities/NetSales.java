/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.ft.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    , @NamedQuery(name = "NetSales.findByNetsalesAmount", query = "SELECT n FROM NetSales n WHERE n.netsalesAmount = :netsalesAmount")})
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
    @Size(max = 2147483647)
    @Column(name = "category")
    private String category;
    @Column(name = "netsales_amount")
    private Double netsalesAmount;
    @Column(name = "original_amount")
    private Double originalAmount;

    @Transient
    private Double invoiceIssued;
    
    @JoinTable(name = "netsales_j_customer_extrawork_invoice", joinColumns = {
        @JoinColumn(name = "ns_id", referencedColumnName = "netsales_id")}, inverseJoinColumns = {
        @JoinColumn(name = "invoice_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<CustomerExtraworkInvoice> customerExtraworkInvoiceCollection;
    @JoinTable(name = "netsales_j_customer_service_invoice", joinColumns = {
        @JoinColumn(name = "ns_id", referencedColumnName = "netsales_id")}, inverseJoinColumns = {
        @JoinColumn(name = "invoice_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<CustomerServiceInvoice> customerServiceInvoiceCollection;

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

    
    @Transient
    public Double getInvoiceIssued() {
        invoiceIssued = 0.0;
        if(getCustomerServiceInvoiceCollection()!=null){
            Object[] poSr = getCustomerServiceInvoiceCollection().toArray();
                    for (Object poSrMd : poSr) {
                        invoiceIssued += ((CustomerServiceInvoice)poSrMd).getInvoiceValue();
                    }
        }
        if(getCustomerExtraworkInvoiceCollection()!=null){
            Object[] poEw = getCustomerExtraworkInvoiceCollection().toArray();
                    for (Object poEwMd : poEw) {
                        invoiceIssued += ((CustomerExtraworkInvoice)poEwMd).getInvoiceValue();
                    }
        }
        return invoiceIssued;
    }

    @XmlTransient
    public Collection<CustomerExtraworkInvoice> getCustomerExtraworkInvoiceCollection() {
        return customerExtraworkInvoiceCollection;
    }

    public void setCustomerExtraworkInvoiceCollection(Collection<CustomerExtraworkInvoice> customerExtraworkInvoiceCollection) {
        this.customerExtraworkInvoiceCollection = customerExtraworkInvoiceCollection;
    }

    @XmlTransient
    public Collection<CustomerServiceInvoice> getCustomerServiceInvoiceCollection() {
        return customerServiceInvoiceCollection;
    }

    public void setCustomerServiceInvoiceCollection(Collection<CustomerServiceInvoice> customerServiceInvoiceCollection) {
        this.customerServiceInvoiceCollection = customerServiceInvoiceCollection;
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
        return "new3.NetSales[ netsalesId=" + netsalesId + " ]";
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
    
    
    
}
