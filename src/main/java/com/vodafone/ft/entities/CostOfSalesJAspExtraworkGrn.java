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
@Table(name = "cost_of_sales_j_asp_extrawork_grn")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CostOfSalesJAspExtraworkGrn.findAll", query = "SELECT c FROM CostOfSalesJAspExtraworkGrn c")
    , @NamedQuery(name = "CostOfSalesJAspExtraworkGrn.findByCosId", query = "SELECT c FROM CostOfSalesJAspExtraworkGrn c WHERE c.costOfSalesJAspExtraworkGrnPK.cosId = :cosId")
    , @NamedQuery(name = "CostOfSalesJAspExtraworkGrn.findByGrnId", query = "SELECT c FROM CostOfSalesJAspExtraworkGrn c WHERE c.costOfSalesJAspExtraworkGrnPK.grnId = :grnId")
    , @NamedQuery(name = "CostOfSalesJAspExtraworkGrn.findBySettlePercentage", query = "SELECT c FROM CostOfSalesJAspExtraworkGrn c WHERE c.settlePercentage = :settlePercentage")})
public class CostOfSalesJAspExtraworkGrn implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CostOfSalesJAspExtraworkGrnPK costOfSalesJAspExtraworkGrnPK;
    @Column(name = "settle_percentage")
    private Double settlePercentage;
    @JoinColumn(name = "grn_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private AspExtraworkGrn aspExtraworkGrn;
    @JoinColumn(name = "cos_id", referencedColumnName = "cost_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CostOfSales costOfSales;

    public CostOfSalesJAspExtraworkGrn() {
    }

    public CostOfSalesJAspExtraworkGrn(CostOfSalesJAspExtraworkGrnPK costOfSalesJAspExtraworkGrnPK) {
        this.costOfSalesJAspExtraworkGrnPK = costOfSalesJAspExtraworkGrnPK;
    }

    public CostOfSalesJAspExtraworkGrn(long cosId, long grnId) {
        this.costOfSalesJAspExtraworkGrnPK = new CostOfSalesJAspExtraworkGrnPK(cosId, grnId);
    }

    public CostOfSalesJAspExtraworkGrnPK getCostOfSalesJAspExtraworkGrnPK() {
        return costOfSalesJAspExtraworkGrnPK;
    }

    public void setCostOfSalesJAspExtraworkGrnPK(CostOfSalesJAspExtraworkGrnPK costOfSalesJAspExtraworkGrnPK) {
        this.costOfSalesJAspExtraworkGrnPK = costOfSalesJAspExtraworkGrnPK;
    }

    public Double getSettlePercentage() {
        return settlePercentage;
    }

    public void setSettlePercentage(Double settlePercentage) {
        this.settlePercentage = settlePercentage;
    }

    public AspExtraworkGrn getAspExtraworkGrn() {
        return aspExtraworkGrn;
    }

    public void setAspExtraworkGrn(AspExtraworkGrn aspExtraworkGrn) {
        this.aspExtraworkGrn = aspExtraworkGrn;
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
        hash += (costOfSalesJAspExtraworkGrnPK != null ? costOfSalesJAspExtraworkGrnPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CostOfSalesJAspExtraworkGrn)) {
            return false;
        }
        CostOfSalesJAspExtraworkGrn other = (CostOfSalesJAspExtraworkGrn) object;
        if ((this.costOfSalesJAspExtraworkGrnPK == null && other.costOfSalesJAspExtraworkGrnPK != null) || (this.costOfSalesJAspExtraworkGrnPK != null && !this.costOfSalesJAspExtraworkGrnPK.equals(other.costOfSalesJAspExtraworkGrnPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vodafone.ft.entities.CostOfSalesJAspExtraworkGrn[ costOfSalesJAspExtraworkGrnPK=" + costOfSalesJAspExtraworkGrnPK + " ]";
    }
    
}
