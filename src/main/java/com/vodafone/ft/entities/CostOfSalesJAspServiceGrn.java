/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vodafone.ft.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author eamrela
 */
@Entity
@Table(name = "cost_of_sales_j_asp_service_grn")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CostOfSalesJAspServiceGrn.findAll", query = "SELECT c FROM CostOfSalesJAspServiceGrn c")
    , @NamedQuery(name = "CostOfSalesJAspServiceGrn.findByCosId", query = "SELECT c FROM CostOfSalesJAspServiceGrn c WHERE c.costOfSalesJAspServiceGrnPK.cosId = :cosId")
    , @NamedQuery(name = "CostOfSalesJAspServiceGrn.findByGrnId", query = "SELECT c FROM CostOfSalesJAspServiceGrn c WHERE c.costOfSalesJAspServiceGrnPK.grnId = :grnId")
    , @NamedQuery(name = "CostOfSalesJAspServiceGrn.findBySettlePercentage", query = "SELECT c FROM CostOfSalesJAspServiceGrn c WHERE c.settlePercentage = :settlePercentage")})
public class CostOfSalesJAspServiceGrn implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CostOfSalesJAspServiceGrnPK costOfSalesJAspServiceGrnPK;
    @Column(name = "settle_percentage")
    private Double settlePercentage;
    @JoinColumn(name = "grn_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AspServiceGrn aspServiceGrn;
    @JoinColumn(name = "cos_id", referencedColumnName = "cost_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CostOfSales costOfSales;

    public CostOfSalesJAspServiceGrn() {
    }

    public CostOfSalesJAspServiceGrn(CostOfSalesJAspServiceGrnPK costOfSalesJAspServiceGrnPK) {
        this.costOfSalesJAspServiceGrnPK = costOfSalesJAspServiceGrnPK;
    }

    public CostOfSalesJAspServiceGrn(long cosId, long grnId) {
        this.costOfSalesJAspServiceGrnPK = new CostOfSalesJAspServiceGrnPK(cosId, grnId);
    }

    public CostOfSalesJAspServiceGrnPK getCostOfSalesJAspServiceGrnPK() {
        return costOfSalesJAspServiceGrnPK;
    }

    public void setCostOfSalesJAspServiceGrnPK(CostOfSalesJAspServiceGrnPK costOfSalesJAspServiceGrnPK) {
        this.costOfSalesJAspServiceGrnPK = costOfSalesJAspServiceGrnPK;
    }

    public Double getSettlePercentage() {
        return settlePercentage;
    }

    public void setSettlePercentage(Double settlePercentage) {
        this.settlePercentage = settlePercentage;
    }

    public AspServiceGrn getAspServiceGrn() {
        return aspServiceGrn;
    }

    public void setAspServiceGrn(AspServiceGrn aspServiceGrn) {
        this.aspServiceGrn = aspServiceGrn;
    }

    public CostOfSales getCostOfSales() {
        return costOfSales;
    }

    public void setCostOfSales(CostOfSales costOfSales) {
        this.costOfSales = costOfSales;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (costOfSalesJAspServiceGrnPK != null ? costOfSalesJAspServiceGrnPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CostOfSalesJAspServiceGrn)) {
            return false;
        }
        CostOfSalesJAspServiceGrn other = (CostOfSalesJAspServiceGrn) object;
        if ((this.costOfSalesJAspServiceGrnPK == null && other.costOfSalesJAspServiceGrnPK != null) || (this.costOfSalesJAspServiceGrnPK != null && !this.costOfSalesJAspServiceGrnPK.equals(other.costOfSalesJAspServiceGrnPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vodafone.ft.entities.CostOfSalesJAspServiceGrn[ costOfSalesJAspServiceGrnPK=" + costOfSalesJAspServiceGrnPK + " ]";
    }
    
}
