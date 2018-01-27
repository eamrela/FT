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
@Table(name = "cost_of_sales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CostOfSales.findAll", query = "SELECT c FROM CostOfSales c")
    , @NamedQuery(name = "CostOfSales.findByCostId", query = "SELECT c FROM CostOfSales c WHERE c.costId = :costId")
    , @NamedQuery(name = "CostOfSales.findByDescription", query = "SELECT c FROM CostOfSales c WHERE c.description = :description")
    , @NamedQuery(name = "CostOfSales.findByAspName", query = "SELECT c FROM CostOfSales c WHERE c.aspName = :aspName")
    , @NamedQuery(name = "CostOfSales.findByCostAmount", query = "SELECT c FROM CostOfSales c WHERE c.costAmount = :costAmount")
    , @NamedQuery(name = "CostOfSales.findByOriginalAmount", query = "SELECT c FROM CostOfSales c WHERE c.originalAmount = :originalAmount")
    , @NamedQuery(name = "CostOfSales.findByCategory", query = "SELECT c FROM CostOfSales c WHERE c.category = :category")})
public class CostOfSales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cost_id")
    private Long costId;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Size(max = 2147483647)
    @Column(name = "asp_name")
    private String aspName;
    @Column(name = "cost_amount")
    private Double costAmount;
    @Column(name = "original_amount")
    private Double originalAmount;
    @Size(max = 2147483647)
    @Column(name = "category")
    private String category;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "costOfSales")
    private Collection<CostOfSalesJAspExtraworkGrn> costOfSalesJAspExtraworkGrnCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "costOfSales")
    private Collection<CostOfSalesJAspServiceGrn> costOfSalesJAspServiceGrnCollection;

    @Transient
    private Double grnIssued;
    
    public CostOfSales() {
    }

    public CostOfSales(Long costId) {
        this.costId = costId;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAspName() {
        return aspName;
    }

    public void setAspName(String aspName) {
        this.aspName = aspName;
    }

    public Double getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(Double costAmount) {
        this.costAmount = costAmount;
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
    public Collection<CostOfSalesJAspExtraworkGrn> getCostOfSalesJAspExtraworkGrnCollection() {
        return costOfSalesJAspExtraworkGrnCollection;
    }

    public void setCostOfSalesJAspExtraworkGrnCollection(Collection<CostOfSalesJAspExtraworkGrn> costOfSalesJAspExtraworkGrnCollection) {
        this.costOfSalesJAspExtraworkGrnCollection = costOfSalesJAspExtraworkGrnCollection;
    }

    @XmlTransient
    public Collection<CostOfSalesJAspServiceGrn> getCostOfSalesJAspServiceGrnCollection() {
        return costOfSalesJAspServiceGrnCollection;
    }

    public void setCostOfSalesJAspServiceGrnCollection(Collection<CostOfSalesJAspServiceGrn> costOfSalesJAspServiceGrnCollection) {
        this.costOfSalesJAspServiceGrnCollection = costOfSalesJAspServiceGrnCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (costId != null ? costId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CostOfSales)) {
            return false;
        }
        CostOfSales other = (CostOfSales) object;
        if ((this.costId == null && other.costId != null) || (this.costId != null && !this.costId.equals(other.costId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vodafone.ft.entities.CostOfSales[ costId=" + costId + " ]";
    }
    
     @Transient
    public Double getGrnIssued() {
        grnIssued = 0.0;
        if(getCostOfSalesJAspServiceGrnCollection()!=null){
            Object[] poSrGrn = getCostOfSalesJAspServiceGrnCollection().toArray();
            for (Object poSrGrn1 : poSrGrn) {
                
                grnIssued += ((CostOfSalesJAspServiceGrn)poSrGrn1).getAspServiceGrn().getGrnValue()
                        * ((CostOfSalesJAspServiceGrn)poSrGrn1).getSettlePercentage();
            }
        }
        if(getCostOfSalesJAspExtraworkGrnCollection()!=null){
            Object[] poEwGrn = getCostOfSalesJAspExtraworkGrnCollection().toArray();
            for (Object poEwGrn1 : poEwGrn) {
                grnIssued += ((CostOfSalesJAspExtraworkGrn)poEwGrn1).getAspExtraworkGrn().getGrnValue()
                        * ((CostOfSalesJAspExtraworkGrn)poEwGrn1).getSettlePercentage();
            }
        }
        
        return grnIssued;
    }
}
