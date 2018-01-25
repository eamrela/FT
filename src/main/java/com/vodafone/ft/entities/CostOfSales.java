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
@Table(name = "cost_of_sales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CostOfSales.findAll", query = "SELECT c FROM CostOfSales c")
    , @NamedQuery(name = "CostOfSales.findByCostId", query = "SELECT c FROM CostOfSales c WHERE c.costId = :costId")
    , @NamedQuery(name = "CostOfSales.findByDescription", query = "SELECT c FROM CostOfSales c WHERE c.description = :description")
    , @NamedQuery(name = "CostOfSales.findByAspName", query = "SELECT c FROM CostOfSales c WHERE c.aspName = :aspName")
    , @NamedQuery(name = "CostOfSales.findByCostAmount", query = "SELECT c FROM CostOfSales c WHERE c.costAmount = :costAmount")})
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
    @Column(name = "category")
    private String category;
    @Size(max = 2147483647)
    @Column(name = "asp_name")
    private String aspName;
    @Column(name = "cost_amount")
    private Double costAmount;
    @Column(name = "original_amount")
    private Double originalAmount;

    @Transient
    private Double grnIssued;
    
    @JoinTable(name = "cost_of_sales_j_asp_service_grn", joinColumns = {
        @JoinColumn(name = "cos_id", referencedColumnName = "cost_id")}, inverseJoinColumns = {
        @JoinColumn(name = "grn_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<AspServiceGrn> aspServiceGrnCollection;
    @JoinTable(name = "cost_of_sales_j_asp_extrawork_grn", joinColumns = {
        @JoinColumn(name = "cos_id", referencedColumnName = "cost_id")}, inverseJoinColumns = {
        @JoinColumn(name = "grn_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<AspExtraworkGrn> aspExtraworkGrnCollection;

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

    

    @Transient
    public Double getGrnIssued() {
        grnIssued = 0.0;
        if(getAspServiceGrnCollection()!=null){
            Object[] poSrGrn = getAspServiceGrnCollection().toArray();
                    for (Object poSrGrn1 : poSrGrn) {
                        grnIssued += ((AspServiceGrn)poSrGrn1).getGrnValue();
                    }
        }
        if(getAspExtraworkGrnCollection()!=null){
            Object[] poEwGrn = getAspExtraworkGrnCollection().toArray();
            for (Object poEwGrn1 : poEwGrn) {
                grnIssued += ((AspExtraworkGrn)poEwGrn1).getGrnValue();
            }
        }
        
        return grnIssued;
    }

    @XmlTransient
    public Collection<AspServiceGrn> getAspServiceGrnCollection() {
        return aspServiceGrnCollection;
    }

    public void setAspServiceGrnCollection(Collection<AspServiceGrn> aspServiceGrnCollection) {
        this.aspServiceGrnCollection = aspServiceGrnCollection;
    }

    @XmlTransient
    public Collection<AspExtraworkGrn> getAspExtraworkGrnCollection() {
        return aspExtraworkGrnCollection;
    }

    public void setAspExtraworkGrnCollection(Collection<AspExtraworkGrn> aspExtraworkGrnCollection) {
        this.aspExtraworkGrnCollection = aspExtraworkGrnCollection;
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
        return "new3.CostOfSales[ costId=" + costId + " ]";
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
